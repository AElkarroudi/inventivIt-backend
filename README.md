# Dream Case API

## Overview
Dream Case API is a Spring Boot application designed to manage case records. It provides endpoints for creating, retrieving, updating, and deleting cases, with pagination and sorting capabilities.

## Features
- **Spring Boot** application
- **PostgreSQL** as the main database
- **H2** database for testing
- **Docker & Docker Compose** for containerized deployment
- **Environment variables** for configuration management

## Prerequisites
Ensure you have the following installed:
- Java 17+
- Docker & Docker Compose
- PostgreSQL (if running without Docker)
- Make (for Makefile usage)

## Getting Started
### 1. Clone the repository
```sh
git clone https://github.com/your-repo/dream-case-api.git
cd dream-case-api
```

### 2. Set up environment variables
Copy the `.env.example` file and configure your database credentials.
```sh
cp .env.example .env
```
Edit the `.env` file and set the required values:
```sh
APPLICATION_NAME="Dream Case"
APPLICATION_VERSION=1
POSTGRESQL_URL=jdbc:postgresql://localhost:5432/dreamcase
POSTGRESQL_USERNAME=your_username
POSTGRESQL_PASSWORD=your_password
SERVER_PORT=8081
```

### 3. Run with Docker
Build and start the application using Docker Compose:
```sh
docker-compose up --build -d
```

### 4. Run using Makefile
```sh
make build  # Build the project
make run    # Run the application
make test   # Run tests
```

## API Endpoints
| Method   | Endpoint                | Description                  |
|----------|-------------------------|------------------------------|
| GET      | `/api/v1/cases/all`      | Retrieve all cases          |
| GET      | `/api/v1/cases`          | Retrieve cases with paging  |
| GET      | `/api/v1/cases/{id}`     | Retrieve a specific case    |
| POST     | `/api/v1/cases`          | Create a new case           |
| PUT      | `/api/v1/cases/{id}`     | Update an existing case     |
| DELETE   | `/api/v1/cases/{id}`     | Delete a case               |

## Running Tests
H2 is used for testing. Run tests with:
```sh
mvn test
```
