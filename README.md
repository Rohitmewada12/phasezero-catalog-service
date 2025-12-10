Overview

PhaseZero Catalog Service is a Spring Boot–based RESTful microservice designed to manage a product catalog.
It provides APIs to add, list, search, filter, sort products, and calculate total inventory value.

This project demonstrates:
1. Clean REST API design
2. Proper layered architecture
3. Business rule enforcement
4. Structured and consistent API responses

Tech Stack
1. Java
2. Spring Boot
3. Spring Web
4. Spring Data JPA
5. H2 / In-memory storage
6.Maven


How to Build and Run the Application
Prerequisites
Java 
Maven
IDE (Eclipse / IntelliJ / VS Code)


###  Application Design (Layered Architecture) ###

The project follows a clean layered architecture with proper separation of concerns:

1️⃣ Controller Layer

Handles HTTP requests and responses
Exposes REST APIs
Delegates requests to Service layer
Example Class
ProductController



2️⃣ Service Layer
Contains all business logic
Applies validations and business rules
Interacts with DAO layer for data operations
Responsibilities
Validate product data
Prevent duplicate partNumber
Normalize partName to lowercase
Handle sorting, filtering, and inventory value calculation
Example Class
ProductService

3️⃣ DAO (Repository) Layer 
Responsible for data access and persistence
Isolates storage logic from business logic
Uses Spring Data JPA / in-memory storage
Responsibilities
Save product data
Fetch products
Search and filter products
Maintain uniqueness of partNumber
Example Classes
ProductRepository

Custom query methods for search and filter operations
✅ This layer ensures loose coupling and better maintainability.


4️⃣ Entity Layer
Represents database / in-memory data model
Mapped using JPA annotations (if H2 is used)
Example Class
Product



5️⃣ DTO Layer
Provides a standard response structure for all APIs
Example Class
ResponseStructure<T>

Exception Layer


Product Data Model
| Field Name | Type   | Description                        |
| ---------- | ------ | ---------------------------------- |
| partNumber | String | Unique business identifier         |
| partName   | String | Product name (stored in lowercase) |
| category   | String | Product category                   |
| price      | double | Unit price                         |
| stock      | int    | Available quantity                 |



## Business Rules ##
partName is stored in lowercase
partNumber must be unique
price and stock cannot be negative



| HTTP Method | Endpoint                      | Description                     |
| ----------- | ----------------------------- | ------------------------------- |
| POST        | `/products/save`              | Add a new product               |
| GET         | `/products`                   | List all products               |
| GET         | `/products/search/{partName}` | Search by product name          |
| GET         | `/products/filter/{category}` | Filter by category              |
| GET         | `/products/sort`              | Sort products by price          |
| GET         | `/products/inventory/value`   | Calculate total inventory value |

Example API Requests & Responses
POST /products/save
json
{
  "partNumber": "P1001",
  "partName": "Hydraulic Filter",
  "category": "Mechanical",
  "price": 1200,
  "stock": 10
}

response
{
    "data": {
        "category": "Mechanical",
        "id": 4,
        "partName": "hydraulic filter",
        "partNumber": "P1001",
        "price": 1200.0,
        "stock": 10
    },
    "message": "Product details saved",
    "statusCode": 201
}


Inventory Value
GET /products/inventory/value

{
  "statusCode": 200,
  "message": "Total inventory value calculated",
  "data": 15019500.0
}

