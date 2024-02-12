
using HackathonDotnetServer;



const string appName = "Hackathon .NET Server Template";
const string appDescription = "This is the template for the .NET server part of the Hackathon.<br><br>" +
                              "It's a REST WebAPI implemented using ASP.NET Core Minimal API, .NET 8.0, and C# 12.<br><br>" +
                              "As an example, this WebAPI provides a list of persons and some details about them.";



//------------------------------------------------------------------------------------
// Create the web application builder, configure the web application, and add services
//------------------------------------------------------------------------------------

var builder = WebApplication.CreateBuilder();
builder.AddKestrel();
builder.AddLoggingServices();
builder.AddSwaggerServices(appName, appDescription);
builder.Services.AddCors(options => {
    options.AddPolicy(name: "MyAllowedOrigins", policy => {
        policy.AllowAnyOrigin();
        policy.AllowAnyMethod();
    });
});



//--------------------------------------------------------------------------------------------------
// Build the actual web application and configure its used middleware which processes HTTPS requests
//--------------------------------------------------------------------------------------------------

var app = builder.Build();
app.UseCors("MyAllowedOrigins");
app.UseExceptionHandler();
app.UseHttpsRedirection();
app.UseSwaggerUi();



//----------------------
// Create some demo data
//----------------------

var persons = Persons.CreateDemoData();



//--------------------------
// Define the REST endpoints
//--------------------------

app.DefineEndpoints(persons);
app.DefineSwaggerEndpointDocumentation();



//------------------------------------------------------------
// Run the web application and begin processing HTTPS requests
//------------------------------------------------------------

app.Run();
