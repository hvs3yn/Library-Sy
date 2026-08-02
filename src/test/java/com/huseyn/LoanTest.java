package com.huseyn;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void constructor_setsDefaults() {
        Loan loan = new Loan("B1", "M1");

        assertEquals("B1", loan.getBookId());
        assertEquals("M1", loan.getMemberId());
        assertNotNull(loan.getId());
        assertTrue(loan.getId().matches("\\d{6}"));
        assertEquals(LocalDate.now(), loan.getBorrowDate());
        assertEquals(LocalDate.now().plusDays(14), loan.getDueDate());
        assertNull(loan.getReturnDate());
        assertFalse(loan.isReturned());
    }

    @Test
    void setReturnDate_marksLoanAsReturned() {
        Loan loan = new Loan("B1", "M1");
        LocalDate returnDate = LocalDate.now().plusDays(3);

        loan.setReturnDate(returnDate);

        assertEquals(returnDate, loan.getReturnDate());
        assertTrue(loan.isReturned());
    }

    @Test
    void toString_containsAllFields() {
        Loan loan = new Loan("B1", "M1");

        String result = loan.toString();

        assertTrue(result.contains("B1"));
        assertTrue(result.contains("M1"));
    }
}
