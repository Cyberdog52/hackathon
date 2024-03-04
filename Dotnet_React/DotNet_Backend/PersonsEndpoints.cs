
namespace HackathonDotnetServer;




internal static class PersonsEndpoints
{
    public static WebApplication DefinePersonsEndpoints(this WebApplication app, List<PersonDetails> persons)
    {




        // ----------------------------------------------------------
        // GET which responds with a simple string array of all names
        //-----------------------------------------------------------

        app.MapGet("persons/names",

        [SwaggerOperation(Summary = "Gets the list of all available persons", Description = "The names of all available persons are returned in JSON as an array of strings.", Tags = ["Persons"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well", typeof(string[]))]
        () => persons.Select(p => p.Name));




        // ------------------------------------------------------------------------
        // GET which responds with an array of PersonDetail objects for all persons
        // ------------------------------------------------------------------------

        app.MapGet("persons/details",

        [SwaggerOperation(Summary = "Gets the details of all available persons", Description = $"The details of all available persons are returned in JSON as an array of {nameof(PersonDetails)} objects.", Tags = ["Persons"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well", typeof(PersonDetails[]))]
        () => persons);




        // -------------------------------------------------------------------------------------
        // GET which responds with a PersonDetail object for a specified person (name parameter)
        // -------------------------------------------------------------------------------------

        app.MapGet("persons/{name}/details",

        [SwaggerOperation(Summary = "Gets the details of a person", Description = $"The details of a specified person are returned in JSON as a single {nameof(PersonDetails)} object.", Tags = ["Persons"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well", typeof(PersonDetails))]
        [SwaggerResponse(StatusCodes.Status400BadRequest, "Invalid or no name specified")]
        [SwaggerResponse(StatusCodes.Status404NotFound, "The specified name does not exist")]
        (
            [DefaultValue("Akua"), // ("Wonder of nature!")
            SwaggerParameter("The name of the person", Required = true)]
            string name) =>
            {
                if(string.IsNullOrWhiteSpace(name))
                {
                    return Results.BadRequest("Invalid or no name specified");
                }

                var person = persons.GetPerson(name);

                if (person is null)
                {
                    return Results.NotFound("The specified name does not exist");
                }

                return Results.Ok(person);
            });




        // -------------------------------------------------------------------------------------------------------
        // GET which responds with the lucky number of a specified person (name parameter) using an anonymous type
        // -------------------------------------------------------------------------------------------------------

        app.MapGet("persons/{name}/luckynumber",

        [SwaggerOperation(Summary = "Gets the lucky number of a person", Description = $"The lucky number of a specified person is returned in JSON with a name and a luckynumber value.<br/><br/>IMPORTANT: There is no Swagger/OpenAPI schema available because we use an anonymous type.<br/>The actual JSON data provided by the WebAPI contains the proper names.", Tags = ["Persons"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well")]
        [SwaggerResponse(StatusCodes.Status400BadRequest, "Invalid or no name specified")]
        [SwaggerResponse(StatusCodes.Status404NotFound, "The specified name does not exist")]
        (
            [DefaultValue("Megumin"), // (best character in that show because she's probably the most crazy one)
            SwaggerParameter("The name of the person.", Required = true)]
            string name) =>
            {
                if (string.IsNullOrWhiteSpace(name))
                {
                    return Results.BadRequest("Invalid or no name specified");
                }

                var person = persons.GetPerson(name);

                if (person is null)
                {
                    return Results.NotFound("The specified name does not exist");
                }

                return Results.Ok(new
                {
                    Name = name,
                    LuckyNumber = Random.Shared.Next(100),
                });
            }
        );




        // --------------------------------------------------------------------------------------
        // POST which adds an additional to do to a specified person, taken from the request body
        // --------------------------------------------------------------------------------------

        app.MapPost("persons/{name}/addtodo",

        [SwaggerOperation(Summary = "Adds a new TODO to a person", Description = $"The TODO must be provided as JSON body using a {nameof(PersonTodo)} object.", Tags = ["Persons"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well", typeof(PersonDetails))]
        [SwaggerResponse(StatusCodes.Status400BadRequest, $"Invalid or no name specified -or- Invalid or no JSON body")]
        [SwaggerResponse(StatusCodes.Status404NotFound, "The specified name does not exist")]
        (
            [Required]
            [DefaultValue("Dakunesu"), // (she also has some... interesting... features...)
            SwaggerParameter("The name of the person.", Required = true)]
            string name,
            [Required]
            [SwaggerParameter("The data of the new TODO to add", Required = true)]
            PersonTodo? todo) =>
            {
                if (string.IsNullOrWhiteSpace(name))
                {
                    return Results.BadRequest("Invalid or no name specified");
                }

                if(todo is null || string.IsNullOrWhiteSpace(todo.Task) || (todo.Priority < 0))
                {
                    return Results.BadRequest("Invalid or no JSON body");
                }

                var person = persons.GetPerson(name);

                if (person is null)
                {
                    return Results.NotFound("The specified name does not exist");
                }

                var todoList = new List<PersonTodo>(person.Todos ?? []);
                todoList.Add(todo!);

                var updatedPerson = person with { Todos = todoList };
                persons.Add(updatedPerson);
                persons.Remove(person); // Order isn't preserved

                return Results.Ok(updatedPerson);
            }
        );




        return app;
    }




    private static PersonDetails? GetPerson(this IEnumerable<PersonDetails> persons, string name)
    {
        return persons.FirstOrDefault(p => string.Equals(name, p.Name, StringComparison.InvariantCultureIgnoreCase));
    }
}