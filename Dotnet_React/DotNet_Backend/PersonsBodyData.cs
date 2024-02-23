
using Swashbuckle.AspNetCore.Annotations;



namespace HackathonDotnetServer;



internal static class Persons
{
    public static List<PersonDetails> CreateDemoData()
    {
        return
        [
            new PersonDetails("Kazuma", 20, "Adventurer", "/persons/profile_kazuma.png", "https://en.wikipedia.org/wiki/Kazuma_Satou"),
            new PersonDetails("Akua", -1, "Goddess of water", "/persons/profile_akua.png", "https://en.wikipedia.org/wiki/List_of_KonoSuba_characters#Aqua"),
            new PersonDetails("Megumin", 21, "Crimson Demon", "/persons/profile_megumin.png", "https://en.wikipedia.org/wiki/Megumin"),
            new PersonDetails("Dakunesu", 31, "Crusader", "/persons/profile_dakunesu.png", "https://en.wikipedia.org/wiki/List_of_KonoSuba_characters#Darkness")
        ];
    }
}



[SwaggerSchema("Details about a person")]
internal sealed record PersonDetails
{
    public PersonDetails(string name, int age, string role, string imageUrl, string bioUrl)
    {
        Name = name;
        Age = age;
        Role = role;
        ImageUrl = imageUrl;
        BioUrl = bioUrl;

        Todos = new List<PersonTodo>();
    }

    [SwaggerSchema(description: "The persons name")]
    public string Name { get; init; }

    [SwaggerSchema(description: "The persons age")]
    public int Age { get; init; }

    [SwaggerSchema(description: "The persons role in the mission of defeating the demon king")]
    public string Role { get; init; }

    [SwaggerSchema(description: "The relative URL to the persons profile picture")]
    public string ImageUrl { get; init; }

    [SwaggerSchema(description: "The absolute URL to the persons biography")]
    public string BioUrl { get; init; }

    [SwaggerSchema(description: "A list of menial tasks this person has to do")]
    public List<PersonTodo>? Todos { get; set; }
}



[SwaggerSchema("A single TODO item")]
internal sealed record PersonTodo
{
    public PersonTodo(string task, int priority)
    {
        Task = task;
        Priority = priority;
    }

    [SwaggerSchema(description: "Description of the task")]
    public string Task { get; init; }

    [SwaggerSchema(description: "Priority of the task")]
    public int Priority { get; init; }
}
