package borrowRecord;

import book.Book;
import user.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowRecord {
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public BorrowRecord(User user, Book book, LocalDate borrowDate, LocalDate dueDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public BorrowRecord(User user, Book book) {
        this(user, book, LocalDate.now(), LocalDate.now().plusDays(14));
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getLateDays() {
        LocalDate comprisonDate;

        if (returnDate != null) {
            comprisonDate = returnDate;
        } else {
            comprisonDate = LocalDate.now();
        }
        if (!comprisonDate.isAfter(dueDate)) {
            return 0;
        }

        return (int) ChronoUnit.DAYS.between(dueDate,comprisonDate);
    }

    public void markReturned(LocalDate date) {
        this.returnDate = date;
    }
}


