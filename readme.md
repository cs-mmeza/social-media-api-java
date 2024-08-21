# Social Media Blog API

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Project Structure](#project-structure)

## Overview

The **Social Media Blog API** is a backend application for a hypothetical social media platform that allows users to register, log in, and create, read, update, and delete (CRUD) messages. The API is built using **Java**, **JDBC**, and **Javalin**, providing RESTful endpoints for seamless interaction with client applications. The application follows a micro-blogging concept where users can view all messages or filter messages by specific users.

## Features

- **User Registration**: Users can create new accounts by providing a unique username and a secure password.
- **User Login**: Registered users can log in by verifying their credentials.
- **Create Message**: Authenticated users can post new messages.
- **Retrieve All Messages**: Users can fetch all messages posted on the platform.
- **Retrieve Message by ID**: Users can fetch a specific message using its unique ID.
- **Delete Message by ID**: Users can delete a specific message by its ID.
- **Update Message by ID**: Users can update the content of a specific message.
- **Retrieve Messages by User**: Users can fetch all messages posted by a specific user.

## Technologies Used

- **Java**
- **JDBC** (Java Database Connectivity)
- **Javalin** (Lightweight Java Web Framework)
- **H2 Database** (in-memory databases)
- **JUnit** (for testing)
- **Maven** (for project management and dependency management)

## Database Schema

The application uses two main tables: `Account` and `Message`. These tables are initialized using an SQL script executed by the `ConnectionUtil` class upon application startup.

### Account Table

```mysql
CREATE TABLE Account (
    account_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

### Message Table

```mysql
CREATE TABLE Message (
    message_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    posted_by INTEGER,
    message_text VARCHAR(255) NOT NULL,
    time_posted_epoch BIGINT,
    FOREIGN KEY (posted_by) REFERENCES Account(account_id)
);
```
## API Endpoints

1. **User Registration**
   - **POST** `/register`
   - Registers a new user account.
   - Responses:
     - `200 OK`: Registration successful.
     - `400 Bad Request`: Invalid registration details.

2. **User Login**
   - **POST** `/login`
   - Verifies user credentials.
   - Responses:
     - `200 OK`: Login successful.
     - `401 Unauthorized`: Invalid credentials.

3. **Create Message**
   - **POST** `/messages`
   - Creates a new message.
   - Responses:
     - `200 OK`: Message creation successful.
     - `400 Bad Request`: Invalid message details.

4. **Retrieve All Messages**
   - **GET** `/messages`
   - Retrieves all messages from the database.
   - Response:
     - `200 OK`: Returns a list of messages.

5. **Retrieve Message by ID**
   - **GET** `/messages/{message_id}`
   - Retrieves a message by its ID.
   - Response:
     - `200 OK`: Returns the message details.

6. **Delete Message by ID**
   - **DELETE** `/messages/{message_id}`
   - Deletes a message by its ID.
   - Response:
     - `200 OK`: Message deleted successfully.

7. **Update Message by ID**
   - **PATCH** `/messages/{message_id}`
   - Updates the content of a message identified by its ID.
   - Responses:
     - `200 OK`: Message updated successfully.

8. **Retrieve Messages by User**
   - **GET** `/accounts/{account_id}/messages`
   - Retrieves all messages posted by a specific user.
   - Response:
     - `200 OK`: Returns a list of messages posted by the user.

## Getting Started
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven (for project build and dependency management)
- Database: H2 Database is recommended for testing purposes, but you can use any relational database (e.g., MySQL, PostgreSQL).
- Git (for version control)
#### Installation

1. Clone the Repository
```bash
git clone https://github.com/yourusername/social-media-api-java.git
```
2. Navigate to Project Directory

```bash
cd social-media-api-java
```

3. Set Up the Database
- Ensure your database is running and accessible.
- Update database configurations in the ConnectionUtil class if necessary.
- The provided SQL script will initialize the required tables upon application startup.

4. Build the Project

```bash
mvn clean install
```

## Running the Application
1. Start the Application
```bash
mvn exec:java -Dexec.mainClass="Main"
```

2. Access the API
- The API will be available at `http://localhost:8080/`.
- Use tools like Postman or cURL to interact with the endpoints.

## Project Structure
```css
social-media-blog-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controllers/
│   │   │   │   └── SocialMediaController.java
│   │   │   ├── dao/
│   │   │   │   ├── AccountDAO.java
│   │   │   │   ├── AccountDAOImpl.java
│   │   │   │   ├── MessageDAO.java
│   │   │   │   └── MessageDAOImpl.java
│   │   │   ├── services/
│   │   │   │   ├── AccountService.java
│   │   │   │   └── MessageService.java
│   │   │   ├── models/
│   │   │   │   ├── Account.java
│   │   │   │   └── Message.java
│   │   │   ├── util/
│   │   │   │   └── ConnectionUtil.java
│   │   │   └── Main.java
│   │   └── resources/
│   │       └── schema.sql
│   └── test/
│       └── java/
│           └── integration/
│               └── IntegrationTests.java
├── pom.xml
└── README.md
```
controllers/: Contains the SocialMediaController class where all endpoints are defined.
dao/: Data Access Object layer handling direct interactions with the database.
services/: Business logic layer that processes data before passing it between the controller and DAO layers.
models/: Contains the data models Account and Message.
util/: Utility classes like ConnectionUtil for database connections.
resources/: Contains SQL scripts and configuration files.
test/: Contains integration tests to verify the functionality of the API.
     - `400 Bad Request`: Invalid update details.
