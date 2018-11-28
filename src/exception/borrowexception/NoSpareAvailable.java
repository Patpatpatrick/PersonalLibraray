package exception.borrowexception;

import exception.borrowexception.BorrowUnsuccessfulException;

public class NoSpareAvailable extends BorrowUnsuccessfulException {
    public NoSpareAvailable(String message) {
        super(message);
    }
}
