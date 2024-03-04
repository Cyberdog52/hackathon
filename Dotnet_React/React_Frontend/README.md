# Hackathon React Demo and Template

Author: Zühlke, Franziska Roten ([franziska.roten@zühlke.com](mailto:franziska.roten@zühlke.com))





## Introduction



Although a separate independent project with no build-time dependencies to other projects, this React frontend was created in tandem with the Hackathon .NET Demo and Template. At runtime, this React frontend depends on the WebAPI provided by the .NET backend.

The frontend provides views to select/display persons, diagrams, and jokes which are fetched from the backends WebAPI.
It was created using React (and [Typescript](https://www.typescriptlang.org/), [Vite](https://vitejs.dev/), [Bootstrap](https://getbootstrap.com/), [Mermaid](https://mermaid.js.org/))



> [!IMPORTANT]
>
> The demo could be overwhelming for beginners.
>
> See [Setup the template](#setup-the-template) how to use the template instead of the demo.
>
> The template creates a simple boilerplate project which has the bare minimum but can still be run immediately.
> 





## Prerequisites



### Tools



| Name                                                         | Purpose                                             | Version | Version command             | License                                                      |
| ------------------------------------------------------------ | --------------------------------------------------- | ------- | --------------------------- | ------------------------------------------------------------ |
| [Git (Linux)](https://git-scm.com/download/linux)<br />[Git (Windows)](https://gitforwindows.org/) | Repository                                          | 2.43    | `git --version`             | GPL 2.0                                                      |
| [Visual Studio Code](https://code.visualstudio.com/)         | Code editing                                        | 1.86    | `code --version`            | MIT                                                          |
| [Power Shell (Linux)](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-linux?view=powershell-7.4)<br />[Power Shell (Windows)](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-windows?view=powershell-7.4) | CLI for using/managing tools, frameworks, libraries | 7.4     | `$PSVersionTable.PSVersion` | MIT                                                          |
| [node.js](https://nodejs.org/)                               | Package management                                  | 20.11   | `node --version`            | [*proprietary*](https://github.com/nodejs/node?tab=License-1-ov-file#readme) |



### Visual Studio Code extensions



| Name | Purpose | ID   | Version | License |
| ---- | ------- | ---- | ------- | ------- |
|      |         |      |         |         |
|      |         |      |         |         |
|      |         |      |         |         |



### NPM packages



| Name | Purpose | Package | Version | License |
| ---- | ------- | ------- | ------- | ------- |
|      |         |         |         |         |
|      |         |         |         |         |
|      |         |         |         |         |
|      |         |         |         |         |





## Setup the demo



1. make sure that you have all the required stuff installed (Tools, Visual Studio Code extensions, NPM packages)
2. create a new empty local project directory
3. git clone `hackathon/Dotnet_React/React_Frontend/` into your project directory
4. open Power Shell console and go to the project directory
5. execute `npm install` (installs or restores required NPM packages)
6. execute `npm rebuild`  (rebuild dependencies, if necessary)
8. that's it, now start coding!





## Setup the template



1. make sure that you have all the required stuff installed (Tools, Visual Studio Code extensions, NPM packages)
2. create a new empty local project directory
3. open Power Shell console and go to the project directory
4. xxxxxxxxxx
5. that's it, now start coding!





## Start coding and debugging



1. open Power Shell console and go to the project directory
2. execute `code .` to open the Vite project in Visual Studio Code
3. execute `execute npm run dev` to run the local Vite development server, including hot reload
   *and/or*
   use Visual Studio Code to debug and run the application



Typescript 5.2 is used in the demo and template.
Documentation of the language can be found [here](https://www.typescriptlang.org/).

The frontend can be accessed by a browser using the local URL as shown by the Vite development server.





## Important things



### Power Shell



- **Fail to install/update:** In some cases, the upgrade to Power Shell version 7.x fails on Windows (`0xblahblah: Unknown Error`). In that case, try to reinstall Power Shell from the Microsoft App Store.



### Visual Studio Code



- **File changes:** Files in Visual Studio Code must be saved before the compiler "sees" the modifications. It doesn't automatically save on debug/run as the ancient Visual Studio does.



> [!TIP]
>
> You can use the Power Shell console directly in Visual Studio Code (called "terminal").



### Platforms



Only tools and Visual Studio Code extensions which are cross-platform (Windows, Linux) have been considered.

However, it can't be guaranteed that it really works (as we need it) on both of those platforms.



### Licenses



Only tools, Visual Studio Code extensions, and NPM packages which are open-source, free, and with full freedom-of-use (e.g. no commercial use requirements) have been considered.

There are a few proprietary licenses and modified licenses (based on well-known open source licenses).
Check if those licenses allow usage in your context.

[A list of well-known open source licenses can be found here](https://opensource.org/licenses/)



> [!IMPORTANT]
>
> The usage, modification, distribution licenses are **NOT THE SAME** as the licenses granted for the output created with the corresponding software (e.g. code, documents, binaries, ...)!
>
> There are no licenses or license-based restrictions respectively to the outputs produced by you using the software mentioned in this document. However, it can't be guaranteed, as it was not checked if this is true for all of them. The nature of the software mentioned leads to the very strong assumption, that there are no such respectively.



> [!WARNING]
>
> This document or any of its content is **NOT LEGAL ADVISE !**



### Versions



The demo and template were created using the specified versions mentioned.

You can use different NPM package versions but it might not work (properly) as the demo or template are intended to.

Of course you can choose your own IDE, use the tools you like and are familiar with.





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



> [!TIP]
>
> Some lists of useful (?) Visual Studio Code extensions can be found here:
>
> (incl. advertising contaminated pages with font sizes larger than the depth of the Mariane trench...)
>
> [Visual Studio Code Extensions to Boost Your Productivity in 2024 (freecodecamp.org)](https://www.freecodecamp.org/news/best-vscode-extensions/)
>
> [Top Visual Studio Code Extensions: 50 Tools, and Snippets (stackify.com)](https://stackify.com/top-visual-studio-code-extensions/)



| Name                                                         | Purpose                                                      | ID                                    | License                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------- | ------------------------------------------------------------ |
| ***general***                                                |                                                              |                                       |                                                              |
| [Commands](https://marketplace.visualstudio.com/items?itemName=fabiospampinato.vscode-commands) | Allows placing commands on the statusbar                     | fabiospampinato.vscode-commands       | MIT                                                          |
| [Markdown All in One](https://marketplace.visualstudio.com/items?itemName=yzhang.markdown-all-in-one) | Markdown editor                                              | yzhang.markdown-all-in-one            | MIT                                                          |
| [Markdown Preview Enhanced](https://marketplace.visualstudio.com/items?itemName=shd101wyy.markdown-preview-enhanced) | Rendered preview of markdown files                           | shd101wyy.markdown-preview-enhanced   | [University of Illinois/NCSA   Open Source License](https://shd101wyy.gallerycdn.vsassets.io/extensions/shd101wyy/markdown-preview-enhanced/0.8.11/1702203517797/Microsoft.VisualStudio.Services.Content.License) |
| [Json](https://marketplace.visualstudio.com/items?itemName=ZainChen.json) | Additional JSON editing features                             | zainchen.json                         | MIT                                                          |
| [YAML](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-yaml) | YAML language support                                        | redhat.vscode-yaml                    | MIT                                                          |
| [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode) | Code formatter for many programming/scripting/markup languages | esbenp.prettier-vscode                | MIT                                                          |
| ***TypeScript / React specific***                            |                                                              |                                       |                                                              |
| [Debugger for Firefox](https://marketplace.visualstudio.com/items?itemName=firefox-devtools.vscode-firefox-debug) | Web application debugging                                    | firefox-devtools.vscode-firefox-debug | MIT                                                          |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |
|                                                              |                                                              |                                       |                                                              |



### NuGet packages



> [!TIP]
>
> An excessive list of third-party .NET libraries can be found:
>
> [quozd/awesome-dotnet: A collection of awesome .NET libraries, tools, frameworks and software (github.com)](https://github.com/quozd/awesome-dotnet)



| Name | Purpose | NPM package(s) | Links | License |
| ---- | ------- | -------------- | ----- | ------- |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |
|      |         |                |       |         |

