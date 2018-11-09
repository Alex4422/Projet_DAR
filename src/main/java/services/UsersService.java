package services;

import entities.User;
import entities.UserSession;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import services.errors.UserExistsException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsersService extends ServiceBase {
    public static void addUser(String username, String passWord) throws UserExistsException {
        User user = new User(username, hashPassWord(passWord));
        try {
            add(user);
        } catch (ConstraintViolationException e) {
            throw new UserExistsException(e);
        }
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
