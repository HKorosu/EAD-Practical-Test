# Student Information System

Spring Boot web application for managing students and their subject scores.

## Requirements

- Java 21 or later
- MySQL with a database named `sis`

By default, the application connects to `jdbc:mysql://localhost:3306/sis` as `root` with an empty password. Configure `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables to use different connection settings. Hibernate creates or updates the tables, and the application inserts the sample subjects and student if they are not already present.

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

Open `http://localhost:8080`. Use **Sinh viên** to add a student and **Điểm số** to enter scores.

The final score is `0.3 × score 1 + 0.7 × score 2`: A (8–10), B (6–<8), D (4–<6), F (<4).

Run tests with:

```powershell
.\mvnw.cmd test
```

Tests use an in-memory H2 database and do not require MySQL.
