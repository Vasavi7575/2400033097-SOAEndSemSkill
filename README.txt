LIBRARY SERVICE WITH JWT - SPRING TOOL SUITE (STS)
=================================================

WHAT THIS PROJECT DOES
- Validates a Bearer JWT on API requests when a token is supplied.
- Anyone can view books using GET endpoints.
- Only ADMIN or LIBRARIAN can add, update, or delete books.
- A normal USER receives HTTP 403 Forbidden for write operations.
- Invalid/expired JWT receives HTTP 401 Unauthorized.

REQUIREMENTS
- Spring Tool Suite (STS)
- Java 17 or later
- PostgreSQL running locally

1. CREATE DATABASE
Open pgAdmin Query Tool and execute:
    CREATE DATABASE librarydb;

2. SET YOUR POSTGRESQL PASSWORD
Open:
    src/main/resources/application.properties
Change:
    spring.datasource.password=YOUR_POSTGRES_PASSWORD
to your actual PostgreSQL password.

3. IMPORT INTO STS
- Extract LibraryServiceJWT_STS.zip.
- Open STS.
- Select File -> Import -> Maven -> Existing Maven Projects.
- Browse to the extracted LibraryServiceJWT_STS folder.
- Select the project and click Finish.
- Wait for Maven dependencies to download.

4. RUN
- Make sure PostgreSQL is running.
- Right-click LibraryServiceJwtApplication.java.
- Select Run As -> Spring Boot App.
- Application runs at http://localhost:8085

5. LOGIN AND GET JWT
Use Postman, Thunder Client, or REST Client:

POST http://localhost:8085/api/auth/login
Header: Content-Type: application/json
Body:
{
  "username": "admin",
  "password": "admin123"
}

Other demo logins:
- librarian / lib123
- user / user123

Copy the token value from the response.

6. TEST BOOK ENDPOINTS
GET all books (no token required):
GET http://localhost:8085/api/books

GET one book:
GET http://localhost:8085/api/books/1

ADD book (ADMIN or LIBRARIAN token required):
POST http://localhost:8085/api/books
Headers:
Authorization: Bearer PASTE_TOKEN_HERE
Content-Type: application/json
Body:
{
  "title": "Java Programming",
  "author": "James Gosling",
  "category": "Programming",
  "publishedYear": 2024
}

UPDATE book (ADMIN or LIBRARIAN token required):
PUT http://localhost:8085/api/books/1
Headers: Authorization: Bearer PASTE_TOKEN_HERE
Content-Type: application/json
Body:
{
  "title": "Advanced Java",
  "author": "James Gosling",
  "category": "Programming",
  "publishedYear": 2025
}

DELETE book (ADMIN or LIBRARIAN token required):
DELETE http://localhost:8085/api/books/1
Header: Authorization: Bearer PASTE_TOKEN_HERE

7. VERIFY ROLE-BASED AUTHORIZATION
- Login as admin and use its token for POST/PUT/DELETE: allowed.
- Login as librarian and use its token for POST/PUT/DELETE: allowed.
- Login as user and use its token for POST/PUT/DELETE: denied with HTTP 403.
- Send a bad or expired token: HTTP 401.

IMPORTANT
This is a self-contained lab demonstration. The three demo accounts are stored in
memory in AuthController and reset whenever the application restarts. In a real
system, store users and password hashes in a database and keep the JWT secret
outside source control. Do not use the demo passwords in production.
