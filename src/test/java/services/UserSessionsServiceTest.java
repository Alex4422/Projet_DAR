package services;

import entities.UserSession;
import org.junit.Test;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class UserSessionsServiceTest extends TestWithDb {
    @Test
    public void retrieveUserSession() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("username", "password");
        String token = s.login("username", DatatypeConverter.printHexBinary(UsersService.hashPassWord("password")))
                .getUuid();

        UserSessionsService userSessionsService = new UserSessionsService(getSessionFactory());
        UserSession session = userSessionsService.retrieveSession(token);
        assertEquals("username", session.getUser().getUsername());
    }

    @Test(expected = UnAuthenticatedUserException.class)
    public void retriveUnAuthenticatedUserSession() throws UserExistsException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("username", "password");

        UserSessionsService userSessionsService = new UserSessionsService(getSessionFactory());
        userSessionsService.retrieveSession("randomSessionToken");
    }

    @Test
    public void refreshSession() throws UserExistsException, NonExistingUserException, InterruptedException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("username", "password");
        String token = s.login("username", DatatypeConverter.printHexBinary(UsersService.hashPassWord("password")))
                .getUuid();
        Thread.sleep(2000);

        UserSessionsService userSessionsService = new UserSessionsService(getSessionFactory());
        Date age = userSessionsService.retrieveSession(token).getDate();
        userSessionsService.refreshSession(token);
        Date newAge = userSessionsService.retrieveSession(token).getDate();
        long ellapsed = (newAge.getTime() - age.getTime()) / 1000;

        assertTrue(ellapsed >= 2);
    }

    @Test(expected = UnAuthenticatedUserException.class)
    public void revokeOldSessions() throws UserExistsException, NonExistingUserException, InterruptedException, UnAuthenticatedUserException {
        UsersService s = new UsersService(getSessionFactory());
        s.addUser("u1", "p1");
        s.addUser("u2", "p2");
        String u1Token = s.login("u1", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p1")))
                .getUuid();
        String u2Token = s.login("u2", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p2")))
                .getUuid();

        Thread.sleep(2000);

        Calendar oldestValidTime = Calendar.getInstance();
        oldestValidTime.add(Calendar.SECOND, -2);

        UserSessionsService userSessionsService = new UserSessionsService(getSessionFactory());
        userSessionsService.refreshSession(u2Token);
        userSessionsService.revokeOldSessions(oldestValidTime.getTime());
        userSessionsService.retrieveSession(u1Token);
    }
}
