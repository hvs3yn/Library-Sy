package com.huseyn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DbTest {

    private MockedStatic<DriverManager> stubConnection(Connection connection) {
        MockedStatic<DriverManager> driverManager = mockStatic(DriverManager.class);
        driverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                .thenReturn(connection);
        return driverManager;
    }

    @Test
    void insertBook_bindsAllColumnsAndExecutesUpdate() throws SQLException {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5, 3);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.insertBook(book);
        }

        verify(statement).setString(1, "B1");
        verify(statement).setString(2, "Title");
        verify(statement).setString(3, "Author");
        verify(statement).setString(4, "Genre");
        verify(statement).setInt(5, 2000);
        verify(statement).setInt(6, 5);
        verify(statement).setInt(7, 3);
        verify(statement).executeUpdate();
    }

    @Test
    void updateBook_bindsQuantityAvailableAndId() throws SQLException {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5, 3);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.updateBook(book);
        }

        verify(statement).setInt(1, 5);
        verify(statement).setInt(2, 3);
        verify(statement).setString(3, "B1");
        verify(statement).executeUpdate();
    }

    @Test
    void deleteBook_bindsIdAndExecutesUpdate() throws SQLException {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.deleteBook(book);
        }

        verify(statement).setString(1, "B1");
        verify(statement).executeUpdate();
    }

    @Test
    void getBooks_mapsResultSetRowsToBooks() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("B1");
        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getString("author")).thenReturn("Author");
        when(resultSet.getString("genre")).thenReturn("Genre");
        when(resultSet.getInt("published_year")).thenReturn(2000);
        when(resultSet.getInt("quantity")).thenReturn(5);
        when(resultSet.getInt("available")).thenReturn(3);

        List<Book> books;
        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            books = db.getBooks();
        }

        assertEquals(1, books.size());
        Book book = books.get(0);
        assertEquals("B1", book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals(3, book.getAvailable());
    }

    @Test
    void findById_returnsBook_whenRowFound() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("id")).thenReturn("B1");
        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getString("author")).thenReturn("Author");
        when(resultSet.getString("genre")).thenReturn("Genre");
        when(resultSet.getInt("published_year")).thenReturn(2000);
        when(resultSet.getInt("quantity")).thenReturn(5);
        when(resultSet.getInt("available")).thenReturn(3);

        Optional<Book> result;
        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            result = new db().findById("B1");
        }

        assertTrue(result.isPresent());
        assertEquals("B1", result.get().getId());
        verify(statement).setString(1, "B1");
    }

    @Test
    void findById_returnsEmpty_whenRowNotFound() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<Book> result;
        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            result = new db().findById("missing");
        }

        assertTrue(result.isEmpty());
    }

    @Test
    void insertMember_bindsAllColumns() throws SQLException {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.insertMember(member);
        }

        verify(statement).setString(1, "M1");
        verify(statement).setString(2, "John");
        verify(statement).setString(3, "Doe");
        verify(statement).setInt(4, 25);
        verify(statement).setString(5, "john@example.com");
        verify(statement).executeUpdate();
    }

    @Test
    void updateMember_bindsFieldsAndId() throws SQLException {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.updateMember(member);
        }

        verify(statement).setString(1, "John");
        verify(statement).setString(2, "Doe");
        verify(statement).setInt(3, 25);
        verify(statement).setString(4, "john@example.com");
        verify(statement).setString(5, "M1");
        verify(statement).executeUpdate();
    }

    @Test
    void deleteMember_bindsId() throws SQLException {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.deleteMember(member);
        }

        verify(statement).setString(1, "M1");
        verify(statement).executeUpdate();
    }

    @Test
    void getMembers_mapsResultSetRowsToMembers() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("M1");
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getInt("age")).thenReturn(25);
        when(resultSet.getString("email")).thenReturn("john@example.com");

        List<Member> members;
        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            members = db.getMembers();
        }

        assertEquals(1, members.size());
        assertEquals("M1", members.get(0).getId());
    }

    @Test
    void borrowBook_bindsLoanFieldsIncludingNullReturnDate() throws SQLException {
        Loan loan = new Loan("B1", "M1");
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.borrowBook(loan);
        }

        verify(statement).setString(1, loan.getId());
        verify(statement).setString(2, "B1");
        verify(statement).setString(3, "M1");
        verify(statement).setDate(4, Date.valueOf(loan.getBorrowDate()));
        verify(statement).setDate(5, Date.valueOf(loan.getDueDate()));
        verify(statement).setDate(6, null);
        verify(statement).setBoolean(7, false);
        verify(statement).executeUpdate();
    }

    @Test
    void returnBook_bindsReturnDateAndReturnedFlag() throws SQLException {
        Loan loan = new Loan("B1", "M1");
        loan.setReturnDate(LocalDate.now());
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            db.returnBook(loan);
        }

        verify(statement).setDate(1, Date.valueOf(loan.getReturnDate()));
        verify(statement).setBoolean(2, true);
        verify(statement).setString(3, loan.getId());
        verify(statement).executeUpdate();
    }

    @Test
    void getLoans_mapsResultSetRowsToLoans() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("L1");
        when(resultSet.getString("book_id")).thenReturn("B1");
        when(resultSet.getString("member_id")).thenReturn("M1");
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14);
        when(resultSet.getDate("borrow_date")).thenReturn(Date.valueOf(borrowDate));
        when(resultSet.getDate("due_date")).thenReturn(Date.valueOf(dueDate));
        when(resultSet.getDate("return_date")).thenReturn(null);
        when(resultSet.getBoolean("returned")).thenReturn(false);

        List<Loan> loans;
        try (MockedStatic<DriverManager> driverManager = stubConnection(connection)) {
            loans = db.getLoans();
        }

        assertEquals(1, loans.size());
        Loan loan = loans.get(0);
        assertEquals("L1", loan.getId());
        assertEquals(borrowDate, loan.getBorrowDate());
        assertEquals(dueDate, loan.getDueDate());
        assertNull(loan.getReturnDate());
        assertFalse(loan.isReturned());
    }
}
