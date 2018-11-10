package services;

import entities.User;
import entities.UserSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsersService extends ServiceBase {
    public UsersService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void addUser(String username, String passWord) throws UserExistsException {
        User user = new User(username, hashPassWord(passWord));
        try {
            add(user);
        } catch (ConstraintViolationException e) {
            throw new UserExistsException(e);
        }
    }

    public static byte[] hashPassWord(String passWord) {
        MessageDigest md = getDigest();
        md.update(passWord.getBytes());
        return md.digest();
    }

    public String login(String username, String hexHashedPassword) throws NonExistingUserException {
        byte[] password = DatatypeConverter.parseHexBinary(hexHashedPassword);
        User user = getUser(username, password);

        if (user == null) {
            throw new NonExistingUserException();
        }

        UserSession session = new UserSession(user);
        add(session);

        return session.getUuid();
    }

    public User getUser(String username, byte[] password) {
        String queryString = "FROM User U WHERE U.username = :username AND U.password = :password";
        beginTransaction();
        Query query = getSession().createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List result = query.list();
        getSession().getTransaction().commit();
        return result.isEmpty() ? null : (User) result.get(0);
    }

    public UserSession retrieveSession(String userToken) throws UnAuthenticatedUserException {
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

    public void refreshSession(String userToken) throws UnAuthenticatedUserException {
        UserSession s = retrieveSession(userToken);
        s.setDate(Calendar.getInstance().getTime());
        update(s);
    }

    public void revokeSessions(Date lastValidDate) {
        String queryString = "DELETE FROM UserSession U where U.date <= :oldestValidDate";
        beginTransaction();
        Query query = getSession().createQuery(queryString);
        query.executeUpdate();
        getSession().getTransaction().commit();
        // TODO: add tests
    }

    public void clear() {
        super.clear("User");
        super.clear("UserSession");
    }

    private static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
