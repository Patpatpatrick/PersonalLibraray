package model.Borrower;
import control.backendcenter.EmailCenter;
import exception.borrowexception.AlreadyLoanException;
import model.publication.Publication;
import observer.ItemReserver;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Borrower implements ItemReserver {
    private BorrowingInfo borrowingInfo = new BorrowingInfo();
    private String nameofbrwr;
    private String password;
    private String email;

    public Borrower(String nameofbrwr, String passWord, String email) {
        this.email = email;
        this.nameofbrwr = nameofbrwr;
        this.password = passWord;
        this.email = email;
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

    public void addItemtoCurrent(Publication item,LocalDate borrowdate) throws Exception {
        borrowingInfo.addItemtoCurrent(item,borrowdate);
        if(!item.getBorrowRegistrationCard().getBorrowlog().containsKey(this)){
            item.getBorrowRegistrationCard().addCurrentBorrower(this,borrowdate);
        }
    }

    public void addItemtoReservation(Publication item, LocalDate borrower) throws AlreadyLoanException {
        if(!borrowingInfo.getCurrentitemsborrowlog().containsKey(item.getIsbn())) {
            borrowingInfo.addItemtoReservation(item, borrower);
            item.getReserveRegistrationCard().addReserver(this);
        }
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
    public void updateWith(String name, String isbn, int remaining) throws MessagingException {
        EmailCenter.getInstance().setMailServerProperties();
        EmailCenter.getInstance().createEmailMessageToReservers(email,nameofbrwr,name,isbn,remaining);
    }

}
