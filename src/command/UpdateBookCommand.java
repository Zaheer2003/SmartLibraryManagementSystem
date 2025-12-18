package command;

import book.Book;
import librarian.Librarian;

public class UpdateBookCommand implements Command {
    private Librarian librarian;
    private String bookId;

    private String newTitle, newAuthor, newCategory, newIsbn;

    private String oldTitle, oldAuthor, oldCategory, oldIsbn;

    public UpdateBookCommand(Librarian librarian, Book book, String title, String author, String category, String isbn) {
        this.librarian = librarian;
        this.bookId = book.getBookId();

        this.newTitle = title.isEmpty() ? book.getTitle() : title;
        this.newAuthor = author.isEmpty() ? book.getAuthor() : author;
        this.newCategory = category.isEmpty() ? book.getCategory() : category;
        this.newIsbn = isbn.isEmpty() ? book.getIsbn() : isbn;

        this.oldTitle = book.getTitle();
        this.oldAuthor = book.getAuthor();
        this.oldCategory = book.getCategory();
        this.oldIsbn = book.getIsbn();
    }

    @Override
    public void execute() {
        librarian.updateBook(bookId, newTitle, newAuthor, newCategory, newIsbn);
    }

    @Override
    public void undo() {
        librarian.updateBook(bookId, oldTitle, oldAuthor, oldCategory, oldIsbn);
        System.out.println("Undo: Book ID: " + bookId + " reverted to original details");
    }
}
