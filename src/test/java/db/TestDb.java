package db;

import app.App;
import app.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class TestDb {
    @Before
    public void init() {
        try {
            Statement s = DBService.getConnexion().createStatement();
            s.execute(User.tableCreationQuery);
            new User("geoffrey", "mot de passe").save(DBService.getConnexion().createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        DBService.closeConnection();
        new File("site_db.sql").delete();
    }

    @Test
    public void testNothing() {
        assertEquals(1, 1);
    }
}
