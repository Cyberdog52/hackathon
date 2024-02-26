
using Swashbuckle.AspNetCore.Annotations;




namespace HackathonDotnetServer;




internal static class JokesEndpoints
{
    public static WebApplication DefineJokesEndpoints(this WebApplication app)
    {
        // --------------------------------------------------------------
        // GET which responds with a random joke in JSON as a Joke object
        //---------------------------------------------------------------

        app.MapGet("jokes/random",

        [SwaggerOperation(Summary = "Gets a random joke", Description = $"The random joke as a {nameof(Joke)} object.", Tags = ["Jokes"])]
        [SwaggerResponse(StatusCodes.Status200OK, "Everything went well")]
        (CancellationToken ct) => app.Services.GetRequiredService<JokeProvider>().GetRandomJoke(ct));

        return app;
    }
}
