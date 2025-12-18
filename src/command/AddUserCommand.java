package command;

import librarian.Librarian;
import user.User;

public class AddUserCommand implements Command {
    private Librarian librarian;
    private User user;

    public AddUserCommand(Librarian librarian, User user) {
        this.librarian = librarian;
        this.user = user;
    }

    @Override
    public void execute() {
        librarian.registerUser(user);
    }

    @Override
    public void undo() {
        librarian.removeUser(user.getUserId());
        System.out.println("Undo: Registration cancelled. User '" + user.getName() + "' removed.");
    }
}
