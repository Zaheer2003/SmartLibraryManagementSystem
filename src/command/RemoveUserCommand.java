package command;

import librarian.Librarian;
import user.User;

public class RemoveUserCommand implements Command {
    private Librarian librarian;
    private User user;

    public RemoveUserCommand(Librarian librarian, User user) {
        this.librarian = librarian;
        this.user = user;
    }

    @Override
    public void execute() {
        librarian.removeUser(user.getUserId());
    }

    @Override
    public void undo() {
        librarian.registerUser(user);
        System.out.println("Undo: User '" + user.getName() + "' has been restored.");
    }
}
