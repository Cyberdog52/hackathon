
using HackathonDotnetServer;




const string appName = "Hackathon .NET demo and template";
const string appDescription = "This is the backend of the combined .NET / React demo and template.<br/>See <a href=\"https://github.com/Cyberdog52/hackathon/blob/dotnet_react/Dotnet_React/DotNet_Backend/Readme.md\" target=\"_blank\">Readme</a> for more details.";




//------------------------------------------------------------------------------------
// Create the web application builder, configure the web application, and add services
//------------------------------------------------------------------------------------

var builder = WebApplication.CreateBuilder();

builder.AddKestrel();
builder.AddLoggingServices();
builder.AddJokeProvider();
builder.AddSwaggerServices(appName, appDescription);

builder.ConfigureExceptionHandling();
builder.ConfigureDefaultCorsToAllowAllOrigins();




//------------------------------------------------------------------------------------------------
// Build the actual web application and specify the used middleware which processes HTTPS requests
//------------------------------------------------------------------------------------------------

var app = builder.Build();

app.UseDefaultCors();
app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseSwaggerUi();




//----------------------
// Create some demo data
//----------------------

var persons = Persons.CreateDemoData();
var diagrams = Diagrams.CreateDemoData();




//--------------------------
// Define the REST endpoints
//--------------------------

app.DefinePersonsEndpoints(persons);
app.DefineDiagramEndpoints(diagrams);
app.DefineJokesEndpoints();




//------------------------------------------------------------
// Run the web application and begin processing HTTPS requests
//------------------------------------------------------------

app.Run();
