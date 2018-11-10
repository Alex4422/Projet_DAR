package services;

import launch.Main;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;

public class TestWithDb {
    private static SessionFactory factory;

    @BeforeClass
    public static void init() {
        factory = Main.initDb("jdbc:h2:./testDb");
    }

    @After
    public void cleanTables() {
        new UsersService(getSessionFactory());
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
}
