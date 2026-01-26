# Project Overview

This project is a Java-based RESTful web service for a gym and fitness center management platform called "FlipFit". It is built using the Dropwizard framework and Maven. The application allows users to register as admins, gym owners, or gym members. Gym owners can add and manage their fitness centers and class slots. Gym members can browse and book available slots.

The application follows a layered architecture:

*   **Core:** Contains the core business objects (e.g., `User`, `Center`, `Slot`, `Booking`).
*   **DB:** Data Access Objects (DAOs) for interacting with the database using JDBI.
*   **Service:** Encapsulates the business logic.
*   **Resources:** Defines the RESTful API endpoints.

The application uses an in-memory H2 database by default, but can be configured to use a PostgreSQL database.

# Building and Running

1.  **Build the application:**

    ```bash
    mvn clean install
    ```

    This command will compile the code, run tests, and package the application into an executable JAR file in the `target/` directory.

2.  **Run the application:**

    ```bash
    java -jar target/flipfit-1.0-SNAPSHOT.jar server config.yml
    ```

    The application will start on port 8080, with the admin server on port 8081.

# Development Conventions

*   **Code Style:** The code follows standard Java conventions.
*   **Testing:** The project is set up for testing, but no tests have been implemented yet. The `guide.md` suggests writing unit and integration tests for DAOs, Services, and Resources.
*   **API Documentation:** The project is configured to use Swagger for API documentation, but it has not been fully set up yet. The `guide.md` provides instructions on how to access the OpenAPI specification and Swagger UI once it is set up.
*   **Authentication & Authorization:** The `guide.md` mentions that the API is currently open and suggests implementing proper token-based authentication (e.g., JWT) and role-based authorization to secure endpoints.
*   **Password Hashing:** The `guide.md` suggests implementing robust password hashing (e.g., BCrypt) instead of storing plain text.
*   **Logging:** The project is set up for logging, and the `guide.md` suggests implementing comprehensive logging using Dropwizard's logging capabilities.
*   **Error Handling:** The `guide.md` suggests enhancing error handling and providing more descriptive error messages.
*   **Validation:** The `guide.md` suggests adding more robust input validation for all API requests.
