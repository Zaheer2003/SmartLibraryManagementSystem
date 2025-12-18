package command;

import book.Book;
import librarian.Librarian;

public class RemoveBookCommand implements Command{
    private Librarian librarian;
    public Book book;

    public RemoveBookCommand(Librarian librarian, Book book) {
        this.librarian = librarian;
        this.book = book;
    }

    @Override
    public void execute() {
        librarian.removeBook(book.getBookId());
        System.out.println("Book '" + book.getTitle() + "' has been removed.");
    }

    @Override
    public void undo() {
        librarian.addBook(book);
        System.out.println("Undo: Book '" + book.getTitle() + "' has been restored to the library.");
    }
}
