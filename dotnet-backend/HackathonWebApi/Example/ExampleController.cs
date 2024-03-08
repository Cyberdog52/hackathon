using HackathonWebApi.Example;
using Microsoft.AspNetCore.Mvc;
using OpenAI.Net;
using OpenAI.Net.Models.Requests;

namespace HackathonWebApi.Controllers
{
    [ApiController]
    [Route("/api/Example")]
    public class ExampleController : ControllerBase
    {
        private readonly ILogger<ExampleController> logger;
        private readonly ExampleService exampleService;
        private readonly IOpenAIService openAIService;

        public ExampleController(
            ILogger<ExampleController> logger,
            ExampleService exampleService,
            IOpenAIService openAIService
            )
        {
            this.logger = logger;
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
            var chatResponse = await openAIService.Chat.Get(Message.Create(ChatRoleType.User, "Create a philosophical message of the day that will be displayed during a Hackathon. Questioning if the developer himself is conscious or not."), options => {
                options.MaxTokens = 100;
            });

            if (chatResponse.IsSuccess && chatResponse.Result is not null)
            {
                logger.LogInformation($"Tokens used for chat: {chatResponse.Result.Usage.Total_tokens} / 100.");

                var content = chatResponse.Result.Choices.First().Message.Content;

                var imageResponse = await openAIService.Images.Generate(content, options => {
                    options.N = 1;
                    options.Size = "512x512";
                });

                if (imageResponse.IsSuccess && imageResponse.Result is not null)
                {
                    return new MotdDto(content, imageResponse.Result.Data.First().Url);
                }

                return new MotdDto(content, null);
            }
            else
            {
                logger.LogError(chatResponse.Exception, "Could not get message of the day.");
                return NoContent();
            }
        }
    }
}
