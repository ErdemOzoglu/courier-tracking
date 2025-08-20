# Courier Tracking System

### About The Project
Courier Tracking System is an online logging application that allows logging of couriers' locations. It also reports delivery times.
When 100 meters approach to the stores, it is considered to have entered the store. 
Re-entry will not be counted if still in a similar location in less than 1 minute.

### Summary

In this project;

- Dependencies are managed by Maven.
- Request validation is handled via javax.validation annotations.
- Every request is validated with Spring Validation
- Documented with Swagger.
- Endpoints use DTOs instead of entities. Convert operations are done with mapstruct.
- Tests were written with Junit&Mockito.
- Lombok was used. 
- The structure of the all responses are same. It can be understood from the isSuccess field that the requests are successful or unsuccessful.



### Technologies
- Java 17
- PostgreSQL
- Spring Boot 
- Maven
- Junit, Mockito
- Swagger
- Lombok
- Mapstruct

### Prerequisites
- Java 17 or never
- Docker Desktop
- Apache Maven 

## 🚀 Getting Started

Follow these steps to build and run the project locally using Maven and Docker.

### 1. Navigate to the project directory
>cd courier-tracking

### 2. Build the project with Maven
>mvn clean package -DskipTests

### 3. Ensure Docker Desktop is running

### 4. Start the application using Docker Compose
>docker-compose up --build

### 5. Access the Swagger UI
http://localhost:8080/swagger-ui.html
