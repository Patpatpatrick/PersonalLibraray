package exception.createuserexception;

public class NoDuplicateUserName extends RuntimeException {
    public NoDuplicateUserName(String message) {
        super(message);
    }
}
