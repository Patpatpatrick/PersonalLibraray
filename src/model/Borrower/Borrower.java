package model.Borrower;
import exception.borrowexception.AlreadyLoanException;
import exception.borrowexception.ExceedMaxLoanCreditException;
import exception.borrowexception.NoDuplicateLoanException;
import model.publication.Publication;
import observer.ItemReserver;

import java.util.ArrayList;
import java.util.Objects;

public class Borrower implements ItemReserver {
    private BorrowingInfo borrowingInfo = new BorrowingInfo();
    private String nameofbrwr;
    private String password;

    public ArrayList<Publication> getHistoryitems() {
        return borrowingInfo.getHistoryitems();
    }

    public Borrower(String nameofbrwr, String passWord) {
        this.nameofbrwr = nameofbrwr;
        this.password = passWord;
        borrowingInfo.currentitems = new ArrayList<>();
        borrowingInfo.historyitems = new ArrayList<>();
    }

    public BorrowingInfo getBorrowingInfo() {
        return borrowingInfo;
    }

    public Borrower(String nameofbrwr) {
        this.nameofbrwr = nameofbrwr;
    }

    public String getNameofbrwr() {
        return nameofbrwr;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Publication> getCurrentitems() {
        return borrowingInfo.getCurrentitems();
    }

    public void addItemtoCurrent(Publication item) throws Exception {
        borrowingInfo.addItemtoCurrent(item);
        if(!item.getCurrentborrowers().contains(this)){
            item.addCurrentBorrower(this);
        }
    }

    public void addItemtoReservation(Publication item,Borrower borrower) throws AlreadyLoanException {
        if(!borrowingInfo.getCurrentitems().contains(item))
        borrowingInfo.addItemtoReservation(item,this);
        else
            throw new AlreadyLoanException("You have already loan this item!");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrower borrower = (Borrower) o;
        return Objects.equals(nameofbrwr, borrower.nameofbrwr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameofbrwr);
    }

    @Override
    public void updateWith(String name, String isbn, int remaining) {
        System.out.println("An email is sent to me:");
        System.out.println(name+" "+isbn+"is now available with "+remaining+" shares!");
        System.out.println("You can go and get it now!!");
    }
}
