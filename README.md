# Spring Social Media Blog API

## Description
A backend API for a hypothetical social media application that manages user accounts and messages. Built using the Spring Boot framework, it provides CRUD functionality for user registration, login, and message handling.

---

## Features
1. **User Registration**  
   - Endpoint: `POST /register`  
   - Validates username uniqueness and password strength.
   - Returns `200 OK` with account details on success or `409 Conflict`/`400 Bad Request` on failure.

2. **User Login**  
   - Endpoint: `POST /login`  
   - Authenticates using username and password.  
   - Returns `200 OK` with account details on success or `401 Unauthorized` on failure.

3. **Message Management**  
   - **Create Message**  
     - Endpoint: `POST /messages`  
     - Validates message content and associated user.  
     - Returns `200 OK` with message details or `400 Bad Request` on failure.
   - **Retrieve All Messages**  
     - Endpoint: `GET /messages`  
     - Returns a list of all messages or an empty list if none exist.  
   - **Retrieve Message by ID**  
     - Endpoint: `GET /messages/{messageId}`  
     - Returns message details or an empty response if not found.
   - **Delete Message**  
     - Endpoint: `DELETE /messages/{messageId}`  
     - Deletes a message and returns the number of rows updated (`1` on success).  
     - Always returns `200 OK`.
   - **Update Message**  
     - Endpoint: `PATCH /messages/{messageId}`  
     - Updates the message text if valid.  
     - Returns `200 OK` with rows updated (`1`) or `400 Bad Request` on failure.

4. **Retrieve Messages by User**  
   - Endpoint: `GET /accounts/{accountId}/messages`  
   - Returns all messages from a specific user.

---

## Database Schema

### Account Table
| Column       | Type        | Constraints            |
|--------------|-------------|------------------------|
| `accountId`  | `INTEGER`   | Primary Key, Auto-Increment |
| `username`   | `VARCHAR`   | Not Null, Unique       |
| `password`   | `VARCHAR`   | Not Null              |

### Message Table
| Column           | Type        | Constraints                |
|-------------------|-------------|----------------------------|
| `messageId`       | `INTEGER`   | Primary Key, Auto-Increment |
| `postedBy`        | `INTEGER`   | Foreign Key -> `Account.accountId` |
| `messageText`     | `VARCHAR`   | Not Null, Max 255 Characters |
| `timePostedEpoch` | `LONG`      | Not Null                  |

---

## Technical Details

- **Framework**: Spring Boot  
- **Dependency Injection**: Uses Spring annotations for autowiring and bean management.  
- **Repositories**: JPA-based `AccountRepository` and `MessageRepository`.  
- **Controllers**: `SocialMediaController` for handling API endpoints.  
- **Services**: `AccountService` and `MessageService` for business logic.

---

## Setup

1. Clone the repository.
2. Configure `application.properties` with database connection details.
3. Run the SQL script to initialize the database.
4. Build and run the application using Maven/Gradle.

---

## Testing

- Functional tests are included to validate all endpoints.
- Verified compliance with Spring Boot, Spring MVC, and Spring Data using `SpringTest`.

---

## Future Improvements

- Add authentication and role-based access control.
- Implement pagination for message retrieval.
- Expand API to support media uploads.

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.
