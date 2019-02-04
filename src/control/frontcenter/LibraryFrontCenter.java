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

    private static final String password = "admin";
    public static int bookcount;
    public static int cdcount;
    private int operator;

    //EFFECTS：create empty list of books,borrowers. Set initial book counts as zero
    private LibraryFrontCenter() throws Exception {
        bookcount = 0;
        cdcount = 0;
//        loadBookLog();
//        loadCDLog();
        loadBorrowerLog();
//        PublicationParser.getInstance().createXML();
        loadData();
//        System.out.println(PublicationItems.getPublicationHashMap());
    }
    private void loadData() {
        try {
            PublicationParser.getInstance().parseItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBookLog() throws IOException {
        List<String> booklines = Files.readAllLines(Paths.get("booklog.txt"));
        PrintWriter booksaver=new PrintWriter("booklog.txt","UTF-8");
        for (String line : booklines){
            ArrayList<String> partsOfLine = splitOnSpace(line);
            Publication newbk=new Book(partsOfLine.get(0),partsOfLine.get(1),partsOfLine.get(2),Integer.parseInt(partsOfLine.get(3)));
            if(PublicationItems.getPublicationHashMap().containsKey(partsOfLine.get(0))){
                boolean dual=false;
                for(Publication a: PublicationItems.getPublicationHashMap().get(partsOfLine.get(0))){
                    if(a.equals(newbk)){
                        a.multiplicityIncre(Integer.parseInt(partsOfLine.get(3)));
                        bookcount+=Integer.parseInt(partsOfLine.get(3));
                        dual=true;
                        break;
                    }
                }
                if(!dual){
                    PublicationItems.getPublicationHashMap().get(partsOfLine.get(0)).add(newbk);
                    bookcount+=Integer.parseInt(partsOfLine.get(3));
                }
            }
            else{
                ArrayList<Publication> newbookarraylist=new ArrayList<>();
                newbookarraylist.add(newbk);
                PublicationItems.getPublicationHashMap().put(partsOfLine.get(0),newbookarraylist);
                bookcount+=Integer.parseInt(partsOfLine.get(3));
            }
            booksaver.println(line);
        }
        booksaver.close();
    }
    public void loadCDLog() throws IOException {
        List<String> cdlines = Files.readAllLines(Paths.get("cdlog.txt"));
        PrintWriter cdsaver=new PrintWriter("cdlog.txt","UTF-8");
        for (String line : cdlines){
            ArrayList<String> partsOfLine = splitOnSpace(line);
            Publication newcd=new CD(partsOfLine.get(0),partsOfLine.get(1),partsOfLine.get(2),Integer.parseInt(partsOfLine.get(3)));
            if(PublicationItems.getPublicationHashMap().containsKey(partsOfLine.get(0))){
                boolean dual=false;
                for(Publication a: PublicationItems.getPublicationHashMap().get(partsOfLine.get(0))){
                    if(a.equals(newcd)){
                        a.multiplicityIncre(Integer.parseInt(partsOfLine.get(3)));
                        cdcount+=Integer.parseInt(partsOfLine.get(3));
                        dual=true;
                        break;
                    }
                }
                if(!dual){
                    PublicationItems.getPublicationHashMap().get(partsOfLine.get(0)).add(newcd);
                    cdcount+=Integer.parseInt(partsOfLine.get(3));
                }
            }
            else{
                ArrayList<Publication> newcdarraylist=new ArrayList<>();
                newcdarraylist.add(newcd);
                PublicationItems.getPublicationHashMap().put(partsOfLine.get(0),newcdarraylist);
                cdcount+=Integer.parseInt(partsOfLine.get(3));
            }
            cdsaver.println(line);
        }
        cdsaver.close();
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
    //MODIFIES: this
    //EFFECTS: set the operator number
    public void setOperator(int operator) {
        this.operator = operator;
    }

    //EFFECTS: get the operator number
    public int getOperator() {
        return operator;
    }

    //EFFECTS: to verify the current user is administrator by asking the user to enter
    //          the correct password within three times.
    private boolean isAdmin() throws FailedAttemptSpillOverException {
        System.out.println("You need to input the admin's password to verify that you are the admin, " +
                "you have only three times attempts");
        Scanner userInputScanner = new Scanner(System.in);
        for (int i = 2; i >= 0; i--) {
            System.out.println("please enter it:");
            String adminpasswordreceiver = userInputScanner.nextLine();
            if (adminpasswordreceiver.equals(password)) {
                System.out.println("Successfully loged in");
                return true;
            } else
                System.out.println("Incorrect admin password, now you have " + i + " remaining attempt times");
        }
        System.out.println("You used all possible attempts.");
        throw new FailedAttemptSpillOverException();
    }

    //EFFECTS:  show how many books are there in the library.
    private void showTotalCounts() {
        if (bookcount == 1) {
            System.out.println("There are " + bookcount + " book in my library now.");
        } else {
            System.out.println("There are " + bookcount + " books in my library now.");
        }
        if (cdcount == 1) {
            System.out.println("There are " + cdcount + " CD in my library now.");
        } else {
            System.out.println("There are " + cdcount + " CDs in my library now.");
        }
    }

    //EFFECTS: check if a borrower is a valid one by correctly enter the username and
    //          corresponding password in three times. Return this borrower.
    private Borrower isBorrower() throws FailedAttemptSpillOverException {
        System.out.println("You have only three times attempts to verify that you are an legal borrower");
        Scanner userInputScanner = new Scanner(System.in);
        String usernamereceiver, passwordreceiver;
        PublicationOperationCenter operationCenter = PublicationOperationCenter.getInstance();
        for (int i = 2; i > 0; i--) {
            System.out.println("Please enter the username:");
            usernamereceiver = userInputScanner.nextLine();
            System.out.println("Please enter the password:");
            passwordreceiver = userInputScanner.nextLine();
            try {
                if (operationCenter.stringIsUserName(usernamereceiver) && operationCenter.correctPassword(usernamereceiver, passwordreceiver) != null)
                    return operationCenter.correctPassword(usernamereceiver, passwordreceiver);
            } catch (NonExistException e) {
                System.out.println("Incorrect username or password input, " +
                        "now you have " + i + " remaining attempt times");
                continue;
            }
        }
        throw new FailedAttemptSpillOverException();
    }

    public void displayBookAvailableByName(String name) throws NonExistException {
        if (PublicationItems.getPublicationHashMap().containsKey(name)) {
            int i = 1;
            System.out.println("Here is the information of the book still available");
            System.out.println("Number\tName\tISBN\tAuthorName\ttotalshare#\tremaining");
            for (Publication a : PublicationItems.getPublicationHashMap().get(name)) {
                if (a.getShares().getRemaining() > 0) {
                    System.out.println(i + "\t" + a.getName() + "\t" + a.getIsbn() + "\t" + a.getAuthorName() + "\t" + a.getShares().getMultiplicity() + "\t" + a.getShares().getRemaining());
                    i++;
                }
            }
        } else throw new NonExistException("No such book");
    }

//    public void returnBooksInteraction() throws FileNotFoundException, UnsupportedEncodingException, NonExistException, RemainingExceedMultiException {
//        System.out.println("please input your username");
//        Scanner userInputScanner = new Scanner(System.in);
//        String username = userInputScanner.nextLine();
//        PublicationOperationCenter publicationOperationCenter = PublicationOperationCenter.getInstance();
//        publicationOperationCenter.stringIsUserName(username);
//        Borrower b = publicationOperationCenter.stringIsThisBorrower(username);
//        publicationOperationCenter.ShowAllLoanedBooks(b);
//        do{
//        System.out.println("Which one do you want to return,please enter the number");
//        int returnbookNum = userInputScanner.nextInt();
//        try {
//            publicationOperationCenter.returnBook(b, returnbookNum, returnedbookisbn);
//            return;
//        } catch (InputMismatchException e) {
//            System.out.println("you should enter the number shown above");
//        }catch(IndexOutOfBoundsException e){
//            System.out.println("You don't have a book with this number");
//        }}while (true);
//    }


//    public void booklost() {
//        //stub
//    }
//    public void getfine(){
//        //stub
//
//    }
//    public void payfine(){
//        //stub
//    }
}
