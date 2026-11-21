# Library Management System

Advance Java Technology (01CE0308) project, Marwadi University.

**Student:** MINNY VEMPA | **Enrollment No:** 92500120170

## About
A Java web application for a library. Members can register and log in, view the book list from the database, issue books and return books. A Java Swing desktop dashboard shows the same book records.

## Features
- Member registration with form validation (name, email, password length)
- Login with `HttpSession`, and an `AuthFilter` that protects pages for logged-in members only
- Book list rendered with JSP, JSTL and EL
- Issue Book and Return Book, with the stock (available copies) updated in MySQL
- Swing dashboard (`LibrarySwingDashboard`) that lists the books
- Layered design: Servlet > Service > DAO > MySQL, with Spring Java-based configuration for dependency injection

## Technologies
Java 17, Servlets, JSP, JSTL, HttpSession, Spring Framework 5.3 (DI), JDBC, MySQL 8.4, Java Swing, Apache Tomcat 9, Eclipse IDE.

(Hibernate is not used. Database access is done with JDBC and DAO classes.)

## Project Structure
```
src/main/java      Servlets, Service, DAO, AppConfig (Spring), Swing dashboard
src/main/webapp    HTML and JSP pages, WEB-INF/lib (jars)
```

## Database Setup
1. Install MySQL and run:
```sql
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    author VARCHAR(150),
    category VARCHAR(100),
    available INT DEFAULT 0
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    book_id INT,
    issue_date DATE,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'Issued',
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

INSERT INTO books (title, author, category, available) VALUES
('Python Programming', 'John Smith', 'Programming', 1),
('Java Programming', 'James Gosling', 'Programming', 1),
('Database Management', 'Korth', 'Database', 1),
('Web Development', 'Robert Smith', 'Web', 2);
```
2. Open `src/main/java/DBConnection.java` and set your own MySQL username and password.

## How to Run (Eclipse + Tomcat 9)
1. Import the project: File > Import > Existing Projects into Workspace.
2. Make sure the Tomcat 9 server runtime is added, and the jars in `WEB-INF/lib` are on the build path.
3. Right click the project > Run As > Run on Server (Tomcat v9.0).
4. Open `http://localhost:8080/LibraryManagementSystem/login.html`

Useful pages:
- `register.html` : register a new member
- `login.html` : login
- `booklist` : book list (Servlet + Spring Service + DAO + JSTL)
- `issue-book.html` / `return-book.html` : issue and return books

## Swing Dashboard
Open `LibrarySwingDashboard.java` in Eclipse and choose Run As > Java Application. MySQL must be running.

## Known Limitations
- Passwords are stored as plain text (academic demo only).
- `members.html` and `books.html` show static content.
