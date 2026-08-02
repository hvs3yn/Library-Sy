package com.huseyn;

public class Main {
    public static void main(String[] args) {
        LibraryService service = new LibraryService();
        Validator validator = new Validator();

        while (true) {
            LibraryService.displayMenu();
            int choice = validator.readInt("Choose an option: ");

            switch (choice) {
                case 1: {
                    // Add Book
                    String title = validator.readName("Title: ");
                    String author = validator.readName("Author: ");
                    String genre = validator.readName("Genre: ");
                    int publishedYear = validator.readInt("Published year: ", 1450, 2026);
                    int quantity = validator.readInt("Quantity: ", 1, Integer.MAX_VALUE);
                    Book book = new Book(title, author, genre, publishedYear, quantity);
                    service.addBook(book);
                    System.out.println("Book added successfully.");
                    break;
                }

                case 2: {
                    // Update Book
                    String id = validator.readString("Book ID: ");
                    int quantity = validator.readInt("New quantity: ", 0, Integer.MAX_VALUE);
                    service.updateBook(id, quantity);
                    System.out.println("Book updated.");
                    break;
                }

                case 3: {
                    // Delete Book
                    String id = validator.readString("Book ID: ");
                    service.deleteBook(id);
                    System.out.println("Book deleted.");
                    break;
                }

                case 4: {
                    // Search Book
                    String id = validator.readString("Book ID: ");
                    Book book=service.findBook(id);
                    if(book!=null){
                        System.out.println(book);
                    }
                    break;
                }

                case 5: {
                    // Display Books
                    service.displayBooks();
                    break;
                }

                case 6: {
                    // Add Member
                    String firstName = validator.readName("First name: ");
                    String lastName = validator.readName("Last name: ");
                    int age = validator.readInt("Age: ", 1, 120);
                    String email = validator.readEmail("Email: ");
                    Member member = new Member(firstName, lastName, age, email);
                    service.addMember(member);
                    System.out.println("Member added successfully.");
                    break;
                }

                case 7: {
                    // Update Member
                    String id = validator.readString("Member ID: ");
                    String firstName = validator.readName("New first name: ");
                    String lastName = validator.readName("New last name: ");
                    int age = validator.readInt("New age: ", 1, 120);
                    String email = validator.readEmail("New email: ");
                    service.updateMember(id, firstName, lastName, age, email);
                    System.out.println("Member updated.");
                    break;
                }

                case 8: {
                    // Delete Member
                    String id = validator.readString("Member ID: ");
                    service.deleteMember(id);
                    System.out.println("Member deleted.");
                    break;
                }

                case 9: {
                    // Search Member
                    String id = validator.readString("Member ID: ");
                    Member member=service.findMember(id);
                    if(member!=null){
                        System.out.println(member);
                    }
                    break;
                }

                case 10: {
                    // Display Members
                    service.displayMembers();
                    break;
                }

                case 11: {
                    // Borrow Book
                    String bookId = validator.readString("Book ID: ");
                    String memberId = validator.readString("Member ID: ");
                    service.borrowBook(bookId, memberId);
                    break;
                }

                case 12: {
                    // Return Book
                    String loanId = validator.readString("Loan ID: ");
                    service.returnBook(loanId);
                    break;
                }

                case 13: {
                    // Display Borrowed Books
                    service.displayBorrowedBooks();
                    break;
                }

                case 14: {
                    System.out.println("Exiting...");
                    validator.close();
                    FileManager fileManager=new FileManager();
                    fileManager.save();
                    return;
                }

                default: {
                    System.out.println("Invalid option!");
                }
            }
        }
    }
}
