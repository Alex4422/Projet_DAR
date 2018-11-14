package services;

import org.hibernate.SessionFactory;

public class MesseagesService extends ServiceBase {

    public MesseagesService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
