
using System.Net.Http.Json;
using System.Text.Json.Nodes;
using Swashbuckle.AspNetCore.Annotations;




namespace HackathonDotnetServer;




internal sealed class JokeProvider
{
    private readonly IHttpClientFactory factory;

    public JokeProvider(IHttpClientFactory factory)
    {
        this.factory = factory;
    }

    public async Task<Joke> GetRandomJoke()
    {
        var client = factory.CreateClient();
        var response = await client.GetAsync("https://official-joke-api.appspot.com/jokes/random");
        var content = await response.Content.ReadAsStringAsync();
        dynamic data = JsonValue.Parse();
        //var data = System.Text.Json.JsonSerializer.Deserialize<dynamic>(content);
        return new Joke(data!.setup, data!.punchline);
    }
}




[SwaggerSchema("A single joke")]
internal sealed record Joke
{
    public Joke(string setup, string punchline)
    {
        Setup = setup;
        Punchline = punchline;
    }

    [SwaggerSchema(description: "What?!")]
    public string Setup { get; init; }

    [SwaggerSchema(description: "Ha ha ha")]
    public string Punchline { get; init; }
};
