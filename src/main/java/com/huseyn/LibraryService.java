package com.huseyn;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {
    List<Book> books;
    List<Member> members;
    List<Loan> loans;

    public LibraryService(){
        books = db.getBooks();
        members = db.getMembers();
        loans = db.getLoans();
    }

    void addBook(Book book){
        books.add(book);
        db.insertBook(book);
    }
    void updateBook(String id, int quantity){
        for(Book book: books){
            if(book.getId().equals(id)){
                int x=book.getQuantity()-book.getAvailable();
                book.setQuantity(quantity);
                book.setAvailable(quantity-x);
                db.updateBook(book);
                return;
            }
        }
    }

    void deleteBook(String id){
        for(Book book: books){
            if(book.getId().equals(id)){
                db.deleteBook(book);
                books.remove(book);
                return;
            }
        }
    }

    void displayBooks(){
        for(Book book:books){
            System.out.println(book.toString());
        }
    }

    void findBook(String id){
        for(Book book: books){
            if(book.getId().equals(id)){
                System.out.println(book);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    void addMember(Member member){
        members.add(member);
        db.insertMember(member);
    }

    void updateMember(String id, String firstName, String lastName, int age, String email){
        for(Member member: members){
            if(member.getId().equals(id)){
                member.setFirstName(firstName);
                member.setLastName(lastName);
                member.setAge(age);
                member.setEmail(email);
                db.updateMember(member);
                return;
            }
        }
        System.out.println("Member not found.");
    }

    void deleteMember(String id){
        for(Member member: members){
            if(member.getId().equals(id)){
                db.deleteMember(member);
                members.remove(member);
                return;
            }
        }
        System.out.println("Member not found.");
    }

    void displayMembers(){
        for(Member member: members){
            System.out.println(member);
        }
    }

    void borrowBook(String bookId, String memberId){
        for(Book book: books){
            if(book.getId().equals(bookId)){
                if(book.getAvailable() <= 0){
                    System.out.println("No copies available.");
                    return;
                }
                Loan loan = new Loan(bookId, memberId);
                loans.add(loan);
                db.borrowBook(loan);
                book.setAvailable(book.getAvailable() - 1);
                db.updateBook(book);
                System.out.println("Book borrowed successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    void returnBook(String loanId){
        for(Loan loan: loans){
            if(loan.getId().equals(loanId) && !loan.isReturned()){
                loan.setReturnDate(LocalDate.now());
                db.returnBook(loan);
                for(Book book: books){
                    if(book.getId().equals(loan.getBookId())){
                        book.setAvailable(book.getAvailable() + 1);
                        db.updateBook(book);
                        break;
                    }
                }
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Loan not found or already returned.");
    }

    void displayBorrowedBooks(){
        for(Loan loan: loans){
            if(!loan.isReturned()){
                System.out.println(loan);
            }
        }
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
                9. Display Members
                
                10. Borrow Book
                11. Return Book
                12. Borrowed Books
                
                13. Exit""");
    }
}
