package state;

import book.Book;

public class AvailableState implements BookState {

    public AvailableState() {
    }

    @Override
    public void borrow(Book book) {
        book.setState(new BorrowedState());
        System.out.println("Book '" + book.getTitle() + "' state changed to Borrowed.");
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("Book is already available.");
    }

    @Override
    public void reserve(Book book) {
        book.setState(new ReservedState());
        System.out.println("Book '" + book.getTitle() + "' state changed to Reserved.");
    }

    @Override
    public String getStateName() {
        return "Available";
    }
}