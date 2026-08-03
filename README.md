# Library Management System

A console-based library management system in Java, backed by PostgreSQL.
Manage books, members, and loans with full borrow/return workflows.

## Features
- Add, update, delete, search, and list books, members, and loans
- Borrow and return books with availability tracking
- Input validation (ranges, email format)
- PostgreSQL persistence via JDBC with a connection pool (HikariCP)
- Transaction-safe writes with commit/rollback

## Tech Stack
- Java (version …)
- PostgreSQL
- JDBC + HikariCP connection pooling
- Maven
- JUnit 5 + Mockito (tests)

## Architecture
UI (Main / menu)  →  LibraryService (business logic)
→  DAO layer (BookDAO / MemberDAO / LoanDAO interfaces)
→  db (JDBC implementation)  →  PostgreSQL

The service depends on DAO *interfaces*, not the concrete implementation,
which allows the DAOs to be mocked in unit tests.

## How to Run
1. Install PostgreSQL and create a database named `library`.
2. Run the schema in `docs/schema.sql` (create this).
3. Set your DB credentials via environment variables (see Configuration).
4. Build and run: