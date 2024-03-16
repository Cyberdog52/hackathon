using OpenAI.Net.Models.OperationResult;
using OpenAI.Net.Models.Responses.Common;
using OpenAI.Net.Models.Responses;
using OpenAI.Net;
using Message = OpenAI.Net.Message;

namespace HackathonWebApi.Example
{
    public class ExampleService
    {
        private static readonly string[] possibleValues = [ "Example", "Beispiel", "Exemple", "Ejemplar" ];
        private static readonly Random random = new Random(DateTime.Now.Millisecond);

        private readonly Task<OpenAIHttpOperationResult<ChatCompletionResponse, ErrorResponse>> chatRequest;
        private readonly Task<OpenAIHttpOperationResult<ImageGenerationResponse, ErrorResponse>> imageRequest;

        public ExampleService(IOpenAIService openAIService)
        {
            chatRequest = openAIService.Chat.Get(Message.Create(ChatRoleType.User, "Write a message of the day for a software engineer."), options => {
                options.MaxTokens = 100;
            });

            imageRequest = openAIService.Images.Generate("Draw an image of a cat engineering software.", options => {
                options.N = 1;
                options.Size = "512x512";
            });
        }

        public ExampleDto GetExampleDto()
        {
            var randomInt = random.Next(possibleValues.Length);

            return new ExampleDto(possibleValues[randomInt], randomInt);
        }

        public Task<OpenAIHttpOperationResult<ChatCompletionResponse, ErrorResponse>> GetMotdContent()
        {
            return chatRequest;
        }

        public Task<OpenAIHttpOperationResult<ImageGenerationResponse, ErrorResponse>> GetMotdImageUrl()
        {
            return imageRequest;
        }
    }
}
