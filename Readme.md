# Spring Boot CRUD Application with Spring Security

## Project Overview

This project demonstrates the development of a **Spring Boot CRUD (Create, Read, Update, Delete) application** with database integration and role-based authentication using Spring Security.

The application modernizes traditional Spring MVC and Hibernate-based CRUD operations using Spring Boot, Spring Data JPA, and Spring Security.

It also implements secure user authentication, password encryption, and authorization based on user roles (ADMIN and USER).

## What is Spring Boot?

Spring Boot is a Java-based framework built on top of the Spring Framework. It simplifies application development by providing auto-configuration, embedded servers, and production-ready features.

It reduces manual configuration and boilerplate code, allowing developers to focus on application functionality.

## Project Objectives

- Build a CRUD application using Spring Boot.
- Implement database operations using Spring Data JPA.
- Manage user information through a web interface.
- Implement authentication using Spring Security.
- Secure passwords using BCrypt encryption.
- Implement role-based access control.
- Restrict application features based on user permissions.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Application framework |
| Spring MVC | Web application architecture |
| Spring Data JPA | Database operations |
| Hibernate | ORM implementation |
| Spring Security | Authentication and authorization |
| MySQL | Relational database |
| Maven | Dependency management |
| Thymeleaf | Server-side HTML templates |
| HTML/CSS | Frontend development |
| Git & GitHub | Version control |

## Project Features

### 1. CRUD Operations

The application supports complete user management functionality:

- **Create:** Add new users to the database.
- **Read:** Display all users and retrieve individual user records.
- **Update:** Edit existing user information.
- **Delete:** Remove users from the database.

### 2. Form Validation

The application implements server-side validation to maintain data accuracy.

- First name and last name are required.
- Names must contain only alphabetic characters.
- Email address must be valid.
- Email addresses must be unique.
- Required fields cannot be left empty.

### 3. Database Integration

MySQL is used as the relational database.

Spring Data JPA simplifies database operations through repository interfaces, while Hibernate handles object-relational mapping.

The application manages three main entities:

- **User:** Stores user details, email, and encrypted password.
- **Role:** Stores application roles such as ADMIN and USER.
- **Users_Roles:** Maintains the many-to-many relationship between users and roles.

### 4. Spring Security Authentication

Spring Security is integrated to secure the application.

Authentication is performed using user credentials stored in the MySQL database.

Key components include:

- CustomUserDetailsService
- UserDetails implementation
- DaoAuthenticationProvider
- AuthenticationManager
- BCryptPasswordEncoder
- SecurityFilterChain

### 5. Role-Based Authorization

The application supports two roles:

| Role | Permissions |
|---|---|
| ADMIN | Access to administrative pages and protected admin operations |
| USER | Access to user-specific pages |

Access restrictions are configured using Spring Security authorization rules.

- ADMIN users can access `/admin/**`.
- USER and ADMIN users can access `/user/**`.
- Unauthenticated users are redirected to the login page for protected resources.

### 6. Password Security

Passwords are encrypted using BCrypt before being stored in the database.

Spring Security verifies submitted passwords against the stored BCrypt hashes during authentication.

### 7. Login and Logout

The application includes:

- Login page.
- Email-based authentication.
- Password-based authentication.
- Secure logout functionality.
- Role-based post-login redirection.

## Project Architecture

The application follows a layered architecture to separate responsibilities.

| Layer | Responsibility |
|---|---|
| Controller | Handles HTTP requests and responses |
| Service | Contains application business logic |
| Repository | Communicates with the database |
| Model | Defines JPA entities |
| Config | Contains Spring Security configuration |
| Templates | Contains Thymeleaf HTML pages |

## Database Design

The application uses the following database entities:

**Users**
- ID
- First Name
- Last Name
- Email
- Password

**Roles**
- ID
- Name

**Users_Roles**
- User ID
- Role ID

A many-to-many relationship allows a user to have multiple roles and a role to be assigned to multiple users.

## Application Workflow

1. A user opens the application.
2. Spring Security checks whether the user is authenticated.
3. Unauthenticated users are directed to the login page.
4. The user enters their email and password.
5. CustomUserDetailsService retrieves the user from MySQL.
6. Spring Security verifies the password using BCrypt.
7. The user's assigned roles are loaded.
8. Authorization rules determine which resources the user can access.
9. Authorized users can access the appropriate application features.

## Getting Started

### Prerequisites

Before running the application, ensure you have installed:

- Java JDK
- Maven
- MySQL Server
- IntelliJ IDEA or Eclipse
- Git

### Installation and Setup

1. Clone the repository from GitHub and import it into your preferred Java IDE (e.g., IntelliJ IDEA).
2. Create the required MySQL database (matching `spring.datasource.url` in `application.properties`).
3. Set the required environment variables in your IDE Run Configuration (`Run → Edit Configurations... → Environment variables`) or terminal:
   - `DB_USERNAME` and `DB_PASSWORD` for MySQL access.
   - `ADMIN_PASSWORD` and `USER_PASSWORD` for initial test account passwords (injected via `@Value` and BCrypt-encoded on startup).
4. Build the project using Maven (`./mvnw clean install`) and allow Spring Boot to initialize/update the database tables.
5. Run the Spring Boot application on local port `8080`.
6. Open `http://localhost:8080/login` in your browser and log in with either the `ADMIN` account (redirects to `/admin`) or `USER` account (redirects to `/user`) using the passwords set in your environment variables.
7. If logged in with ADMIN credentials, add a new user.
   

## Learning Outcomes

Through this project, I gained practical experience in:

- Migrating traditional Spring MVC applications to Spring Boot.
- Understanding Spring Boot autoconfiguration.
- Implementing CRUD operations using Spring Data JPA.
- Designing layered application architecture.
- Working with MySQL and Hibernate.
- Implementing authentication and authorization using Spring Security.
- Managing user roles and permissions.
- Securing passwords using BCrypt.
- Managing projects using Git and GitHub.


Developed as a Java back end Development project to demonstrate Spring Boot, database management, CRUD operations, and Spring Security implementation.