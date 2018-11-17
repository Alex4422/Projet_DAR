package services;

import entities.User;
import org.junit.Test;
import services.errors.NonExistingUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;

public class UsersServiceTest extends TestWithDb {
    @Test
    public void registerNewUser() throws UserExistsException, NonExistingUserException {
        String username = "username";
        String password = "password";
        UsersService s = new UsersService(getSessionFactory());
        s.addUser(username, DatatypeConverter.printHexBinary(UsersService.hashPassWord(password)));
        User u = s.getUser(username, UsersService.hashPassWord(password));
        assertEquals(u.getUsername(), username);
    }

    @Test(expected=UserExistsException.class)
    public void failRegisteringExistingUser() throws UserExistsException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("testUser", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
        s.addUser("testUser", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
    }

    @Test
    public void loginAfterREgitration() throws UserExistsException, NonExistingUserException {
        String username = "username";
        String password = "password";
        UsersService s = new UsersService(getSessionFactory());
        s.addUser(username, DatatypeConverter.printHexBinary(UsersService.hashPassWord("password")));
        String hashedPawword = DatatypeConverter.printHexBinary(UsersService.hashPassWord(password));
        String userToken = s.login(username, hashedPawword)
                .getUuid();
        assertFalse(userToken.isEmpty());
    }

    @Test(expected = NonExistingUserException.class)
    public void loginWithInvalidPassword() throws UserExistsException, NonExistingUserException {
        String username = "username";
        String password = "password";
        UsersService s = new UsersService(getSessionFactory());
        s.addUser(username, DatatypeConverter.printHexBinary(UsersService.hashPassWord(password)));
        String invalidPassword = DatatypeConverter.printHexBinary(UsersService.hashPassWord("invalidPassword"));
        s.login(username, invalidPassword);
    }

    @Test(expected = NonExistingUserException.class)
    public void loginNonExistingUser() throws NonExistingUserException {
        UsersService s = new UsersService(getSessionFactory());
        String hashedPawword = DatatypeConverter.printHexBinary(UsersService.hashPassWord("password"));
        s.login("username", hashedPawword);
    }
}
