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

## Design patterns

* RESTful API Design Pattern: REST (Representational State Transfer) expose the endpoints restfully way
* Controller-Service-Repository Pattern:
  Controller: Receives incoming HTTP requests, handles request validation, and invokes the appropriate service methods.
  Service: Contains the business logic, including validation and processing, and interacts with the repository.
  Repository: Manages data storage and retrieval.
* DTO (Data Transfer Object) Pattern: Use DTOs to transfer data between your API and the client.
* Error Handling Patterns: Implement a consistent error-handling mechanism using Spring's exception handling. Return meaningful error responses in JSON format.
* Dependency Injection (DI) Pattern: Implement DI with constructor injection

## Advance Plan
* Replace in memory database to store the information for keeping history
* Set Drone initialized location by GPS tracking

## API Definition

OpenAPI implemented for API definition 
* http://localhost:8080/api-docs 
* http://localhost:8080/swagger-ui/index.html


