\# Inventory Management API



\## About This Project



This is a simple Inventory Management REST API built using Spring Boot and MySQL.



The goal of this project was to practice and demonstrate:

\- Clean layered architecture

\- Proper use of DTOs

\- RESTful API design

\- Exception handling

\- Database integration with JPA



The application allows basic CRUD operations on products and includes validation and structured error handling.



---



\## Tech Stack Used



\- Java 25

\- Spring Boot

\- Spring Data JPA

\- Hibernate

\- MySQL

\- Maven

\- Postman (for API testing)



---



\## Project Architecture



The project follows a layered structure:



\- \*\*Controller\*\* – Handles HTTP requests and responses  

\- \*\*Service\*\* – Contains business logic  

\- \*\*Repository\*\* – Handles database operations  

\- \*\*DTO\*\* – Used to separate request and response models  

\- \*\*Mapper\*\* – Converts between Entity and DTO  

\- \*\*Global Exception Handler\*\* – Handles errors properly  







---



\## Database Configuration



 `application.properties` file:



```properties

spring.datasource.url=jdbc:mysql://localhost:3306/inventory\_db

spring.datasource.username=root

spring.datasource.password=yourpassword



spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format\_sql=true



spring.sql.init.mode=never

spring.jpa.defer-datasource-initialization=true

