# Java Fullstack Hackathon 2023

---

## Setup

If your setup already has some of the required tools installed, please adjust accordingly.

1. Install newest Node from here https://nodejs.org/en/ (currently 18.15.0 LTS)
2. Run `npm install -g npm@9.6.1`
3. Run `npm install -g @angular/cli@15.2.2`
4. Verify your versions with `ng version`:  
   Angular CLI: **15.2.2** \
   Node: **18.15.0** \
   Package Manager: npm **9.6.1**
4. Install a new version of IntelliJ
5. Clone the master branch with `git clone https://github.com/Cyberdog52/hackathon.git`
6. Open the project with IntelliJ and let gradle build the project
7. If prompted, install java (17.0.5)
7. Install the npm dependencies with `cd frontend` followed by `npm install`

During the hackathon your team can decide to use a custom branch on this GitHub repository or to fork it to your own
private repository.

## Run

### Start backend

1. Start the backend either by
   1. By selecting the "Backend" run configuration and running it
       ![runconfig-springboot.png](doc/runconfig-springboot.png)
   2. Or by executing `./gradlew bootRun`

You should be able to open the Swagger endpoint documentation at `http://localhost:8080/swagger-ui.html`.

### Start frontend

1. By selecting the "Frontend" and running it
   ![runconfig-npm.png](doc/runconfig-npm.png)
2. Or by executing `cd frontend` followed by `npm start`

You should now be able to open `http://localhost:4200` to access the frontend.

Now you should see the following screen:
![setup-complete-with-example-component.png](doc/setup-complete-with-example-component.png)

