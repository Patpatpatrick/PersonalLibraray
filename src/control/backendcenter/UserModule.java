package control.backendcenter;

import exception.publicationexception.NonExistException;
import model.Borrower.Borrower;

import java.io.IOException;

public interface UserModule {

    boolean addBorrower(String username, String userpassword, String email) throws IOException;
    void saveBorrowerLog(String username, String password,String email) throws IOException;
}
