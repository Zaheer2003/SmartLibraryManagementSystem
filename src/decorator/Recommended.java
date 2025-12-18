package decorator;

import book.Book;

public class Recommended extends BookDecorator {

    public Recommended(Book book) {
        super(book);
    }

    public String getDisplayTitle() {
        return "[RECOMMENDED] " + book.getTitle();
    }

    public String getFeatureType() {
        return "Recommended";
    }
}
