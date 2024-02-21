
using System.ComponentModel;
using Swashbuckle.AspNetCore.Annotations;



namespace HackathonDotnetServer;



internal static class PersonsEndpoints
{
    public static WebApplication DefinePersonsEndpoints(this WebApplication app, List<PersonDetails> persons)
    {
        // ----------------------------------------------------------
        // GET which responds with a simple string array of all names
        //-----------------------------------------------------------

        app.MapGet("persons/names", () => persons.Select(p => p.Name))
        .WithTags("Persons")
        .WithName("persons_names_all")
        .WithSummary("Gets the list of all available persons.")
        .WithDescription("The names of all available persons are returned in JSON as an array of strings.")
        .Produces(StatusCodes.Status200OK, typeof(string[]))
        .ProducesProblem(StatusCodes.Status404NotFound)
        .WithOpenApi();



        // ------------------------------------------------------------------------
        // GET which responds with an array of PersonDetail objects for all persons
        // ------------------------------------------------------------------------

        app.MapGet("persons/details", () => persons)
        .WithTags("Persons")
        .WithName("persons_details_all")
        .WithSummary("Gets the details of all available persons.")
        .WithDescription("The details of all available persons are returned in JSON as an array of PersonDetails object.")
        .Produces(StatusCodes.Status200OK, typeof(PersonDetails[]))
        .ProducesProblem(StatusCodes.Status404NotFound)
        .WithOpenApi();



        // -------------------------------------------------------------------------------------
        // GET which responds with a PersonDetail object for a specified person (name parameter)
        // -------------------------------------------------------------------------------------

        app.MapGet("persons/{name}/details", (   // --- here comes a delegate which produces the response

            // --- a single parameter as we know it... but with attributes
            [DefaultValue("Akua"),                                          // Default value for Swagger UIs "try it out"
            SwaggerParameter("The name of the person.", Required = true)]   // Description of the parameter
            string name) =>

        {
            if(string.IsNullOrWhiteSpace(name))
            {
                return Results.BadRequest();
            }

            var person = persons.GetPerson(name);

            if (person is null)
            {
                return Results.NotFound();
            }

            return Results.Ok(person);
        })
        .WithTags("Persons")                                                                                            // Groups all endpoints in the Swagger UI under the same name
        .WithName("persons_details_one")                                                                                // Unique name of that endpoint
        .WithSummary("Gets the details of a person.")                                                                   // Short summary of the endpoint
        .WithDescription("The details of a specified person are returned in JSON as a single PersonDetails object.")    // Describes the endpoint with more details
        .Produces(StatusCodes.Status200OK, typeof(PersonDetails))                                                       // Lists the possible responses in good cases
        .ProducesProblem(StatusCodes.Status400BadRequest)                                                               // Lists the possible responses in bad cases
        .ProducesProblem(StatusCodes.Status404NotFound)                                                                 // Lists the possible responses in bad cases
        .WithOpenApi();                                                                                                 // Emits the documentation as OpenAPI data



        // -------------------------------------------------------------------------------------------------------
        // GET which responds with the lucky number of a specified person (name parameter) using an anonymous type
        // -------------------------------------------------------------------------------------------------------

        app.MapGet("persons/{name}/luckynumber", (
            [DefaultValue("Megumin"), // best character in that show!
            SwaggerParameter("The name of the person.", Required = true)]
            string name) =>
        {
            if (string.IsNullOrWhiteSpace(name))
            {
                return Results.BadRequest();
            }

            var person = persons.GetPerson(name);

            if (person is null)
            {
                return Results.NotFound();
            }

            return Results.Ok(new
            {
                Name = name,
                LuckyNumber = Random.Shared.Next(100),
            });
        })
        .WithTags("Persons")
        .WithName("persons_luckynumber_one")
        .WithSummary("Gets the lucky number of a person.")
        .WithDescription("The lucky number of a specified person are returned in JSON with a name and a luckynumber value.")
        .Produces(StatusCodes.Status200OK, typeof(PersonDetails[]))
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .ProducesProblem(StatusCodes.Status404NotFound)
        .WithOpenApi();



        // -------------------------------------------------------------------------------------------
        // POST which adds an additional to do to a specified person, taken from the request JSON body
        // -------------------------------------------------------------------------------------------

        app.MapPost("persons/{name}/addtodo",
            ([DefaultValue("Kazuma"),
            SwaggerParameter("The name of the person.", Required = true)]
            string name,
            [DefaultValue("{\"task\": \"123\", \"priority\": \"99\"}"),
            SwaggerParameter("The data of the new TODO to add.", Required = true)]
            Todo todo) =>
            {
                if (string.IsNullOrWhiteSpace(name))
                {
                    return Results.BadRequest();
                }

                var person = persons.GetPerson(name);

                if (person is null)
                {
                    return Results.NotFound();
                }

                var todoList = person.Todos ?? new List<Todo>();
                todoList.Add(todo);

                var updatedPerson = person with { Todos = todoList };
                persons.Add(updatedPerson);
                persons.Remove(person); // Order isn't preserved

                return Results.Ok(updatedPerson);
            })
        .WithTags("Persons")
        .WithName("persons_todo_add")
        .WithSummary("Adds a new TODO to a person.")
        .WithDescription("The TODO must be provided as JSON body using an Todo object.")
        .Produces(StatusCodes.Status200OK, typeof(PersonDetails[]))
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .ProducesProblem(StatusCodes.Status404NotFound)
        .WithOpenApi();

        return app;
    }

    private static PersonDetails? GetPerson(this IEnumerable<PersonDetails> persons, string name)
    {
        return persons.FirstOrDefault(p => string.Equals(name, p.Name, StringComparison.InvariantCultureIgnoreCase));
    }
}