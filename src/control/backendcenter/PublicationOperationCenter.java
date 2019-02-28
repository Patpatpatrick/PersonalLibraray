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

    //MODIFIES: this, Borrower
    //EFFECTS: Lend a book that matches the name that a verified borrower claims,
    //         otherwise do nothing.
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
                // saveItemLog(Name, isbn, authorName, multi, bookimportdate,"booklog.txt");
            }
            else {
                newItem = new CD(Name, isbn, authorName, multi,bookimportdate);
                // saveItemLog(Name, isbn, authorName, multi, bookimportdate, "cdlog.txt");
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
