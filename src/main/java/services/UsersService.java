package services;

import entities.User;
import entities.UserSession;
import org.hibernate.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsersService extends ServiceBase {
    public static String addUser(String username, String passWord) {
        User user = new User(username, hashPassWord(passWord));
        UserSession userSession = new UserSession(user);
        add(user);
        add(userSession);
        return userSession.getUuid();
    }

    public static byte[] hashPassWord(String passWord) {
        MessageDigest md = getDigest();
        md.update(passWord.getBytes());
        return md.digest();
    }

    private static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
