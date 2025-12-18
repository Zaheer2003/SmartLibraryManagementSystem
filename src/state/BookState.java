package state;

import book.Book;

public interface BookState {
    void borrow(Book book);
    void returnBook(Book book);
    void reserve(Book book);
    String getStateName();
}
