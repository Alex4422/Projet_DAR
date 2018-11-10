package services.errors;

public class UserExistsException extends Exception {
    public UserExistsException (Exception e) {
        super(e);
    }
}
