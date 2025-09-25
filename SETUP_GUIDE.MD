# 🚀 ABK EduApp – Setup & Roadmap

This document explains how to set up and run the project locally, including both backend and frontend.

---

## 1. Requirements
- Java 17+
- Maven 3.8+
- PostgreSQL 16+
- Node.js 18+ & npm (or Yarn)
- Git

---

## 📂 Project Structure
```text
abk_eduapp/
├── backend/        # Spring Boot backend
└── frontend/       # Vue 3 frontend

---

## 2. Clone the Repository
```bash
git clone https://github.com/alpbatuhankarakas/abk_eduapp.git
cd abk_eduapp

---



---

## Backend Setup (Spring Boot)

Navigate:
cd backend

Copy the example config:
cp src/main/resources/application.properties.example src/main/resources/application.properties

Create database:
CREATE DATABASE abkschool OWNER abkschool ENCODING 'UTF8';

Run:
mvn spring-boot:run

Endpoints:
API: http://localhost:8090/api

Swagger: http://localhost:8090/swagger-ui.html

---

## Frontend Setup (Vue 3)

Navigate:
cd frontend

Copy env file:
cp .env.example .env

Install dependencies:
npm install

Run:
npm run dev

URL:
Frontend: http://localhost:5173/

---

## Running Tests
Backend:
cd backend
mvn test


---

## ----------Roadmap--------
✅ CRUD for Students, Teachers, Courses, Enrollments
✅ Enrollment date overlap validation
✅ Average grade views
✅ Swagger documentation
🔜 Role-based authentication
🔜 Improved frontend UI
🔜 GitHub Actions CI/CD
🔜 Docker Compose one-command setup
