
# LEDGERLINE

LEDGERLINE is a Spring Boot 3.5 inventory application with Spring MVC, Spring Security, Spring Data JPA, Hibernate, JDBC transactions, MySQL 8.4, Bean Validation and Thymeleaf. It is a modular monolith: controllers handle transport, services own business rules, repositories own persistence, and templates provide the server-rendered experience.

## Run with Docker

Requirements: Docker Desktop and Maven.

### Configure local environment

For the first run, create your local environment file:

```bash
cp .env.example .env
```

Edit `.env` before starting the application. Replace the sample database, MySQL root, and administrator passwords with your own values.

Docker Compose automatically reads the `.env` file.

Never commit `.env` or expose real credentials in the repository. The `.env.example` file contains demonstration placeholders for local development only.

### Start the application

```bash
./scripts/start.sh
```

The script compiles the executable Spring Boot JAR, builds the application image, starts MySQL 8.4, and starts LEDGERLINE on:

```text
http://localhost:8080/
```

### Stop the application

```bash
./scripts/stop.sh
```

### Default demonstration login

Only when using the unmodified local `.env.example`:

```text
Email:    admin@ledgerline.local
Password: change-me-now
```

Set `LEDGERLINE_ADMIN_EMAIL` and `LEDGERLINE_ADMIN_PASSWORD` in `.env` to use different administrator credentials.

The database is exposed to the host on port `3307`; the application uses the internal MySQL service name.

## Local Maven run

Install JDK 21+, Maven and MySQL 8.4.

Set the following environment variables:

```bash
export LEDGERLINE_DB_URL='jdbc:mysql://localhost:3306/ledgerline?createDatabaseIfNotExist=true&serverTimezone=UTC'
export LEDGERLINE_DB_USER=ledgerline
export LEDGERLINE_DB_PASSWORD=your-password
export LEDGERLINE_ADMIN_EMAIL=admin@ledgerline.local
export LEDGERLINE_ADMIN_PASSWORD=change-me-now
```

Build and run:

```bash
mvn clean test package
java -jar target/ledgerline-2.0.0.jar
```

The Maven `java-27` profile activates automatically on JDK 27+. The current development host uses the compatible Java 21 release fallback while the Docker runtime uses Eclipse Temurin 21.

## Architecture

- `LedgerlineApplication`: `@SpringBootApplication` entry point.
- `config`: Spring Security and one-time initialization beans.
- `entity`: JPA `@Entity` mappings for the existing MySQL tables.
- `repository`: Spring Data JPA repositories.
- `service`: `@Service` transactional inventory workflows.
- `controller`: Thymeleaf `@Controller` pages and `/api` `@RestController` endpoints.
- `dto`: validated request records.
- `exception`: `@RestControllerAdvice` business error responses.
- `resources/templates`: Thymeleaf views.
- `resources/static/css`: preserved LEDGERLINE visual identity.

The existing MySQL schema and records are retained. The initializer only ensures that the administrator exists and records a `DEMO_INITIALIZED` marker. It does not reseed products, suppliers, warehouses, or stock when records are deleted.

## REST examples

Authenticated REST endpoints include:

```text
GET  /api/dashboard
GET  /api/products?q=dock
POST /api/products
GET  /api/ledger
POST /api/stock
POST /api/transfers
POST /api/purchases
POST /api/purchases/{id}/approve
POST /api/purchases/{id}/receive
```

Critical stock operations use `@Transactional`, row locks, immutable movement records, and negative-balance checks.

