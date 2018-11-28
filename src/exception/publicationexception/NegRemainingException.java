package exception.publicationexception;

import exception.borrowexception.BorrowUnsuccessfulException;

public class NegRemainingException extends BorrowUnsuccessfulException {
    public NegRemainingException(String message) {
        super(message);
    }
}
