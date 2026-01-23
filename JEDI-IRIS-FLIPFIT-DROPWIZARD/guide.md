# FlipFit Application Guide

This guide provides instructions on how to set up, build, and run the FlipFit application, along with an overview of its architecture and basic API interactions.

## 1. Architecture Overview

The FlipFit application is built using Dropwizard, a Java framework for developing RESTful web services. It follows a layered architecture:

*   **`flipfitApplication.java`**: The main entry point of the application, responsible for configuring and orchestrating all components.
*   **`flipfitConfiguration.java`**: Defines application-specific settings, including database connection details.
*   **`core/` (Models/Entities)**: Contains the core business objects (e.g., `User`, `Center`, `Slot`, `Booking`, `Payment`, `Role`, `Status`).
*   **`db/` (Data Access Objects - DAOs)**: Interfaces for interacting with the database using JDBI. Each DAO corresponds to a core entity and provides CRUD (Create, Read, Update, Delete) operations. This layer also includes custom JDBI mappers for `Role`, `Status`, and `LocalDateTime`.
*   **`service/` (Service Layer)**: Encapsulates the business logic. Services use DAOs to perform database operations and enforce business rules. They act as an intermediary between resources and DAOs.
*   **`resources/` (API Endpoints)**: RESTful endpoints that handle incoming HTTP requests, delegate to the service layer for business logic, and return appropriate responses.

## 2. Setting up the Development Environment

1.  **Java Development Kit (JDK)**: Ensure you have JDK 11 or later installed.
2.  **Apache Maven**: Make sure Maven is installed and configured in your PATH.
3.  **PostgreSQL (or H2 for local development)**:
    *   **PostgreSQL**: Install PostgreSQL and create a database named `flipfit`. Ensure you have appropriate user credentials.
    *   **H2 (for testing/local)**: The application is configured to use H2 as an in-memory database for quick local testing if a PostgreSQL database is not explicitly configured in `config.yml`.

## 3. Project Structure

The project follows a standard Maven directory structure. Key directories:
*   `src/main/java/com/flipfit/`: Contains all Java source code.
*   `src/main/java/com/flipfit/core/`: Domain models and enums.
*   `src/main/java/com/flipfit/db/`: JDBI DAOs and mappers.
*   `src/main/java/com/flipfit/service/`: Business logic services.
*   `src/main/java/com/flipfit/resources/`: REST API endpoints.
*   `src/main/resources/`: Configuration files and other resources.
*   `src/main/resources/assets/`: This is where `openapi.json` will be generated.
*   `src/main/resources/swagger-ui/`: This is where you will place the Swagger UI static files.

## 4. Building the Application (with OpenAPI Spec Generation)

Navigate to the project's root directory (`/Users/deepkumar.patel/deepkpat/jedi/flipfit/flipfit/`) in your terminal and run:

```bash
mvn clean install
```

This command compiles the code, runs tests, and packages the application into an executable JAR file in the `target/` directory. Crucially, the `swagger-maven-plugin-jakarta` will execute during the `compile` phase, generating the `openapi.json` file in `target/classes/assets/`.

## 5. Configuration

The application uses `config.yml` for configuration. A sample `config.yml` should look like this (you might need to create it in the root directory if it doesn't exist or is empty):

```yaml
server:
  type: default
  applicationConnectors:
    - type: http
      port: 8080
  adminConnectors:
    - type: http
      port: 8081

database:
  driverClass: org.postgresql.Driver # Use org.h2.Driver for H2
  url: jdbc:postgresql://localhost:5432/flipfit # Use jdbc:h2:mem:flipfit;DB_CLOSE_DELAY=-1 for H2
  user: postgres
  password: your_postgres_password
  # properties:
  #   charSet: UTF-8
  #   hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect
  #   hibernate.hbm2ddl.auto: update # Caution: Use with care in production

healthCheck: true # Enable/disable the basic ping health check
```

**Note**: For local development or testing without a PostgreSQL instance, you can use H2 in-memory database. Update the `database` section in `config.yml` as follows:

```yaml
database:
  driverClass: org.h2.Driver
  url: jdbc:h2:mem:flipfit;DB_CLOSE_DELAY=-1 # In-memory database, data wiped on restart
  user: sa
  password: sa
```

## 6. Running the Application

After building, you can run the application using the generated JAR file and the `config.yml`:

```bash
java -jar target/flipfit-1.0-SNAPSHOT.jar server config.yml
```

The application will start, and you should see console output indicating that the various services and resources are initialized and running on the configured ports (default 8080 for application, 8081 for admin).

## 7. Accessing OpenAPI Specification and Swagger UI

TODO

## 8. Basic API Interaction (Using `curl` or Postman)

Once the application is running, you can interact with its API endpoints.

### Health Check

*   **GET `/healthcheck`** (Admin Port - default 8081)
    ```bash
    curl -X GET http://localhost:8081/healthcheck
    ```
    Expected output: JSON detailing the health of registered checks (e.g., `ping` health check).

### Root Resource

*   **GET `/`** (Application Port - default 8080)
    ```bash
    curl -X GET http://localhost:8080/
    ```
    Expected output: `Hello from FlipFit!`

### User Management

*   **Register a User (Admin)**
    *   **POST `/users/register`** (Application Port - default 8080)
    *   **Body (JSON):**
        ```json
        {
            "name": "Admin User",
            "email": "admin@example.com",
            "password": "password123",
            "role": "ADMIN"
        }
        ```
    *   **Command:**
        ```bash
        curl -X POST -H "Content-Type: application/json" -d '{"name": "Admin User", "email": "admin@example.com", "password": "password123", "role": "ADMIN"}' http://localhost:8080/users/register
        ```
        Repeat for `GYM_MEMBER` and `GYM_OWNER` roles. Note the `id` returned for future operations.

*   **Get User Details**
    *   **GET `/users/{id}`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X GET http://localhost:8080/users/<USER_ID>
        ```

### Gym Owner Operations

*   **Add a Center** (requires a registered `GYM_OWNER` user ID)
    *   **POST `/gymowner/centers`** (Application Port - default 8080)
    *   **Body (JSON):**
        ```json
        {
            "name": "Downtown Fitness",
            "address": "123 Main St",
            "gymOwnerId": "<GYM_OWNER_ID>"
        }
        ```
    *   **Command:**
        ```bash
        curl -X POST -H "Content-Type: application/json" -d '{"name": "Downtown Fitness", "address": "123 Main St", "gymOwnerId": "<GYM_OWNER_ID>"}' http://localhost:8080/gymowner/centers
        ```
        Note the `id` returned for the center.

*   **Add a Slot to a Center** (requires a `GYM_OWNER` user ID and `CENTER_ID`)
    *   **POST `/gymowner/slots`** (Application Port - default 8080)
    *   **Body (JSON):**
        ```json
        {
            "centerId": "<CENTER_ID>",
            "startTime": "2026-02-01T09:00:00",
            "endTime": "2026-02-01T10:00:00",
            "capacity": 10
        }
        ```
    *   **Command:**
        ```bash
        curl -X POST -H "Content-Type: application/json" -d '{"centerId": "<CENTER_ID>", "startTime": "2026-02-01T09:00:00", "endTime": "2026-02-01T10:00:00", "capacity": 10}' http://localhost:8080/gymowner/slots
        ```

### Admin Operations

*   **Approve a Center** (requires a `CENTER_ID` created by a gym owner)
    *   **POST `/admin/centers/{id}/approve`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X POST http://localhost:8080/admin/centers/<CENTER_ID>/approve
        ```

### Gym Member Operations

*   **List Centers**
    *   **GET `/gymmember/centers`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X GET http://localhost:8080/gymmember/centers
        ```

*   **List Available Slots for a Center**
    *   **GET `/gymmember/centers/{centerId}/slots`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X GET http://localhost:8080/gymmember/centers/<CENTER_ID>/slots
        ```

*   **Book a Slot** (requires a registered `GYM_MEMBER` user ID and `SLOT_ID`)
    *   **POST `/gymmember/{gymMemberId}/book/{slotId}`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X POST http://localhost:8080/gymmember/<GYM_MEMBER_ID>/book/<SLOT_ID>
        ```

*   **Join Waitlist (if slot is full)**
    *   **POST `/gymmember/{gymMemberId}/waitlist/{slotId}`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X POST http://localhost:8080/gymmember/<GYM_MEMBER_ID>/waitlist/<SLOT_ID>
        ```

*   **Get Gym Member's Schedule**
    *   **GET `/gymmember/{gymMemberId}/schedule`** (Application Port - default 8080)
    *   **Command:**
        ```bash
        curl -X GET http://localhost:8080/gymmember/<GYM_MEMBER_ID>/schedule
        ```

## 9. Next Steps & Potential Enhancements

*   **Authentication & Authorization**: Currently, the API assumes basic authentication (or is open). Implement proper token-based authentication (e.g., JWT) and role-based authorization to secure endpoints.
*   **Error Handling**: Enhance error handling and provide more descriptive error messages.
*   **Validation**: Add more robust input validation for all API requests.
*   **Logging**: Implement comprehensive logging using Dropwizard's logging capabilities.
*   **Testing**: Write unit and integration tests for DAOs, Services, and Resources.
*   **Notification System**: The UML diagram feedback suggests notifications should be events. Implement an event bus (e.g., Guava EventBus) or a message queue (e.g., Kafka, RabbitMQ) to handle notifications asynchronously.
*   **Concurrency**: Consider thread safety and concurrency for operations like booking slots, especially in a high-traffic environment.
*   **Unique IDs**: While `UUID.randomUUID().toString()` is used, for very high-scale systems, consider distributed ID generators.
*   **Deployment**: Set up deployment pipelines for continuous integration and continuous delivery.
*   **API Documentation (Enhanced)**: While `swagger-maven-plugin-jakarta` generates the basic spec, you can further annotate your resource methods and DTOs with `io.swagger.v3.oas.annotations` (e.g., `@Operation`, `@ApiResponse`, `@Schema`) to provide richer documentation in the Swagger UI.
*   **Password Hashing**: Implement robust password hashing (e.g., BCrypt) instead of storing plain text.
*   **Complex Queries**: Implement more complex queries in DAOs as needed (e.g., finding available slots within a date range, searching centers by location).