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

## Assumption

The initial task primarily focused on creating a drone server for the management of a single drone. I architected the server with the foresight to facilitate the connection and control of multiple drones, thereby enhancing scalability.

I assume that the Drone Controller server should be situated external to the drone itself. This strategic decision is particularly advantageous in scenarios where this logic is entrusted with the responsibility of supervising and orchestrating the operations of multiple drones. Additionally, this external placement allows for seamless integration with other system components. By locating the controller externally, the system benefits from centralized administration, coordination, and communication among the drones, streamlining the implementation of sophisticated control and monitoring systems.

## Design Approach
* A single drone controller doesn't require a persistent data store. However, I envision a scenario where multiple drones could connect to and be managed by a server on the client's end.
* While HashMap can serve as an efficient choice for local data storage, especially for small datasets, I'm considering the possibility of transitioning to an H2 database (which also support in mobile device) for scalability and flexibility.
* I have included the DroneServiceInMemory as a code example to illustrate how to utilize HashMap or ConcurrentHashMap.
* I am planning to adhere to the RESTful API design pattern by employing controller, service, and repository patterns.
* I intend to establish a consistent error-handling mechanism using Spring's exception handling capabilities.
* I implemented Dependency Injection (DI) through constructor injection to manage component dependencies effectively.

## Advance Plan
* Replace in memory database to store the information for keeping history considering server can be restarted
* Set Drone initialized location by GPS tracking

## API Definition
OpenAPI implemented for API definition 
* http://localhost:8080/api-docs 
* http://localhost:8080/swagger-ui/index.html


