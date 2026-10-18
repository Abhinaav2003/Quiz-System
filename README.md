# Online Exam System

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?style=flat-square&logo=openjdk&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot 3">
  <img src="https://img.shields.io/badge/Spring%20Security-6.x-green?style=flat-square&logo=springsecurity&logoColor=white" alt="Spring Security 6">
  <img src="https://img.shields.io/badge/JPA-Hibernate-red?style=flat-square" alt="JPA / Hibernate">
  <img src="https://img.shields.io/badge/Database-H2-lightgrey?style=flat-square&logo=h2&logoColor=white" alt="H2 Database">
  <img src="https://img.shields.io/badge/Build-Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven">
</p>

<p align="center">
  A secure web-based examination platform built with Spring Boot, Spring Security, Thymeleaf, and Bootstrap 5.
</p>

<p align="center">
  <b>Exam Management · Timed Online Tests · Automatic Evaluation · Result Tracking</b>
</p>

---

## Table of Contents

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

---

## Overview

Online Exam System is a web application that gives administrators a central place to create and manage examinations, and gives students a straightforward way to take them online.

The platform covers the full examination lifecycle: exam and question management, timed assessments, automatic scoring, student account administration, and result tracking. It also serves as a practical reference implementation of Spring Boot, Spring Security, Spring Data JPA, Hibernate, and Thymeleaf working together.

---

## Features

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

### Admin Dashboard

<p align="center">
  <img src="./screenshots/Admin%20Page.png" alt="Admin dashboard" width="90%">
</p>

### Exam Management

<p align="center">
  <img src="./screenshots/Questions%20Page.png" alt="Exam management page" width="90%">
</p>

### Question Management

<p align="center">
  <img src="./screenshots/ManageQuestion%20Page.png" alt="Question management page" width="90%">
</p>

### Student Dashboard

<p align="center">
  <img src="./screenshots/Student%20Page.png" alt="Student dashboard" width="90%">
</p>

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Backend Framework | Spring Boot 3.x |
| Security | Spring Security 6 |
| Templating | Thymeleaf, HTML5, CSS3 |
| UI Framework | Bootstrap 5 |
| Charts | Chart.js (optional) |
| ORM | Hibernate / Spring Data JPA |
| Database | H2 (file-based) |
| Build Tool | Maven |
| File Storage | Local file system |
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
│   │       │   ├── js/
│   │       │   └── images/
│   │       └── application.properties
│   └── test/
├── data/
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

**2. Build the application**

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

**3. Run the application**

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, run `OnlineExamApplication.java` directly from your IDE.

**4. Open the application**

Navigate to **http://localhost:8080**. The port may differ if `server.port` is changed in `application.properties`.

---

## Configuration

The application uses a file-based H2 database, so data persists locally between restarts.

### Example `application.properties`

Use this as a reference and adapt it to your project's dependencies and existing settings.

```properties
spring.datasource.url=jdbc:h2:file:./data/examdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

server.port=8080
```

### H2 Console

When enabled and permitted by Spring Security, the console is available at **http://localhost:8080/h2-console**.

| Setting | Value |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/examdb` |
| Username | `sa` |
| Password | `password` |
| Driver Class | `org.h2.Driver` |

### Configuration Notes

- The JDBC URL entered in the console must match `spring.datasource.url` exactly.
- The console path must match `spring.h2.console.path`.
- Spring Security may require explicit configuration to allow access to the console.
- `spring.jpa.hibernate.ddl-auto=update` is suited to development. Use a managed migration tool such as Flyway or Liquibase in production.
- Do not commit real credentials to version control. Externalize secrets through environment variables or a secrets manager.

---

## Default Administrator Account

The application may create an administrator account on startup. Check `OnlineExamApplication.java` and the security configuration for the actual credentials.

| Field | Example Value |
|---|---|
| Username | `admin` |
| Password | `adminpass` |

These values are illustrative and have not been independently verified against the current source.

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

This removes generated build artifacts in `target/` and rebuilds the project. It does not intentionally delete the H2 database or uploaded profile pictures.

---

## Roadmap

Planned and potential enhancements:

- [ ] Question banks with randomized question and option ordering
- [ ] Support for additional question types (multi-select, short answer)
- [ ] Exam scheduling and access windows
- [ ] Exportable reports (PDF / CSV)
- [ ] Email notifications and password recovery
- [ ] Migration to a production-grade database (PostgreSQL / MySQL)
- [ ] Docker support and CI/CD pipeline

---

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes with clear, descriptive messages
4. Push the branch and open a pull request

---

## License

Add your license information here (for example, MIT) and include a corresponding `LICENSE` file in the repository.

---

## Authors
 
This project was designed and developed by:
 
- **Abhinav Tiwari**
- **Anshu Rajput**
 
---
