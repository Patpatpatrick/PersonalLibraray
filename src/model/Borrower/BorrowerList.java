package model.Borrower;
import model.Borrower.Borrower;

import java.util.HashMap;

public class BorrowerList {
    private static HashMap<String,Borrower> borrowerHashMap=new HashMap<>();
    public static HashMap<String, Borrower> getBorrowerHashMap() {
        return borrowerHashMap;
    }
}
