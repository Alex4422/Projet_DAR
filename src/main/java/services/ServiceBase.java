package services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class ServiceBase {
    private SessionFactory sessionFactory;

    public ServiceBase(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add(List<Object> objects) {
        beginTransaction();
        for (Object o: objects) {
            getSession().save(o);
        }
        getSession().getTransaction().commit();
    }

    protected void add(Object o) {
        beginTransaction();
        getSession().persist(o);
        getSession().getTransaction().commit();
    }

    protected void update(Object o) {
        beginTransaction();
        getSession().update(o);
        getSession().getTransaction().commit();
    }

    protected void delete(Object o) {
        beginTransaction();
        getSession().delete(o);
        getSession().getTransaction().commit();
    }

    protected void clearTable(String tablename) {
        beginTransaction();
        Query q = getSession().createQuery("FROM " + tablename);
        q.list().stream().forEach(getSession()::delete);
        getSession().getTransaction().commit();
    }

    protected <T> T first(List<T>  queryResults) {
        if (queryResults.isEmpty()) {
            return null;
        } else {
            return queryResults.get(0);
        }
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected SessionFactory getSessionFactory() { return sessionFactory; }

    protected void beginTransaction() {
        if (getSession().getTransaction().isActive()) {
            getSession().getTransaction().rollback();
        }
        getSession().beginTransaction();
    }
}
