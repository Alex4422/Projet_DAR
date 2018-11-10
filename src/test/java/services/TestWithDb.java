package services;

import launch.Main;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

import java.io.File;

public class TestWithDb {
    private SessionFactory factory;

    @Before
    public void init() {
        factory = Main.initDb("jdbc:h2:./testDb");
    }

    @After
    public void cleanUp() {
        new UsersService(getSessionFactory());
    }

    protected Session getSession() {
        return factory.openSession();
    }

    protected SessionFactory getSessionFactory() {
        return factory;
    }
}
