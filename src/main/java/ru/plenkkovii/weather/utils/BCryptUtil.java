package ru.plenkkovii.weather.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
