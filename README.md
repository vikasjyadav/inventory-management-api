Inventory Management API
About This Project

This is a simple Inventory Management REST API built using Spring Boot and MySQL.

The purpose of this project was to practice backend development concepts in a structured way and apply clean architecture principles instead of building a basic CRUD application.

Through this project, I focused on:

Clean layered architecture

Proper use of DTOs

RESTful API design

Global exception handling

Input validation

Database integration using JPA

Configurable business logic

Sorting support

The application allows full CRUD operations on products and includes structured error handling with meaningful HTTP responses.

Tech Stack Used

Java 25

Spring Boot

Spring Data JPA

Hibernate

MySQL

Maven

Postman (for API testing)

Project Architecture

The project follows a clean layered structure:

Controller – Handles HTTP requests and responses

Service – Contains business logic

Repository – Handles database operations

DTO – Separates request and response models

Mapper – Converts between Entity and DTO

Exception Layer – Centralized error handling using @ControllerAdvice

This structure keeps responsibilities separate and makes the project easier to maintain and extend.

Key Design Decisions
1️⃣ DTO Pattern

I used ProductRequest and ProductResponse DTOs to avoid exposing the entity directly.
This improves security and keeps the API flexible.

2️⃣ Global Exception Handling

A centralized exception handler is implemented to:

Handle resource not found errors

Handle validation errors

Return structured JSON error responses

Provide proper HTTP status codes

3️⃣ Configurable Business Logic

The low-stock threshold is not hardcoded.
It is configurable through:

inventory.low-stock-threshold

inside application.properties.

This makes the business rule flexible without changing the source code.

4️⃣ Sorting Support

The GET /api/products endpoint supports dynamic sorting using query parameters:

/api/products?sortBy=price&direction=desc

Sorting is implemented using Spring Data JPA Sort.

5️⃣ Auditing Fields

createdAt is automatically set using @PrePersist

updatedAt is automatically updated using @PreUpdate

This ensures proper tracking of product lifecycle events.

API Endpoints
Method	Endpoint	Description
POST	/api/products	Create a new product
GET	/api/products	Get all products (with optional sorting)
GET	/api/products/{id}	Get product by ID
PUT	/api/products/{id}	Update product
PATCH	/api/products/{id}/quantity	Update product quantity
DELETE	/api/products/{id}	Delete product
GET	/api/products/low-stock	Get low stock products
Database Configuration

Configure database in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.sql.init.mode=never
spring.jpa.defer-datasource-initialization=true

inventory.low-stock-threshold=10
How to Run

Create MySQL database:

CREATE DATABASE inventory_db;

Update database credentials in application.properties.

Run the application:

mvn spring-boot:run

Test APIs using Postman.

Future Improvements

Add pagination support

Add Swagger documentation

Add authentication & authorization

Add unit testing

This project helped me strengthen my understanding of Spring Boot backend development and build a more structured API instead of a simple CRUD application.
