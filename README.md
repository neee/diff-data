# Application: find differences between input Base64 encoded data

## Setup

For run application:
1. Download source from github
2. Change directory to root project folder
3. Use gradle for start the app: `./gradlew bootRun`
After the run application you can see the log like that
```
    Jetty started on port(s) 9090 (http/1.1) with context path '/'
    Started DiffApplication in 2.078 seconds (JVM running for 2.392)
```
Swagger endpoint for localhost: http://localhost:9090/swagger-ui.html

Another variant start app via docker:
1. Download source from github
2. Change directory to root project folder
3. Use gradle to create docker image (you have already installed docker service on you computer), command: ` ./gradlew dockerBuildImage
`
4. After creation image check the image: `docker images`, there have to be image with name: `ru.serdyuk/diff-data:0.0.1-snapshot`
5. Run the image: `docker run -p 9090:9090 ru.serdyuk/diff-data:0.0.1-snapshot`

Swagger endpoint for localhost: http://localhost:9090/swagger-ui.html
--- 

## Available methods
```
POST /v1/diff/{key}/left - put first value
POST /v1/diff/{key}/right - put second value
GET  /v1/deff/{key} - get result
```
Keep in mind, is it not final version, and the storage (for values) persists values, good way to implement external
storage with evictions policy. it might use mongodb or some distributed cache (like redis or hazelcast).

---
