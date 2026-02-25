Inventory Management API
About This Project

This is a simple Inventory Management REST API built using Spring Boot and MySQL.

The main goal of this project was to practice backend development in a structured way instead of building a basic CRUD application. I focused on applying clean architecture principles, proper layering, validation, and structured exception handling.

Through this project, I worked on:

Clean layered architecture

Proper use of DTOs

RESTful API design

Global exception handling

Input validation

Database integration using JPA

Configurable business logic

Sorting support

The application supports full CRUD operations on products and includes additional features like low-stock filtering and dynamic sorting.

Tech Stack Used

Java 25

Spring Boot

Spring Data JPA

Hibernate

MySQL

Maven

Postman (for API testing)

Project Architecture

The project follows a layered structure:

Controller Layer – Handles HTTP requests and responses

Service Layer – Contains business logic

Repository Layer – Handles database operations

DTO Layer – Separates request and response models

Mapper – Converts between Entity and DTO

Exception Layer – Centralized error handling using @ControllerAdvice

This structure keeps responsibilities clearly separated and makes the project easier to maintain and extend.

Key Design Decisions
1. DTO Pattern

I used ProductRequest and ProductResponse DTOs to avoid exposing the entity directly to the client.
This makes the API safer and keeps flexibility if the entity structure changes later.

2. Global Exception Handling

A centralized exception handler is implemented to:

Handle resource not found scenarios

Handle invalid input and validation errors

Return structured JSON error responses

Provide proper HTTP status codes

This ensures consistent and readable API responses.

3. Configurable Low-Stock Threshold

The low-stock threshold is not hardcoded inside the service layer.

It is configurable using:

inventory.low-stock-threshold

inside application.properties.

This makes the business rule flexible without modifying source code.

4. Sorting Support

The GET /api/products endpoint supports dynamic sorting using query parameters.

Example:

/api/products?sortBy=price&direction=desc

Sorting is implemented using Spring Data JPA Sort.

5. Auditing Fields

The entity includes:

createdAt – Automatically set using @PrePersist

updatedAt – Automatically updated using @PreUpdate

This ensures proper tracking of product creation and updates.

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

Configure your database in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.sql.init.mode=never
spring.jpa.defer-datasource-initialization=true

inventory.low-stock-threshold=10
How to Run the Project

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

Dockerize the application

Project Status

This project was built as part of backend development practice to strengthen understanding of Spring Boot, JPA, and clean API design principles.