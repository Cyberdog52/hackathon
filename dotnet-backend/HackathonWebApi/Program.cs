
using HackathonWebApi.Example;
using OpenAI.Net;

namespace HackathonWebApi
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddControllers();
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            builder.Services.AddOpenAIServices(options => {
                var t = builder.Configuration?["OpenAi:ApiKey"];
                options.ApiKey = builder.Configuration?["OpenAi:ApiKey"] ?? string.Empty;
            });

            builder.Services.AddScoped<ExampleService>();

            var app = builder.Build();

            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseAuthorization();
            app.MapControllers();

            app.Run();
        }
    }
}
