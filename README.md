# API Testing Project

This project demonstrates a simple Node.js API for a login system and provides examples of how to test the API using various tools: Postman, Swagger-UI, Thunder Client, and Rest-Assured (Java).

## Table of Contents

- [Project Setup](#project-setup)
- [API Endpoints](#api-endpoints)
- [Testing the API](#testing-the-api)
  - [Postman](#postman)
  - [Swagger-UI](#swagger-ui)
  - [Thunder Client](#thunder-client)
  - [Rest-Assured (Java)](#rest-assured-java)
- [Descriptions of the Tests](#descriptions-of-the-tests)
- [Notes](#notes)

## Project Setup

### Prerequisites

- Node.js and npm installed
- Java (for Rest-Assured)
- Maven (for Rest-Assured)
- Postman installed
- Visual Studio Code (for Thunder Client)
- Thunder Client extension for Visual Studio Code

### Installation

1. **Clone the repository:**

   ```sh
   git clone <repository-url>
   cd api-testing-project
   ```

2. **Initialize the project:**

   ```sh
   npm init -y
   ```

3. **Install dependencies:**

   ```sh
   npm install express body-parser
   ```

4. **Create the `server.js` file:**

   ```javascript
   const express = require('express');
   const bodyParser = require('body-parser');

   const app = express();
   const port = 3000;

   app.use(bodyParser.json());

   // Simple in-memory user store
   const users = [
     { username: 'user1', password: 'password1' },
     { username: 'user2', password: 'password2' }
   ];

   // GET route for the root path
   app.get('/', (req, res) => {
     res.send('Server is running!');
   });

   // Login endpoint
   app.post('/login', (req, res) => {
     const { username, password } = req.body;

     const user = users.find(u => u.username === username && u.password === password);
     if (user) {
       res.status(200).json({ message: 'Login successful' });
     } else {
       res.status(401).json({ message: 'Invalid credentials' });
     }
   });

   app.listen(port, () => {
     console.log(`Server running at http://localhost:3000/`);
   });
   ```

5. **Run the server:**

   ```sh
   node server.js
   ```

6. **Verify the server:**

   Open your browser and navigate to `http://localhost:3000/`. You should see the message "Server is running!".

## API Endpoints

### POST /login

- **Description:** Login endpoint to authenticate users.
- **Request Body:** JSON object containing `username` and `password`.
- **Responses:**
  - `200 OK`: Login successful.
  - `401 Unauthorized`: Invalid credentials.

## Testing the API

### Postman

1. **Open Postman.**
2. **Create a new POST request:**
   - URL: `http://localhost:3000/login`
   - Body: Raw, JSON format
   - Example Body:

     ```json
     {
       "username": "user1",
       "password": "password1"
     }
     ```

3. **Send the request.**
4. **Verify the response:** You should see a `200 OK` response with the message "Login successful".

### Swagger-UI

1. **Create a `swagger.json` file:**

   ```json
   {
     "swagger": "2.0",
     "info": {
       "title": "Login API",
       "version": "1.0.0"
     },
     "paths": {
       "/login": {
         "post": {
           "summary": "User login",
           "consumes": ["application/json"],
           "produces": ["application/json"],
           "parameters": [
             {
               "in": "body",
               "name": "body",
               "required": true,
               "schema": {
                 "type": "object",
                 "properties": {
                   "username": {
                     "type": "string"
                   },
                   "password": {
                     "type": "string"
                   }
                 }
               }
             }
           ],
           "responses": {
             "200": {
               "description": "Login successful",
               "schema": {
                 "type": "object",
                 "properties": {
                   "message": {
                     "type": "string"
                   }
                 }
               }
             },
             "401": {
               "description": "Invalid credentials",
               "schema": {
                 "type": "object",
                 "properties": {
                   "message": {
                     "type": "string"
                   }
                 }
               }
             }
           }
         }
       }
     }
   }
   ```

2. **View the Swagger spec:**
   - Use the Swagger Editor online or integrate Swagger-UI in your project to visualize and interact with the API documentation.

### Thunder Client

1. **Open Visual Studio Code.**
2. **Install the Thunder Client extension from the VSCode marketplace.**
3. **Create a new POST request:**
   - URL: `http://localhost:3000/login`
   - Body: JSON format
   - Example Body:

     ```json
     {
       "username": "user1",
       "password": "password1"
     }
     ```

4. **Send the request.**
5. **Verify the response:** You should see a `200 OK` response with the message "Login successful".

### Rest-Assured (Java)

1. **Add Rest-Assured to your project:**
   - If using Maven, add the following dependency to your `pom.xml`:

     ```xml
     <dependency>
       <groupId>io.rest-assured</groupId>
       <artifactId>rest-assured</artifactId>
       <version>4.3.3</version>
       <scope>test</scope>
     </dependency>
     ```

2. **Create a test class:**

   ```java
   import io.restassured.RestAssured;
   import io.restassured.http.ContentType;
   import org.junit.jupiter.api.Test;

   import static io.restassured.RestAssured.given;
   import static org.hamcrest.CoreMatchers.equalTo;

   public class LoginApiTest {

       @Test
       public void testLoginSuccess() {
           RestAssured.baseURI = "http://localhost:3000";

           given()
               .contentType(ContentType.JSON)
               .body("{ \"username\": \"user1\", \"password\": \"password1\" }")
           .when()
               .post("/login")
           .then()
               .statusCode(200)
               .body("message", equalTo("Login successful"));
       }

       @Test
       public void testLoginFailure() {
           RestAssured.baseURI = "http://localhost:3000";

           given()
               .contentType(ContentType.JSON)
               .body("{ \"username\": \"user1\", \"password\": \"wrongpassword\" }")
           .when()
               .post("/login")
           .then()
               .statusCode(401)
               .body("message", equalTo("Invalid credentials"));
       }
   }
   ```

3. **Run the tests:**
   - Use your preferred Java testing framework (e.g., JUnit) to execute the tests.

## Descriptions of the Tests

### Postman

Postman is a popular tool for API testing that allows you to create and send HTTP requests to your API. You can use Postman to manually test your API endpoints by configuring requests with various parameters and body contents, then viewing the responses.

### Swagger-UI

Swagger-UI is a tool that automatically generates interactive API documentation from a Swagger (OpenAPI) specification. It allows you to visualize and test API endpoints directly from the documentation interface. This is useful for ensuring your API conforms to its documentation and for interactive exploration.

### Thunder Client

Thunder Client is a lightweight REST API client extension for Visual Studio Code. It provides a simple and convenient way to create and send HTTP requests without leaving your code editor. This is especially useful for developers who prefer to work within VSCode.

### Rest-Assured (Java)

Rest-Assured is a Java library for testing RESTful web services. It simplifies the process of writing tests by providing a domain-specific language (DSL) for making HTTP requests and validating responses. Rest-Assured is typically used for automated API testing in Java projects, often integrated with testing frameworks like JUnit or TestNG.

## Notes

- These examples are provided for educational purposes and to demonstrate how to use various tools for API testing.
- The tests cover basic login functionality and include both successful and unsuccessful login attempts.
- Ensure that your server is running (`node server.js`) before attempting to send requests from any testing tool.
- Modify and expand the tests as needed to cover additional use cases and scenarios.

Feel free to explore and modify the examples to fit your specific testing needs. If you have any questions or need further assistance, don't hesitate to ask!