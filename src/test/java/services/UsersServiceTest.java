package services;

import entities.User;
import org.junit.Test;
import services.errors.UserExistsException;

import static junit.framework.TestCase.assertEquals;

public class UsersServiceTest extends TestWithDb {
    @Test
    public void registerNewUser() throws UserExistsException {
        String username = "username";
        String password = "password";
        UsersService s = new UsersService(getSessionFactory());
        s.addUser(username, password);
        User u = s.getUser(username, UsersService.hashPassWord(password));
        assertEquals(u.getUsername(), username);
    }

    @Test(expected=UserExistsException.class)
    public void failRegisteringExistingUser() throws UserExistsException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("testUser", "password");
        s.addUser("testUser", "password");
    }
}
