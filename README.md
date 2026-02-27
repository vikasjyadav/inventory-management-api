👑 FINAL UPGRADED README (Submission Ready)

Replace your entire README with this:

📦 ERP Inventory Management System (Secure Production Version)

A production-ready Inventory Management REST API built using Spring Boot, MySQL, and Spring Security with JWT-based authentication and role-based authorization.

This project was developed as part of the ZeroOne Tech Labs Java Internship task and enhanced with real-world production features.

🚀 Tech Stack

Java 17+ (or your exact version)

Spring Boot

Spring Security

JWT Authentication

Spring Data JPA / Hibernate

MySQL

Swagger (OpenAPI)

SLF4J / Logback

JUnit & Mockito

Maven

🔐 Security Implementation
Authentication

Login API generates JWT token

Passwords encrypted using BCrypt

Stateless session management

JWT validated via custom filter

To access secured APIs:

Authorization Header:

Bearer <your-jwt-token>
👥 Role-Based Authorization

Roles implemented:

ADMIN

MANAGER

STAFF

Access Control Matrix
Endpoint	ADMIN	MANAGER	STAFF
GET /api/products	✅	✅	✅
POST /api/products	✅	✅	❌
PATCH /api/products/{id}/quantity	✅	✅	❌
DELETE /api/products/{id}	✅	❌	❌

Only ADMIN can delete products.

📑 Core Features
1️⃣ Clean Layered Architecture

Controller Layer

Service Layer

Repository Layer

DTO Layer

Mapper Layer

Global Exception Handler

2️⃣ JWT Authentication Flow

User logs in via /auth/login

Server returns JWT token

Client sends token in Authorization header

JWT filter validates token on every request

Access granted based on role

3️⃣ Pagination Support
GET /api/products?page=0&size=5
4️⃣ Dynamic Sorting
GET /api/products?sort=name,asc
GET /api/products?sort=price,desc

Supported sorting fields:

name

price

quantity

createdAt

5️⃣ Audit Fields

Entity automatically maintains:

createdAt

updatedAt

createdBy

updatedBy

6️⃣ Structured Exception Handling

Custom error response structure:

{
"timestamp": "",
"status": 404,
"message": "Product not found",
"path": "/api/products/100"
}
7️⃣ Swagger Documentation

Access Swagger UI:

http://localhost:8080/swagger-ui/index.html
8️⃣ Logging

Application logging implemented using SLF4J and Logback.

Logs include:

API access logs

Error logs

Business operation logs

9️⃣ Unit Testing

Basic service-layer unit tests implemented using:

JUnit

Mockito

📌 API Endpoints
🔑 Auth APIs
Method	Endpoint	Description
POST	/auth/register	Register new user
POST	/auth/login	Login & generate JWT
📦 Product APIs
Method	Endpoint	Description
GET	/api/products	Get products (pagination & sorting supported)
GET	/api/products/{id}	Get product by ID
POST	/api/products	Create product
PUT	/api/products/{id}	Update product
PATCH	/api/products/{id}/quantity	Update quantity
DELETE	/api/products/{id}	Delete product
GET	/api/products/low-stock	Get low stock products
🗄️ Database Configuration

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
▶️ How To Run

Create database:

CREATE DATABASE inventory_db;

Update DB credentials

Run:

mvn clean install
mvn spring-boot:run
🧪 Postman Testing

Call /auth/login

Copy JWT token

Add Bearer Token in Postman

Test secured APIs

Postman collection included in repository.

🏁 Project Status

✔ Fully secured using JWT
✔ Role-based authorization
✔ Pagination & Sorting
✔ Swagger Documentation
✔ Logging implemented
✔ Unit testing added
✔ Production-ready structure