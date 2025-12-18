package user;

import book.Book;
import borrowRecord.BorrowRecord;
import fine.FineStrategy;
import observer.Observer;
import reservation.Reservation;
import system.LibraryManagementSystem;
import util.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class User implements Observer {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected String contactNumber;
    protected List<BorrowRecord> borrowedBooksHistory;
    protected FineStrategy fineStrategy;


    public User(String userId, String name, String email, String password, String contactNumber, FineStrategy fineStrategy) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.fineStrategy = fineStrategy;
        this.borrowedBooksHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() { return email;}
    public String getPassword() {return password;}
    public String getContactNumber() {return contactNumber;}

    public void setEmail(String email) {
        if (InputValidator.isValidEmail(email)){
        this.email = email;
    } else {
        System.out.println("Error: Invalid email format for '" + email + "'. Email not updated.");
        }
    }

    public void setContactNumber(String contactNumber) {
        if (InputValidator.isValidContactNumber(contactNumber)) {
        this.contactNumber = contactNumber;
    } else {
            System.out.println("Error: Invalid phone number format for '" + contactNumber + "'. Contact not updated." );
        } }

    public abstract int getBorrowLimit();

    public abstract int getDueDays();

    public abstract String getMembershipType();

    public double calculateFine(int daysLate) {
        return fineStrategy.calculateFine(daysLate);
    }

    public List<BorrowRecord> getBorrowedBooksHistory() {
        return borrowedBooksHistory;
    }

    public void borrowBook(Book book , LibraryManagementSystem lms) {
        // check borrow limit
        if (borrowedBooksHistory.size() >= getBorrowLimit()) {
            System.out.println(name + " has reached the borrow limit of " + getBorrowLimit() + " books.");
            return;
        }
        // check book state
       String state = book.getStateName();

        if (state.equalsIgnoreCase("Available")) {
            processBorrow(book);
        } else if (state.equalsIgnoreCase("Reserved")) {
            Reservation res = lms.findActiveReservationForBook(book);
            if (res != null && res.getUser().getUserId().trim().equals(this.userId)) {
                processBorrow(book);
                res.confirmReservation();
            } else {
                System.out.println("Sorry, the book '" + book.getTitle() + "' is reserved by another user.");
            }
        } else {
            System.out.println("Sorry, the book '" + book.getTitle() + "' is currently ["+ state +"].");

        }
    }

    private void processBorrow(Book book) {
        book.borrow();
        BorrowRecord record = new BorrowRecord(this, book);
        borrowedBooksHistory.add(record);
        book.addBorrowRecord(record);

        System.out.println( getName() + " successfully borrowed '" + book.getTitle() + "'");
        System.out.println("Borrowed Date: " + record.getBorrowDate());
        System.out.println("Due Date: " + record.getDueDate());
    }

    public void returnBook(Book book, LibraryManagementSystem lms) {
        BorrowRecord targetRecord = null;

        for (BorrowRecord record : borrowedBooksHistory) {
            if (record.getBook().equals(book) && record.getReturnDate() == null) {
                targetRecord = record;
                break;
            }
        }

        if (targetRecord != null) {
            targetRecord.markReturned(LocalDate.now());

            Reservation activeRes = lms.findActiveReservationForBook(book);

            if (activeRes != null) {
                activeRes.confirmReservation();
                System.out.println("Notification: Book is reserved. Notifying " + activeRes.getUser().getName());
            } else {
                book.returnBook();
            }
            //calculate fine
            int lateDays = targetRecord.getLateDays();
            if (lateDays > 0 ) {
                double fineAmount = calculateFine(lateDays);
                System.out.println("Late Return Detected!");
                System.out.println("Days Overdue: " + lateDays + " days");
                System.out.println("Total Fine : LKR " + fineAmount);
            }
            System.out.println(getName() + " successfully returned '" + book.getTitle() + "'.");
        } else {
            System.out.println("Error: You do not have active borrow for this book.");
        }
    }


    public Optional<BorrowRecord> findActiveBorrowRecord(Book book) {
        return borrowedBooksHistory.stream()
                .filter(record -> record.getBook().equals(book) && record.getReturnDate() == null)
                .findFirst();
    }

    @Override
    public void update(String message) {
        System.out.println("\n[NOTFICATION fo " + name + "]: " + message);
    }



}
