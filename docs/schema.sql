-- Library Management System — Database Schema
-- PostgreSQL

CREATE TABLE books (
    id             VARCHAR(50) PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    author         VARCHAR(255) NOT NULL,
    genre          VARCHAR(100),
    published_year INTEGER,
    quantity       INTEGER NOT NULL,
    available      INTEGER NOT NULL
);

CREATE TABLE members (
    id         VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    age        INTEGER,
    email      VARCHAR(255)
);

CREATE TABLE loans (
    id          VARCHAR(50) PRIMARY KEY,
    book_id     VARCHAR(50) NOT NULL REFERENCES books(id),
    member_id   VARCHAR(50) NOT NULL REFERENCES members(id),
    borrow_date DATE NOT NULL,
    due_date    DATE NOT NULL,
    return_date DATE,
    returned    BOOLEAN NOT NULL DEFAULT FALSE
);

-- Indexes on the foreign-key columns (frequently joined/filtered on)
CREATE INDEX idx_loans_book_id   ON loans (book_id);
CREATE INDEX idx_loans_member_id ON loans (member_id);