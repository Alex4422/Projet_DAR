package services;

import entities.UserSession;
import launch.Main;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import services.errors.NonExistingUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;
import java.io.File;

public class TestWithDb {
    private static SessionFactory factory;

    @BeforeClass
    public static void init() {
        factory = Main.initDb("jdbc:h2:./testDb");
    }

    @After
    public void cleanTables() {
        new RatingService(getSessionFactory()).clear();
        new UsersService(getSessionFactory()).clear();
        new EpisodesService(getSessionFactory()).clear();
        new UserSessionsService(getSessionFactory()).clear();
    }

    @AfterClass
    public static void deleteDbFile() {
        new File("testDb.mv.db").delete();
        new File("testDb.mv.db").delete();
    }

    protected Session getSession() {
        return factory.openSession();
    }

    protected SessionFactory getSessionFactory() {
        return factory;
    }

    protected UserSession registerAndLoginUser() throws UserExistsException, NonExistingUserException {
        UsersService usersService = new UsersService(getSessionFactory());
        usersService.addUser("u", "p");
        return usersService.login("u", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
    }

    protected UserSession registerAndLoginUser(String username, String password) throws UserExistsException, NonExistingUserException {
        UsersService usersService = new UsersService(getSessionFactory());
        usersService.addUser(username, password);
        return usersService.login(username, DatatypeConverter.printHexBinary(UsersService.hashPassWord(password)));
    }
}
