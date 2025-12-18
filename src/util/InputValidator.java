package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    //email regex
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    //phone regex
    private static final String PHONE_REGEX =
            "^(\\+?\\d{1,3})?[\\s-]?\\d{7,10}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    private static final Pattern phonePattern = Pattern.compile(PHONE_REGEX);


    // validate email
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    // validate contactNumber
    public static boolean isValidContactNumber(String contactNumber) {
        if (contactNumber == null) return false;
        Matcher matcher = phonePattern.matcher(contactNumber);
        return matcher.matches();
    }
}
