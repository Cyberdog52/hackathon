
using System.IO;
using System.Text;

namespace HackathonDotnetServer;

internal sealed class Graphs
{
    public static List<GraphData> CreateDemoData() => Directory.EnumerateFiles("./wwwroot/mermaid", "*.mms", SearchOption.TopDirectoryOnly)
        .Select(x =>
        {
            var fileName = Path.GetFileNameWithoutExtension(x);
            var name = fileName.ToUpper()[0] + fileName.Substring(1);
            var script = File.ReadAllText(x, Encoding.UTF8);
            return new GraphData(name, script);
        })
        .ToList();
}

internal sealed record GraphData(string Name, string MermaidScript);
