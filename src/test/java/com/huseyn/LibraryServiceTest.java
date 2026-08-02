package com.huseyn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private LoanDAO loanDAO;
    private LibraryService service;

    @BeforeEach
    void setUp() {
        bookDAO = mock(BookDAO.class);
        memberDAO = mock(MemberDAO.class);
        loanDAO = mock(LoanDAO.class);
        service = new LibraryService(bookDAO, memberDAO, loanDAO);
    }

    @Test
    void addBook_delegatesToBookDAO() {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3);

        service.addBook(book);

        verify(bookDAO).insertBook(book);
    }

    @Test
    void updateBook_recalculatesAvailableFromBorrowedCount() {
        Book existing = new Book("B1", "Title", "Author", "Genre", 2000, 5, 2);
        when(bookDAO.findBookById("B1")).thenReturn(Optional.of(existing));

        service.updateBook("B1", 10);

        assertEquals(10, existing.getQuantity());
        assertEquals(7, existing.getAvailable());
        verify(bookDAO).updateBook(existing);
    }

    @Test
    void updateBook_doesNothingWhenBookNotFound() {
        when(bookDAO.findBookById("missing")).thenReturn(Optional.empty());

        service.updateBook("missing", 10);

        verify(bookDAO, never()).updateBook(any());
    }

    @Test
    void deleteBook_delegatesToBookDAO() {
        service.deleteBook("B1");

        verify(bookDAO).deleteBook("B1");
    }

    @Test
    void findBook_returnsBookWhenPresent() {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3);
        when(bookDAO.findBookById("B1")).thenReturn(Optional.of(book));

        assertEquals(book, service.findBook("B1"));
    }

    @Test
    void findBook_returnsNullWhenAbsent() {
        when(bookDAO.findBookById("missing")).thenReturn(Optional.empty());

        assertNull(service.findBook("missing"));
    }

    @Test
    void addMember_delegatesToMemberDAO() {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");

        service.addMember(member);

        verify(memberDAO).insertMember(member);
    }

    @Test
    void deleteMember_delegatesToMemberDAO() {
        service.deleteMember("M1");

        verify(memberDAO).deleteMember("M1");
    }

    @Test
    void findMember_returnsMemberWhenPresent() {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");
        when(memberDAO.findMemberById("M1")).thenReturn(Optional.of(member));

        assertEquals(member, service.findMember("M1"));
    }

    @Test
    void findMember_returnsNullWhenAbsent() {
        when(memberDAO.findMemberById("missing")).thenReturn(Optional.empty());

        assertNull(service.findMember("missing"));
    }

    @Test
    void borrowBook_reducesAvailabilityAndCreatesLoan() {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3, 2);
        when(bookDAO.findBookById("B1")).thenReturn(Optional.of(book));

        service.borrowBook("B1", "M1");

        assertEquals(1, book.getAvailable());
        verify(bookDAO).updateBook(book);
        verify(loanDAO).borrowBook(any(Loan.class));
    }

    @Test
    void borrowBook_doesNothingWhenNoCopiesAvailable() {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3, 0);
        when(bookDAO.findBookById("B1")).thenReturn(Optional.of(book));

        service.borrowBook("B1", "M1");

        verify(loanDAO, never()).borrowBook(any());
        verify(bookDAO, never()).updateBook(any());
    }

    @Test
    void borrowBook_doesNothingWhenBookNotFound() {
        when(bookDAO.findBookById("missing")).thenReturn(Optional.empty());

        service.borrowBook("missing", "M1");

        verify(loanDAO, never()).borrowBook(any());
    }

    @Test
    void returnBook_marksLoanReturnedAndRestocksBook() {
        Loan loan = new Loan("B1", "M1");
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3, 1);
        when(loanDAO.findLoanById("L1")).thenReturn(Optional.of(loan));
        when(bookDAO.findBookById("B1")).thenReturn(Optional.of(book));

        service.returnBook("L1");

        assertTrue(loan.isReturned());
        assertNotNull(loan.getReturnDate());
        assertEquals(2, book.getAvailable());
        verify(loanDAO).returnBook(loan);
        verify(bookDAO).updateBook(book);
    }

    @Test
    void returnBook_doesNothingWhenLoanNotFound() {
        when(loanDAO.findLoanById("missing")).thenReturn(Optional.empty());

        service.returnBook("missing");

        verify(loanDAO, never()).returnBook(any());
    }

    @Test
    void returnBook_doesNothingWhenAlreadyReturned() {
        Loan loan = new Loan("B1", "M1");
        loan.setReturnDate(java.time.LocalDate.now());
        when(loanDAO.findLoanById("L1")).thenReturn(Optional.of(loan));

        service.returnBook("L1");

        verify(loanDAO, never()).returnBook(any());
    }

    @Test
    void findLoan_returnsLoanWhenPresent() {
        Loan loan = new Loan("B1", "M1");
        when(loanDAO.findLoanById("L1")).thenReturn(Optional.of(loan));

        assertEquals(loan, service.findLoan("L1"));
    }

    @Test
    void findLoan_returnsNullWhenAbsent() {
        when(loanDAO.findLoanById("missing")).thenReturn(Optional.empty());

        assertNull(service.findLoan("missing"));
    }

    @Test
    void displayBooks_readsFromBookDAO() {
        when(bookDAO.getBooks()).thenReturn(List.of(new Book("B1", "Title", "Author", "Genre", 2000, 3)));

        service.displayBooks();

        verify(bookDAO).getBooks();
    }
}
