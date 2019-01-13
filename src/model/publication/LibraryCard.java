package model.publication;

import model.Borrower.Borrower;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class LibraryCard {//TODO:use this field
    private LocalDate recordeddate;
    // TODO
    protected HashMap<String, ArrayList<Borrower>> currentborrowers = new HashMap<>();

    public LibraryCard(LocalDate recordeddate) {

    }
}