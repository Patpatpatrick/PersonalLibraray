package model.Borrower;

import exception.borrowexception.ExceedMaxLoanCreditException;
import exception.borrowexception.NoDuplicateLoanException;
import model.publication.Publication;

import java.time.LocalDate;
import java.util.HashMap;

public class BorrowingInfo {
    private static final int MAXMUM = 5;
    public final static long daystoloan = 30;
    private HashMap<String, PublicationLocalDateTuple<Publication,LocalDate>> currentitemsborrowlog =new HashMap<>();
    private HashMap<String, PublicationLocalDateTuple<Publication,LocalDate>> currentitemsreturnscheme =new HashMap<>();
    private HashMap<String, PublicationLocalDateTuple<Publication,LocalDate>> historyitems=new HashMap<>();
    private HashMap<String, PublicationLocalDateTuple<Publication,LocalDate>> reserveditems=new HashMap<>();


    public BorrowingInfo() {
    }

    public HashMap<String, PublicationLocalDateTuple<Publication, LocalDate>> getCurrentitemsborrowlog() {
        return currentitemsborrowlog;
    }

    public HashMap<String, PublicationLocalDateTuple<Publication, LocalDate>> getCurrentitemsreturnscheme() {
        return currentitemsreturnscheme;
    }

    public HashMap<String, PublicationLocalDateTuple<Publication, LocalDate>> getHistoryitems() {
        return historyitems;
    }

    public HashMap<String, PublicationLocalDateTuple<Publication, LocalDate>> getReserveditems() {
        return reserveditems;
    }

    public void addItemtoCurrent(Publication item, LocalDate borroweddate) throws ExceedMaxLoanCreditException, NoDuplicateLoanException {
        if (currentitemsborrowlog.size() >= MAXMUM) {
            throw new ExceedMaxLoanCreditException("Exceed max credit");
        }
        if (currentitemsborrowlog.containsKey(item))
            throw new NoDuplicateLoanException("You cannot make duplicate loan");
        PublicationLocalDateTuple<Publication,LocalDate> tuple = new PublicationLocalDateTuple<>(item,borroweddate);
        currentitemsborrowlog.put(item.getIsbn(),tuple);
        PublicationLocalDateTuple<Publication,LocalDate> tuple2 = new PublicationLocalDateTuple<>(item,borroweddate.plusDays(daystoloan));
        currentitemsreturnscheme.put(item.getIsbn(),tuple2);
    }

    public void addItemtoReservation(Publication item, LocalDate reserveDate) {
        if(!reserveditems.containsKey(item.getIsbn())){
            PublicationLocalDateTuple<Publication,LocalDate> tuple = new PublicationLocalDateTuple<>(item,reserveDate);
            reserveditems.put(item.getIsbn(),tuple);
        }
    }
}