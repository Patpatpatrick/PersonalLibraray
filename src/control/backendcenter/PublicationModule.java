package control.backendcenter;

import exception.borrowexception.AlreadyLoanException;
import exception.borrowexception.BorrowUnsuccessfulException;
import exception.publicationexception.NonExistException;
import model.Borrower.Borrower;

import java.io.IOException;

public interface PublicationModule {
    boolean addPublication(String bookName) throws IOException;
    void displayByName(String name) throws NonExistException;
    void displayAll();
    boolean borrowOrReserveItems(String name, String isbn, Borrower borrower) throws Exception, AlreadyLoanException;
}
