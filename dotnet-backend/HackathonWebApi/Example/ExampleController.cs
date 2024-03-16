using HackathonWebApi.Example;
using Microsoft.AspNetCore.Mvc;

namespace HackathonWebApi.Controllers
{
    [ApiController]
    [Route("/api/Example")]
    public class ExampleController : ControllerBase
    {
        private readonly ILogger<ExampleController> logger;
        private readonly ExampleService exampleService;

        public ExampleController(
            ILogger<ExampleController> logger,
            ExampleService exampleService
            )
        {
            this.logger = logger;
            this.exampleService = exampleService;
        }

        [HttpGet]
        public ExampleDto GetExample()
        {
            return exampleService.GetExampleDto();
        }

        [HttpGet("motd")]
        public async Task<ActionResult<MotdDto>> GetMessageOfTheDay()
        {
            var chatResponseRequest = exampleService.GetMotdContent();
            var imageResponseRequest = exampleService.GetMotdImageUrl();

            await Task.WhenAll(chatResponseRequest, imageResponseRequest);

            var chatResponse = chatResponseRequest.Result;
            var imageResponse = imageResponseRequest.Result;

            var motdContent = string.Empty;
            if (chatResponse.IsSuccess && chatResponse.Result is not null)
            {
                logger.LogInformation($"Tokens used for chat: {chatResponse.Result.Usage.Total_tokens} / 100.");
                motdContent = chatResponse.Result.Choices.First().Message.Content;
            }
            else
            {
                logger.LogError(chatResponse.Exception, "Could not get message of the day.");
            }

            string? imageUrl = null;
            if (imageResponse.IsSuccess && imageResponse.Result is not null)
            {
                imageUrl = imageResponse.Result.Data.First().Url;
            }
            else
            {
                logger.LogError(imageResponse.Exception, "Could not get message of the day.");
            }

            return new MotdDto(motdContent, imageUrl);
        }
    }
}
