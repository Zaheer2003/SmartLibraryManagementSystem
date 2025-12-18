package system;

import book.Book;
import borrowRecord.BorrowRecord;
import librarian.Librarian;
import observer.NotificationService;
import reservation.Reservation;
import reservation.ReservationStatus;
import state.AvailableState;
import user.User;

import java.time.LocalDate;
import java.util.*;

public class LibraryManagementSystem {
    private static void printBoxedHeader(String title) {
        int length = title.length();

        String horizontalLine = "═".repeat(length + 4);

        System.out.println("\n╔" + horizontalLine + "╗");
        System.out.println("║  " + title + "  ║");
        System.out.println("╚" + horizontalLine + "╝");
    }

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Librarian> librarians = new HashMap<>();
    private List<Reservation> reservations = new ArrayList<>();
    private NotificationService notificationService = new NotificationService();


    public void registerLibrarian(Librarian librarian) {
        librarians.put(librarian.getLibrarianId(), librarian);
        System.out.println(" Librarian " + librarian.getLibaraianName() + " Sucessfully Registered.");
    }

    public void registerUser(User user) {
        users.put(user.getUserId(), user);
        notificationService.attach(user);
        System.out.println(" User " + user.getName() + " Successfully Registered.");
    }

    public void updateUser(String userId, String newEmail, String newContact) {
        User user = users.get(userId);

        if (user != null) {
            System.out.println("Updating details for User: " + user.getName() + " (ID: "+userId+")");

            if (newEmail != null && !newEmail.trim().isEmpty()){
                System.out.println(" - Email changed from: " + user.getEmail() + " to: " + newEmail);
                user.setEmail(newEmail);
            }
            if (newContact != null && !newContact.trim().isEmpty()){
                System.out.println(" - Contact number changed from: " + user.getContactNumber() + " to: " + newContact);
                user.setContactNumber(newContact);
            }
            System.out.println(" User ID " + userId + " sucessfully updated.");
        } else {
            System.out.println(" Error: User ID " + userId + " not found for update.");
        }
    }

    public boolean removeUser(String userId){
        User userToRemove = users.get(userId);

        //verify user availability
        if (userToRemove == null) {
            System.out.println("Error: User ID: " + userId + "not found for removal.");
            return false;
        }

        //check user have any books available (not returned books)
        boolean hasActiveBorrows = userToRemove.getBorrowedBooksHistory().stream()
                .anyMatch(record -> record.getReturnDate() == null);

        if (hasActiveBorrows) {
            System.out.println("Warning: Cannot remove User ID: " + userId + "('" + userToRemove.getName() + "').");
            System.out.println("Reason: The user currently has books that are not yet returned.");
            return false;
        }

        users.remove(userId);
        return true;

    }

    public String verifyUser(String userId, String password) {
        if (librarians.containsKey(userId)) {
            Librarian librarian = librarians.get(userId);
            if (librarian.getLibrarianPassword().equals(password)) {
                return "Librarian";
            }
        }

        if (users.containsKey(userId)) {
            User user = users.get(userId);
            if (user.getPassword().equals(password)) {
                return "User";
            }
        }
        return "Failed";
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public Librarian getLibrarian(String librarianId) {
        return librarians.get(librarianId);
    }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
        System.out.println("Book: '" + book.getTitle() + "' Successfully Registered.");
    }

    public Book getBook(String bookId) {
        return books.get(bookId);
    }

    public void displayAllBooks() {
        printBoxedHeader("All Registered Books"); // உங்கள் ஸ்டைலில் ஒரு ஹெடர்

        if (books.isEmpty()) {
            System.out.println("No Books available in the library.");
            return;
        }

        System.out.printf("%-10s | %-40s | %-20s | %-15s\n", "ID", "Title", "Author", "Status");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Book book : books.values()) {
            System.out.printf("%-10s | %-40s | %-20s | %-15s\n",
                    book.getBookId(),
                    book.getDisplayTitle(),
                    book.getAuthor(),
                    book.getStateName());
        }
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public void borrowBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null) {
            System.out.println("Error: User ID " + userId + " not found.");
            return;
        }

        if (book == null) {
            System.out.println("Error: Book ID " + bookId + " not found.");
            return;
        }
        user.borrowBook(book, this);
    }

    public void returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user != null || book != null) {
            user.returnBook(book, this);
            book.returnBook();
            notificationService.notifyObservers("Good news! The book '" + book.getTitle() + "' has been returned and is now available.");
        } else {
            System.out.println("Error: User or Book not found");
        }

    }

    public void reserveBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null ) {
            System.out.println("Error: User or Book not found.");
            return;
        }

        if (book.getStateName().equalsIgnoreCase("Reserved")) {
            System.out.println(" Sorry, the book '" + book.getTitle() + "' is already reserved by another user");
            System.out.println(" You can only reserve is once the current reservation is cleared.");
            return;
        }

        String reservationId = "RES" + (reservations.size() + 1);
        Reservation reservation = new Reservation(reservationId, user, book);
        reservations.add(reservation);

        book.reserve();

        System.out.println(book.getTitle() +"' reserved successfully for " + user.getName());
    }

    public boolean cancelReservation(String userId, String bookId) {
        Book book = books.get(bookId);

        if (book == null) {
            System.out.println("Error: Book ID " + bookId + " not found.");
            return false;
        }

        Reservation targetReservation = null;
        for (Reservation res : reservations) {
            if (res.getBook().getBookId().equals(bookId) &&
            res.getUser().getUserId().equals(userId) &&
            res.getStatus() == ReservationStatus.ACTIVE) {
                targetReservation = res;
                break;
            }
        }

        if (targetReservation != null) {
            targetReservation.cancelReservation();
            book.setState(new AvailableState());
            System.out.println("Success: Reservation for '" + book.getTitle() + "' has been cancelled.");
            return true;
        } else {
            System.out.println("Error: No active reservation found for this user and book.");
            return false;
        }
    }

    public Reservation findActiveReservationForBook(Book book) {
        for (Reservation res : reservations ) {
            if (res.getBook().equals(book) && res.getStatus() == ReservationStatus.ACTIVE || res.getStatus() == ReservationStatus.CONFIRMED) {
                return res;
            }
        }
        return null;
    }

    public void updateBookDetails(String bookId, String newTitle, String newAuthor, String newCategory,  String newIsbn) {
        Book book = books.get(bookId);
        if (book != null) {
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                book.setTitle(newTitle);
            }
            if (newAuthor != null && !newAuthor.trim().isEmpty()) {
                book.setAuthor(newAuthor);
            }
            if (newCategory != null && !newCategory.trim().isEmpty()) {
                book.setCategory(newCategory);
            }
            if (newIsbn != null && !newIsbn.trim().isEmpty()) {
                book.setIsbn(newIsbn);
            }
            System.out.println("Book ID: " + bookId + " details updated successfully.");
        } else {
            System.out.println("Error: Book ID: " + bookId + " not found.");
        }
    }

    public boolean removeBook(String bookId) {
        Book bookToRemove = books.get(bookId);

        if (bookToRemove == null) {
            System.out.println("Error: Book ID: " + bookId + " not found for removal");
            return false;
        }
        //logic for check book state
        String currentState = bookToRemove.getStateName();

        // book currently borrowed
        if (currentState.equalsIgnoreCase("Borrowed")) {
            System.out.println("Warning: cannot remove Book ID " + bookId + "('" + bookToRemove.getTitle() + "') because it is currently [Borrowed].");
            System.out.println(" First, the user must return the book.");
            return false;
            // book currently reserved
        } else if (currentState.equalsIgnoreCase("Reserved")) {

            System.out.println(" Warning: cannot remove Book ID " + bookId + "('"+ bookToRemove.getTitle() + "') because it is currently [Reserved].");
            System.out.println(" First, the reservation must be cancelled.");
            return false;
        }

        books.remove(bookId);
        return true;
    }


    public boolean checkUserOverdueStatus(User user, LocalDate today) {
        List<BorrowRecord> history = user.getBorrowedBooksHistory();

        for (BorrowRecord record : history) {
            if (record.getReturnDate() == null && record.getDueDate().isBefore(today)) {
                return true;
            }
        }
        return false;
    }

    public void notifyOverdueUsers() {
        LocalDate today = LocalDate.now();
        boolean foundOverdue = false;

        for (User user : users.values()) {
            if (checkUserOverdueStatus(user, today)) {
                double totalFine = 0;
                for (BorrowRecord record : user.getBorrowedBooksHistory()) {
                    if (record.getReturnDate() == null && record.getDueDate().isBefore(today)) {
                        totalFine += user.calculateFine(record.getLateDays());
                    }
                }

                String alertMessage = "Overdue Alert! Total Fine: LKR " + totalFine +
                        ". Please return the books to avoid further charges.";

                user.update(alertMessage);
                foundOverdue = true;
            }
        }
    }

    public void generateSystemReport() {
        System.out.println("\n========= LIBRARY SYSTEM REPORT ========= ");

        long totalBooks = books.size();
        long borrowedBooks = books.values().stream()
                .filter(b -> b.getStateName().equalsIgnoreCase("Borrowed")).count();
        long availableBooks = totalBooks - borrowedBooks;

        System.out.println(" BOOK STATISTICS:");
        System.out.println("   - Total Books: " + totalBooks);
        System.out.println("   - Available: " + availableBooks);
        System.out.println("   - Currently Borrowed: " + borrowedBooks);

        // 2. பயனர் புள்ளிவிவரங்கள்
        System.out.println("\n USER STATISTICS:");
        System.out.println("   - Total Registered Users: " + users.size());

        // 3. விரிவான கடன் விவரங்கள் (Active Borrows)
        System.out.println("\n ACTIVE BORROW LIST:");
        boolean anyActive = false;
        for (User user : users.values()) {
            for (var record : user.getBorrowedBooksHistory()) {
                if (record.getReturnDate() == null) {
                    System.out.println("   - User: " + user.getName() + " | Book: " + record.getBook().getTitle());
                    anyActive = true;
                }
            }
        }
        if (!anyActive) System.out.println("   - No active borrows at the moment.");

        System.out.println("============================================\n");
    }
}
