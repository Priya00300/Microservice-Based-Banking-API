Banking API - Microservice-Based Application
A comprehensive RESTful banking API built with Spring Boot 3.x, featuring account management and transaction processing capabilities.

🚀 Features
Account Management
Create new bank accounts (Savings, Checking, Business)
View account details and balances
Update account status (Active, Inactive, Frozen, Closed)
Pagination and sorting support
Transaction Processing
Deposit money into accounts
Withdraw money from accounts
Transfer money between accounts
View transaction history with filtering
Automatic balance tracking
Technical Features
RESTful API design
PostgreSQL database with JPA/Hibernate
Docker containerization
Optimistic locking for concurrent transactions
Comprehensive error handling
API documentation with Swagger/OpenAPI
Unit and integration tests
📋 Prerequisites
Java 17 or higher
Maven 3.6+
Docker Desktop (for Windows)
VS Code with Java extensions
Git
🛠️ Technology Stack
Framework: Spring Boot 3.2.0
Language: Java 17
Database: PostgreSQL 15
Build Tool: Maven
Containerization: Docker & Docker Compose
Documentation: SpringDoc OpenAPI 3
Testing: JUnit 5, Mockito
ORM: Spring Data JPA with Hibernate
📦 Installation & Setup
Option 1: Using Docker (Recommended)
Clone the repository

bash
git clone <your-repo-url>
cd banking-api
Build and run with Docker Compose

bash
docker-compose up --build
This will start:

PostgreSQL database on port 5432
Banking API application on port 8080
Option 2: Local Development
Start PostgreSQL manually

bash
docker run --name banking-postgres \
  -e POSTGRES_DB=bankingdb \
  -e POSTGRES_USER=bankuser \
  -e POSTGRES_PASSWORD=bankpass \
  -p 5432:5432 \
  -d postgres:15-alpine
Build the application

bash
./mvnw clean install
Run the application

bash
./mvnw spring-boot:run
📚 API Documentation
Once the application is running, access the interactive API documentation:

Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/api-docs
🔗 API Endpoints
Account Management
Method	Endpoint	Description
POST	/api/accounts	Create new account
GET	/api/accounts	Get all accounts (paginated)
GET	/api/accounts/{id}	Get account by ID
GET	/api/accounts/number/{accountNumber}	Get account by account number
GET	/api/accounts/status/{status}	Get accounts by status
PATCH	/api/accounts/{accountNumber}/status	Update account status
Transaction Management
Method	Endpoint	Description
POST	/api/transactions/deposit	Deposit money
POST	/api/transactions/withdraw	Withdraw money
POST	/api/transactions/transfer	Transfer between accounts
GET	/api/transactions/account/{accountNumber}	Get transaction history
GET	/api/transactions/account/{accountNumber}/type/{type}	Get transactions by type
📝 Sample API Requests
Create Account

json
POST /api/accounts
{
  "holderName": "John Doe",
  "holderEmail": "john.doe@email.com",
  "accountType": "SAVINGS",
  "initialDeposit": 5000.00
}
Deposit Money

json
POST /api/transactions/deposit
{
  "accountNumber": "ACC1234567890",
  "amount": 1000.00,
  "description": "Monthly salary"
}
Transfer Money

json
POST /api/transactions/transfer
{
  "fromAccountNumber": "ACC1234567890",
  "toAccountNumber": "ACC1234567891",
  "amount": 500.00,
  "description": "Payment to Jane"
}
🧪 Testing
Run all tests

bash
./mvnw test
Run specific test class

bash
./mvnw test -Dtest=AccountServiceTest
Import Postman Collection
Import the Banking-API.postman_collection.json file into Postman for manual API testing.

📊 Database Schema
Accounts Table
id (Primary Key)
account_number (Unique)
balance
account_type (SAVINGS, CHECKING, BUSINESS)
status (ACTIVE, INACTIVE, FROZEN, CLOSED)
holder_name
holder_email
created_date
updated_date
version (for optimistic locking)
Transactions Table
id (Primary Key)
amount
type (DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT)
transaction_date
description
reference_number (Unique)
balance_after
account_id (Foreign Key)
related_account_number
🔧 Configuration
Key configuration properties in application.yml:


yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bankingdb
    username: bankuser
    password: bankpass
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080
🐛 Troubleshooting
Port Already in Use

bash
# Windows - Kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID <process_id> /F
Database Connection Issues
Ensure PostgreSQL container is running: docker ps
Check database credentials in application.yml
Verify PostgreSQL is accessible: docker logs banking-postgres
Docker Build Issues

bash
# Clean and rebuild
docker-compose down -v
docker-compose up --build --force-recreate
📈 Performance Optimization
Connection Pooling: HikariCP configured with optimal settings
Database Indexing: Indexes on frequently queried columns
Pessimistic Locking: Prevents concurrent transaction conflicts
Pagination: Efficient data retrieval for large datasets
Lazy Loading: Optimized JPA fetch strategies
🔒 Security Considerations
Input validation using Bean Validation
Optimistic locking for data integrity
Transaction isolation levels
CORS configuration for cross-origin requests
Proper error handling without exposing sensitive data
🚀 Future Enhancements
JWT-based authentication
Rate limiting
Audit logging
Email notifications
Account statements generation
Currency conversion support
Scheduled transactions
Redis caching layer