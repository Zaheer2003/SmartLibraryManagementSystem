package librarian;

import book.Book;
import system.LibraryManagementSystem;
import user.User;

public class Librarian {
    private String librarianId;
    private String name;
    private String email;
    private String password;
    private LibraryManagementSystem lms;


    public Librarian(String librarianId, String name, String email, String password, LibraryManagementSystem lms) {
        this.librarianId = librarianId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.lms = lms;
    }

    public String getLibrarianId() {
        return librarianId;
    }

    public String getLibaraianName() {
        return name;
    }

    public String getLibrarianEmail() {return email;}

    public String getLibrarianPassword() {return password;}
    //Users Manage
    public void registerUser(User user) {
        if (lms != null) {
            lms.registerUser(user);
            System.out.println(" Librarian " + name + "registered new user: " + user.getName());
        }
    }

    public void updateUser(String userId, String newEmail, String newContact) {
        if (lms != null) {
            lms.updateUser(userId, newEmail, newContact);
            System.out.println(" Librarian " + name + " initiated update for user ID: " + userId);
        }
    }

    public boolean removeUser(String userId) {
        if (lms != null) {
            boolean isRemoved = lms.removeUser(userId);
            if (isRemoved) {
                System.out.println(" Librarian " + name + " removed user ID: " + userId);
                return true;
            }
        }
        return false;
    }

    //Books Manage
    public void addBook(Book book) {
        if (lms != null) {
            lms.addBook(book);
            System.out.println("Librarian "+ name + " added a new book: " + book.getTitle());
        }
    }

    public void updateBook(String bookId, String newTitle, String newAuthor, String newCategory, String newIsbn){
       if (lms != null) {
           lms.updateBookDetails(bookId, newTitle, newAuthor, newCategory, newIsbn);
           System.out.println(" Librarian " + name + "updated book ID: " + bookId);
       }
    }

    public boolean removeBook(String bookId) {
        if (lms != null) {
            boolean isRemoved = lms.removeBook(bookId);
            if (isRemoved) {
                System.out.println(" Book ID: " + bookId + " removed by " + name + ".");
                return true;
            }
        }
        return false;
    }

    public void generateReport(){}

    public void notifyUser(){
        if (lms != null) {
            lms.notifyOverdueUsers();
            System.out.println("Librar " + name + " sent overdue notfications.");
        }
    }

}


