<h1 align="center">Online Exam System</h1>

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,spring,hibernate,postgres,maven" alt="Tech stack icons" />
</p>

<p align="center">
  A secure web-based examination platform built with Spring Boot, Spring Security, Thymeleaf, Bootstrap 5, and PostgreSQL.
</p>

<p align="center">
  <b>Exam Management · Timed Online Tests · Automatic Evaluation · Result Tracking</b>
</p>

<p align="center">
  <a href="https://quiz-system-hmxo.onrender.com/">
    <img src="https://img.shields.io/badge/Live%20Demo-Online-brightgreen?style=for-the-badge" alt="Live Demo" />
  </a>
</p>

---

## Table of Contents

- [Live Demo](#-live-demo)
- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Default Administrator Account](#default-administrator-account)
- [Common Commands](#common-commands)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Authors](#authors)

---

## 🚀 Live Demo

Try the Online Exam System here: **[quiz-system-hmxo.onrender.com](https://quiz-system-hmxo.onrender.com/)**

---

## Overview

Online Exam System is a web application that gives administrators a central place to create and manage examinations, and gives students a straightforward way to take them online.

The platform covers the full examination lifecycle: exam and question management, timed assessments, automatic scoring, student account administration, and result tracking. It also serves as a practical reference implementation of Spring Boot, Spring Security, Spring Data JPA, Hibernate, PostgreSQL, and Thymeleaf working together.

---

## Features

### Project Flow Diagram

<p align="center">
  <img src="./screenshots/Flow%20Diagram.png" alt="Project flow diagram" width="90%">
</p>

### Administrator

- Secure authentication with role-based access control
- Dashboard with examination statistics
- Full lifecycle management of examinations (create, view, update, delete)
- Question management for each examination
- Student account management, including password resets and account deletion
- Review of student submissions and examination results
- Monitoring of examination and student performance

### Student

- Self-service registration and secure login
- Personalized dashboard
- Online examinations with a question palette for easy navigation
- Timed exams with automatic submission when time expires
- Score, percentage, and pass/fail status after submission
- Review of submitted answers and correct answers, where enabled
- Profile management, including profile picture and password change
- Access to previous examination results

> Feature availability reflects the current state of the source code.

---

## Screenshots

Screenshots are stored in the `screenshots/` directory.

### Front Page

<p align="center">
  <img src="./screenshots/Front%20Page.png" alt="Front page" width="90%">
</p>

### Create Account

<p align="center">
  <img src="./screenshots/Create%20Page.png" alt="Create account page" width="90%">
</p>

### Login

<p align="center">
  <img src="./screenshots/Login%20Page.png" alt="Login page" width="90%">
</p>

### Admin Dashboard

<p align="center">
  <img src="./screenshots/Admin%20Page.png" alt="Admin dashboard" width="90%">
</p>

### Manage Quiz

<p align="center">
  <img src="./screenshots/Manage%20Page.png" alt="Exam management page" width="90%">
</p>

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Backend Framework | Spring Boot 3.x |
| Security | Spring Security 6 |
| Templating | Thymeleaf, HTML5, CSS3 |
| UI Framework | Bootstrap 5 |
| Charts | Chart.js (optional) |
| ORM | Hibernate / Spring Data JPA |
| Database | PostgreSQL (Neon in production, local PostgreSQL for development) |
| Build Tool | Maven |
| Hosting | Render |
| Version Control | Git / GitHub |

---

## Project Structure

The layout below is representative; package names and folders may vary slightly in the repository.

```text
online-exam-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/exam/
│   │   │   ├── OnlineExamApplication.java
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   ├── config/
│   │   │   └── security/
│   │   └── resources/
│   │       ├── templates/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       └── application.properties
│   └── test/
├── screenshots/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## Getting Started

### Prerequisites

| Requirement | Notes |
|---|---|
| JDK 17 or higher | Verify with `java -version` |
| PostgreSQL 14 or higher | Local install, Docker, or a hosted service such as Neon; verify with `psql --version` |
| Maven | Optional; the included Maven Wrapper is recommended |
| Git | Required to clone the repository |
| IDE | VS Code, IntelliJ IDEA, or Eclipse |

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/Abhinaav2003/online-exam-system-master-main.git
cd online-exam-system-master-main
```

> If `pom.xml` is located in a nested directory, change into that directory before running Maven commands.

**2. Create the database**

Create a PostgreSQL database (local, Docker, or hosted):

```sql
CREATE DATABASE examdb;
```

**3. Configure the application**

Edit `src/main/resources/application.properties` (see [Configuration](#configuration)).

**4. Build the application**

Windows (PowerShell):

```powershell
.\mvnw.cmd clean package -DskipTests
```

macOS / Linux:

```bash
./mvnw clean package -DskipTests
```

Using a locally installed Maven:

```bash
mvn clean package -DskipTests
```

**5. Run the application**

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

macOS / Linux:

```bash
./mvnw spring-boot:run
```

Alternatively, run `OnlineExamApplication.java` directly from your IDE.

**6. Open the application**

Navigate to **http://localhost:8080**. The port may differ if `server.port` is changed in `application.properties`.

---

## Configuration

The application uses PostgreSQL for persistence. Tables are created and updated automatically by Hibernate on startup.

For local development, set these in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/examdb
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

For deployment (Render, Neon, etc.), use environment variables instead of committing credentials:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

| Variable | Description |
|---|---|
| `DB_URL` | JDBC connection URL, e.g. `jdbc:postgresql://HOST/DBNAME?sslmode=require` |
| `DB_USER` | Database username |
| `DB_PASSWORD` | Database password |

> **Never commit real database credentials to GitHub.**

---

## Default Administrator Account

The application may create an administrator account on startup. Check `OnlineExamApplication.java` and the security configuration for the actual credentials.

| Field | Value |
|---|---|
| Username | Set in source code or environment variables |
| Password | Set in source code or environment variables |

> **Security notice:** Never deploy an application with default administrator credentials. Set a strong, unique password before any public or production deployment.

---

## Common Commands

| Task | Command (PowerShell) |
|---|---|
| Clean previous build | `.\mvnw.cmd clean` |
| Build without tests | `.\mvnw.cmd clean package -DskipTests` |
| Start the application | `.\mvnw.cmd spring-boot:run` |
| Stop the application | `Ctrl + C` in the running terminal |

**Full clean rebuild**

```powershell
Remove-Item -Recurse -Force target -ErrorAction SilentlyContinue
.\mvnw.cmd clean package -DskipTests
.\mvnw.cmd spring-boot:run
```

This removes generated build artifacts in `target/` and rebuilds the project. It does not touch the PostgreSQL database or uploaded profile pictures.

---

## Roadmap

Planned and potential enhancements:

- [ ] Question banks with randomized question and option ordering
- [ ] Support for additional question types (multi-select, short answer)
- [ ] Exam scheduling and access windows
- [ ] Exportable reports (PDF / CSV)
- [ ] Email notifications and password recovery
- [ ] Schema versioning with Flyway or Liquibase
- [ ] Docker Compose setup (application + PostgreSQL) and CI/CD pipeline

---

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes with clear, descriptive messages
4. Push the branch and open a pull request

---

## License

This project is licensed under the MIT License. Add a `LICENSE` file to the repository root with the MIT license text.

---

## Authors

This project was designed and developed by:

- **Abhinav Tiwari**
- **Anshu Rajput**