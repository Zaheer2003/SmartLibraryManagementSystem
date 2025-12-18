package reservation;

import book.Book;
import user.User;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private String reservationId;
    private User user;
    private Book book;
    private LocalDate reserveDate;
    private ReservationStatus status;

    public Reservation (String reservationId, User user, Book book) {
        this.reservationId = reservationId;
        this.user = user;
        this.book = book;
        this.reserveDate = LocalDate.now();
        this.status = ReservationStatus.ACTIVE;
    }

    public void confirmReservation() {
        if(status == ReservationStatus.ACTIVE) {
            status = ReservationStatus.CONFIRMED;
            user.update("Your reserved book '" + book.getTitle() + "' is now available.");
        }
    }

    public void cancelReservation() {
        status = ReservationStatus.CANCELLED;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }
}





