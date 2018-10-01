package db;

import app.App;
import app.User;
import app.UserService;
import db.errors.UninitializedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDb {
    private static String testUrl = "jdbc:sqlite:testdb.sqlite";

    @Before
    public void init() {
        DBService.init(testUrl);
    }

    @Test
    public void addUser() {
        User user = new User("geoffrey", "password");
        try {
            new UserService().save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<User> allUsers = UserService.getAll();
            assertEquals(user, allUsers.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        DBService.closeConnection();
        new File("testdb.sqlite").delete();
    }

    @Test
    public void testNothing() {
        assertEquals(1, 1);
    }
}
