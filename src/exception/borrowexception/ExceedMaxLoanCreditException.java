package exception.borrowexception;

import exception.borrowexception.BorrowUnsuccessfulException;

public class ExceedMaxLoanCreditException extends BorrowUnsuccessfulException {

    public ExceedMaxLoanCreditException(String message) {
        super(message);
    }
}
