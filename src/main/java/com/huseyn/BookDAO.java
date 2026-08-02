package com.huseyn;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
     void insertBook(Book book);
     void updateBook(Book book);
     void deleteBook(String id);
     List<Book> getBooks();
     Optional<Book> findBookById(String id);
}