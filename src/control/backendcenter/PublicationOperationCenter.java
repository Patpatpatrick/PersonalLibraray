package control.backendcenter;

import control.frontcenter.LibraryFrontCenter;
import control.frontcenter.PublicationParser;
import exception.borrowexception.AlreadyLoanException;
import exception.borrowexception.BorrowUnsuccessfulException;
import exception.publicationexception.NonExistException;
import exception.publicationexception.RemainingExceedMultiException;
import model.Borrower.Borrower;
import model.Borrower.PublicationLocalDateTuple;
import model.publication.Book;
import model.publication.CD;
import model.publication.Publication;
import model.publication.PublicationItems;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PublicationOperationCenter extends OperationCenter {
    private static PublicationOperationCenter instance = new PublicationOperationCenter();

    private PublicationOperationCenter() {
    }

    public static PublicationOperationCenter getInstance() {
        return instance;
    }

    @Override
    protected void five(){

    }
    //MODIFIES:this
    //EFFECTS: add a book into the library with only name
    @Override
    public boolean addPublication(String bookName) throws IOException {
        Book new_book = new Book(bookName);
        if (PublicationItems.getPublicationHashMap().containsKey(bookName)) {
            PublicationItems.getPublicationHashMap().get(bookName).add(new_book);
        }
        else {
            ArrayList<Publication> newpublication = new ArrayList<>();
            newpublication.add(new_book);
            PublicationItems.getPublicationHashMap().put(bookName, newpublication);
        }
        LibraryFrontCenter.bookcount++;
        System.out.println("Book with missing information added.");
        LocalDate bookimportdate = LocalDate.now();
        saveItemLog(bookName,"NA","NA",1, bookimportdate, "booklog.txt");
        return false;
    }

    //REQUIRES: Name is not null, booklist is not null
    //EFFECTS: Display specified books, if there is no match do nothing.
    @Override
    public void displayByName(String name) throws NonExistException {
        if(PublicationItems.getPublicationHashMap().containsKey(name)){
            int i=1;
            System.out.println("Here is the information of the book");
            System.out.println("Number\tName\tISBN\tAuthorName\ttotalshare#\tremaining");
            for(Publication a: PublicationItems.getPublicationHashMap().get(name)){
                System.out.println(i+"\t"+a.getName() + "\t" + a.getIsbn() + "\t" + a.getAuthorName() + "\t" + a.getShares().getMultiplicity()+ "\t" +a.getShares().getRemaining());
                i++;
            }
        }
        else throw new NonExistException("No such book");
    }

    //REQUIRES: Booklist is not null
    //EFFECTS: Display all books.
    @Override
    public void displayAll(){
        System.out.println("There are ");
        System.out.println("Name\tISBN\tAuthor\ttotalshares#\tremaining");
        Iterator iter = PublicationItems.getPublicationHashMap().entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            ArrayList<Publication> arrayList = (ArrayList<Publication>) entry.getValue();
            for(Publication a:arrayList){
                System.out.println(a.getName()+"\t"+a.getIsbn()+"\t"+a.getAuthorName()+"\t"+a.getShares().getMultiplicity()+"\t"+a.getShares().getRemaining());
            }
        }
    }

    //MODIFIES: this, Borrower
    //EFFECTS: Lend a book that matches the name that a verified borrower claims,
    //         otherwise do nothing.
    //fdf
    @Override
    public boolean borrowOrReserveItems(String itemname, String isbn, Borrower borrower) throws Exception, AlreadyLoanException {
        for(Publication a: PublicationItems.getPublicationHashMap().get(itemname)) {
            if (a.getIsbn().equals(isbn)&&a.getShares().getRemaining() > 0) {
//                System.out.println("Here is a matched book, and it is in our library now, now maybe we can lend it to you!");
                borrower.addItemtoCurrent(a,LocalDate.now());
                a.remainingDecre();
//                System.out.println("Borrow successfully!");
                PublicationParser.getInstance().createXML();
                return true;
            }
            else if(a.getIsbn().equals(isbn)&&a.getShares().getRemaining() == 0){
                if(!borrower.getBorrowingInfo().getCurrentitemsborrowlog().containsKey(a)){
                borrower.addItemtoReservation(a,LocalDate.now());
                PublicationParser.getInstance().createXML();}
                else throw new AlreadyLoanException();
            }
        }
        throw new BorrowUnsuccessfulException("Borrow unsuccessful.");
    }
    //MODIFIES:this
    //EFFECTS: add a book into the library
    @Override
    public boolean addItem(String Name, String isbn, String authorName, int multi, boolean isBook, LocalDate bookimportdate) throws Exception {
            Publication newItem;
            if(isBook) {
                newItem = new Book(Name, isbn, authorName, multi, bookimportdate);
                saveItemLog(Name, isbn, authorName, multi, bookimportdate,"booklog.txt");
            }
            else {
                newItem = new CD(Name, isbn, authorName, multi,bookimportdate);
                saveItemLog(Name, isbn, authorName, multi, bookimportdate, "cdlog.txt");
            }
            if (PublicationItems.getPublicationHashMap().containsKey(Name)) {
                boolean dual = false;
                for (Publication a : PublicationItems.getPublicationHashMap().get(Name)) {
                    if (a.equals(newItem)) {
                        a.multiplicityIncre(multi);
                        dual = true;
                        if(a instanceof Book) {
                            LibraryFrontCenter.bookcount += multi;
                            System.out.println("Book added successfully.");
                        }
                        if(a instanceof CD){
                            LibraryFrontCenter.cdcount+=multi;
                            System.out.println("CD added succesfully.");
                        }
                        break;
                    }
                }
                if (!dual) {
                    PublicationItems.getPublicationHashMap().get(Name).add(newItem);
                }
            }else {
                ArrayList<Publication> newbookarraylist = new ArrayList<>();
                newbookarraylist.add(newItem);
                PublicationItems.getPublicationHashMap().put(Name, newbookarraylist);
            }
            PublicationParser.getInstance().createXML();
            return true;
    }


    public void saveItemLog(String bkn, String isbn, String author, int multiplicity, LocalDate bookimportdate, String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));;
        PrintWriter bkwriter = new PrintWriter(path,"UTF-8");
        String multi=Integer.toString(multiplicity);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = bookimportdate.format(formatter);
        lines.add(bkn+","+isbn+","+author+","+multi+","+formattedString);
        for(String line:lines){
            bkwriter.println(line);
        }
        bkwriter.close();
    }

    public void returnBook(Borrower b, String returnbookname) throws RemainingExceedMultiException, MessagingException {
        Publication returnedBook = b.getBorrowingInfo().getCurrentitemsborrowlog().get(returnbookname).getFirst();
        returnedBook.remainingIncre();
        returnedBook.getBorrowRegistrationCard().getBorrowlog().remove(b);
        b.getBorrowingInfo().getHistoryitems().put(returnedBook.getIsbn(),new PublicationLocalDateTuple<>(returnedBook,LocalDate.now()));
        if(returnedBook.getShares().getRemaining()>=1){
            returnedBook.getReserveRegistrationCard().notifyReservers(returnedBook.getName(),returnedBook.getIsbn(),returnedBook.getShares().getRemaining());
        }
    }


}
