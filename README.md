# 📦 Spring Boot API Data Manager

A Spring Boot application that fetches data from an external API, persists it in a database (H2/PostgreSQL), and provides endpoints to **create**, **read**, **update**, and **delete** records. Includes **structured logging**, **Swagger UI**, and **unit/integration tests**.

---

## 🚀 Features

- ✅ Fetch JSON data from external API
- ✅ Persist data using JPA (H2/PostgreSQL)
- ✅ CRUD operations on stored data
- ✅ Logging with Logback (INFO/ERROR to file & console)
- ✅ Unit & Integration Testing (TDD approach)
- ✅ Swagger API documentation

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Web, Data JPA
- H2 / PostgreSQL
- Lombok
- Logback (custom logging config)
- JUnit 5 & Mockito
- Swagger / OpenAPI

---

## ⚙️ Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven
- (Optional) Docker (for PostgreSQL)

---

### ▶️ Running the App

```bash
# Build the app
mvn clean install

# Run the app
mvn spring-boot:run
