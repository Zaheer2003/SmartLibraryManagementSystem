package builder;

import user.Faculty;
import user.Guest;
import user.Student;
import user.User;

public class UserBuilder {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String userType;

    public UserBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder setUserType(String type) {
        this.userType = type;
        return this;
    }

    public UserBuilder setUserType(int typeChoice) {
        switch (typeChoice) {
            case 1 -> this.userType = "Student";
            case 2 -> this.userType = "Faculty";
            case 3 -> this.userType = "Guest";
        }
        return this;
    }

    public User build() {
        if (userType == null) return null;
        return switch (userType.toLowerCase()) {
            case "student" -> new Student(userId, name, email, password, phone);
            case "faculty" -> new Faculty(userId, name, email, password, phone);
            case "guest" -> new Guest(userId, name, email, password, phone);
            default -> null;
        };
    }
}


