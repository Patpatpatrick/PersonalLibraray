package control.backendcenter;

import exception.borrowexception.AlreadyLoanException;
import exception.borrowexception.BorrowUnsuccessfulException;
import exception.publicationexception.NonExistException;
import model.Borrower.Borrower;

import java.io.IOException;
import java.time.LocalDate;

public interface PublicationModule {
    boolean addPublication(String bookName) throws IOException;
    void displayByName(String name) throws NonExistException;
    void displayAll();
    boolean borrowOrReserveItems(String name, String isbn, Borrower borrower) throws Exception, AlreadyLoanException;
    boolean addItem(String text, String text1, String text2, int a, boolean selected, LocalDate bookimportdate) throws Exception;
}
