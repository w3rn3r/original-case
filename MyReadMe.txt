Requirements:
Git
Maven
Java 8


To run from CMD:
- Check out changes from GitHub
- cd to project folder
- Run mvn clean package -DskipTests
- Run mvn install
- cd to target
- Run java -jar original-testcase-1.0.0-SNAPSHOT.jar
* App runs but localhost:9000/flights is not yet reachable (lack of time to fix)
However, running from within IntelliJ will work to hit
* Due to time limit proper TDD was not applied, also therefore lack of unit tests (besides the integration tests)

ToDo:
- Fix ViewResolver issue for webapp so that pages can be hit when jar is running
- Add monitoring (lack of time). Micrometer can be used which is already included in spring-boot-starter-actuator.
To log number of 2xx, 4xx and 5xx a counting service service can be used.

Notes:
- For the sake of review I committed two screenshots of the working frontend: original-testcase-1.0.0-SNAPSHOT.PNG and original-testcase-1.0.0-SNAPSHOT.PNG
- Activity can be followed in log.
- In the location relative from where the jar is run a folder "logs" will be created. Therein a log file named
 "flight-finder.log" will be created and all info, warnings, errors entries will be logged here. The current log level
 is trace. See log4j2.xml in the project.
- In order to override the application.properties from outside the jar: Create a folder "config" adjacent to the jar and
paste the application.properties therein with changed values.
* E.g, for production create application-production.properties in the "config" folder. Run with
java -jar -Dspring.profiles.active=prod original-testcase-1.0.0-SNAPSHOT.jar


Steps taken for production readiness:
- Jar is executable
- Profiles supported
- Logging
- Variable values are configurable on application.properties
- Used rolling file appender. The logs will be compressed when they get too long.

* Skipped JavaDoc
