package com.huseyn;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final LoanDAO loanDAO;

    public LibraryService(){
        db dataBase = new db();
        this.bookDAO = dataBase;
        this.memberDAO = dataBase;
        this.loanDAO = dataBase;
    }

    LibraryService(BookDAO bookDAO, MemberDAO memberDAO, LoanDAO loanDAO){
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
        this.loanDAO = loanDAO;
    }

    void addBook(Book book){
        bookDAO.insertBook(book);
    }
    void updateBook(String id, int quantity){
        Book book ;
        Optional<Book> optionalOfBook =bookDAO.findBookById(id);
        if(optionalOfBook.isPresent()){
            book=optionalOfBook.get();
            int x=book.getQuantity()-book.getAvailable();
            book.setQuantity(quantity);
            book.setAvailable(quantity-x);
            bookDAO.updateBook(book);
        }

    }

    void deleteBook(String id){
        bookDAO.deleteBook(id);
    }

    void displayBooks(){
        List<Book> books=bookDAO.getBooks();
        books.forEach(System.out::println);
    }

    Book findBook(String id){
        Book book ;
        Optional<Book> optionalBook =bookDAO.findBookById(id);
        if(optionalBook.isPresent()){
            book=optionalBook.get();
            return book;
        }
        System.out.println("Book not found!");
        return null;
    }

    void addMember(Member member){
        memberDAO.insertMember(member);
    }

    void updateMember(String id, String firstName, String lastName, int age, String email){
        Member member=new Member(id,firstName,lastName,age,email);
        memberDAO.updateMember(member);
    }

    void deleteMember(String id){
        memberDAO.deleteMember(id);
    }

    void displayMembers(){
        List<Member> members= memberDAO.getMembers();
        members.forEach(System.out::println);
    }
    Member findMember(String id){
        Member member;
        Optional<Member> optionalMember=memberDAO.findMemberById(id);
        if(optionalMember.isPresent()){
            member=optionalMember.get();
            return member;
        }
        System.out.println("Member not found!");
        return null;
    }

    void borrowBook(String bookId, String memberId){
        Book book = findBook(bookId);
        if(book == null){
            System.out.println("Book not found.");
            return;
        }
        if(book.getAvailable() <= 0){
            System.out.println("No copies available.");
            return;
        }
        Loan loan = new Loan(bookId, memberId);
        book.setAvailable(book.getAvailable() - 1);
        bookDAO.updateBook(book);
        loanDAO.borrowBook(loan);
        System.out.println("Book borrowed successfully.");
    }

    void returnBook(String loanId){
        Loan loan = findLoan(loanId);
        if(loan == null || loan.isReturned()){
            System.out.println("Loan not found or already returned.");
            return;
        }
        loan.setReturnDate(LocalDate.now());
        loanDAO.returnBook(loan);

        Book book = findBook(loan.getBookId());
        if(book != null){
            book.setAvailable(book.getAvailable() + 1);
            bookDAO.updateBook(book);
        }
        System.out.println("Book returned successfully.");
    }

    void displayBorrowedBooks(){
        List<Loan> loans= loanDAO.getLoans();
        loans.forEach(System.out::println);
    }
    Loan findLoan(String id){
        Loan loan;
        Optional<Loan> optionalLoan=loanDAO.findLoanById(id);
        if(optionalLoan.isPresent()){
            loan=optionalLoan.get();
            return loan;
        }
        return null;
    }
    public static void displayMenu(){
        System.out.println("""
                
                ===== LIBRARY MANAGEMENT SYSTEM =====
                1. Add Book
                2. Update Book
                3. Delete Book
                4. Search Book
                5. Display Books
                
                6. Add Member
                7. Update Member
                8. Delete Member
                9. Search Member
                10. Display Members

                11. Borrow Book
                12. Return Book
                13. Borrowed Books

                14. Exit""");
    }
}
