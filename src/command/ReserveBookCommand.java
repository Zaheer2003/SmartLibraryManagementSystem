package command;

import system.LibraryManagementSystem;

public class ReserveBookCommand implements Command {
    private LibraryManagementSystem lms;
    private String userId;
    private String bookId;

    public ReserveBookCommand(LibraryManagementSystem lms, String userId, String bookId) {
        this.lms = lms;
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public void execute() {
        lms.reserveBook(userId, bookId);
    }

    @Override
    public void undo() {
        lms.cancelReservation(userId, bookId);
        System.out.println("Undo Success: Reservation for book " + bookId + " has been cancelled.");
    }
}