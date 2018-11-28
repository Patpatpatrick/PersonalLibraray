package exception.publicationexception;

public class NonExistException extends Exception {
    public NonExistException() {
    }

    public NonExistException(String message) {
        super(message);
    }
}
