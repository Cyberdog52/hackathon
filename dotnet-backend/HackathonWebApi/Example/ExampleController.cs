using HackathonWebApi.Example;
using Microsoft.AspNetCore.Mvc;
using OpenAI.Net;

namespace HackathonWebApi.Controllers
{
    [ApiController]
    [Route("/api/Example")]
    public class ExampleController : ControllerBase
    {
        private readonly ExampleService exampleService;
        private readonly IOpenAIService openAIService;

        public ExampleController(
            ExampleService exampleService,
            IOpenAIService openAIService
            )
        {
            this.exampleService = exampleService;
            this.openAIService = openAIService;
        }

        [HttpGet]
        public ExampleDto GetExample()
        {
            return exampleService.GetExampleDto();
        }

        [HttpGet("motd")]
        public async Task<ActionResult<MotdDto>> GetMessageOfTheDay()
        {
            var response = await openAIService.TextCompletion.Get("Create a philosophical message of the day that will be displayed during a Hackathon. Questioning if the developer himself is conscious or not.");

            if (response.IsSuccess && response.Result is not null)
            {
                foreach (var result in response.Result.Choices)
                {
                    Console.WriteLine(result.Text);
                }

                return new MotdDto("hi");
            }
            else
            {
                return NoContent();
            }
        }
    }
}
