package com.huseyn;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {
    void borrowBook(Loan loan);
    void returnBook(Loan loan);
    List<Loan> getLoans();
    Optional<Loan> findLoanById(String id);
}