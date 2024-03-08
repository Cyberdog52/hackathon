using HackathonWebApi.Example;
using Microsoft.AspNetCore.Mvc;

namespace HackathonWebApi.Controllers
{
    [ApiController]
    [Route("/api/Example")]
    public class ExampleController : ControllerBase
    {
        private readonly ExampleService exampleService;

        public ExampleController(ExampleService exampleService)
        {
            this.exampleService = exampleService;
        }

        [HttpGet]
        public ExampleDto GetExample()
        {
            return exampleService.GetExampleDto();
        }
    }
}
