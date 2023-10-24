# Drone Controller

## Used Technologies

* Java 17
* Spring Boot 3.1.3
* H2 Database
* Spring Boot JPA
* Lombok
* Docker


# How to run

### Run only test cases

```sh
mvn test -Dspring.profiles.active=test
```

### Package the application as a JAR file

```sh
mvn clean install -DskipTests
```

### Run the Spring Boot application and PostgreSQL with Docker Compose
(for docker build change the database config postgresql in application.properties)

```sh
docker-compose up -d --build
```

## Testing Coverage

* Implement unit tests, integration tests with JUnit5 and mockito.
* Implement JaCoCo to measure code coverage and ensure that critical code paths are tested.

## Design Approach
* For a single drone controller, I can handle without in datastore like H2. I consider a server where multiple drones can be connected and controlled by client
* I can use a HashMap for local data storage considering better performance for small data, but I would consider moving to an H2 database for extensibility.
* Implement RESTful API Design Pattern with controller, service and repository pattern
* Implement a consistent error-handling mechanism using Spring's exception handling.
* Implement Dependency Injection (DI) with constructor injection

## Advance Plan
* Replace in memory database to store the information for keeping history considering server can be restarted
* Set Drone initialized location by GPS tracking

## API Definition

OpenAPI implemented for API definition 
* http://localhost:8080/api-docs 
* http://localhost:8080/swagger-ui/index.html


