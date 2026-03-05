📦 ERP Inventory Management System (Order Management & Stock Flow)

A production-ready Inventory Management REST API built using Spring Boot, MySQL, and Spring Security (JWT).

This project was developed as part of the ZeroOne Tech Labs Java Internship Task and implements a real-world ERP order management module where product stock automatically updates based on order transactions.

🚀 Tech Stack

Java 17+

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

Stateless authentication

Custom JWT Authentication Filter

To access secured APIs:

Authorization: Bearer <your-jwt-token>
👥 Role-Based Authorization

Roles implemented:

ADMIN
MANAGER
STAFF
Access Control Matrix
Endpoint	                        ADMIN	MANAGER	STAFF
GET /api/products                   ✅	      ✅	  ✅
POST /api/products	                ✅    	  ✅	  ❌
PATCH /api/products/{id}/quantity	✅	      ✅	  ❌
DELETE /api/products/{id}	        ✅	      ❌	  ❌
POST /orders	                    ❌	      ✅	  ✅
GET /orders	                        ❌        ✅      ✅
PUT /orders/{id}/status	            ❌	      ✅	  ❌
GET /reports/sales              	✅	      ❌	  ❌
GET /reports/low-stock/{quantity}	✅	      ✅	  ❌
📑 Core Features
1️⃣ Clean Layered Architecture

The project follows a clean layered architecture:

Controller Layer
Service Layer
Repository Layer
DTO Layer
Mapper Layer
Global Exception Handler
2️⃣ Order Management & Stock Flow

The system simulates real ERP order transactions.

Order Creation Flow

Client sends order request with product IDs and quantities.

System verifies product availability.

If stock is sufficient:

Order is created.

Product stock is automatically reduced.

Total order amount is calculated dynamically.

Order Cancellation Flow

When an order status is changed to CANCELLED:

System fetches order items.

Product quantities are restored back to inventory.

Order status is updated.

This ensures accurate stock management.

3️⃣ Pagination Support

Products API supports pagination.

Example:

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

Entities automatically maintain audit fields:

createdAt
updatedAt
createdBy
updatedBy
6️⃣ Structured Exception Handling

Custom error response structure:

{
"timestamp": "2026-03-05T10:00:00",
"status": 404,
"message": "Product not found",
"path": "/api/products/100"
}
7️⃣ Swagger API Documentation

Swagger UI available at:

http://localhost:8080/swagger-ui/index.html

This provides interactive API documentation.

8️⃣ Logging

Application logging implemented using SLF4J and Logback.

Logs include:

API request logs

Error logs

Business operation logs

9️⃣ Unit Testing

Service layer tests implemented using:

JUnit 5
Mockito

Tests verify:

Order creation

Insufficient stock handling

Order status updates

Sales summary logic

📌 API Endpoints
🔑 Authentication APIs
Method	Endpoint	Description
POST	/api/auth/register	Register new user
POST	/api/auth/login	Login and generate JWT
📦 Product APIs
Method	Endpoint	Description
GET	/api/products	Get products (pagination supported)
GET	/api/products/{id}	Get product by ID
POST	/api/products	Create product
PUT	/api/products/{id}	Update product
PATCH	/api/products/{id}/quantity	Update product quantity
DELETE	/api/products/{id}	Delete product
📦 Order APIs
Method	Endpoint	Description
POST	/orders	Create new order
GET	/orders	Fetch all orders
GET	/orders/{id}	Get order details
PUT	/orders/{id}/status	Update order status
📊 Report APIs
Method	Endpoint	Description
GET	/reports/sales	Get sales summary
GET	/reports/low-stock/{quantity}	Get low stock products
🗄️ Database Configuration

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
▶️ How To Run
1️⃣ Create Database
CREATE DATABASE inventory_db;
2️⃣ Update Database Credentials

Modify application.properties.

3️⃣ Run Application
mvn clean install
mvn spring-boot:run
🧪 Postman Testing

1️⃣ Login using:

POST /api/auth/login

2️⃣ Copy returned JWT token.

3️⃣ Add token to Postman header:

Authorization: Bearer <token>

4️⃣ Test secured APIs.

A Postman collection is included in the repository.

🎥 Demonstration

The demo video shows:

Login with JWT authentication

Product retrieval

Order creation

Automatic stock deduction

Order cancellation

Stock restoration

Sales report generation

Low stock report

🏁 Project Status

✔ Order Management System implemented
✔ Automatic stock deduction
✔ Stock restoration on cancellation
✔ Sales reporting module
✔ JWT security implemented
✔ Role-based authorization
✔ Swagger documentation
✔ Unit testing included
✔ Production-ready architecture