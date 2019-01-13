package model.publication;

import model.Borrower.Borrower;
import observer.ItemReserver;

import java.util.ArrayList;

public class Book extends Publication {
    /*
    I tried to add constant member in this class because I think in the future, I can display the shape of
    book instances in some UI.
    So in this case, the width of books are set as 700 pixels, maybe all books will be displayed in part of the UI,
    say, like, class DisplayShelf.
    */
    public static final int WIDTH = 700;
    public static final int HEIGHT = 1000;

    /*
    So, in the field part, I listed some parts of the attributes that a book may have, such as Name, Isbn, AuthorName
    total number of pages of a book instance(denoted by Pagenum), boolean to denote if the book is lended.
    */
    private int Pagenum;

    @Override
    public void addCurrentBorrower(Borrower currentborrower) throws Exception {
        if(!libraryCard.currentborrowers.contains(currentborrower)){
            libraryCard.currentborrowers.add(currentborrower);
            if(!currentborrower.getBorrowingInfo().getCurrentitems().contains(this)){
                currentborrower.addItemtoCurrent(this);
            }
        }
    }

    //  In addition, I tried to add a static field into this class, it stands for the total books that the library possesses.
    // Constructors overloading
    public Book() {
       super();
    }

    public Book(String name) {
        super(name);
        isbook=true;
    }

    public Book(String name, String isbn, String authorName, int multiplicity) {
        super(name,isbn,authorName,multiplicity);
        isbook=true;

    }

    public Book(String name, String isbn, String authorname, SharesInfo shares, ArrayList<Borrower> currentborrower, ArrayList<ItemReserver> reservers) {
        super(name, isbn, authorname, shares, currentborrower, reservers);
        isbook=true;

    }

    public Book(String name, String isbn, String authorname, SharesInfo shares) {
        super(name, isbn, authorname, shares);
        isbook=true;

    }
}
