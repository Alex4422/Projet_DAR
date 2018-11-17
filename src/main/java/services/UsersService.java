package services;

import entities.User;
import entities.UserSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import services.errors.NonExistingUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsersService extends ServiceBase {
    public UsersService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User getUser(Integer userId) throws NonExistingUserException {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(User.class)
                .add(Restrictions.eq("id", userId));
        List<User> result = criteria.list();
        getSession().getTransaction().commit();

        if (result.isEmpty()) {
            throw new NonExistingUserException(userId);
        }

        return first(result);
    }

    public void addUser(String username, String passWord) throws UserExistsException {
        User user = new User(username, DatatypeConverter.parseHexBinary(passWord));
        try {
            add(user);
        } catch (ConstraintViolationException e) {
            throw new UserExistsException(username);
        }
    }

    public static byte[] hashPassWord(String passWord) {
        MessageDigest md = getDigest();
        md.update(passWord.getBytes());
        return md.digest();
    }

    public UserSession login(String username, String hexHashedPassword) throws NonExistingUserException {
        byte[] password = DatatypeConverter.parseHexBinary(hexHashedPassword);
        User user = getUser(username, password);

        if (user == null) {
            throw new NonExistingUserException(username);
        }

        return new UserSessionsService(getSessionFactory()).startSession(user);
    }

    public User getUser(String username, byte[] password) throws NonExistingUserException {
        String queryString = "FROM User U WHERE U.username = :username AND U.password = :password";
        beginTransaction();
        Query query = getSession().createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> result = query.list();
        getSession().getTransaction().commit();

        if (result.isEmpty()) {
            throw new NonExistingUserException(username);
        }

        return first(result);
    }

    public void clear() {
        super.clearTable("User");
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
