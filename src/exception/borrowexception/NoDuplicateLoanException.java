package exception.borrowexception;

import exception.borrowexception.BorrowUnsuccessfulException;

public class NoDuplicateLoanException extends BorrowUnsuccessfulException {
    public NoDuplicateLoanException(String message) {
        super(message);
    }
}
