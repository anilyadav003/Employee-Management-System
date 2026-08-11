# Employee Management System

### Production-ready full-stack employee management platform built with React, Spring Boot, MySQL, Docker, AWS EC2 and GitHub Actions.
## 🔑 Demo Credentials

Use the following credentials to access the deployed application:

| Field | Value |
|---|---|
| Username | `Dummy` |
| Password | `dummy123` |

**Live Application:** http://65.0.45.153:5173
**Source Code:** https://github.com/anilyadav003/Employee-Management-System

---

##  Why I Built This

Managing employees in a growing organization involves much more than basic CRUD operations.

This project was built to simulate a real-world enterprise application where authentication, authorization, employee management, departments, attendance, leave management, database relationships, API security, containerization, CI, and cloud deployment work together as one system.

The focus was not only on making the application work, but also on building it with a production-oriented backend architecture and deployment workflow.

---

##  Features

###  Authentication & Authorization

- JWT-based authentication
- Spring Security integration
- BCrypt password hashing
- Role-based access control
- Admin, HR and Employee roles
- Protected REST APIs
- Stateless authentication
- Secure CORS configuration
- Logout support

###  Employee Management

- Create employees
- View employee details
- Update employee information
- Delete employees
- Search and manage employees
- Department assignment
- Employee status management

### Department Management

- Create departments
- Update departments
- Delete departments
- View departments
- Associate employees with departments

###  User Management

- Create users
- View users
- Update users
- Delete users
- Assign roles
- Manage user accounts

###  Attendance Management

- Record attendance
- View attendance records
- Manage employee attendance
- Track attendance information

###  Leave Management

- Submit leave requests
- View leave requests
- Manage leave requests
- Track employee leave information

###  Dashboard

- Employee statistics
- Department information
- User information
- Attendance information
- Leave information

---

#  Architecture

The application follows a layered backend architecture designed to keep business logic, API handling, persistence, and security concerns separated.

```text
                    CLIENT
                      │
                      ▼
              ┌───────────────┐
              │ React Frontend│
              └───────┬───────┘
                      │
                   REST API
                      │
                      ▼
              ┌───────────────┐
              │  Spring Boot  │
              │    Backend    │
              └───────┬───────┘
                      │
          ┌───────────┼───────────┐
          │           │           │
          ▼           ▼           ▼
      Security     Services    Validation
      + JWT           │
                      ▼
                 Repositories
                      │
                      ▼
                ┌───────────┐
                │   MySQL   │
                └───────────┘
```

### Backend Request Flow

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

Security is integrated into the request pipeline using Spring Security and JWT authentication.

---

#  Production Deployment

The application is deployed on an AWS EC2 instance using Docker.

```text
                    Internet
                       │
                       ▼
                 AWS EC2 Instance
                       │
                 Docker Compose
                       │
        ┌──────────────┼──────────────┐
        │              │              │
        ▼              ▼              ▼
   React Frontend  Spring Boot     MySQL
     Container      Container      Container
        │              │              │
      :5173          :8082          :3307
```

### Production Environment

- AWS EC2
- Docker
- Docker Compose
- React frontend container
- Spring Boot backend container
- MySQL container
- Environment-based configuration
- JWT authentication
- Production database

---

#  Technology Stack

| Layer | Technology |
|---|---|
| Frontend | React, JavaScript, Vite |
| Backend | Java 21, Spring Boot |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL 8.4 |
| Build | Maven |
| Containerization | Docker, Docker Compose |
| Cloud | AWS EC2 |
| Version Control | Git, GitHub |
| CI | GitHub Actions |

---

#  Security

Security was treated as a core part of the application rather than an afterthought.

The application implements:

- JWT authentication
- Spring Security
- BCrypt password hashing
- Role-based authorization
- Stateless sessions
- Protected API endpoints
- CORS configuration
- Request validation
- Environment-based secrets

Sensitive configuration such as database credentials and JWT secrets is kept outside the source code.

---

#  CI with GitHub Actions

Every push to the repository can trigger the CI workflow.

```text
Git Push
   │
   ▼
GitHub Actions
   │
   ├── Checkout Repository
   │
   ├── Setup Java 21
   │
   ├── Configure Maven
   │
   ├── Build Application
   │
   └── Run Tests
           │
           ▼
        CI Result
```

The CI pipeline helps ensure that changes pushed to the repository can be automatically built and tested.

> **Note:** AWS deployment is currently handled separately from the GitHub Actions CI workflow. Automated CD to AWS is a planned improvement.

---

#  Dockerized Application

The application is containerized so that the major components can run consistently across environments.

```text
docker-compose.yml
       │
       ├── ems-frontend
       ├── ems-backend
       └── ems-mysql
```

### Start the complete application

```bash
docker compose up -d --build
```

### Check running containers

```bash
docker ps
```

Expected services:

```text
ems-frontend
ems-backend
ems-mysql
```

### Stop the application

```bash
docker compose down
```

---

#  Project Structure

```text
Employee-Management-System/
│
├── backend/
│   ├── src/main/java/com/anilyadav/ems/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── repository/
│   │   ├── security/
│   │   ├── service/
│   │   ├── util/
│   │   └── validation/
│   │
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── ...
│   ├── Dockerfile
│   └── package.json
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

---

#  Authentication Flow

```text
1. User enters username and password
              │
              ▼
2. React sends login request
              │
              ▼
3. Spring Boot AuthenticationManager
              │
              ▼
4. User credentials verified
              │
              ▼
5. JWT token generated
              │
              ▼
6. Token returned to frontend
              │
              ▼
7. Frontend sends JWT with API requests
              │
              ▼
8. JwtAuthenticationFilter validates token
              │
              ▼
9. Request is authorized
```

---

#  Testing & CI

The project uses Maven-based build and test execution.

GitHub Actions automatically performs the backend build and test process.

The CI pipeline helps catch build or test failures before changes are considered stable.

---

#  Running Locally

## Prerequisites

Make sure the following are installed:

- Java 21
- Node.js
- npm
- MySQL
- Docker
- Docker Compose
- Git

## Clone the Repository

```bash
git clone https://github.com/anilyadav003/Employee-Management-System.git
cd Employee-Management-System
```

## Run with Docker

```bash
docker compose up -d --build
```

Check the containers:

```bash
docker ps
```

---

# ☁️ AWS Deployment

The application is deployed on an AWS EC2 instance using Docker Compose.

Production architecture:

```text
AWS EC2
   │
   ├── React Frontend Container
   │
   ├── Spring Boot Backend Container
   │
   └── MySQL Container
```

### Production Ports

| Service | Port |
|---|---:|
| Frontend | 5173 |
| Backend | 8082 |
| MySQL | 3307 |

The production frontend is accessible through:

```text
http://65.0.45.153:5173
```

---

#  Development Workflow

```text
Feature Development
        ↓
Git
        ↓
GitHub
        ↓
GitHub Actions
        ↓
Build + Test
        ↓
Docker
        ↓
AWS EC2
        ↓
Production Application
```

The project follows Git-based version control and uses GitHub Actions for continuous integration.

---

#  Engineering Practices

### Backend

- Layered architecture
- DTO-based API communication
- Service layer for business logic
- Repository pattern using Spring Data JPA
- Global exception handling
- Input validation
- Security configuration
- Role-based authorization

### Infrastructure

- Dockerized services
- Docker Compose orchestration
- AWS EC2 deployment
- Environment-based configuration
- GitHub Actions CI

### Security

- JWT authentication
- BCrypt password hashing
- Role-based access control
- Stateless sessions
- Protected REST APIs
- CORS configuration

---

#  What I Learned

Building and deploying this project provided hands-on experience across the complete software development lifecycle:

```text
System Design
     ↓
Database Design
     ↓
Backend Development
     ↓
REST APIs
     ↓
Authentication & Authorization
     ↓
Frontend Integration
     ↓
Testing
     ↓
Git & GitHub
     ↓
CI with GitHub Actions
     ↓
Docker
     ↓
AWS Deployment
     ↓
Production Debugging
```

The deployment process also provided practical experience with production concerns such as environment configuration, CORS, Docker networking, database connectivity, authentication configuration, and debugging cloud-hosted applications.

---

#  Future Improvements

- Automated CI/CD deployment to AWS
- HTTPS with a custom domain
- AWS RDS for production database hosting
- Refresh token mechanism
- Automated database backups
- Application monitoring
- Centralized logging
- Email notifications
- Payroll management
- Performance management
- Advanced reporting

---

#  Author

## Anil Yadav

GitHub:  
https://github.com/anilyadav003

---

#  Project Highlights

```text
✓ Full-stack enterprise-style application
✓ Secure JWT authentication
✓ Role-based authorization
✓ REST API architecture
✓ MySQL relational database
✓ Employee management
✓ User management
✓ Department management
✓ Attendance management
✓ Leave management
✓ Dockerized application
✓ AWS EC2 deployment
✓ GitHub Actions CI
✓ Production debugging
✓ Cloud deployment experience
```

---

## License

This project is intended for educational and portfolio purposes.
