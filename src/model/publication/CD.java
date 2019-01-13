package model.publication;

import model.Borrower.Borrower;
import observer.ItemReserver;

import java.util.ArrayList;

public class CD extends Publication {

    //polymorphism
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    @Override
    public void addCurrentBorrower(Borrower currentborrower) throws Exception {
        if(!this.libraryCard.currentborrowers.contains(currentborrower)){
            this.libraryCard.currentborrowers.add(currentborrower);
            if(!currentborrower.getBorrowingInfo().getCurrentitems().contains(this)){
                currentborrower.addItemtoCurrent(this);
            }
        }
    }

    public CD() {
        super();        isbook=false;

    }

    public CD(String name) {
        super(name);isbook=false;
    }

    public CD(String name, String isbn, String authorName,int multiplicity) {
       super(name,isbn,authorName,multiplicity);isbook=false;
    }

    public CD(String name, String isbn, String authorname, SharesInfo shares) {
        super(name, isbn, authorname, shares);isbook=false;
    }

    public CD(String name, String isbn, String authorname, SharesInfo shares, ArrayList<Borrower> currentborrower, ArrayList<ItemReserver> reservers) {
        super(name, isbn, authorname, shares, currentborrower, reservers);isbook=false;
    }
}
