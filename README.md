# Java Fullstack Hackathon 2021

---

## Setup

If your setup already has some of the required tools installed, please adjust accordingly.

1. Install Node 12.14.1 from here https://nodejs.org/en/blog/release/v12.14.1/
2. Run `npm install -g npm@7.23.0`
3. Run `npm install -g @angular/cli@12.2.4`
4. Verify your versions with `ng version`:  
   Angular CLI: **12.2.4**  
   Node: **12.14.1**  
   Package Manager: **npm 7.23.0**
4. Install a new version of IntelliJ
5. Clone the master branch with `git clone https://github.com/Cyberdog52/hackathon.git`
6. Open the project with IntelliJ and let gradle build the project
7. Install the npm dependencies with `cd frontend` followed by `npm install`

During the hackathon your team can decide to use a custom branch on this GitHub repository or to fork it to your own
private repository.

## Run

### Start backend

1. Start the backend either by
    1. executing `./gradlew bootRun`
    2. or by creating a run configuration of type SpringBoot pointing to *
       ch.zuehlke.fullstack.hackathon.HackathonApplication*
       ![runconfig-springboot.png](doc/runconfig-springboot.png)

You should be able to open the Swagger endpoint documentation at `http://localhost:8080/swagger-ui.html`.

### Start frontend

1. By executing `cd frontend` followed by `npm start`
2. Or by creating a run configuration of type **npm** pointing to *hackathon\frontend\package.json* with the script **
   start**
   ![runconfig-npm.png](doc/runconfig-npm.png)

You should now be able to open `http://localhost:4200` to access the frontend.

Now you should see the following screen:
![setup-complete-with-example-component.png](doc/setup-complete-with-example-component.png)

## Insight API

We highly encourage you to use the Insight API.  
You can find the API documentation in json format at https://insight.zuehlke.com/swagger/docs/v1 (VPN access required)
.  
To test the endpoints and to have a better overview of the API, we recommend you to import this API into Postman.
Unfortunately the official Swagger documentation at
https://insight.zuehlke.com/swagger/ui/index
is not working at the time of this writing. Here is how you can test the Insight API locally:

1. Install Postman from https://www.postman.com/downloads/
2. On the top-left corner in Postman click **Import** and select **Link**
3. Enter https://insight.zuehlke.com/swagger/docs/v1 in the link and press **Continue**
   ![postman-import.png](doc/postman-import.png)
4. On the next screen, you can verify the collection name (WebApi), format (Swagger 2.0) and Import as (API). Click **
   Import**. This may take a while.
5. You will see the new collection 'WebApi' under collections
6. For any request you will have to adjust the Auth tab to be **Basic Auth**. User: *your Zuehlke email address*.
   Password: *your Zuehlke password*. Be careful that you do not share this!
   ![postman-auth.png](doc/postman-auth.png)

## Other API ideas

- RapidApi: Overview of a large collection of APIs
    - https://rapidapi.com/hub

- Weather
    - https://rapidapi.com/blog/access-global-weather-data-with-these-weather-apis/

- Google Maps
    - https://developers.google.com/maps/documentation/javascript/get-api-key

- Reddit
    - https://www.reddit.com/dev/api/

- Twitter
    - https://developer.twitter.com/en/docs/twitter-api/getting-started/about-twitter-api

- Youtube
    - https://developers.google.com/youtube/v3

- LinkedIn
    - https://developer.linkedin.com/

- Tmdb The Movie Database
    - https://developers.themoviedb.org/3/getting-started/introduction

- Microsoft Teams
    - https://docs.microsoft.com/en-us/graph/api/resources/teams-api-overview?view=graph-rest-1.0

- Microsoft Outlook
    - https://docs.microsoft.com/en-us/outlook/rest/get-started
