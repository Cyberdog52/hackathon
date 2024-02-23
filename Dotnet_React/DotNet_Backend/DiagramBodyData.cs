
using System.Text;
using System.Text.Json.Serialization;
using Swashbuckle.AspNetCore.Annotations;




namespace HackathonDotnetServer;




internal static class Diagrams
{
    public static List<DiagramData> CreateDemoData() => Directory.EnumerateFiles("./wwwroot/diagrams", "*.*", SearchOption.TopDirectoryOnly)
        .Select(x =>
        {
            var path = x.Replace('\\', '/'); // WINDOWS!!! >:(
            var renderer = GetRendererFromFileExtension(Path.GetExtension(path));

            if(renderer is null)
            {
                return null;
            }

            var fileName = Path.GetFileNameWithoutExtension(path);
            var name = fileName.ToUpper()[0] + fileName.Substring(1);
            var script = File.ReadAllText(path, Encoding.UTF8);
            return new DiagramData(name, renderer.Value, script);
        })
        .Where(y => y != null)
        .Select(z => z!)
        .ToList();

        private static DiagramRenderer? GetRendererFromFileExtension(string? extension)
        {
            if(string.IsNullOrWhiteSpace(extension))
            {
                return null;
            }

            return extension switch
            {
                ".mms" => DiagramRenderer.Mermaid,
                _ => null,
            };
        }
}




[SwaggerSchema("Specifies a diagram renderer")]
[JsonConverter(typeof(JsonStringEnumConverter))]
internal enum DiagramRenderer
{
    Unspecified = 0,

    Mermaid = 1,
}



[SwaggerSchema("Data for a single diagram")]
internal sealed record DiagramData
{
    public DiagramData(string name, DiagramRenderer renderer, string script)
    {
        Name = name;
        Type = renderer;
        Script = script;
    }

    [SwaggerSchema(description: "The name of the diagram")]
    public string Name { get; init; }

    [SwaggerSchema(description: "The renderer to use for the diagram script")]
    public DiagramRenderer Type { get; init; }

    [SwaggerSchema(description: "The diagram script")]
    public string Script { get; init; }
}
