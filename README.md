Here is a complete README.md file based on all the commands that worked for you. You can copy and paste this directly into a file named README.md in your project's root folder (C:\Users\User\Downloads\banking-api\banking-api).

🚀 Banking API Project
This is a Spring Boot application for managing bank accounts and transactions. It uses a PostgreSQL database running in a Docker container.

Prerequisites
Before you begin, ensure you have the following tools installed:

Java 17+ (You are using JDK 21)

Apache Maven (You are using 3.9.11)

Docker Desktop (Must be running)

1. Workflow: Run for Local Development (Recommended)
This is the best workflow for actively writing code and testing. It runs the database in Docker and the Spring Boot app directly in your terminal.

Step 1: Start the Database
Open a terminal in the project's root folder (C:\Users\User\Downloads\banking-api\banking-api).

Important: To avoid errors, it's best to start with a fresh, empty database. Run this command first to stop and delete any old, bad data:

PowerShell

docker-compose down -v
Now, start a new database container in the background (-d):

PowerShell

docker-compose up -d postgres
(Wait 10-15 seconds for the database to initialize).

Step 2: Run the Spring Boot Application
In the same terminal, run the application using the Maven wrapper.

PowerShell

.\mvnw.cmd spring-boot:run
This command will:

Connect to your PostgreSQL database.

Automatically create the accounts and transactions tables (from ddl-auto: update).

Run data.sql to insert your 3 sample accounts (because of defer-datasource-initialization: true).

Start the web server.

You will see the log Tomcat started on port 8080 (http). Your application is now running!

2. Accessing the API (Swagger UI)
Once the application is running, you can test all the APIs visually.

Open your web browser and navigate to: http://localhost:8080/swagger-ui.html

You will see the API documentation, including "Account Management" and "Transaction Management".

How to Test (Fixing the 400 Error)
When you test a POST request (like creating an account), you must replace the default example text.

Click on POST /api/accounts.

Click "Try it out".

In the "Request body" box, change the default JSON:

Change This:

JSON

{
  "holderName": "string",
  "holderEmail": "string",
  "accountType": "SAVINGS",
  "initialDeposit": 0
}
To This (with valid data):

JSON

{
  "holderName": "Alice Johnson",
  "holderEmail": "alice@example.com",
  "accountType": "CHECKING",
  "initialDeposit": 1500
}
Click "Execute". You will now get a 201 Created response.

3. Other Useful Command Workflows
How to Stop Everything
To stop the Spring Boot app: Press Ctrl + C in the terminal where it's running.

To stop the Docker database:

PowerShell

docker-compose down
(This stops the container but saves its data. Use docker-compose down -v to wipe it clean).

How to Run a Full Build (with Tests)
If you want to build the final .jar file and run all the tests, you must have the database running.

PowerShell

# 1. Start the database
docker-compose up -d postgres

# 2. Run the full build
.\mvnw.cmd clean install

# 3. Stop the database
docker-compose down
How to Run Everything in Docker
If you don't want to run the app in your terminal, Docker Compose can run both the database and the app.

PowerShell

# Build and run all services
docker-compose up --build
(This will show you the logs from both containers. Your app will still be available at http://localhost:8080/swagger-ui.html).