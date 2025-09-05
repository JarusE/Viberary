SpringBoot Book Microservices Project

A complete Spring Boot microservices project with Docker, MySQL, and Google Books API integration. The project includes authentication, role-based access control via Spring Security, and a basic Angular frontend.
Architecture

The project uses a microservices architecture with the following services:

    Auth Service – manages users, roles, and JWT-based authentication

    User Service – manages user profiles and related data

    Book Service – stores book information and populates the database using Google Books API

    Booking Service – handles book reservations and tracking

    Gateway Service – API Gateway for routing and centralized security

    Config Service – centralized configuration management

    Frontend – Angular-based UI for interacting with the backend

    Database – MySQL, running via Docker

Each service runs in a separate Docker container.

Technologies

    Java 17+

    Spring Boot (Web, Security, Data JPA, Cloud)

    MySQL

    Docker & Docker Compose

    Google Books API

    JWT for authentication and authorization

    Angular (frontend)

Features

    User registration, authentication, and profile management

    Role-based access control (USER, ADMIN)

    CRUD operations for books

    Book reservations via Booking Service

    Automatic database population with books from Google Books API

    Angular frontend for user interaction

    Fully Dockerized for local and production deployment

Getting Started

    1. Clone the repository:

    git clone https://github.com/JarusE/Viberary.git
    cd Viberary

    2. Configure .env files for each service with database credentials and Google Books API key.

    3. Build and start all services and frontend using Docker Compose:

    docker-compose up --build

    4. Available endpoints:

    Gateway: http://localhost:8080

    User Service: http://localhost:8081

    Book Service: http://localhost:8082

    Booking Service: http://localhost:8083

    Auth Service: http://localhost:9000

    Angular Frontend: http://localhost:4200


User Roles

    ADMIN 	Full access to book CRUD, user management, booking management
    USER 	View books, manage own bookings and profile

Dependencies

    Spring Boot Starter Web

    Spring Boot Starter Security

    Spring Boot Starter Data JPA

    Spring Cloud Config Client/Server

    Spring Cloud Gateway

    MySQL Connector/J

    JWT (io.jsonwebtoken)

    Lombok

    Angular
