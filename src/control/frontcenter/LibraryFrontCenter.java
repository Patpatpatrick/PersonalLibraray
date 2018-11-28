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

import javax.swing.*;
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
            Borrower newbrwr=new Borrower(partsOfLine.get(0),partsOfLine.get(1));
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

    //MODIFIES: this
    //EFFECTS:  Receive a new book's information by recording at least the book's name if the current is administrator
    //          otherwise do nothing.
//    public void addBookInteraction() throws IOException {
//        try {
//            if (isAdmin()) {
//                showTotalCounts();
//                Scanner userInputScanner = new Scanner(System.in);
//                System.out.println("To add a book, you need to enter its information");
//                System.out.println("Please enter the name");
//                String bookName = userInputScanner.nextLine();
//                System.out.println("You may want to add some information, if so press Y, if not press any key");
//                if (userInputScanner.nextLine().equals("Y")) {
//                    System.out.println("Input the book's ISBN");
//                    String ISBN = userInputScanner.nextLine();
//                    System.out.println("Input the book's authorname");
//                    String Authorname = userInputScanner.nextLine();
//                    System.out.println("Input how many share(s) do you have for this book, e.g. 2");
//                    int totalpgnum = userInputScanner.nextInt();
//                    PublicationOperationCenter operationCenter = PublicationOperationCenter.getInstance();
//                    //operationCenter.addItem(bookName,ISBN,Authorname,totalpgnum);
//                } else {
//                    PublicationOperationCenter operationCenter = PublicationOperationCenter.getInstance();
//                    operationCenter.addPublication(bookName);
//                }
//                System.out.println("Go back to the main menu");
//            } else {
//                System.out.println("You are not a legal admin.");
//            }
//        } catch (FailedAttemptSpillOverException e) {
//            System.exit(0);
//        }
//    }

//    public void addCDInteraction() throws IOException {
//        try {
//            if (isAdmin()) {
//                showTotalCounts();
//                Scanner userInputScanner = new Scanner(System.in);
//                System.out.println("To add a CD, you need to enter its information");
//                System.out.println("Please enter the name");
//                String cdName = userInputScanner.nextLine();
//                System.out.println("You may want to add some information, if so press Y, if not press any key");
//                if (userInputScanner.nextLine().equals("Y")) {
//                    System.out.println("Input the cd's ISBN");
//                    String ISBN = userInputScanner.nextLine();
//                    System.out.println("Input the cd's authorname");
//                    String Authorname = userInputScanner.nextLine();
//                    System.out.println("Input the cd's multiplicity ,e.g. 2 for two identical shares");
//                    int multi = userInputScanner.nextInt();
//                    //CDOperationCenter operationCenter =new CDOperationCenter();
//                    // operationCenter.addCD(cdName,ISBN,Authorname,multi);
//                } else {
//                    //CDOperationCenter operationCenter =new CDOperationCenter();
//                    // operationCenter.addPublication(cdName);
//                }
//                System.out.println("Go back to the main menu");
//            } else {
//                System.out.println("You are not a legal admin.");
//            }
//        } catch (FailedAttemptSpillOverException e) {
//            System.out.println("You have failed too many times!!!Unforgivable!!");
//            System.exit(0);
//        }
//    }

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

//    public boolean borrowBookInteraction() throws NonExistException, BorrowUnsuccessfulException {
//        System.out.println("You need to enter the username and corresponding password to borrow a book, " +
//                "you have only three times attempts");
//        Borrower tmp = null;
//        try {
//            tmp = isBorrower();
//        } catch (FailedAttemptSpillOverException e) {
//            System.out.println("You have failed too many times!!!Unforgivable!!");
//            System.exit(0);
//        }
//        System.out.println("Please input the name of the book that you want to borrow");
//        System.out.println("Start finding your designated book");
//        Scanner userInputScanner = new Scanner(System.in);
//        String s = userInputScanner.nextLine();
//        displayBookAvailableByName(s);
//        System.out.println("Now please input the ISBN to specify the desired book");
//        PublicationOperationCenter operationCenter = PublicationOperationCenter.getInstance();
//        return operationCenter.borrowOrReserveItems(s, userInputScanner.nextLine(), tmp);
//    }

//    public void borrowCDInteraction() throws FailedAttemptSpillOverException {
//        System.out.println("You need to enter the username and corresponding password to borrow a cd, " +
//                "you have only three times attempts");
//        Borrower tmp = null;
//        tmp = isBorrower();
//
//        System.out.println("Please input the name of the book that you want to borrow");
//        System.out.println("Start finding your designated book");
//        Scanner userInputScanner = new Scanner(System.in);
//        String s = userInputScanner.nextLine();
//        //displayCDAvailableByName(s);
//        //CDOperationCenter operationCenter=new CDOperationCenter();
//        //return operationCenter.borrowOrReserveItems(s,userInputScanner.nextLine(),tmp);
//    }

//    //REQUIRES: Booklist is not null
//    //EFFECTS: Display all books or specified books, able to do this for many times.
//    public void DisplayBooksInteraction() throws FileNotFoundException, UnsupportedEncodingException {
//        showTotalCounts();
//        Scanner userInputScanner = new Scanner(System.in);
//        PublicationOperationCenter operationCenter=PublicationOperationCenter.getInstance();
//        int receiver = 0;
//        do{
//            System.out.println("Press 1 if you want to display all the books in the library, " +
//                    "or press 2 if you want to see a specific book");
//            try{
//                receiver=userInputScanner.nextInt();
//            } catch (InputMismatchException e){
//                System.out.println("You should enter an integer");
//                continue;
//            }
//            if(receiver==1){
//                operationCenter.displayAll();
//                break;
//            }
//            else if(receiver==2){
//                System.out.println("Please type in the name of the book on which you want information");
//                String str=userInputScanner.nextLine();
//                try {
//                    operationCenter.displayByName(str);
//                } catch (NonExistException e) {
//                    System.out.println("NONexistbook");
//                    continue;
//                }
//            }
//            else{
//                System.out.println("Invalid input!");
//            }
//            System.out.println("You can press 0 to quit or any other buttom to continue displaying books");
//        }while(userInputScanner.nextInt()!=0);
//    }
//    public void DisplayCDsInteraction() {
////        showTotalCounts();
////        Scanner userInputScanner = new Scanner(System.in);
////       // CDOperationCenter operationCenter=new CDOperationCenter();
////        do{
////            System.out.println("Press 1 if you want to display all the CDs in the library, " +
////                    "or press 2 if you want to see a specific CD");
////            try {
////                int receiver=userInputScanner.nextInt();
////                if(receiver==1){
////               //     operationCenter.displayAll();
////                    break;
////                }
////                else if(receiver==2){
////                    System.out.println("Please type in the name of the CD on which you want information");
////                    String str=userInputScanner.nextLine();
////                    try {
////                        operationCenter.displayByName(str);
////                    } catch (NonExistException e) {
////                        System.out.println("NONexistcd!");
////                        continue;
////                    }
////                }
////            }catch (NumberFormatException e){
////                System.out.println("You should Enter 1 or 2");
////                continue;
////            }
////            System.out.println("You can press 0 to quit or any other buttom to continue displaying CDs");
////        }while(userInputScanner.nextInt()!=0);
//    }

    public void returnBooksInteraction() throws FileNotFoundException, UnsupportedEncodingException, NonExistException, RemainingExceedMultiException {
        System.out.println("please input your username");
        Scanner userInputScanner = new Scanner(System.in);
        String username = userInputScanner.nextLine();
        PublicationOperationCenter publicationOperationCenter = PublicationOperationCenter.getInstance();
        publicationOperationCenter.stringIsUserName(username);
        Borrower b = publicationOperationCenter.stringIsThisBorrower(username);
        publicationOperationCenter.ShowAllLoanedBooks(b);
        do{
        System.out.println("Which one do you want to return,please enter the number");
        int returnbookNum = userInputScanner.nextInt();
        try {
            publicationOperationCenter.returnBook(b, returnbookNum);
            return;
        } catch (InputMismatchException e) {
            System.out.println("you should enter the number shown above");
        }catch(IndexOutOfBoundsException e){
            System.out.println("You don't have a book with this number");
        }}while (true);
    }


    public void booklost() {
        //stub}
    }
    public void getfine(){
        //stub

    }
    public void payfine(){
        //stub
    }
    //MODIFIES：this
    //EFFECTS: Receiving a new borrower's information, that is, an unused username and
    //         a password.
//    public void addBorrowerInput() throws IOException {
//        System.out.println("Please input a string as your username");
//        Scanner userInputScanner = new Scanner(System.in);
//        //Need to modify so as to prevent repeated username;
//        String username;
//        PublicationOperationCenter operationCenter=PublicationOperationCenter.getInstance();
//        do{
//            username=userInputScanner.nextLine();
//            try {
//                if(operationCenter.stringIsUserName(username)){
//                    System.out.println("Username already exists, please enter again");
//                }
//                else{
//                    System.out.println("feasible username!");
//                    break;
//                }
//            } catch (NonExistException e) {
//                e.printStackTrace();
//            }
//        }while(true);
//        String userpassword,userpassword2;
//        do {
//            System.out.println("Please input a string as your password");
//            userpassword = userInputScanner.nextLine();
//            System.out.println("Please input password again to make it effective");
//            userpassword2 = userInputScanner.nextLine();
//            if(userpassword.equals(userpassword2)){
//                break;
//            }
//            else{
//                System.out.println("the two inputs are not identical, please try again.");
//            }
//        }while(true);
//        operationCenter.addBorrower(username, userpassword);
//    }

}
