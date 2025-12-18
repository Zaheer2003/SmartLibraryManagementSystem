package decorator;

import book.Book;

public class SpecialEdition extends BookDecorator {

    public SpecialEdition(Book book) {
        super(book);
    }

    public String getDisplayTitle() {
        return "[SPECIAL EDITION] " + book.getTitle();
    }

    public String getFeatureType() {
        return "Special Edition";
    }
}
