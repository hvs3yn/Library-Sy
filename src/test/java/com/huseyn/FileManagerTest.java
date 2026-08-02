package com.huseyn;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class FileManagerTest {

    private static final Path BOOKS = Path.of("books.json");
    private static final Path MEMBERS = Path.of("members.json");
    private static final Path LOANS = Path.of("loans.json");

    private byte[] booksBackup;
    private byte[] membersBackup;
    private byte[] loansBackup;

    @BeforeEach
    void backupExistingFiles() throws IOException {
        booksBackup = Files.exists(BOOKS) ? Files.readAllBytes(BOOKS) : null;
        membersBackup = Files.exists(MEMBERS) ? Files.readAllBytes(MEMBERS) : null;
        loansBackup = Files.exists(LOANS) ? Files.readAllBytes(LOANS) : null;
    }

    @AfterEach
    void restoreOriginalFiles() throws IOException {
        restore(BOOKS, booksBackup);
        restore(MEMBERS, membersBackup);
        restore(LOANS, loansBackup);
    }

    private void restore(Path path, byte[] backup) throws IOException {
        if (backup == null) {
            Files.deleteIfExists(path);
        } else {
            Files.write(path, backup);
        }
    }

    @Test
    void save_writesBooksMembersAndLoansFetchedFromDb() throws IOException {
        Book book = new Book("B1", "Title", "Author", "Genre", 2000, 3);
        Member member = new Member("M1", "John", "Doe", 20, "john@example.com");
        Loan loan = new Loan("B1", "M1");

        try (MockedStatic<db> dbMock = mockStatic(db.class)) {
            dbMock.when(db::getBooks).thenReturn(List.of(book));
            dbMock.when(db::getMembers).thenReturn(List.of(member));
            dbMock.when(db::getLoans).thenReturn(List.of(loan));

            new FileManager().save();
        }

        assertTrue(Files.exists(BOOKS));
        assertTrue(Files.exists(MEMBERS));
        assertTrue(Files.exists(LOANS));

        assertTrue(Files.readString(BOOKS).contains("\"id\": \"B1\""));
        assertTrue(Files.readString(MEMBERS).contains("\"id\": \"M1\""));
        assertTrue(Files.readString(LOANS).contains("\"bookId\": \"B1\""));
    }
}
