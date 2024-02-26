# Hackathon .NET demo and template





## Introduction



**This is the .NET demo and template for the Hackathon**
➡️ *hackathon/Dotnet_React/DotNet_Backend/*

**It's the backend part of the combined .NET / React demo and template**
➡️ *hackathon/Dotnet_React/*

The .NET backend and the React frontend are two separated projects with no build-time dependencies.
At runtime, the frontend depends on the WebAPI provided by the backend.

The backends WebAPI provides a list of persons (names, details, etc.) and shows some basics about how to configure the HTTP request pipeline, use endpoints, and transmit JSON data using REST (GET, POST).

The frontend uses that WebAPI to retrieve said person names and details to display them in a list.

Frontend and backend are not only a boilerplate template but also a demo which can be set up and used separately.



> [!IMPORTANT]
>
> The demo could be overwhelming for beginners.
>
> See [Setup the template](#setup-the-template) how to use the template instead of the demo. The demo can always be us
> The template creates a simple boilerplate project which has the bare minimum but can still be run immediately.





## Prerequisites



### Tools



| Name               | Purpose                                             | Version | Download                                                     | Version command             | License             |
| ------------------ | --------------------------------------------------- | ------- | ------------------------------------------------------------ | --------------------------- | ------------------- |
| Git                | Repository                                          | 2.43.0  | [Linux](https://git-scm.com/download/linux)<br />[Windows](https://gitforwindows.org/) | `git --version`             | GPL 2.0             |
| Visual Studio Code | Code editing                                        | 1.86.1  | [Link](https://code.visualstudio.com/)                       | `code --version`            | MIT                 |
| Power Shell        | CLI for using/managing tools, frameworks, libraries | 7.4.1   | [Link](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-windows?view=powershell-7.4) | `$PSVersionTable.PSVersion` | MIT                 |
| .NET SDK           | .NET runtime and development tools                  | 8.0.102 | [Link](https://dotnet.microsoft.com/download)                | `dotnet --version`          | MIT<br />Apache 2.0 |



> [!IMPORTANT]
>
> This table is just for reference, use the tools you like and are familiar with.
> However, this documentation assumes that the aforementioned tools and versions are used.



> [!NOTE]
>
> In some cases, the upgrade to Power Shell version 7.x fails on Windows (`0xblahblah: Unknown Error`).
> In that case, try to reinstall Power Shell from the Microsoft App Store.



### Visual Studio Code extensions



| Name              | Purpose                                                      | Version | License                                                      |
| ----------------- | ------------------------------------------------------------ | ------- | ------------------------------------------------------------ |
| C#                | Basic C# language support                                    | 2.15.30 | *[proprietary](https://ms-dotnettools.gallerycdn.vsassets.io/extensions/ms-dotnettools/csharp/2.15.30/1704914611079/Microsoft.VisualStudio.Services.Content.License)* |
| C# Dev Kit        | Project system, code editing enhancements, NuGet package management, debugging, testing | 1.3.10  | *[proprietary](https://ms-dotnettools.gallerycdn.vsassets.io/extensions/ms-dotnettools/csdevkit/1.3.10/1707244323031/Microsoft.VisualStudio.Services.Content.License)* |
| .NET Install Tool | .NET SDK and runtime management                              | 2.0.1   | MIT                                                          |



### NuGet packages



| Name                               | Purpose                                               | Version | License |
| ---------------------------------- | ----------------------------------------------------- | ------- | ------- |
| Microsoft.OpenApi                  | OpenAPI JSON model writers                            | 1.16.13 | MIT     |
| Microsoft.AspNetCore.OpenApi       | OpenAPI annotations for endpoints                     | 8.0.2   | MIT     |
| Swashbuckle.AspNetCore             | Swagger middleware for exposing the API documentation | 6.5.0   | MIT     |
| Swashbuckle.AspNetCore.Annotations | Swagger annotations for endpoints                     | 6.5.0   | MIT     |





## Setup the demo



1. make sure that you have all the required stuff installed (Tools, Visual Studio Code extensions, Frameworks)
2. create a new empty local project directory
3. git clone `hackathon/Dotnet_React/DotNet_Backend/` into your project directory
4. open Power Shell console and go to the project directory
5. execute `dotnet restore HackathonDotnetServer.csproj` (installs or restores required NuGet packages)
6. execute `dotnet clean HackathonDotnetServer.csproj`  (to clean out compilation leftovers, if there are any)
7. execute `dotnet build HackathonDotnetServer.csproj` (to initially build the project)
8. that's it, now start coding!



## Demo files and code structure



[TBD]



## Setup the template



1. make sure that you have all the required stuff installed (Tools, Visual Studio Code extensions, Frameworks)
2. create a new empty local project directory
3. open Power Shell console and go to the project directory
4. execute `dotnet new webapi`
5. that's it, now start coding!





## Start coding and debugging



1. open Power Shell console and go to the project directory
2. execute `code .` to open the C# project in Visual Studio Code
3. execute `dotnet run [your *.csproj file]` to run the local WebAPI development server
   *and/or*
   use Visual Studio Code to debug and run the application



C# Version 12 is used in the demo and template.
Documentation of the language can be found [here](https://learn.microsoft.com/dotnet/csharp/).

Both demo and template provide a Swagger documentation of the WebAPI.
It's accessible using the relative path `/swagger/index.html`.



## More important things



### Visual Studio Code



> [!NOTE]
>
> In some cases, breakpoints are not hit by the Visual Studio Code debugger.
>
> In that case, the reason is mostly that the wrong debug information type is generated by the compiler.
> Make sure, that the `portable` debug information type is used (`full` does **not** work).
>
> Check the C# project file whether it's correctly set:
>
> ```xml
> </PropertyGroup>
>  ...
>  <DebugSymbols>true</DebugSymbols>
>  <DebugType>portable</DebugType>
>  ...
> </PropertyGroup>```



> [!NOTE]
>
> Files in Visual Studio Code must be saved before the compiler uses them.
> It doesn't automatically save on debug/run as Visual Studio does.




> [!NOTE]
>
> If the IntelliSense becomes loopy, restart Visual Studio Code and ensure that there is a `*.sln` file which refers to the `*.csproj` files you use.



> [!NOTE]
> If there are multiple `*.csproj` and/or `*.sln` files, `dotnet restore/clean/build/run` can't automatically determine the project/solution it has to use.
>
> In that case, you have to specify the project to use, e.g. `dotnet clean HackathonDotnetServer.csproj`.



> [!TIP]
> You can use the Power Shell console directly in Visual Studio Code (called "terminal").



### Platforms



> [!NOTE]
>
> Only tools, Visual Studio Code extensions, and NuGet packages which are cross-platform (Windows, Linux) have been considered.
>
> However, it can't be guaranteed that it really works (as we need it) on both of those platforms.



### Licenses



> [!NOTE]
>
> Only tools, Visual Studio Code extensions, and NuGet packages which are open-source, free, and with full freedom-of-use (e.g. no commercial use only) have been considered.
>
> There are a few proprietary licenses and licenses which are based on well-known licenses but have been modified.
> Check if those licenses allow usage in your context.
>
> [A list of well-known open-source licenses can be found here](https://opensource.org/licenses/)



### Versions



> [!IMPORTANT]
> 
> The demo and template were created using the specified versions above.
> 
> You can use different versions but it might not work (properly) as the demo or template are intended to.



### Development certificate



> [!WARNING]
>
> To run the local development server, a development certificate is required server-side for using HTTPS.
>
> The certificate must be installed **and** trusted. Using the command...
> `dotnet dev-certs https --check --trust`
> ...you can check if both of those requirements are fulfilled.
>
> If so, the output will be:
> `A trusted certificate was found: B6E8...`
>
> If no trusted development certificate was found, use the command...
> `dotnet dev-certs https --clear`
> ...to remove the installed but untrusted development certificate (if any).
>
> Then create, install, and trust a new certificate using the command:
> `dotnet dev-certs https --trust`.



> [!CAUTION]
>
> Tampering with the development certificate could affect other tools and/or runtimes which also rely on a development certificate!
> 
> Make sure you know what you are doing!





## More nice things



> [!NOTE]
>
> The listed thing in this section are not part of the demo or template.
>
> Of course you don't have to use all NuGet packages for the corresponding library.



### Tools



| Name                                  | Purpose                                                     | License | Costs | Comments                                                     |
| ------------------------------------- | ----------------------------------------------------------- | ------- | ----- | ------------------------------------------------------------ |
| [Typora](https://typora.io/)          | Markdown editor for easily create and modify Markdown files |         |       | This document was created with it.<br />Costs money after the trial period! |
| [draw.io](https://draw.io/)           | Diagrams by hand                                            |         | *no*  | XML based, thus no usable change tracking<br />[Online playground](https://app.diagrams.net/) |
| [Mermaid](https://mermaid.js.org/)    | Diagrams by markup                                          |         | *no*  | [Online playground](https://mermaid.live/)                   |
| [PlantUML](https://www.plantuml.com/) | Diagrams by markup                                          |         | *no*  | [Online playground](https://www.plantuml.com/plantuml/uml/)  |



### Visual Studio Code extensions



| Name | Purpose | License | Comments |
| ---- | ------- | ------- | -------- |
|      |         |         |          |
|      |         |         |          |
|      |         |         |          |
|      |         |         |          |
|      |         |         |          |
|      |         |         |          |



### NuGet packages



| Name              | Purpose                                                      | NuGet packages                                               | Links                                                        | License                                                      |
| ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Polly             | Resilience and transient-fault handling                      | Polly<br />Polly.Extensions<br />Polly.Extensions.Http<br /><br />Microsoft.Extensions.Http.Polly | Polly.\*<br />[Project page](https://github.com/App-vNext/Polly)<br /><br />[Resilience strategies](https://www.pollydocs.org/strategies/) | Polly.\*<br />BSD 3<br /><br />Microsoft.\*<br />MIT         |
| FluentValidation  | Data/Model validation using strongly-typed rules             | FluentValidation<br />FluentValidation.DependencyInjectionExtensions<br />FluentValidation.AspNetCore<br />FluentValidation.WebAPI<br />FluentValidation.ValidatorAttribute | [Project page](https://github.com/FluentValidation)          | Apache 2.0                                                   |
| AutoMapper        | Convention-based object-to-object mapper                     | AutoMapper<br />AutoMapper.Collection<br />AutoMapper.Data<br />AutoMapper.Extensions.ExpressionMapping<br />AutoMapper.Extensions.Microsoft.DependencyInjection | [Project page](https://github.com/AutoMapper/AutoMapper)<br />[Documentation](http://automapper.org/) | MIT                                                          |
| Dapper            | Simple and fast ORM for ADO.NET                              | Dapper                                                       | [Project page](https://github.com/DapperLib/Dapper)          | Apache 2.0                                                   |
| SQLite            | Serverless, zero-configuration, transactional, file-based database | SQLite                                                       | [Documentation](https://www.sqlite.org/)                     | Public Domain                                                |
| RestSharp         | REST API client                                              | RestSharp                                                    | [Project page](https://restsharp.dev/)                       | Apache 2.0                                                   |
| HttpClient        | REST API client                                              | System.Net.Http<br />System.Net.Http.Json                    | [Documentation](https://learn.microsoft.com/dotnet/api/system.net.http.httpclient?view=net-8.0) | MIT<br />Apache 2.0<br />                                    |
| Serilog           | Structured logging                                           | Serilog<br />Serilog.Sinks.File<br />Serilog.Sinks.RollingFile<br />Serilog.Sinks.Console<br />Serilog.Sinks.Debug<br />Serilog.Enrichers.Environment<br />Serilog.Enrichers.Thread<br />Serilog.Enrichers.Process<br />Serilog.Formatting.Compact<br />Serilog.Extensions.Hosting<br />Serilog.Extensions.Logging<br />Serilog.Extensions.Logging.File<br />Serilog.Settings.Configuration<br />Serilog.Settings.AppSetings<br />Serilog.AspNetCore<br /><br />Serilog.Exceptions | Serilog.\*<br />[Project page](https://github.com/serilog/serilog)<br />[Documentation](https://serilog.net/)<br /><br />Serilog.Exceptions<br />[Project page<br />](https://github.com/RehanSaeed/Serilog.Exceptions) | Serilog.\*<br />Apache 2.0<br /><br />Serilog.Exceptions<br />MIT |
| Coravel           | Job and task scheduling                                      | Coravel                                                      | [Project page](https://github.com/jamesmh/coravel)<br />[Documentation](https://docs.coravel.net/Installation/) | MIT                                                          |
| HangFire          | Job and task scheduling                                      | Hangfire<br />Hangfire.AspNetCore<br /><br />Hangfire.Storage.SQLite | Hangfire.\*<br />[Project page](https://github.com/HangfireIO/Hangfire)<br />[Documentation](https://www.hangfire.io/)<br /><br />Hangfire.Storage.SQLite<br />[Project page](https://github.com/raisedapp/Hangfire.Storage.SQLite) | HangFire.\*<br />LGPLv3<br /><br />Hangfire.Storage.SQLite MIT |
| MediatR           | Simple mediator pattern implementation                       | MediatR<br />MediatR.Contracts<br />MediatR.Extensions.Microsoft.DependencyInjection<br /><br />MediatR.Extensions<br /><br />MediatR.Extensions.FluentValidation.AspNetCore | <br />MediatR.\*<br />[Project page](https://github.com/jbogard/MediatR)<br /><br /><br />MediatR.Extensions<br />[Project page](https://github.com/bernos/MediatR.Extensions)<br /><br /><br />FluentValidation<br />[Project page](https://github.com/GetoXs/MediatR.Extensions.FluentValidation.AspNetCore) | MediatR.\*<br />Apache 2.0<br /><br />MediatR.Extensions<br />Apache 2.0<br /><br />FluentValidation<br />MIT |
| Akka              | Actor pattern implementation                                 | Akka<br />Akka.Persistence<br />Akka.Streams<br />Akka.Coordination<br />Akka.Cluster<br />Akka.Cluster.Tools<br />Akka.Cluster.Sharding<br />Akka.DistributedData<br />Akka.DependencyInjection<br />Akka.Hosting<br />Akka.Serilog | [Project page](https://github.com/akkadotnet/akka.net)<br />[Documentation](https://getakka.net/) | Apache 2.0                                                   |
| PasswordGenerator | Random password generator which meets OWASP specifications   | PasswordGenerator                                            | [Project page](https://github.com/prjseal/PasswordGenerator) | MIT                                                          |
| CRC               | CRC algorithms                                               | InvertedTomato.Crc                                           | [Project page](https://github.com/invertedtomato/crc)        | MIT                                                          |
| BCrypt.Net        | Cryptographic algorithms                                     | BCrypt.Net-Next                                              | [Project page](https://github.com/BcryptNet/bcrypt.net)      | MIT                                                          |
| BouncyCastle      | Cryptographic algorithms                                     | BouncyCastle.Cryptography                                    | [Project page](https://github.com/novotnyllc/bc-csharp)<br />[Documentation](https://www.bouncycastle.org/csharp/) | [*modified MIT X11*](https://www.bouncycastle.org/csharp/licence.html) |
| Json.NET          | JSON data                                                    | Newtonsoft.Json                                              | [Documentation](https://www.newtonsoft.com/json)             | MIT                                                          |
| SharpZipLib       | ZIP files                                                    | ICSharpCode.SharpZipLib                                      | [Project page](https://github.com/icsharpcode/SharpZipLib)<br />[Documentation](https://icsharpcode.github.io/SharpZipLib/) | MIT                                                          |
| CommonMark.NET    | Markdown to HTML converter                                   | CommonMark.NET                                               | [Project page](https://github.com/Knagis/CommonMark.NET)     | BSD 3                                                        |
| Markdig           | Markdown processor                                           | Markdig                                                      | [Project page](https://github.com/xoofx/markdig)             | BSD 2                                                        |
| CsvHelper         | CSV files                                                    | CsvHelper<br />CsvHelper.Fluent                              | CsvHelper<br />[Project page](https://github.com/JoshClose/CsvHelper)<br />[Documentation](https://joshclose.github.io/CsvHelper/)<br /><br />CsvHelper.Fluent<br />[Project page](https://github.com/piccaso/csvhelper-fluent) | CsvHelper<br />MS-PL<br />Apache 2.0<br /><br />CsvHelper.Fluent<br />*none specified* |
| NPOI              | Excel files                                                  | NPOI                                                         | [Project page](https://github.com/nissl-lab/npoi)            | Apache 2.0                                                   |
| ClosedXML         | Excel files                                                  | ClosedXML                                                    | [Project page](https://github.com/ClosedXML/ClosedXML)       | MIT                                                          |
| ImageSharp        | Images and 2D graphics                                       | SixLabors.ImageSharp<br />SixLabors.ImageSharp.Drawing<br />SixLabors.ImageSharp.Web | [Project page](https://github.com/SixLabors/ImageSharp)<br />[Documentation](https://sixlabors.com/products/imagesharp/) | [*modified Apache 2.0*](https://github.com/SixLabors/ImageSharp?tab=License-1-ov-file#readme) |
| QRCoder           | QR Codes                                                     | QRCoder-ImageSharp                                           | [Project page](https://github.com/codebude/QRCoder)          | MIT                                                          |
| NetBarcode        | Barcodes                                                     | NetBarcode                                                   | [Project page](https://github.com/Tagliatti/NetBarcode)      | MIT                                                          |
| ScrapySharp       | Web scrapper                                                 | ScrapySharp                                                  | [Project page](https://github.com/rflechner/ScrapySharp)     | MIT                                                          |
| Html Agility Pack | Web page parser                                              | HtmlAgilityPack                                              | [Project page](https://github.com/zzzprojects/html-agility-pack)<br />[Documentation](https://html-agility-pack.net/) | MIT                                                          |
| AngleSharp        | Web page parser                                              | AngleSharp<br />AngleSharp.Css<br />AngleSharp.Xml<br />AngleSharp.Js<br />AngleSharp.Wasm<br />AngleSharp.Io<br />AngleSharp.Scripting.JavaScript | [Project page](https://github.com/AngleSharp/AngleSharp)<br />[Documentation](https://anglesharp.github.io/) | MIT                                                          |
| MemoryCache       | In-memory cache                                              | Microsoft.Extensions.Caching.Memory                          | [Documentation](https://learn.microsoft.com/aspnet/core/performance/caching/memory?view=aspnetcore-8.0) | MIT                                                          |

