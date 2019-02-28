package control.backendcenter;
import exception.borrowexception.*;
import exception.createuserexception.NoDuplicateUserName;
import exception.publicationexception.NegRemainingException;
import exception.publicationexception.NonExistException;
import model.Borrower.Borrower;
import model.Borrower.BorrowerList;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class OperationCenter implements PublicationModule, UserModule {


    abstract public boolean borrowOrReserveItems(String bookName, String isbn, Borrower borrower) throws Exception, AlreadyLoanException;

    @Override
    public boolean addBorrower(String username, String userpassword, String Email) throws IOException {
        Borrower newBorrower= new Borrower(username,userpassword,Email);
        if(BorrowerList.getBorrowerHashMap().containsKey(username)){
            throw new NoDuplicateUserName("You are trying to add duplicate username.");
        }
        BorrowerList.getBorrowerHashMap().put(username,newBorrower);
        System.out.println("Borrower added successfully!");
        saveBorrowerLog(username,userpassword,Email);
        return true;
    }
    @Override
    public void saveBorrowerLog(String username, String password, String Email) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("borrowerlog.txt"));;
        PrintWriter brwrwriter = new PrintWriter("borrowerlog.txt","UTF-8");
        lines.add(username+","+password+","+Email);
        for(String line:lines){
            brwrwriter.println(line);
        }
        brwrwriter.close();
    }
}
