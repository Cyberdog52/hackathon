using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;

namespace HackathonDotnetServer;

internal static class Endpoints
{
    public static WebApplication DefineEndpoints(this WebApplication app, List<PersonDetails> persons)
    {
        // GET which responds with a simple string array of all names
        app.MapGet("persons/names", () => persons.Select(p => p.Name));

        // GET which responds with an array of PersonDetail objects for all persons
        app.MapGet("persons/details", () => persons);

        // GET which responds with a PersonDetail object for a specified person (name parameter)
        app.MapGet("{name}/details", ([DefaultValue("Akua")] string name) =>
        {
            var person = persons.GetPerson(name);

            if (person is null)
            {
                return Results.NotFound();
            }

            return Results.Ok(person);
        });

        // GET which responds with the lucky number of a specified person (name parameter) using an anonymous type
        app.MapGet("{name}/luckynumber", ([DefaultValue("Megumin")] string name) =>
        {
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
        });

        // POST which adds an additional to do to a specified person, taken from the request JSON body
        app.MapPost("{name}/addtodo",
            ([FromRoute, DefaultValue("Dakunesu")] string name, [FromBody] Todo todo) =>
            {
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

                return Results.Ok();
            });

        return app;
    }

    public static WebApplication DefineSwaggerEndpointDocumentation(this WebApplication app)
    {
        if (app.Environment.IsDevelopment())
        {
            return app;
        }
        
        var endpoints = app.Services.GetRequiredService<IEnumerable<EndpointDataSource>>().ToList();
        
        // TODO add documentation
        
        return app;
    }

    private static PersonDetails? GetPerson(this IEnumerable<PersonDetails> persons, string name)
    {
        return persons.FirstOrDefault(p => string.Equals(name, p.Name, StringComparison.InvariantCultureIgnoreCase));
    }
}