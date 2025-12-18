package command;

import book.Book;
import librarian.Librarian;

public class AddBookCommand implements Command {
    private Librarian librarian;
    private Book book;

    public AddBookCommand(Librarian librarian, Book book) {
        this.librarian = librarian;
        this.book = book;
    }

    @Override
    public void execute() {
        librarian.addBook(book);
    }

    @Override
    public void undo() {
        librarian.removeBook(book.getBookId());
        System.out.println("Undo Success: Book '" + book.getTitle() + "' has been removed.");
    }
}
