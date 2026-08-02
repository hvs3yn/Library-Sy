package com.huseyn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void constructorWithId_setsAvailableEqualToQuantity() {
        Book book = new Book("B1", "Clean Code", "Robert Martin", "Programming", 2008, 5);

        assertEquals("B1", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAuthor());
        assertEquals("Programming", book.getGenre());
        assertEquals(2008, book.getPublishedYear());
        assertEquals(5, book.getQuantity());
        assertEquals(5, book.getAvailable());
    }

    @Test
    void constructorWithExplicitAvailable_usesGivenValue() {
        Book book = new Book("B1", "Clean Code", "Robert Martin", "Programming", 2008, 5, 2);

        assertEquals(5, book.getQuantity());
        assertEquals(2, book.getAvailable());
    }

    @Test
    void constructorWithoutId_generatesSixDigitNumericId() {
        Book book = new Book("Clean Code", "Robert Martin", "Programming", 2008, 5);

        assertNotNull(book.getId());
        assertTrue(book.getId().matches("\\d{6}"));
    }

    @Test
    void settersUpdateFields() {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3);

        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setGenre("New Genre");
        book.setPublishedYear(2020);
        book.setQuantity(10);
        book.setAvailable(7);

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("New Genre", book.getGenre());
        assertEquals(2020, book.getPublishedYear());
        assertEquals(10, book.getQuantity());
        assertEquals(7, book.getAvailable());
    }

    @Test
    void toString_containsAllFields() {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3);

        String result = book.toString();

        assertTrue(result.contains("B1"));
        assertTrue(result.contains("Title"));
        assertTrue(result.contains("Author"));
        assertTrue(result.contains("Genre"));
        assertTrue(result.contains("2000"));
    }
}
