
using System.Runtime.Serialization;
using System.Text.Json;
using Microsoft.Extensions.ObjectPool;
using Swashbuckle.AspNetCore.Annotations;




namespace HackathonDotnetServer;




internal sealed class JokeProvider
{
    private readonly IHttpClientFactory factory;
    private readonly ILogger<JokeProvider> logger;

    private readonly string jokeApiUrl = "https://official-joke-api.appspot.com/jokes/random";

    public JokeProvider(IHttpClientFactory factory, ILogger<JokeProvider> logger)
    {
        this.factory = factory;
        this.logger = logger;
    }

    public async Task<Joke?> GetRandomJoke(CancellationToken ct)
    {
        var client = factory.CreateClient();
        Stream contentStream;

        try
        {
            var response = await client.GetAsync(jokeApiUrl, HttpCompletionOption.ResponseHeadersRead, ct);
            response.EnsureSuccessStatusCode();
            contentStream = response.Content.ReadAsStream(ct);
        }
        catch (Exception ex) when (ex is HttpRequestException || ex is TaskCanceledException)
        {
            logger.LogError(ex, $"Fetching random joke from {jokeApiUrl} failed.");
            return null;
        }

        try
        {
            var joke = await DeserializeJokeAsync(contentStream, new { setup = "", punchline = "" }, ct);
            return new Joke(joke!.setup, joke.punchline);
        }
        catch(Exception ex) when (ex is JsonException || ex is NotSupportedException)
        {
            logger.LogError(ex, "Failed to deserialize JSON body.");
            return null;
        }
    }

    private ValueTask<TObject?> DeserializeJokeAsync<TObject>(Stream responseStream, TObject type, CancellationToken ct)
    {
        return JsonSerializer.DeserializeAsync<TObject>(responseStream, cancellationToken: ct);
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
