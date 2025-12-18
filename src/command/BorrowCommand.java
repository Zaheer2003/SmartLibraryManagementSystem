package command;

import book.Book;
import system.LibraryManagementSystem;

public class BorrowCommand implements Command{
    private LibraryManagementSystem lms;
    private String userId;
    private String bookId;

    public BorrowCommand(LibraryManagementSystem lms, String userId, String bookId) {
        this.lms = lms;
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public void execute() {
        lms.borrowBook(userId, bookId);
    }

    @Override
    public void undo() {
        lms.returnBook(userId, bookId);
        System.out.println("Undo Success: Book " + bookId + " has been returned to the library.");
    }

}
