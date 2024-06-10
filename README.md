# Filters Back-End

This is a backend application for managing filters. The application allows you to create filters with diffrent 
criteria. There are 3 criteria types: Amount, Title and Date.

## Requirements

- Spring Boot 3.3
- Java 21
- Liquibase
- H2 Database
- MapStruct 1.5.5

## Getting Started

1. Clone the repository

2. Navigate into the project directory

3. Run the application:
    - ./gradlew bootrun

* Database connection information is in application.properties

Springdoc: http://localhost:8080/swagger-ui/index.html#/

## Test data
Filter 1: 
   - FilterOne
   - Criteria:
     - Amount, More, 5
     - Title, Starts with, Meow


Filter 2:
   - FilterTwo
   - Criteria:
      - Title, Starts with, Meow

