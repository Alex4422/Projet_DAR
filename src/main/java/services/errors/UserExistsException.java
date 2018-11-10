package services.errors;

public class UserExistsException extends ServiceException {
    public UserExistsException (Exception e) {
        super(e);
    }
}
