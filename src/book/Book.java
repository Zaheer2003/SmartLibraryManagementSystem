package book;

import borrowRecord.BorrowRecord;
import state.AvailableState;
import state.BookState;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private BookState state;
    private List<BorrowRecord> borrowHistory;

    public Book(String bookId, String title, String author, String category, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.state = new AvailableState();
        this.borrowHistory = new ArrayList<>();
    }

    public String getBookId () {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getStateName() {
        return state.getStateName();
    }

    public void setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title;
        }
    }

    public void setAuthor(String author) {
        if (author != null && !author.trim().isEmpty()) {
            this.author = author;
        }
    }

    public void setCategory(String category) {
        if (category != null && !category.trim().isEmpty()) {
            this.category = category;
        }
    }

    public void setIsbn(String isbn) {
        if (isbn != null && !isbn.trim().isEmpty()) {
            this.isbn = isbn;
        }
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public String getDisplayTitle() {
        return title;
    }


    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }


    public void borrow() {
        state.borrow(this);
    }

    public void returnBook() {
        state.returnBook(this);
    }

    public void reserve() {
        state.reserve(this);
    }




    public String getState() {
        return state.getStateName();
    }

    public void addBorrowRecord(BorrowRecord record) {
        borrowHistory.add(record);
    }



}

