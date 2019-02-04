package exception.createuserexception;

public class UnverifiedEmail extends RuntimeException {
    public UnverifiedEmail(String message) {
        super(message);
    }
}
