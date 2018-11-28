package control.backendcenter;

import exception.publicationexception.NonExistException;
import model.Borrower.Borrower;

import java.io.IOException;

public interface UserModule {
    boolean stringIsUserName(String usernamereceiver) throws NonExistException;
    Borrower stringIsThisBorrower(String usernamereceiver) throws NonExistException;
    Borrower correctPassword(String username, String passwordReiceiver) throws NonExistException;
    boolean addBorrower(String username, String userpassword) throws IOException;
    void saveBorrowerLog(String username, String password) throws IOException;
}
