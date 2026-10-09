# Job Portal Management System

A REST API for job postings and applications. Technology stack retained: Java 21, Spring Boot, Spring Data JPA, Maven and PostgreSQL. Use pgAdmin 4 to create/manage the PostgreSQL database.

## Requirements
- JDK 21
- PostgreSQL (pgAdmin 4 is the GUI for PostgreSQL)
- Maven, or use the included Maven Wrapper

## 1. Create database
Open pgAdmin 4, connect to your PostgreSQL server, open Query Tool, and run `CREATE DATABASE job_portal;` (also provided in `database/create_database.sql`).

## 2. Set database credentials
Edit `src/main/resources/application.properties`. Defaults are host `localhost`, port `5432`, database `job_portal`, username `postgres`, password `password`. Replace the password with your actual PostgreSQL password. Alternatively configure `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables.

## 3. Run
Open a terminal in the project directory.
- Windows: `mvnw.cmd spring-boot:run`
- Linux/macOS: `chmod +x mvnw && ./mvnw spring-boot:run`
- Or use installed Maven: `mvn spring-boot:run`

The API listens at `http://localhost:8080`. Hibernate creates/updates tables on startup. Internet access may be needed on the first Maven run to download dependencies.

## API endpoints
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/jobs` | List jobs |
| GET | `/api/jobs?keyword=java` | Search title, company or location |
| GET | `/api/jobs/{id}` | Get job by ID |
| POST | `/api/jobs` | Create job |
| PUT | `/api/jobs/{id}` | Update job |
| DELETE | `/api/jobs/{id}` | Delete job |
| POST | `/api/jobs/{jobId}/applications` | Apply for a job |
| GET | `/api/jobs/{jobId}/applications` | List applications for a job |
| GET | `/api/applications` | List all applications |

## Example: create a job
POST `http://localhost:8080/api/jobs`, JSON body:
```json
{
  "title": "Junior Java Developer",
  "company": "Example Technologies",
  "location": "Chennai",
  "description": "Work on Java and Spring Boot applications.",
  "employmentType": "Full-time",
  "salaryMin": 300000,
  "salaryMax": 500000
}
```

## Example: apply for job ID 1
POST `http://localhost:8080/api/jobs/1/applications`, JSON body:
```json
{
  "applicantName": "Arun Kumar",
  "applicantEmail": "arun@example.com",
  "phone": "9876543210",
  "resumeUrl": "https://example.com/resume"
}
```

Use Postman, Insomnia, or curl to call the REST API. This project is a backend API; it does not include a separate frontend website.
