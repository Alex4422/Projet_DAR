package servlets;

import launch.Main;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import services.*;

import java.util.concurrent.Executors;

public class ServletTest {

    @BeforeClass
    public static void init() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Main.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void clearTables() {
        new MessagesService(Main.getFactory()).clear();
        new RatingService(Main.getFactory()).clear();
        new UsersService(Main.getFactory()).clear();
        new EpisodesService(Main.getFactory()).clear();
        new UserSessionsService(Main.getFactory()).clear();
    }
}
