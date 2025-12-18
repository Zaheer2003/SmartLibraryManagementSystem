import book.Book;
import borrowRecord.BorrowRecord;
import builder.BookBuilder;
import builder.UserBuilder;
import command.*;
import decorator.FeaturedBook;
import decorator.Recommended;
import decorator.SpecialEdition;
import librarian.Librarian;
import reservation.Reservation;
import system.LibraryManagementSystem;
import user.Faculty;
import user.Guest;
import user.Student;
import user.User;
import util.InputValidator;

import java.util.Collection;
import java.util.Scanner;

public class Main {
    private static LibraryManagementSystem system = new LibraryManagementSystem();
    private static Scanner scanner = new Scanner(System.in);
    private static Librarian loggedInLibrarian;
    private static User loggedInUser;
    private static CommandInvoker invoker = new CommandInvoker();

    public static void main(String[] args) {

        setupDemoData();

        boolean running = true;
        while (running) {
            running = runLogin();
        }
        System.out.println("Library Closed, Thank You!");
    }

    private static void setupDemoData() {
        User student = new Student("U001","Zaheer", "mhdzaheer2003@gmail.com", "1234", "0787325149");

        system.registerUser(student);

        Book book = new BookBuilder()
                .setBookId("B001")
                .setTitle("Design of Pattern")
                .setAuthor("Gang of Four")
                .setCategory("Software Engineering")
                .setIsbn("142354-548141312")
                .build();
        system.addBook(book);

        loggedInLibrarian = new Librarian("L001", "Praveen Anthony", "praveen@gmail.com", "Khan", system);

        system.registerLibrarian(loggedInLibrarian);

        System.out.println("Demo data inserted");
    }

    private static boolean runLogin() {
        printBoxedHeader("Library Management System Login");
        System.out.println("1. Login" );
        System.out.println("2. Exit");
        System.out.print("Enter Choice (1-2): ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 2) {
                return false;
            } else if (choice == 1) {
                System.out.print("Enter User ID / Librarian ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                
                String role = system.verifyUser(id, password);

                switch (role) {
                    case "Librarian":
                        Librarian librarian = system.getLibrarian(id);
                        System.out.println("Login Success! Welcome, Librarian " + librarian.getLibaraianName() + ".");
                        runLibrarianView(librarian);
                        break;
                    case "User":
                        loggedInUser = system.getUser(id);
                        System.out.println(" Login Success! Welcome, " +loggedInUser.getMembershipType()+ " " + loggedInUser.getName() + ".");
                        runUserView(loggedInUser);
                        break;
                    case "Failed":
                        System.out.println(" Login Failed: Invalid ID or Password.");
                }
            } else {
                System.out.println(" Invalid Choice.");
            }
        } else {
            System.out.println("Wrong Input.");
            scanner.nextLine();
        }
        return true;
    }

    private static void printBoxedHeader(String title) {
        int length = title.length();

        String horizontalLine = "═".repeat(length + 4);

        System.out.println("\n╔" + horizontalLine + "╗");
        System.out.println("║  " + title + "  ║");
        System.out.println("╚" + horizontalLine + "╝");
    }

    private static void runLibrarianView(Librarian currentLibrarian) {
        boolean adminRunning = true;
        while (adminRunning) {
            printBoxedHeader("Librarian Menu (" + currentLibrarian.getLibaraianName() +")");
            System.out.println("1. Add New Book");
            System.out.println("2. Register New User");
            System.out.println("3. Update User Details");
            System.out.println("4. Remove User");
            System.out.println("5. Update Book Details");
            System.out.println("6. Remove Book");
            System.out.println("7. Notify Overdue Users");
            System.out.println("8. View All Books");
            System.out.println("9. View All Users");
            System.out.println("10. Generate Report");
            System.out.println("11. Undo Last Action");
            System.out.println("12. Logout");
            System.out.print("Enter Your Choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addNewBook();
                        break;
                    case 2:
                        registerNewUser();
                        break;
                    case 3:
                        updateUser();
                        break;
                    case 4:
                        removeUser();
                        break;
                    case 5:
                        updateBook();
                        break;
                    case 6:
                        removeBook();
                        break;
                    case 7:
                        currentLibrarian.notifyUser();
                        break;
                    case 8:
                        system.displayAllBooks();
                        break;
                    case 9:
                        displayAllUsers();
                        break;
                    case 10:
                        generateReport();
                        break;
                    case 11:
                        invoker.undoLastAction();
                        break;
                    case 12:
                        adminRunning = false;
                        System.out.println(" Logged out from Librarian View");
                        break;
                    default:
                        System.out.println(" Invalid librarian choice.");
                }
            } else {
                System.out.println(" Wrong Input");
                scanner.nextLine();
            }
        }
    }

    private static void runUserView(User currentUser) {
        boolean userRunning = true;
        while (userRunning) {
            printBoxedHeader("User Menu (" + currentUser.getName() + " - " + currentUser.getMembershipType() + ")");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Reserve Book");
            System.out.println("4. View My Borrow History");
            System.out.println("5. View All Available Books");
            System.out.println("6. Cancel Reservation");
            System.out.println("7. Undo Last Action");
            System.out.println("8. Logout");
            System.out.print("Enter Your Choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        borrowBook(currentUser);
                        break;
                    case 2:
                        returnBook(currentUser);
                        break;
                    case 3:
                        reserveBook(currentUser);
                        break;
                    case 4:
                        displayBorrowHistory(currentUser);
                        break;
                    case 5:
                        system.displayAllBooks();
                        break;
                    case 6:
                        cancelReservation(currentUser);
                        break;
                    case 7:
                        invoker.undoLastAction();
                        break;
                    case 8:
                        userRunning = false;
                        System.out.println(" Logged out from User view");
                        break;
                    default:
                        System.out.println(" Invalid User Choice.");
                }
            } else {
                System.out.println(" Wrong Input.");
                scanner.nextLine();
            }
        }
    }

    private static void displayBorrowHistory(User currentUser) {
        printBoxedHeader("My Borrow History");

        var history = currentUser.getBorrowedBooksHistory();

        if (history.isEmpty()) {
            System.out.println("No borrow history found. you haven't borrowed any books yet.");
            return;
        }
        System.out.printf("%-10s | %-25s | %-12s | %-12s%n", "Book ID", "Title", "Borrow Date", "Return Date");
        System.out.println("-------------------------------------------------------------------------");

        for (var record : history) {
            String returnDate = (record.getReturnDate() != null)
                    ? record.getReturnDate().toString()
                    : "NOT RETURNED";

            System.out.printf("%-10s | %-25s | %-12s | %-12s%n",
                    record.getBook().getBookId(),
                    record.getBook().getTitle(),
                    record.getBorrowDate(),
                    returnDate);
        }
    }

    private static void registerNewUser() {
        printBoxedHeader("New User Registration");
        System.out.println("User ID: ");
        String id = scanner.nextLine();

        System.out.println("Name: ");
        String name = scanner.nextLine();

        String email;
        do {
            System.out.println("Email: ");
            email = scanner.nextLine();
            if (!InputValidator.isValidEmail(email)) {
                System.out.println(" Invalid  email format. please enter a valid email address.");
            }
        } while (!InputValidator.isValidEmail(email));

        System.out.println("Password: ");
        String password = scanner.nextLine();

        String phone;
        do  {
            System.out.println("Phone: ");
            phone = scanner.nextLine();
            if (!InputValidator.isValidContactNumber(phone)) {
                System.out.println("Invalid phone number format, please use a 10 digit number");
            }
        } while (!InputValidator.isValidContactNumber(phone));


        User newUser = null;

        UserBuilder builder = new UserBuilder()
                .setUserId(id)
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setPhone(phone);
        do {
            System.out.println("---Select Membership Type---");
            System.out.println("1. Student\n2. Faculty\n3. Guest");
            System.out.println("Enter Type (1-3): ");

            if (scanner.hasNextInt()) {
                int userTypeChoice = scanner.nextInt();
                scanner.nextLine();

                newUser = builder.setUserType(userTypeChoice).build();

                if (newUser == null) {
                    System.out.println("Invalid choice, please select 1 - 3");
                }
            } else {
                System.out.println("Invalid input, please enter a number");
                scanner.nextLine();
            }
        } while (newUser == null);

        Command addCommand = new AddUserCommand(loggedInLibrarian, newUser);

        invoker.execute(addCommand);
    }

    private static void updateUser() {
        printBoxedHeader("Update User Details (Admin Task)");
        System.out.println("Enter User ID: ");
        String userId = scanner.nextLine();

        User user = system.getUser(userId);
        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }

        String newEmail;
        do {
            System.out.print("Enter new email or leave blank(skip for): ");
            newEmail = scanner.nextLine();

            if (newEmail.trim().isEmpty()) break;

            if (!InputValidator.isValidEmail(newEmail)) {
                System.out.println(" Invalid email format, please enter a valid email or leave blank");
            }
        } while (!InputValidator.isValidEmail(newEmail));

        String newContact;
        do {
            System.out.print("Enter new phone number or leave blank(for skip): ");
            newContact = scanner.nextLine();
            if (newContact.trim().isEmpty()) break;
            if (!InputValidator.isValidContactNumber(newContact)) {
                System.out.println(" Invalid phone number format, please use a 10 digit number or leave blank.");
            }
        } while (!InputValidator.isValidContactNumber(newContact));


        Command updateCmd = new UpdateUserCommand(loggedInLibrarian, user, newEmail.trim(), newContact.trim());

        invoker.execute(updateCmd);
    }

    private static void removeUser() {
        printBoxedHeader("Remove User");
        System.out.print("Enter User ID to remove: ");
        String userId = scanner.nextLine();

        User user = system.getUser(userId);

        System.out.println("Confirm removal of User ID " + userId + "? (Yes/No): ");
        String confirm = scanner.nextLine();


        if (confirm.equalsIgnoreCase("Yes")) {
            Command removeCmd = new RemoveUserCommand(loggedInLibrarian, user);

            invoker.execute(removeCmd);

        } else {
            System.out.println("Removal Cancelled");
        }
    }

    private static void displayAllUsers() {
        printBoxedHeader("All Registered Users");

        Collection<User> allUsers = system.getAllUsers();

        if (allUsers.isEmpty()) {
            System.out.println("No users registered in the system.");
            return;
        }

        // அட்டவணை தலைப்பு (Table Header)
        System.out.printf("%-10s | %-20s | %-15s | %-15s%n", "User ID", "Name", "User Type", "Phone");
        System.out.println("---------------------------------------------------------------------------");

        for (User user : allUsers) {
            // user.getClass().getSimpleName() என்பது Student, Faculty போன்ற கிளாஸ் பெயரைக் கொடுக்கும்
            String userType = user.getClass().getSimpleName();

            System.out.printf("%-10s | %-20s | %-15s | %-15s%n",
                    user.getUserId(),
                    user.getName(),
                    userType,
                    user.getContactNumber());
        }
        System.out.println("---------------------------------------------------------------------------");
    }

    private static void addNewBook() {
        printBoxedHeader("Add New Book");
        System.out.println("Book Id: ");
        String id = scanner.nextLine();
        System.out.println("Title: ");
        String title = scanner.nextLine();
        System.out.println("Author: ");
        String author = scanner.nextLine();
        System.out.println("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.println("Category: ");
        String category = scanner.nextLine();

        Book newBook = new BookBuilder()
                .setBookId(id)
                .setTitle(title)
                .setAuthor(author)
                .setIsbn(isbn)
                .setCategory(category)
                .build();

    boolean addingDecorators = true;
    while (addingDecorators) {
        System.out.println("---Select Book Type---");
        System.out.println("1. Special Edition");
        System.out.println("2. Featured Book");
        System.out.println("3. Recommended");
        System.out.println("4. No more decorations");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                newBook = new SpecialEdition(newBook);
                System.out.println("Applied: Special Edition");
                break;
            case 2:
                newBook = new FeaturedBook(newBook);
                System.out.println("Applied: Featured Book");
                break;
            case 3:
                newBook = new Recommended(newBook);
                System.out.println("Applied: Recommended");
                break;
            case 4:
                addingDecorators = false;
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
        Command addCommand = new AddBookCommand(loggedInLibrarian, newBook);

        invoker.execute(addCommand);

        System.out.println("\nSuccessfully Added: " + newBook.getTitle());
    }

    private static void updateBook() {
        printBoxedHeader("Update Book Details");

        System.out.print("Enter Book ID to update: ");
        String id = scanner.nextLine();

        Book book = system.getBook(id);

        if (book == null) {
            System.out.println("Error: Book not found!");
            return;
        }

        System.out.println("Current Title: " + book.getTitle());
        System.out.print("Enter New Title (leave blank to keep current): ");
        String title = scanner.nextLine();

        System.out.println("Current Author: " + book.getAuthor());
        System.out.print("Enter New Author (leave blank to keep current): ");
        String author = scanner.nextLine();

        System.out.println("Current Category: " + book.getCategory());
        System.out.print("Enter New Category (leave blank to keep current): ");
        String category = scanner.nextLine();

        System.out.println("Current ISBN: " + book.getIsbn());
        System.out.print("Enter New ISBN (leave blank to keep current): ");
        String isbn = scanner.nextLine();

        Command updateCmd = new UpdateBookCommand(loggedInLibrarian, book, title, author, category, isbn);

        invoker.execute(updateCmd);
    }

    private static void removeBook() {
        printBoxedHeader("Remove Book");

        System.out.print("Enter Book ID to remove: ");
        String bookId = scanner.nextLine();
        // check book availability
        Book book = system.getBook(bookId);
        if (book == null ) {
            System.out.println("Error: Book with ID " + bookId + "not found.");
            return;
        }
        //confirmation
        System.out.println("Are you sure you want to remove '"+ book.getTitle() + "'? (Yes/No): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Yes")) {

            Command removeCmd = new RemoveBookCommand(loggedInLibrarian, book);

            invoker.execute(removeCmd);

        } else {
            System.out.println("Removed cancelled.");
        }
    }

    private static void borrowBook(User currentUser) {
        printBoxedHeader("Borrow Book");

        System.out.println("Enter BookId: ");
        String bookId = scanner.nextLine();

        Command borrowCmd = new BorrowCommand(system, currentUser.getUserId(), bookId);

        invoker.execute(borrowCmd);
    }

    private static void returnBook(User currentUser) {
        printBoxedHeader("Return Book");

        System.out.println("Enter BookID: ");
        String bookId = scanner.nextLine();

        Command returnCmd = new ReturnBookCommand(system, currentUser.getUserId(), bookId);

        invoker.execute(returnCmd);
    }

    private static void reserveBook(User currentUser) {
        printBoxedHeader("Reserve Book");
        System.out.println("Enter Book ID to reserve: ");
        String bookId = scanner.nextLine();

        system.reserveBook(currentUser.getUserId(), bookId);
    }

    private static void cancelReservation(User currentUser) {
        printBoxedHeader("Cancel Reservation");
        System.out.print("Enter Book ID to cancel reservation: ");
        String bookId = scanner.nextLine();
        
        Command cancelCmd = new CancelReservationCommand(system, currentUser.getUserId(), bookId);

        invoker.execute(cancelCmd);
    }

    private static void generateReport() {
        printBoxedHeader("System Report Generation");
        system.generateSystemReport();
    }
}