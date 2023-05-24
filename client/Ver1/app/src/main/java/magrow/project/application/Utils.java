package magrow.project.application;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    // isValidEmailAddress: Check the email address is OK
    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
    public static boolean isEmpty(String inputs)
    {
        return inputs.length() == 0;
    }
    public static boolean isPeriod(String inputs)
    {
        return inputs.equals(".");
    }
    public static boolean isEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password){

        return password.length() >= 8;
    }



}
