package decorator;

import book.Book;
import borrowRecord.BorrowRecord;

public abstract class BookDecorator extends Book {

    protected Book book;

    public BookDecorator(Book book) {
        super(book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getIsbn());
        this.book = book;
    }

    @Override
    public void borrow() {
        book.borrow();
    }

    @Override
    public void returnBook() {
        book.returnBook();
    }

    @Override
    public void reserve() {
        book.reserve();
    }

    @Override
    public void addBorrowRecord(BorrowRecord record) {
        book.addBorrowRecord(record);
    }

    @Override
    public String getDisplayTitle() {
        return book.getDisplayTitle();
    }

    public String getState() {
        return book.getState();
    }
}
