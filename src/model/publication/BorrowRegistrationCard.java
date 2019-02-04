package model.publication;

import model.Borrower.Borrower;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class BorrowRegistrationCard {
    private LocalDate importdate;

    private HashMap<Borrower, LocalDate> borrowlog;

    private HashMap<Borrower, LocalDate> borrowerreturnscheme;

    public BorrowRegistrationCard(LocalDate recordeddate) {
        importdate = recordeddate;
        borrowlog = new HashMap<>();
        borrowerreturnscheme = new HashMap<>();
    }

    public void addCurrentBorrower(Borrower currentborrower, LocalDate loandate) throws Exception {
        borrowlog.put(currentborrower,loandate);
        borrowerreturnscheme.put(currentborrower,loandate.plusDays(currentborrower.getBorrowingInfo().daystoloan));
    }

    public LocalDate getImportdate() {
        return importdate;
    }

    public HashMap<Borrower, LocalDate> getBorrowlog() {
        return borrowlog;
    }

    public HashMap<Borrower, LocalDate> getBorrowerreturnscheme() {
        return borrowerreturnscheme;
    }

}