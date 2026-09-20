# Video Game Store

## Overview

Video Game Store is a Java/Spring backend project for a video game store application.

This project is based on an earlier Java console application I built for a video game shop. I am gradually transforming the original project into a web-based application by introducing Spring Boot, REST APIs, a relational database, and eventually a frontend.

The main goal of the project is to learn how to build and structure a real-world backend application using Java and Spring while applying software engineering practices learned throughout my studies.

## Features

### Currently Implemented

* User registration
* User login
* Product management
* Fetch products through REST APIs
* MySQL database integration
* Relational database design
* Database normalization
* Layered backend architecture
* DTOs for API requests and responses
* Input validation
* Global exception handling

### Planned

* Shopping cart (under development)
* Order management (under development)
* Frontend application (soon)
* Authentication and authorization (soon)
* User roles and permissions (soon)
* Product filtering
* Additional product management operations

## Technologies

### Backend

* Java 23
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven

### Database

* MySQL
* JDBC
* JPA / Hibernate

### Frontend

* Coming soon

### Development Tools

* IntelliJ IDEA
* Git
* GitHub
* Postman

## Architecture

The backend follows a layered architecture to separate different responsibilities within the application.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

* **Controllers** handle HTTP requests and responses.
* **Services** contain the application's business logic.
* **Repositories** handle communication with the database.
* **DTOs** define the data exchanged through the API.
* **Entities** represent the application's database models.
* **Exception handling** provides consistent responses when errors occur.

This structure is intended to keep the application modular and make it easier to maintain and extend as the project grows.

## Database

The application uses MySQL as its relational database.

The database is designed using normalized relational tables to reduce data duplication and maintain relationships between related entities.

The backend communicates with the database through Spring Data JPA and Hibernate.

## Purpose

This project is primarily a learning and portfolio project.

The goal is not only to build a Video Game Store application, but also to gain hands-on experience with development and software engineering concepts, including:

* RESTful API design
* Spring Boot application development
* Layered architecture
* Relational database modeling
* Database normalization
* Object-oriented programming
* Data validation
* Exception handling
* API design
* Project structure and maintainability
* Git and GitHub workflows
* Building software incrementally

## Project Status

This project is currently on hold.
