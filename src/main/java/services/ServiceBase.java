package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class ServiceBase {
    private SessionFactory sessionFactory;

    public ServiceBase(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add(List<Object> objects) {
        Session s = getSession();
        s.beginTransaction();

        for (Object o: objects) {
            s.save(o);
        }

        s.getTransaction().commit();
        s.close();
    }

    protected void add(Object o) {
        Session s = getSession();
        s.beginTransaction();
        s.save(o);
        s.getTransaction().commit();
        s.close();
    }

    protected void clear(String tablename) {
        Session s = getSession();
        s.beginTransaction();
        s.createQuery("DELETE FROM " + tablename);
        s.getTransaction().commit();
        s.close();
    }

    protected Session getSession() {
        return sessionFactory.openSession();
    }
}
