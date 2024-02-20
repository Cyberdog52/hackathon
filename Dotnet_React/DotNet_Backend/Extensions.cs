
using Microsoft.AspNetCore.Diagnostics;
using Microsoft.Extensions.Logging.Console;
using Microsoft.OpenApi.Models;

namespace HackathonDotnetServer;



internal static class Extensions
{
    public static WebApplicationBuilder AddKestrel(this WebApplicationBuilder builder)
    {
        builder.WebHost.UseKestrel();
        
        return builder;
    }
    
    public static WebApplicationBuilder AddLoggingServices(this WebApplicationBuilder builder)
    {
        builder.Logging.AddSimpleConsole(config =>
        {
            config.SingleLine = true;
            config.TimestampFormat = "HH-mm-ss-fff";
            config.ColorBehavior = LoggerColorBehavior.Enabled;
        });
        
        return builder;
    }
    
    public static WebApplicationBuilder AddSwaggerServices(this WebApplicationBuilder builder, string title, string description, string version = "v1")
    {
        builder.Services.AddEndpointsApiExplorer();
        builder.Services.AddSwaggerGen(config =>
        {
            config.EnableAnnotations();
            config.SupportNonNullableReferenceTypes();
            config.SwaggerDoc(version, new OpenApiInfo()
            {
                Title = title,
                Description = description,
                Version = version,
            });
        });
        
        return builder;
    }

    public static WebApplicationBuilder AddCorsServicesAndAllowAllOrigins(this WebApplicationBuilder builder, string profileName = "cors-allow-all")
    {
        builder.Services.AddCors(options =>
        {
            options.AddPolicy(name: "MyAllowedOrigins", policy =>
            {
                policy.AllowAnyOrigin();
                policy.AllowAnyMethod();
            });
        });

        return builder;
    }

    public static WebApplication UseDefaultCors(this WebApplication app, string profileName = "cors-allow-all")
    {
        app.UseCors();
        return app;
    }

    public static WebApplication UseExceptionHandler(this WebApplication app, string prodEnvErrorMessage = "Error!")
    {
        app.UseExceptionHandler(config => config.Run(async context =>
        {
            await context.Response.WriteAsJsonAsync(new
            {
                Request = context.Request.Path.Value,
                Error = app.Environment.IsDevelopment() ?
                    context.Features.Get<IExceptionHandlerPathFeature>()?.Error.Message ?? prodEnvErrorMessage :
                    prodEnvErrorMessage,
            });
        }));
        
        return app;
    }
    
    public static WebApplication UseSwaggerUi(this WebApplication app)
    {
        if (app.Environment.IsDevelopment())
        {
            app.UseSwagger();
            app.UseSwaggerUI();
        }
        
        return app;
    }
}