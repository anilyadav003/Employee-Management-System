# Employee Management System

> **Enterprise-style full-stack Employee Management System built with Spring Boot, React, MySQL, Spring Security and JWT.**

A production-oriented web application designed to centralize employee and organizational management through a secure REST API and modern React interface.

The project demonstrates practical experience in **backend engineering, REST API design, authentication and security, relational database modeling, frontend integration, validation, exception handling, and scalable application architecture**.

---

##  What I Built

This project is structured as a **full-stack enterprise application** with a clear separation between the frontend, backend, security layer, business logic, and persistence layer.

### Core Capabilities

-  JWT-based authentication with Spring Security
-  User and role management
-  Complete employee lifecycle management
-  Department management
-  Attendance management
-  Leave management and approval workflow
-  Data-driven administrative dashboard
-  RESTful API architecture
-  MySQL relational database integration
-  Request validation
-  Centralized exception handling
-  Protected API endpoints
-  React-based responsive frontend
-  Frontend–backend REST integration
-  Maven-based backend build
-  Git/GitHub-based development workflow

---

#  System Architecture

The application follows a layered architecture designed to keep responsibilities separated and make the system easier to maintain and extend.

```text
                         ┌─────────────────────┐
                         │     React Client     │
                         │      Frontend        │
                         └──────────┬──────────┘
                                    │
                              REST / JSON
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    Spring Boot      │
                         │      REST API       │
                         └──────────┬──────────┘
                                    │
                   ┌────────────────┼────────────────┐
                   │                │                │
                   ▼                ▼                ▼
             Controllers       Security         Validation
                   │                │
                   ▼                ▼
               Services      JWT Authentication
                   │
                   ▼
             Repositories
                   │
                   ▼
             ┌──────────────┐
             │    MySQL     │
             └──────────────┘
```

---

#  Authentication & Security

Security was treated as a core part of the application.

### Authentication Flow

```text
User
 │
 ▼
Login
 │
 ▼
Spring Security
 │
 ▼
Credential Verification
 │
 ▼
JWT Generation
 │
 ▼
React Client
 │
 ▼
JWT attached to API requests
 │
 ▼
JWT Authentication Filter
 │
 ▼
Protected REST APIs
```

### Security Implementation

- Spring Security
- JWT authentication
- BCrypt password hashing
- Stateless API authentication
- Authentication filters
- Role infrastructure
- Protected API endpoints
- Secure credential handling

Supported application roles:

```text
ADMIN
HR
EMPLOYEE
```

---

#  User Management

The User module handles the application's authentication identities and access roles.

### Capabilities

- User creation
- User listing
- User editing
- User deletion
- Role assignment
- Account status management
- Authentication integration

The application separates **authentication identity** from employee business data, allowing the security model to evolve independently from HR information.

---

#  Employee Management

The Employee module provides the core business functionality of the system.

### Employee Information

- Employee ID
- Employee Code
- Name
- Designation
- Department
- Salary
- Joining Date
- User association

### Operations

```text
Create
  ↓
Read
  ↓
Update
  ↓
Delete
```

The employee module is integrated with the department and user domains through relational mappings.

---

#  Department Management

Department management provides organizational structure for employees.

### Capabilities

- Department creation
- Department listing
- Department updates
- Department deletion
- Department code management
- Employee–department relationships

Example:

```text
Information Technology
        │
        ├── Software Engineer
        ├── Backend Developer
        └── QA Engineer
```

---

#  Attendance Management

The attendance module provides structured employee attendance tracking.

### Capabilities

- Attendance records
- Attendance status
- Employee association
- Attendance management
- Attendance statistics
- Dashboard integration

---

#  Leave Management

The leave module manages employee leave requests and their lifecycle.

```text
Employee
   │
   ▼
Leave Request
   │
   ▼
Pending
   │
   ├──────► Approved
   │
   └──────► Rejected
```

### Capabilities

- Leave request creation
- Leave tracking
- Leave status management
- Approval workflow
- Rejection workflow
- Dashboard integration

---

#  Dashboard & Analytics

The dashboard transforms application data into useful organizational metrics.

### Dashboard Capabilities

- Total employees
- Total departments
- Attendance statistics
- Leave request statistics
- Employee growth data
- Department distribution
- Summary cards
- Graphical data representation

Dashboard values are obtained from backend data and calculated from application records.

---

#  REST API Design

The backend exposes modular REST endpoints organized around business resources.

```text
/api/v1/auth
/api/v1/users
/api/v1/employees
/api/v1/departments
/api/v1/attendance
/api/v1/leaves
/api/v1/dashboard
```

### API Request Flow

```text
HTTP Request
     ↓
Controller
     ↓
Validation
     ↓
Service
     ↓
Repository
     ↓
Database
     ↓
Response DTO
     ↓
JSON Response
```

This keeps controllers lightweight and places business logic inside the service layer.

---

#  Backend Engineering

The Spring Boot backend follows a structured package architecture:

```text
com.anilyadav.ems
│
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
├── service
├── util
└── validation
```

### Engineering Practices

- DTO-based API communication
- Entity/DTO separation
- Service-layer business logic
- Repository abstraction through Spring Data JPA
- Centralized exception handling
- Bean validation
- Constructor-based dependency injection
- Lombok for boilerplate reduction
- RESTful API design
- Role-aware security architecture

---

#  Frontend Architecture

The frontend is built with React and organized around reusable application modules.

```text
src/
│
├── components/
├── constants/
├── layouts/
├── pages/
│   ├── auth/
│   ├── dashboard/
│   ├── user/
│   ├── employee/
│   ├── department/
│   ├── attendance/
│   ├── leave/
│   └── error/
│
├── routes/
├── services/
└── utils/
```

### Frontend Technologies

- React
- React Router
- Axios
- Material UI
- Vite
- JavaScript

The frontend communicates with the Spring Boot backend through REST APIs and maintains protected application navigation through authentication-aware routing.

---

#  Database Design

The system uses **MySQL** with Spring Data JPA and Hibernate.

### Core Relationships

```text
             ┌──────────┐
             │   User   │
             └────┬─────┘
                  │
                  │
             ┌────▼─────┐
             │ Employee │
             └────┬─────┘
                  │
          ┌───────┴────────┐
          ▼                ▼
   ┌────────────┐    ┌────────────┐
   │ Department │    │ Attendance │
   └────────────┘    └────────────┘

             Employee
                │
                ▼
          ┌────────────┐
          │    Leave   │
          └────────────┘
```

### Persistence Technologies

- JPA
- Hibernate
- Spring Data repositories
- Relational mappings
- Entity relationships
- Database constraints

---

#  Validation & Error Handling

The backend includes centralized mechanisms for handling invalid requests and application errors.

### Validation

Input validation is applied at the API boundary to prevent invalid data from entering the business layer.

### Exception Handling

The application uses centralized exception handling to provide consistent API responses instead of exposing raw internal exceptions.

This improves:

- API consistency
- Debugging
- Client-side error handling
- Maintainability

---

#  Testing & API Verification

The application has been verified across its major functional modules during development.

Testing and verification covered:

```text
Authentication
Users
Employees
Departments
Dashboard
Attendance
Leave
Database integration
Frontend/API integration
Validation
Exception handling
```

API behavior can also be independently verified using Postman.

---

#  Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Backend | Spring Boot 3.5.16 |
| Security | Spring Security + JWT |
| Persistence | Spring Data JPA |
| ORM | Hibernate |
| Database | MySQL |
| Validation | Jakarta Bean Validation |
| Frontend | React |
| UI | Material UI |
| Routing | React Router |
| HTTP Client | Axios |
| Build Tool | Maven |
| Version Control | Git + GitHub |
| API Testing | Postman |
| IDE | IntelliJ IDEA / VS Code |

---

#  Project Structure

```text
Employee-Management-System/
│
├── backend/
│   ├── pom.xml
│   └── src/
│
├── frontend/
│   ├── package.json
│   └── src/
│
└── README.md
```

---

#  Run Locally

## Prerequisites

Make sure the following are installed:

- Java 21
- Maven
- MySQL
- Node.js
- npm
- Git

---

## 1. Clone the Repository

```bash
git clone <YOUR_REPOSITORY_URL>
cd Employee-Management-System
```

---

## 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE employee_management_system;
```

Update the database configuration in:

```text
backend/src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management_system
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

## 3. Start the Backend

```bash
cd backend
mvn spring-boot:run
```

The Spring Boot backend will start on the configured application port.

---

## 4. Start the Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

The frontend will start using the Vite development server.

---

#  End-to-End Application Flow

```text
                    USER
                     │
                     ▼
                 Login Page
                     │
                     ▼
             Authentication API
                     │
                     ▼
                 JWT Token
                     │
                     ▼
              Dashboard Layout
                     │
        ┌────────────┼────────────┐
        │            │            │
        ▼            ▼            ▼
      Users      Employees   Departments
        │            │            │
        └────────────┼────────────┘
                     │
              ┌──────┴──────┐
              ▼             ▼
         Attendance       Leave
              │             │
              └──────┬──────┘
                     ▼
                  MySQL
```

---

#  Development Highlights

This project demonstrates practical experience in:

- Full-stack application development
- Java backend engineering
- Spring Boot architecture
- REST API development
- Spring Security
- JWT authentication
- BCrypt password security
- Relational database design
- JPA/Hibernate
- React development
- Frontend–backend integration
- API validation
- Centralized exception handling
- Layered architecture
- Modular application design
- Git/GitHub workflows
- Secure API development
- Enterprise-style feature organization

---

#  Engineering Highlights

The project was designed with maintainability and extensibility in mind.

### Separation of Concerns

Controllers handle HTTP requests, services contain business logic, repositories handle persistence, and DTOs define API contracts.

### Security First

Authentication and protected APIs are implemented using Spring Security and JWT rather than relying only on frontend restrictions.

### Modular Architecture

Business domains such as Employees, Departments, Attendance and Leave are separated into independent modules.

### Database-Driven Dashboard

Dashboard statistics are generated from application data rather than static frontend values.

### Full-Stack Integration

The React frontend communicates with the Spring Boot backend through REST APIs, creating a complete end-to-end application flow.

---

#  Deployment Roadmap

The application is being prepared for containerized and cloud deployment.

```text
                     GitHub
                        │
                        ▼
                   Docker Build
                        │
          ┌─────────────┴─────────────┐
          ▼                           ▼
   Spring Boot API              React Frontend
          │                           │
          └─────────────┬─────────────┘
                        ▼
                     AWS Cloud
                        │
                        ▼
                     MySQL
```

Planned infrastructure work includes:

- Docker containerization
- Docker Compose
- Production configuration
- AWS deployment
- CI/CD automation
- Cloud environment configuration
- Production monitoring and operational improvements

---

#  Project Accomplishments

### Backend

- Built a modular Spring Boot REST API
- Implemented JWT authentication
- Integrated Spring Security
- Implemented BCrypt password hashing
- Designed service and repository layers
- Implemented DTO-based API contracts
- Added validation and centralized exception handling
- Integrated MySQL using JPA/Hibernate

### Frontend

- Built a React-based management dashboard
- Implemented protected routing
- Developed reusable UI components
- Integrated REST APIs using Axios
- Built management interfaces for multiple business modules
- Implemented responsive Material UI layouts

### Application

- Connected frontend, backend and database into a complete system
- Implemented employee lifecycle management
- Implemented department management
- Implemented attendance management
- Implemented leave management
- Implemented dashboard analytics
- Established Git/GitHub development workflow

---

#  Future Enhancements

The architecture provides a strong foundation for additional enterprise capabilities such as:

- Advanced role-based access control
- Employee self-service portal
- Payroll management
- Advanced reporting
- Notifications
- Performance management
- Enhanced analytics
- Docker containerization
- AWS cloud deployment
- CI/CD automation
- OpenAPI/Swagger documentation

---

#  Author

## G. Anil Yadav

**Software Developer | Java | Spring Boot | React | MySQL**

GitHub:  
`https://github.com/anilyadav003`

LinkedIn:  
`https://linkedin.com/in/g-anil-yadav-8b963429`

---

##  Project

This project represents an end-to-end full-stack engineering journey, from database design and secure REST API development to React integration, containerization and cloud deployment.

If you find the project useful, feel free to explore the repository and follow the development journey.

```
