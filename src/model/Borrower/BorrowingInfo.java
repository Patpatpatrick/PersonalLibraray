package model.Borrower;

import exception.borrowexception.ExceedMaxLoanCreditException;
import exception.borrowexception.NoDuplicateLoanException;
import model.publication.Publication;

import java.util.ArrayList;

public class BorrowingInfo {
    public static final int MAXMUM = 5;
    ArrayList<Publication> currentitems=new ArrayList<>();
    ArrayList<Publication> historyitems=new ArrayList<>();
    ArrayList<Publication> reserveditems=new ArrayList<>();


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