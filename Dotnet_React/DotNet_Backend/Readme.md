# Hackathon .NET  template demo and template

## Introduction

**This is the .NET template for the Hackathon**
➡️ *hackathon/Dotnet_React/DotNet_Backend/*

**It's the backend part of the combined .NET / React template**
➡️ *hackathon/Dotnet_React/*

The .NET backend and the React frontend are two separated projects with no build-time dependencies.
At runtime, the frontend depends on the WebAPI provided by the backend.

Frontend and backend are not just a boilerplate template but also a demo of some basic parts.
The backends WebAPI provides a list of persons (names, details, etc.) and shows some basics about how to configure the HTTP request pipeline, use endpoints, and transmit JSON data using REST (GET, POST).
The frontend uses that WebAPI to retrieve said person names and details to display them in a list.

> [!NOTE]
>
> Demo parts can be removed or replaced easily for implementing the actually desired functionality.



## Used tools

The following tools were used to create this template:

| Tool               | Purpose                                             | Version | Download                                                     | Version command (PS)        |
| ------------------ | --------------------------------------------------- | ------- | ------------------------------------------------------------ | --------------------------- |
| Git                | Repository                                          | 2.43.0  | [Linux](https://git-scm.com/download/linux)<br />[Windows](https://gitforwindows.org/) | `git --version`             |
| Visual Studio Code | Code editing                                        | 1.86.1  | [Link](https://code.visualstudio.com/)                       | `code --version`            |
| Power Shell (PS)   | CLI for using/managing tools, libraries, frameworks | 7.4.1   | [Link](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-windows?view=powershell-7.4) | `$PSVersionTable.PSVersion` |

> [!NOTE]
>
> In some cases the upgrade to Power Shell version fails (`0xblahblah: Unknown Error`).
> If this happens, uninstall previously installed Power Shell version 7.x (and **only** that version!).

> [!NOTE]
>
> This table is just for reference, use the tools you like and are familiar with.



## Used frameworks & libraries

| Name     | Purpose                            | Version | Download                                      | Version command (PS) |
| -------- | ---------------------------------- | ------- | --------------------------------------------- | -------------------- |
| .NET SDK | .NET runtime and development tools | 8.0.102 | [Link](https://dotnet.microsoft.com/download) | `dotnet --version`   |

> [!IMPORTANT]
>
> The demo/template was created and tested using the versions above.
> You can use different versions but it might not work (properly).

[TBD NuGet packages]

[TBD Programming language]



## Setup

1. make sure that you have all the required stuff installed
2. git clone `hackathon/Dotnet_React/DotNet_Backend/` into your local project directory
3. open (Power Shell) console and go to the project directory
4. execute `dotnet restore` (installs or restores required NuGet packages)
5. execute `dotnet clean` (to clean out compilation leftovers, if there are any)
6. execute `dotnet build` (to build the project)

> [!IMPORTANT]
>
> Sometimes, by some freaky accident, a solution file (`*.sln`) is created.
> This template is project-only, not using a solution, as it has only one project.
> If there is also a solution file, `dotnet restore/clean/build/run` can't automatically determine the project/solution it has to use.
> In that case, you have to specify the project to use, e.g. `dotnet clean HackathonDotnetServer.csproj`.
> Or you just delete the solution file... if it's not used...!



## Start coding

- execute `code .` to open Visual Studio Code and start coding
- execute `dotnet run` to run the local WebAPI development server

> [!TIP]
>
> You can use the Power Shell console directly in Visual Studio Code (called "terminal").



## Development certificate

> [!WARNING]
>
> To run the local development server, a development certificate is required for using HTTPS.
>
> The certificate must be installed **and** trusted! Using the command
> `dotnet dev-certs https --check --trust`
> you can check if both of those requirements are fulfilled.
>
> If so, the output will be
> `A trusted certificate was found: B6E8...`.
>
> If no trusted development certificate was found, use the command
> `dotnet dev-certs https --clear`
> to remove the installed but untrusted development certificate (if any).
>
> Then create, install, and trust a new certificate using the command
> `dotnet dev-certs https --trust`.

> [!CAUTION]
>
> Tampering with the development certificate could affect other tools and/or runtimes which also relies on a development certificate!
> Make sure you know what you are doing!



## Other things

### Create a new empty project

If you want to create a new empty .NET WebAPI project, without using this template, you can do the following steps:

1. make sure that you have all the required stuff installed
2. create a local project directory
3. open (Power Shell) console and go to the project directory
4. execute `dotnet new webapi` (creates the project with some boilerplate code and a very simple WebAPI which can be run immediately)
5. that's it, now start coding!



### Online resources

[TBD Links]

[TBD Typora]



