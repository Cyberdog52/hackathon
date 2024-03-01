# Hackathon .NET demo and template

Author: Zühlke, Franziska Roten ([franziska.roten@zühlke.com](mailto:franziska.roten@zühlke.com))





## Introduction



**This is the backend part of the combined .NET / React demo and template**

➡️ [Cyberdog52/hackathon: Zuehlke Java Fullstack Hackathon Template Project (github.com)](https://github.com/Cyberdog52/hackathon)



The .NET backend and the React frontend are two separated projects with no build-time dependencies.
At runtime, the frontend depends on the WebAPI provided by the backend.

The backends WebAPI provides a list of persons with details, diagram scripts (rendered client-side), and jokes by accessing a third-party REST API.
It's created using [ASP.NET Core](https://dotnet.microsoft.com/en-us/apps/aspnet) (plus [.NET](https://dotnet.microsoft.com/), [C#](https://learn.microsoft.com/en-gb/dotnet/csharp/), [Swagger](https://swagger.io/)/[Swashbuckle](https://github.com/domaindrivendev/Swashbuckle.AspNetCore))

The frontend uses that WebAPI to retrieve said data and displays them.
It's created using [React](https://react.dev/) (plus [Vite](https://vitejs.dev/), [Bootstrap](https://getbootstrap.com/), [Mermaid](https://mermaid.js.org/), [TypeScript](https://www.typescriptlang.org/)/[JSX](https://www.typescriptlang.org/docs/handbook/jsx.html))

Frontend and backend are not only a boilerplate template but also a demo which can be set up and used separately.



> [!IMPORTANT]
>
> The demo could be overwhelming for beginners.
>
> See [Setup the template](#setup-the-template) how to use the template instead of the demo.
> The template creates a simple boilerplate project which has the bare minimum but can still be run immediately.





## Prerequisites



### Tools



| Name                                                         | Purpose                                             | Version | Version command             | License             |
| ------------------------------------------------------------ | --------------------------------------------------- | ------- | --------------------------- | ------------------- |
| [Git (Linux)](https://git-scm.com/download/linux)<br />[Git (Windows)](https://gitforwindows.org/) | Repository                                          | 2.43.0  | `git --version`             | GPL 2.0             |
| [Visual Studio Code](https://code.visualstudio.com/)         | Code editing                                        | 1.86.1  | `code --version`            | MIT                 |
| [Power Shell (Linux)](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-linux?view=powershell-7.4)<br />[Power Shell (Windows)](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-windows?view=powershell-7.4) | CLI for using/managing tools, frameworks, libraries | 7.4.1   | `$PSVersionTable.PSVersion` | MIT                 |
| [.NET SDK](https://dotnet.microsoft.com/en-us/download)      | .NET runtime and development tools                  | 8.0.102 | `dotnet --version`          | MIT<br />Apache 2.0 |



### Visual Studio Code extensions



| Name                                                         | Purpose                                                      | ID                      | Version | License                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ----------------------- | ------- | ------------------------------------------------------------ |
| [C#](https://marketplace.visualstudio.com/items?itemName=ms-dotnettools.csharp) | Basic C# language support                                    | ms-dotnettools.csharp   | 2.18.16 | *[proprietary](https://ms-dotnettools.gallerycdn.vsassets.io/extensions/ms-dotnettools/csharp/2.15.30/1704914611079/Microsoft.VisualStudio.Services.Content.License)* |
| [C# Dev Kit](https://marketplace.visualstudio.com/items?itemName=ms-dotnettools.csdevkit) | Project system, code editing enhancements, NuGet package management, debugging, testing | ms-dotnettools.csdevkit | 1.3.10  | *[proprietary](https://ms-dotnettools.gallerycdn.vsassets.io/extensions/ms-dotnettools/csdevkit/1.3.10/1707244323031/Microsoft.VisualStudio.Services.Content.License)* |
| [.NET Install Tool](https://marketplace.visualstudio.com/items?itemName=ms-dotnettools.vscode-dotnet-runtime) | .NET SDK and runtime management                              |                         | 2.0.1   | MIT                                                          |



### NuGet packages



| Name                                                         | Purpose                                               | Version | License |
| ------------------------------------------------------------ | ----------------------------------------------------- | ------- | ------- |
| [Microsoft.OpenApi](https://learn.microsoft.com/en-us/aspnet/core/fundamentals/minimal-apis/openapi?view=aspnetcore-8.0) | OpenAPI JSON model writers                            | 1.16.13 | MIT     |
| [Microsoft.AspNetCore.OpenApi](https://learn.microsoft.com/en-us/aspnet/core/fundamentals/minimal-apis/openapi?view=aspnetcore-8.0) | OpenAPI annotations for endpoints                     | 8.0.2   | MIT     |
| [Swashbuckle.AspNetCore](https://github.com/domaindrivendev/Swashbuckle.AspNetCore) | Swagger middleware for exposing the API documentation | 6.5.0   | MIT     |
| [Swashbuckle.AspNetCore.Annotations](https://github.com/domaindrivendev/Swashbuckle.AspNetCore) | Swagger annotations for endpoints                     | 6.5.0   | MIT     |





## Setup the demo



1. make sure that you have all the required stuff installed (Tools, Visual Studio Code extensions, Frameworks)
2. create a new empty local project directory
3. git clone `hackathon/Dotnet_React/DotNet_Backend/` into your project directory
4. open Power Shell console and go to the project directory
5. execute `dotnet restore HackathonDotnetServer.csproj` (installs or restores required NuGet packages)
6. execute `dotnet clean HackathonDotnetServer.csproj`  (to clean out compilation leftovers, if there are any)
7. execute `dotnet build HackathonDotnetServer.csproj` (to initially build the project)
8. that's it, now start coding!



> [!TIP]
> You can use the Power Shell console directly in Visual Studio Code (called "terminal").





## Demo files and code structure



[TBD]





## Setup the template



1. make sure that you have all the required stuff installed (Tools, Visual Studio Code extensions, Frameworks)
2. create a new empty local project directory
3. open Power Shell console and go to the project directory
4. execute `dotnet new webapi`
5. that's it, now start coding!



> [!TIP]
> You can use the Power Shell console directly in Visual Studio Code (called "terminal").





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





## Important things



### Power Shell



- **Fail to install/update:** In some cases, the upgrade to Power Shell version 7.x fails on Windows (`0xblahblah: Unknown Error`). In that case, try to reinstall Power Shell from the Microsoft App Store.



### Visual Studio Code



- **File changes:** Files in Visual Studio Code must be saved before the compiler "sees" the modifications. It doesn't automatically save on debug/run as Visual Studio does.



- **Breakpoints not hit:** In some cases, breakpoints are not hit by the Visual Studio Code debugger. In that case, the reason is mostly that the wrong debug information type is generated by the compiler. Make sure, that the `portable` debug information type is used (`full` does **not** work). Check the C# project file whether it's correctly set:

 ```xml
 </PropertyGroup>
 ...
 <DebugSymbols>true</DebugSymbols>
 <DebugType>portable</DebugType>
 ...
 </PropertyGroup>```
 ```



- **Loopy code completion:** If the code completionbecomes loopy, restart Visual Studio Code and ensure that there is a `*.sln` file which refers to the `*.csproj` files you use.



- **Multiple project/solution files:** If there are multiple `*.csproj` and/or `*.sln` files, `dotnet restore/clean/build/run` can't automatically determine the project/solution it has to use. In that case, you have to specify the project to use, e.g. `dotnet clean HackathonDotnetServer.csproj`.



### Platforms



Only tools, Visual Studio Code extensions, and NuGet packages which are cross-platform (Windows, Linux) have been considered.

However, it can't be guaranteed that it really works (as we need it) on both of those platforms.



### Licenses



Only tools, Visual Studio Code extensions, and NuGet packages which are open-source, free, and with full freedom-of-use (e.g. no commercial use requirements) have been considered.

There are a few proprietary licenses and licenses which are based on well-known licenses but have been modified.
Check if those licenses allow usage in your context.

[A list of well-known open-source licenses can be found here](https://opensource.org/licenses/)



> [!IMPORTANT]
>
> The usage, modification, distribution licenses are **NOT THE SAME** as the licenses granted for the output created with the corresponding software (e.g. code, documents, binaries, ...)!
>
> There are no licenses or license-based restrictions respectively to the outputs produced by you using the software mentioned in this document. However, it can't be guaranteed, as it was not checked if this is true for all of them. The nature of the software mentioned leads to the assumption, that there are no licenses or license-based restrictions respectively.



> [!WARNING]
>
> This document or any of its content is **NOT LEGAL ADVISE !**



### Versions



The demo and template were created using the specified versions mentioned.

You can use different NuGet package versions but it might not work (properly) as the demo or template are intended to.

Of course you can choose your own IDE, use the tools you like and are familiar with.



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
> Make sure you know what you are doing and which effects it may have!





## Nice things



> [!NOTE]
>
> The listed things in this section are **not** part of the demo or template and therefore not covered by this documentation.
>
> Of course you don't have to use all NuGet packages for the corresponding library.



### Tools



| Name                                  | Purpose                                             | License     | Costs                  | Comments                                                     |
| ------------------------------------- | --------------------------------------------------- | ----------- | ---------------------- | ------------------------------------------------------------ |
| [Typora](https://typora.io/)          | Markdown editor to create and modify Markdown files | proprietary | CHF 15.-<br />one-time | This document was created with it.<br />Costs money after the trial period (15 days)! |
| [draw.io](https://draw.io/)           | Diagrams by hand                                    | Apache 2.0  | no                     | XML based, thus no usable change tracking.<br />[Online playground](https://app.diagrams.net/) |
| [Mermaid](https://mermaid.js.org/)    | Diagrams by markup                                  | MIT         | no                     | It's used in the frontend.<br />Human-readable scripts.<br />[Online playground](https://mermaid.live/) |
| [PlantUML](https://www.plantuml.com/) | Diagrams by markup                                  | GPL         | no                     | Human-readable scripts.<br />[Online playground](https://www.plantuml.com/plantuml/uml/) |



### Visual Studio Code extensions



| Name                                                         | Purpose                                                      | ID                                      | License                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | --------------------------------------- | ------------------------------------------------------------ |
| [IntelliCode for C# Dev Kit](https://marketplace.visualstudio.com/items?itemName=ms-dotnettools.vscodeintellicode-csharp) | AI-based coding assistance                                   | ms-dotnettools.vscodeintellicode-csharp | *[proprietary](https://ms-dotnettools.gallerycdn.vsassets.io/extensions/ms-dotnettools/vscodeintellicode-csharp/0.1.26/1688582152985/Microsoft.VisualStudio.Services.Content.License)* |
| [.NET Core EditorConfig Generator](https://marketplace.visualstudio.com/items?itemName=doggy8088.netcore-editorconfiggenerator) | Creates `.editorconfig` files                                | doggy8088.netcore-editorconfiggenerator | MIT                                                          |
| [Json](https://marketplace.visualstudio.com/items?itemName=ZainChen.json) | Additional JSON editing features                             | zainchen.json                           | MIT                                                          |
| [Commands](https://marketplace.visualstudio.com/items?itemName=fabiospampinato.vscode-commands) | Allows placing commands on the statusbar                     | fabiospampinato.vscode-commands         | MIT                                                          |
| [Markdown All in One](https://marketplace.visualstudio.com/items?itemName=yzhang.markdown-all-in-one) | Markdown editor                                              | yzhang.markdown-all-in-one              | MIT                                                          |
| [Markdown Preview Enhanced](https://marketplace.visualstudio.com/items?itemName=shd101wyy.markdown-preview-enhanced) | Rendered preview of markdown files                           | shd101wyy.markdown-preview-enhanced     | [University of Illinois/NCSA   Open Source License](https://shd101wyy.gallerycdn.vsassets.io/extensions/shd101wyy/markdown-preview-enhanced/0.8.11/1702203517797/Microsoft.VisualStudio.Services.Content.License) |
| [NuGet Gallery](https://marketplace.visualstudio.com/items?itemName=patcx.vscode-nuget-gallery) | NuGet package browser                                        | patcx.vscode-nuget-gallery              | MIT                                                          |
| [NuGet Package Manager GUI](https://marketplace.visualstudio.com/items?itemName=aliasadidev.nugetpackagemanagergui) | UI for the NuGet package manager                             | aliasadidev.nugetpackagemanagergui      | MIT                                                          |
| [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode) | Code formatter for many programming/scripting/markup languages | esbenp.prettier-vscode                  | MIT                                                          |
| [YAML](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-yaml) | YAML language support                                        | redhat.vscode-yaml                      | MIT                                                          |



### NuGet packages



| Name              | Purpose                                                      | NuGet package(s)                                             | Links                                                        | License                                                      |
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

