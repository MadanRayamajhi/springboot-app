# Spring Boot CI/CD Demo

A demo Spring Boot web application built with Java 11, Maven, Thymeleaf, and a CI/CD pipeline configuration.

## Project Overview

This project includes:

- `DemoApplication` — Spring Boot application entry point
- `HomeController` — serves web pages at `/` and `/about`
- `HealthController` — exposes REST endpoints at `/health` and `/api/info`
- Thymeleaf templates in `src/main/resources/templates`
- Maven build support with `spring-boot-maven-plugin`
- CI/CD pipeline configuration in `Jenkinsfile`
- Docker support via `docker-compose.yml` and `docker-compose.prod.yml`

## Key Endpoints

- `GET /` — Home page with application status and navigation
- `GET /about` — About page describing the project
- `GET /health` — JSON health check
- `GET /api/info` — JSON application metadata

## Requirements

- Java 11
- Maven (or use the included Maven wrapper)
- Docker (optional)

## Run Locally

From the project root on Linux/macOS or WSL:

```bash
./mvnw spring-boot:run
```

From Windows PowerShell or CMD:

```powershell
mvnw.cmd spring-boot:run
```

Then open `http://localhost:8080` in your browser.

## Build and Run Packaged Jar

```bash
./mvnw clean package
java -jar target/demo-1.0.0.jar --server.port=8081
```

## Docker

To start the development containers defined in `docker-compose.yml`:

```bash
docker compose up --build
```

To run the production container defined in `docker-compose.prod.yml`:

```bash
docker compose -f docker-compose.prod.yml up --build
```

## CI/CD Pipeline

The `Jenkinsfile` defines a pipeline with these stages:

1. Checkout from GitHub
2. Build and test with Maven
3. SonarQube analysis
4. Package the application
5. Build Docker image
6. Push image to Docker Hub
7. Deploy to AWS EC2

> Note: The current `Jenkinsfile` references `madanrayamajhi/springboot-app` and an EC2 host.

## Project Details

- Java version: 11
- Spring Boot version: 2.7.18
- Templates: `home.html`, `about.html`
- REST endpoints return structured JSON for health and info
- Web UI uses Thymeleaf and inline CSS styling

## Useful Files

- `pom.xml` — Maven project configuration
- `Jenkinsfile` — Jenkins pipeline definition
- `docker-compose.yml` — local CI service stack
- `docker-compose.prod.yml` — production app container
- `nginx/nginx.conf` — Nginx reverse proxy configuration

## Notes

- Use the Maven wrapper to avoid requiring a local Maven install.
- The application is intended as a CI/CD demo rather than a production business app.
