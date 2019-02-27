package control.frontcenter;
import control.backendcenter.PublicationOperationCenter;
import exception.createuserexception.NoDuplicateUserName;
import exception.loginexception.FailedAttemptSpillOverException;
import exception.publicationexception.NonExistException;
import exception.publicationexception.RemainingExceedMultiException;
import model.Borrower.Borrower;
import model.Borrower.BorrowerList;
import model.publication.Book;
import model.publication.CD;
import model.publication.Publication;
import model.publication.PublicationItems;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LibraryFrontCenter {
    private static LibraryFrontCenter instance;

    public static LibraryFrontCenter getInstance() {
        return instance;
    }

    static {
        try {
            instance = new LibraryFrontCenter();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int bookcount;
    public static int cdcount;

    //EFFECTS：create empty list of books,borrowers. Set initial book counts as zero
    private LibraryFrontCenter() throws Exception {
        bookcount = 0;
        cdcount = 0;
        //loadBorrowerLog();
        //loadData();
    }
    private void loadData() {
        try {
            PublicationParser.getInstance().parseItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBorrowerLog() throws IOException {
        List<String> borrowerlines = Files.readAllLines(Paths.get("borrowerlog.txt"));
        PrintWriter borrowersaver=new PrintWriter("borrowerlog.txt","UTF-8");
        for (String line : borrowerlines){
            ArrayList<String> partsOfLine = splitOnSpace(line);
            Borrower newbrwr=new Borrower(partsOfLine.get(0),partsOfLine.get(1),partsOfLine.get(2));
            if(BorrowerList.getBorrowerHashMap().containsKey(partsOfLine.get(0))){
                throw new NoDuplicateUserName("You are trying to load duplicate username");
            }
            BorrowerList.getBorrowerHashMap().put(partsOfLine.get(0),newbrwr);
            borrowersaver.println(line);
        }
        borrowersaver.close();
    }
    public static ArrayList<String> splitOnSpace(String line){
        String[] splits = line.split(",");
        return new ArrayList<>(Arrays.asList(splits));
    }

}
