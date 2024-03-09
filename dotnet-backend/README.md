# Hackathon .NET Demo and Template

Author: Zühlke, Franziska Roten ([franziska.roten@zühlke.com](mailto:franziska.roten@zühlke.com))

## Introduction

It was created using [ASP.NET Core](https://dotnet.microsoft.com/en-us/apps/aspnet) (and [C#](https://learn.microsoft.com/en-gb/dotnet/csharp/), [.NET](https://dotnet.microsoft.com/), [Swagger](https://swagger.io/)/[Swashbuckle](https://github.com/domaindrivendev/Swashbuckle.AspNetCore))

## Prerequisites

### Tools

| Name                                                         | Purpose                                             | Version | Version command             | License              |
| ------------------------------------------------------------ | --------------------------------------------------- | ------- | --------------------------- | -------------------- |
| [Visual Studio 2022](https://visualstudio.microsoft.com/downloads/)         | IDE                                        | 17.9.2    |             | MIT                  |
| [.NET SDK](https://dotnet.microsoft.com/en-us/download)      | .NET runtime and development tools                  | 8.0     | `dotnet --version`          | MIT,<br />Apache 2.0 |

In Visual Studio Installer, make sure to include the `ASP.NET and web development` workload.

## OpenAI API keys

Sign up and create a new secret key here: [Open AI](https://platform.openai.com/api-keys). Then add your secret key in appsettings.json.
```json
{
  ...,
  "OpenAi": {
    "ApiKey": "sk-************************************************"
  }
}
```

## Start dotnet backend

You should be able to open the Swagger endpoint documentation at `http://localhost:8080/swagger/index.html`.

1. make sure that you have all the prerequisites installed
2. Start the dotnet backend either by
   1. Clicking on the green play button `HackathonWebApi`

      ![runconfig-dotnet.png](/doc/runconfig-dotnet.png)
   2. Or by using dotnet commands
   ```powershell
      > cd hackathon\dotnet-backend\HackathonWebApi
      > dotnet restore
      > dotnet clean
      > dotnet run
   ```
