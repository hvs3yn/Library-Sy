# Library Management System

A console-based library management system written in Java, backed by PostgreSQL.
It manages books, members, and loans, with full borrow and return workflows.

## Features
- Add, update, delete, search, and list **books**
- Add, update, delete, search, and list **members**
- **Borrow** and **return** books with automatic availability tracking
- Input validation (value ranges, email format)
- PostgreSQL persistence through JDBC
- Connection pooling with HikariCP
- Transaction-safe writes (commit / rollback)

## Tech Stack
- Java [put your version here, e.g. 21]
- PostgreSQL
- JDBC + HikariCP
- Maven
- JUnit 5 + Mockito (tests)

## Architecture
The app is layered:

Main (menu/UI) → LibraryService (business logic) → DAO interfaces → db (JDBC) → PostgreSQL

The service depends on the DAO **interfaces**, not the concrete implementation,
which lets the DAOs be mocked in unit tests without a real database.

## How to Run
1. Install PostgreSQL and create a database named `library`.
2. Create the tables (see `docs/schema.sql`).
3. Set your database password as an environment variable:
    - Windows (PowerShell): `$env:DB_PASSWORD="yourpassword"`
    - Mac/Linux: `export DB_PASSWORD=yourpassword`
4. Build and run: