
using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;

namespace HackathonDotnetServer;

internal static class GraphEndpoints
{
    public static WebApplication DefineGraphsEndpoints(this WebApplication app, List<GraphData> graphs)
    {
        // TODO add endpoints

        return app;
    }

    public static WebApplication DefineGraphsEndpointsSwaggerDocumentation(this WebApplication app)
    {
        if (app.Environment.IsDevelopment())
        {
            return app;
        }

        var endpoints = app.Services.GetRequiredService<IEnumerable<EndpointDataSource>>().ToList();

        // TODO add documentation

        return app;
    }

    private static PersonDetails? GetGraph(this IEnumerable<PersonDetails> persons, string name)
    {
        return persons.FirstOrDefault(p => string.Equals(name, p.Name, StringComparison.InvariantCultureIgnoreCase));
    }
}