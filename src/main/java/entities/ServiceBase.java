package entities;

import launch.Main;
import org.hibernate.Session;

import java.util.List;

public class ServiceBase {
    public static void add(List<Object> objects) {
        Session s = Main.getFactory().openSession();
        s.beginTransaction();

        for (Object o: objects) {
            s.save(o);
        }

        s.getTransaction().commit();
        s.close();
    }

    public static void add(Object o) {
        Session s = Main.getFactory().openSession();
        s.beginTransaction();
        s.save(o);
        s.getTransaction().commit();
        s.close();
    }
}
