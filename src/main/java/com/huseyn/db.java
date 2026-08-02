package com.huseyn;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class db implements BookDAO,MemberDAO,LoanDAO {
    private static final DataSource dataSource = DataBase.getDataSource();

    private  Connection getConnection() throws SQLException {
        return dataSource.getConnection();   // borrow from pool — was DriverManager before
    }

    public  void insertBook(Book book)  {
        String sql = "INSERT INTO books (id, title, author, genre, published_year, quantity,available) VALUES(?,?,?,?,?,?,?)";
        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql);){


                statement.setString(1, book.getId());
                statement.setString(2, book.getTitle());
                statement.setString(3, book.getAuthor());
                statement.setString(4, book.getGenre());
                statement.setInt(5,book.getPublishedYear());
                statement.setInt(6,book.getQuantity());
                statement.setInt(7,book.getAvailable());
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  void updateBook(Book book){
        String sql = "UPDATE books SET quantity=?,available=? WHERE id=?";
        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql);){
                statement.setInt(1, book.getQuantity());
                statement.setInt(2, book.getAvailable());
                statement.setString(3,book.getId());
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  void deleteBook(String id){
        String sql="Delete FROM books where id=?";
        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql);){

                statement.setString(1,id);
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  List<Book> getBooks() {
        List<Book> books = new ArrayList<>();

        String sql = """
            SELECT id, title, author, genre,
                   published_year, quantity, available
            FROM books
            """;

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("published_year"),
                        rs.getInt("quantity")
                );

                book.setAvailable(rs.getInt("available"));

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get books", e);
        }

        return books;
    }
    @Override
    public Optional<Book> findBookById(String id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(
                            rs.getString("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getInt("published_year"),
                            rs.getInt("quantity"),
                            rs.getInt("available")
                    );

                    return Optional.of(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }


    public  void insertMember(Member member){
        String sql = "INSERT INTO members (id, first_name, last_name, age, email) VALUES (?,?,?,?,?)";
        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql)){

                statement.setString(1, member.getId());
                statement.setString(2, member.getFirstName());
                statement.setString(3, member.getLastName());
                statement.setInt(4, member.getAge());
                statement.setString(5, member.getEmail());
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  void updateMember(Member member){
        String sql = "UPDATE members SET first_name=?, last_name=?, age=?, email=? WHERE id=?";
        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql)){

                statement.setString(1, member.getFirstName());
                statement.setString(2, member.getLastName());
                statement.setInt(3, member.getAge());
                statement.setString(4, member.getEmail());
                statement.setString(5, member.getId());
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     public void deleteMember(String id){
        String sql = "DELETE FROM members WHERE id=?";
        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql);){

                statement.setString(1, id);
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();

        String sql = "SELECT id, first_name, last_name, age, email FROM members";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Member member = new Member(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("email")
                );

                members.add(member);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get members", e);
        }
        return members;
    }

    @Override
    public Optional<Member> findMemberById(String id) {
        String sql = "SELECT id, first_name, last_name, age, email FROM members WHERE id = ?";
        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Member(
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("age"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    public  void borrowBook(Loan loan){
        String sql = "INSERT INTO loans (id, book_id, member_id, borrow_date, due_date, return_date, returned) VALUES (?,?,?,?,?,?,?)";

        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql);){

                statement.setString(1, loan.getId());
                statement.setString(2, loan.getBookId());
                statement.setString(3, loan.getMemberId());
                statement.setDate(4, Date.valueOf(loan.getBorrowDate()));
                statement.setDate(5, Date.valueOf(loan.getDueDate()));
                statement.setDate(6, loan.getReturnDate() == null ? null : Date.valueOf(loan.getReturnDate()));
                statement.setBoolean(7, loan.isReturned());
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     public void returnBook(Loan loan){
        String sql = "UPDATE loans SET return_date=?, returned=? WHERE id=?";

        Connection connection=null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            try(var statement = connection.prepareStatement(sql);){
                statement.setDate(1, Date.valueOf(loan.getReturnDate()));
                statement.setBoolean(2, loan.isReturned());
                statement.setString(3, loan.getId());
                statement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            try{
                if( connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed",e);
        }finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public  List<Loan> getLoans(){
        List<Loan> loans = new ArrayList<>();

        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date, returned FROM loans";
        try(var connection = getConnection();
            var statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while(rs.next()){
                Loan loan = new Loan();
                loan.setId(rs.getString("id"));
                loan.setBookId(rs.getString("book_id"));
                loan.setMemberId(rs.getString("member_id"));
                loan.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                loan.setDueDate(rs.getDate("due_date").toLocalDate());
                Date returnDate = rs.getDate("return_date");
                loan.setReturnDate(returnDate == null ? null : returnDate.toLocalDate());
                loan.setReturned(rs.getBoolean("returned"));
                loans.add(loan);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return loans;
    }

    @Override
    public Optional<Loan> findLoanById(String id) {
        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date, returned FROM loans WHERE id = ?";
        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Loan loan = new Loan();
                    loan.setId(rs.getString("id"));
                    loan.setBookId(rs.getString("book_id"));
                    loan.setMemberId(rs.getString("member_id"));
                    loan.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                    loan.setDueDate(rs.getDate("due_date").toLocalDate());
                    Date rd = rs.getDate("return_date");
                    loan.setReturnDate(rd == null ? null : rd.toLocalDate());
                    loan.setReturned(rs.getBoolean("returned"));
                    return Optional.of(loan);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


}
