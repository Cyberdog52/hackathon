
namespace HackathonDotnetServer;

internal sealed class Persons
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

internal sealed record PersonDetails (string Name, int Age, string Role, string ImageUrl, string BioUrl, List<Todo>? Todos = null);

internal sealed record Todo (string Task, int Priority);
