package builder;

import book.Book;

public class BookBuilder {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;

    public BookBuilder setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public Book build() {
        return new Book(bookId, title, author, category, isbn);
    }


}
