package servlets;

import launch.Main;
import org.junit.BeforeClass;

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
}
