package services.errors;

public class UserExistsException extends ServiceException {
    public UserExistsException (String username) {
        super("User " + username + " already exists");
    }
}
