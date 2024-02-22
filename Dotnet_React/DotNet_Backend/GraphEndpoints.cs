
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

    private static GraphData? GetGraph(this IEnumerable<GraphData> graphs, string name)
    {
        return graphs.FirstOrDefault(p => string.Equals(name, p.Name, StringComparison.InvariantCultureIgnoreCase));
    }
}