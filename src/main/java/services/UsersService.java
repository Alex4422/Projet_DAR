package services;

import entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsersService extends ServiceBase {
    public static void addUser(String username, String passWord) {
        add(new User(username, hashPassWord(passWord)));
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
