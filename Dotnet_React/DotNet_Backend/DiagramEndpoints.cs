
using System.ComponentModel;
using Swashbuckle.AspNetCore.Annotations;

namespace HackathonDotnetServer;

internal static class DiagramEndpoints
{
    public static WebApplication DefineDiagramEndpoints(this WebApplication app, List<DiagramData> diagrams)
    {




        // ----------------------------------------------------------
        // GET which responds with a simple string array of all names
        //-----------------------------------------------------------

        app.MapGet("diagrams/names",

        [SwaggerOperation(Summary = "Gets the list of all available diagrams", Description = "The names of all available diagrams are returned in JSON as an array of strings.", Tags = ["Diagrams"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well")]
        () => diagrams.Select(p => p.Name));




        // ------------------------------------------------------------------------
        // GET which responds with an array of DiagramData objects for all diagrams
        //-------------------------------------------------------------------------

        app.MapGet("diagrams/all",
        [SwaggerOperation(Summary = "Gets all available diagrams", Description = $"All diagrams are returned in JSON as an array of {nameof(DiagramData)} objects.", Tags = ["Diagrams"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well")]
        () => diagrams);




        // -------------------------------------------------------------------------------------
        // GET which responds with a PersonDetail object for a specified person (name parameter)
        // -------------------------------------------------------------------------------------

        app.MapGet("diagrams/{name}/data",
        [SwaggerOperation(Summary = "Gets a specified diagram based on its name", Description = $"The data of a specified diagram are returned in JSON as a single {nameof(DiagramData)} object.", Tags = ["Diagrams"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well", typeof(PersonDetails))]
        [SwaggerResponse(StatusCodes.Status404NotFound, "The specified name does not exist")]
        (
            [DefaultValue("Flowchart"),
            SwaggerParameter("The name of the diagram", Required = true)]
            string name) =>
            {
                if (string.IsNullOrWhiteSpace(name))
                {
                    return Results.BadRequest("Invalid or no name specified");
                }

                var diagram = diagrams.GetDiagram(name);

                if (diagram is null)
                {
                    return Results.NotFound("The specified name does not exist");
                }

                return Results.Ok(diagram);
            });




        return app;
    }




    private static DiagramData? GetDiagram(this IEnumerable<DiagramData> diagrams, string name)
    {
        return diagrams.FirstOrDefault(p => string.Equals(name, p.Name, StringComparison.InvariantCultureIgnoreCase));
    }
}