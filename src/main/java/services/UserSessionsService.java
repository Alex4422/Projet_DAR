package services;

import entities.User;
import entities.UserSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import services.errors.UnAuthenticatedUserException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserSessionsService extends ServiceBase {
    private static final int SESSION_DURATION_IN_HOUR = 2;

    public UserSessionsService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public UserSession startSession(User user) {
        UserSession session = new UserSession(user);
        add(session);
        return session;
    }

    public void clear() {
        super.clearTable("UserSession");
    }

    public void endSession(String userToken) {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(UserSession.class);
        criteria.add(Restrictions.eq("uuid", userToken));
        List<UserSession> s = criteria.list();
        getSession().getTransaction().commit();
        delete(s);
    }

    public UserSession retrieveSession(String userToken) throws UnAuthenticatedUserException {
        revokeOldSessions(getOldestValidDate());
        String queryString = "FROM UserSession U where U.uuid = :userToken";
        beginTransaction();
        Query query = getSession().createQuery(queryString);
        query.setParameter("userToken", userToken);
        List result = query.list();
        getSession().getTransaction().commit();
        if (result.isEmpty()) {
            throw new UnAuthenticatedUserException();
        }
        return (UserSession) result.get(0);
    }

    private Date getOldestValidDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, SESSION_DURATION_IN_HOUR);
        return now.getTime();
    }

    public User retrieveUser(String userToken) throws UnAuthenticatedUserException {
        return retrieveSession(userToken).getUser();
    }

    public void refreshSession(String userToken) throws UnAuthenticatedUserException {
        UserSession s = retrieveSession(userToken);
        s.setDate(Calendar.getInstance().getTime());
        update(s);
    }

    public void revokeOldSessions(Date oldestValidDate) {
        String queryString = "DELETE FROM UserSession U where U.date <= :oldestValidDate";
        beginTransaction();
        Query query = getSession().createQuery(queryString);
        query.setParameter("oldestValidDate", oldestValidDate);
        query.executeUpdate();
        getSession().getTransaction().commit();
    }
}
