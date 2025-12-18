package command;

import librarian.Librarian;
import user.User;

public class UpdateUserCommand implements Command {
    private Librarian librarian;
    private String userId;
    private String newEmail, newPhone;
    private String oldEmail, oldPhone;

    public UpdateUserCommand(Librarian librarian, User user, String newEmail, String newPhone) {
        this.librarian = librarian;
        this.userId = user.getUserId();
        this.newEmail = newEmail;
        this.newPhone = newPhone;

        this.oldEmail = user.getEmail();
        this.oldPhone = user.getContactNumber();
    }

    @Override
    public void execute() {
        librarian.updateUser(userId, newEmail, newPhone);
    }

    @Override
    public void undo() {
        librarian.updateUser(userId, oldEmail, oldPhone);
        System.out.println("Undo: User ID: " + userId + " restored to previous details.");
    }
}
