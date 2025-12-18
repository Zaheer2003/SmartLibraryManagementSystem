package command;

import system.LibraryManagementSystem;

public class ReturnBookCommand implements Command {
    private LibraryManagementSystem lms;
    private String userId;
    private String bookId;

    public ReturnBookCommand(LibraryManagementSystem lms, String userId, String bookId) {
        this.lms = lms;
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public void execute() {
        lms.returnBook(userId, bookId);
    }

    @Override
    public void undo() {
        lms.borrowBook(userId, bookId);
        System.out.println("Undo Success: Book " + bookId + " is now back in your borrowed list.");
    }
}