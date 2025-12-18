package command;

import system.LibraryManagementSystem;

public class CancelReservationCommand implements Command {
    private LibraryManagementSystem lms;
    private String userId;
    private String bookId;

    public CancelReservationCommand(LibraryManagementSystem lms, String userId, String bookId) {
        this.lms = lms;
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public void execute() {
        lms.cancelReservation(userId, bookId);
    }

    @Override
    public void undo() {
        lms.reserveBook(userId, bookId);
        System.out.println("Undo: Reservation restored for Book ID " + bookId);
    }
}