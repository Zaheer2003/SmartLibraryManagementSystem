package decorator;

import book.Book;

public class FeaturedBook extends BookDecorator {

    public FeaturedBook(Book book) {
        super(book);
    }

    @Override
    public String getDisplayTitle() {
        return "[FEATURED] " + book.getTitle();
    }

    public String getFeatureType() {
        return "Featured";
    }


}
