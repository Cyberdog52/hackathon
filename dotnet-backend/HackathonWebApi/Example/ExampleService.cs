namespace HackathonWebApi.Example
{
    public class ExampleService
    {
        private static readonly string[] possibleValues = [ "Example", "Beispiel", "Exemple", "Ejemplar" ];
        private static readonly Random random = new Random(DateTime.Now.Millisecond);

        public ExampleDto GetExampleDto()
        {
            var randomInt = random.Next(possibleValues.Length);

            return new ExampleDto(possibleValues[randomInt], randomInt);
        }
    }
}
