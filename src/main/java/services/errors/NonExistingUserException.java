package services.errors;

public class NonExistingUserException extends ServiceException {
    public NonExistingUserException(String userName) {
        super("User " + userName + " doesn't exist");
    }

    public NonExistingUserException(Integer userId) {
        super("user with id " + userId + " doesn't exist");
    }
}
