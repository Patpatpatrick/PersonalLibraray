package model.Borrower;

import exception.borrowexception.ExceedMaxLoanCreditException;
import exception.borrowexception.NoDuplicateLoanException;
import model.publication.Publication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class BorrowingInfo {
    public static final int MAXMUM = 5;
    private HashMap<Publication, LocalDate> currentitems=new HashMap<>();
    private HashMap<Publication, LocalDate> historyitems=new HashMap<>();
    private HashMap<Publication, LocalDate> reserveditems=new HashMap<>();


    public BorrowingInfo() {
    }

    public ArrayList<Publication> getHistoryitems() {
        return historyitems;
    }

    public ArrayList<Publication> getCurrentitems() {
        return currentitems;
    }

    public void addItemtoCurrent(Publication item) throws ExceedMaxLoanCreditException, NoDuplicateLoanException {
        if (currentitems.size() >= MAXMUM) {
            throw new ExceedMaxLoanCreditException("Exceed max credit");
        }
        if (currentitems.contains(item))
            throw new NoDuplicateLoanException("You cannot make duplicate loan");
        currentitems.add(item);
    }

    public void addItemtoReservation(Publication item, Borrower borrower) {
        if(!reserveditems.contains(item)){
            reserveditems.add(item);
            item.addReserver(borrower);
        }
    }
}