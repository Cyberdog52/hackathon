
using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;

namespace HackathonDotnetServer;

internal static class JokesEndpoints
{
    public static WebApplication DefineJokesEndpoints(this WebApplication app)
    {
        // TODO add endpoints

        return app;
    }

    public static WebApplication DefineJokesEndpointsSwaggerDocumentation(this WebApplication app)
    {
        if (app.Environment.IsDevelopment())
        {
            return app;
        }

        var endpoints = app.Services.GetRequiredService<IEnumerable<EndpointDataSource>>().ToList();

        // TODO add documentation

        return app;
    }
}