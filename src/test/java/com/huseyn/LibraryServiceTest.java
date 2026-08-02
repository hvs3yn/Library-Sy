package com.huseyn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    private LibraryService newService(List<Book> books, List<Member> members, List<Loan> loans, MockedStatic<db> dbMock) {
        dbMock.when(db::getBooks).thenReturn(books);
        dbMock.when(db::getMembers).thenReturn(members);
        dbMock.when(db::getLoans).thenReturn(loans);
        return new LibraryService();
    }

    @Test
    void addBook_persistsAndTracksBook() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            LibraryService service = newService(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), dbMock);
            Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3);

            service.addBook(book);

            assertTrue(service.books.contains(book));
            dbMock.verify(() -> db.insertBook(book));
        }
    }

    @Test
    void updateBook_adjustsQuantityAndAvailable_preservingBorrowedCount() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5);
            book.setAvailable(3); // 2 copies currently borrowed
            LibraryService service = newService(new ArrayList<>(List.of(book)), new ArrayList<>(), new ArrayList<>(), dbMock);

            service.updateBook("B1", 10);

            assertEquals(10, book.getQuantity());
            assertEquals(8, book.getAvailable()); // borrowed count (2) preserved
            dbMock.verify(() -> db.updateBook(book));
        }
    }

    @Test
    void deleteBook_removesFromListAndDb() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5);
            LibraryService service = newService(new ArrayList<>(List.of(book)), new ArrayList<>(), new ArrayList<>(), dbMock);

            service.deleteBook("B1");

            assertFalse(service.books.contains(book));
            dbMock.verify(() -> db.deleteBook(book));
        }
    }

    @Test
    void addMember_persistsAndTracksMember() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            LibraryService service = newService(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), dbMock);
            Member member = new Member("M1", "John", "Doe", 20, "john@example.com");

            service.addMember(member);

            assertTrue(service.members.contains(member));
            dbMock.verify(() -> db.insertMember(member));
        }
    }

    @Test
    void updateMember_updatesFieldsAndPersists() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Member member = new Member("M1", "John", "Doe", 20, "john@example.com");
            LibraryService service = newService(new ArrayList<>(), new ArrayList<>(List.of(member)), new ArrayList<>(), dbMock);

            service.updateMember("M1", "Jane", "Smith", 25, "jane@example.com");

            assertEquals("Jane", member.getFirstName());
            assertEquals("Smith", member.getLastName());
            assertEquals(25, member.getAge());
            assertEquals("jane@example.com", member.getEmail());
            dbMock.verify(() -> db.updateMember(member));
        }
    }

    @Test
    void deleteMember_removesFromListAndDb() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Member member = new Member("M1", "John", "Doe", 20, "john@example.com");
            LibraryService service = newService(new ArrayList<>(), new ArrayList<>(List.of(member)), new ArrayList<>(), dbMock);

            service.deleteMember("M1");

            assertFalse(service.members.contains(member));
            dbMock.verify(() -> db.deleteMember(member));
        }
    }

    @Test
    void borrowBook_decrementsAvailabilityAndCreatesLoan() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5);
            LibraryService service = newService(new ArrayList<>(List.of(book)), new ArrayList<>(), new ArrayList<>(), dbMock);

            service.borrowBook("B1", "M1");

            assertEquals(4, book.getAvailable());
            assertEquals(1, service.loans.size());
            dbMock.verify(() -> db.borrowBook(any(Loan.class)));
            dbMock.verify(() -> db.updateBook(book));
        }
    }

    @Test
    void borrowBook_doesNothing_whenNoCopiesAvailable() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Book book = new Book("B1", "Title", "Author", "Genre", 2000, 1);
            book.setAvailable(0);
            LibraryService service = newService(new ArrayList<>(List.of(book)), new ArrayList<>(), new ArrayList<>(), dbMock);

            service.borrowBook("B1", "M1");

            assertEquals(0, service.loans.size());
            dbMock.verify(() -> db.borrowBook(any(Loan.class)), never());
        }
    }

    @Test
    void returnBook_marksLoanReturnedAndIncrementsAvailability() {
        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            Book book = new Book("B1", "Title", "Author", "Genre", 2000, 5);
            book.setAvailable(4);
            Loan loan = new Loan("B1", "M1");
            LibraryService service = newService(new ArrayList<>(List.of(book)), new ArrayList<>(), new ArrayList<>(List.of(loan)), dbMock);

            service.returnBook(loan.getId());

            assertTrue(loan.isReturned());
            assertEquals(5, book.getAvailable());
            dbMock.verify(() -> db.returnBook(loan));
            dbMock.verify(() -> db.updateBook(book));
        }
    }
}
