# Sectors Backend Service

This is a Spring Boot application that provides backend services for managing sectors.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed Java 11 or later.
- You have installed Maven.

## Installing Sectors Backend Service

To install Sectors Backend Service, follow these steps:

1. Clone the repository from GitHub:

```bash
git clone https://github.com/yourusername/sectors-backend.git
```

## Running Sectors Backend Service

`cd sectors-backend`

Build the project and run the unit tests:

`mvn clean install`


Run the application:

`mvn spring-boot:run`

The application will start and listen on http://localhost:8080.

To look around in the database you can use the H2 console at http://localhost:8080/h2-console.

The JDBC URL is `jdbc:h2:mem:sectors`.
The username is `sa` and the password is empty.