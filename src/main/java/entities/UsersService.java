package entities;

import launch.Main;
import org.hibernate.Session;

public class UsersService extends ServiceBase {
    public static void addUser(String username, String passWord) {
        add(new User(username, passWord));
    }
}
