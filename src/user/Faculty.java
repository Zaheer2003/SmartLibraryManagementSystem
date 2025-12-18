package user;

import fine.FacultyFineStrategy;

public class Faculty extends User {

    private static final int MAX_BORROW_LIMIT = 5;
    private static final int DUE_DAYS = 7;

    public Faculty(String userId, String name, String email, String password, String contactNumber) {
        super(userId, name, email, password, contactNumber, new FacultyFineStrategy());
    }

    @Override
    public int getBorrowLimit() { return MAX_BORROW_LIMIT; }

    @Override
    public int getDueDays() { return DUE_DAYS;}

    @Override
    public String getMembershipType() {
        return "Faculty";
    }

    @Override
    public void update(String message) {
        System.out.println("Notfication for " + name + " (" + getMembershipType() + "): " + message );
    }
}
