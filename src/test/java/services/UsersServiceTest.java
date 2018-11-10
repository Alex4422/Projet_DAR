package services;

import entities.User;
import entities.UserSession;
import org.junit.Test;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;

import java.util.Date;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
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

    @Test
    public void loginAfterREgitration() throws UserExistsException, NonExistingUserException {
        String username = "username";
        String password = "password";
        UsersService s = new UsersService(getSessionFactory());
        s.addUser(username, password);
        String hashedPawword = DatatypeConverter.printHexBinary(UsersService.hashPassWord(password));
        String userToken = s.login(username, hashedPawword);
        assertFalse(userToken.isEmpty());
    }

    @Test(expected = NonExistingUserException.class)
    public void loginWithInvalidPassword() throws UserExistsException, NonExistingUserException {
        String username = "username";
        String password = "password";
        UsersService s = new UsersService(getSessionFactory());
        s.addUser(username, password);
        String invalidPassword = DatatypeConverter.printHexBinary(UsersService.hashPassWord("invalidPassword"));
        s.login(username, invalidPassword);
    }

    @Test(expected = NonExistingUserException.class)
    public void loginNonExistingUser() throws NonExistingUserException {
        UsersService s = new UsersService(getSessionFactory());
        String hashedPawword = DatatypeConverter.printHexBinary(UsersService.hashPassWord("password"));
        s.login("username", hashedPawword);
    }

    @Test
    public void retrieveUserSession() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("username", "password");
        String token = s.login("username", DatatypeConverter.printHexBinary(UsersService.hashPassWord("password")));
        UserSession session = s.retrieveSession(token);
        assertEquals("username", session.getUser().getUsername());
    }

    @Test(expected = UnAuthenticatedUserException.class)
    public void retriveUnAuthenticatedUserSession() throws UserExistsException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("username", "password");
        s.retrieveSession("randomSessionToken");
    }

    @Test
    public void refreshSession() throws UserExistsException, NonExistingUserException, InterruptedException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("username", "password");
        String token = s.login("username", DatatypeConverter.printHexBinary(UsersService.hashPassWord("password")));
        Thread.sleep(2000);

        Date age = s.retrieveSession(token).getDate();
        s.refreshSession(token);
        Date newAge = s.retrieveSession(token).getDate();
        long ellapsed = (newAge.getTime() - age.getTime()) / 1000;

        assertTrue(ellapsed >= 2);
    }
}
