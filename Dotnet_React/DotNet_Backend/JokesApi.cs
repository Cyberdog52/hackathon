
namespace HackathonDotnetServer;

internal sealed class Jokes
{
    public static Joke GetRandomJoke()
    {
        // TODO implement using RestSharp
        return new Joke("", "");
    }
}

internal sealed record Joke(string Setup, string Punchline);
