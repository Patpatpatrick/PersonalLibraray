//package control;
//
//import exception.*;
//import model.*;
//import control.frontcenter.LibraryFrontCenter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//
//public class CDOperationCenter extends OperationCenter{
//
//    public CDOperationCenter()  {
//    }
//
//    //MODIFIES:this
//    //EFFECTS: add a book into the library
//    public void addCD(String cdname, String isbn, String authorName, int multiplicity) throws IOException {
//        CD new_cd = new CD(cdname, isbn, authorName, multiplicity);
//        boolean dual = false;
//        if (CDGallery.getCdHashMap().containsKey(cdname)) {
//            for (CD a : CDGallery.getCdHashMap().get(cdname)) {
//                if (a.equals(new_cd)) {
//                    a.multiplicityIncre(multiplicity);
//                    LibraryFrontCenter.cdcount+=multiplicity;
//                    dual = true;
//                    break;
//                }
//            }
//            if (!dual) {
//                CDGallery.getCdHashMap().get(cdname).add(new_cd);
//                LibraryFrontCenter.cdcount+=multiplicity;
//            }
//        } else {
//            ArrayList<CD> cdArrayList = new ArrayList<>();
//            cdArrayList.add(new_cd);
//            CDGallery.getCdHashMap().put(cdname, cdArrayList);
//        }
//    }
//
//    //MODIFIES:this
//    //EFFECTS: add a book into the library
//    @Override
//    public void addPublication(String cdname) throws IOException {
//        CD new_book = new CD(cdname);
//        if (CDGallery.getCdHashMap().containsKey(cdname)) {
//            CDGallery.getCdHashMap().get(cdname).add(new_book);
//            LibraryFrontCenter.cdcount++;
//            System.out.println("CD with missing information added.");
//            saveCdLog(cdname,"NA","NA",1);
//        }
//        else {
//            ArrayList<CD> newcdarraylist = new ArrayList<>();
//            newcdarraylist.add(new_book);
//            CDGallery.getCdHashMap().put(cdname, newcdarraylist);
//        }
//    }
//
//    //REQUIRES: Name is not null, booklist is not null
//    //EFFECTS: Display specified books, if there is no match do nothing.
//    @Override
//    public void displayByName(String name) throws NonExistException {
//        if(PublicationItems.getPublicationHashMap().containsKey(name)){
//            int i=1;
//            System.out.println("Here is the information of the cd");
//            System.out.println("Number\tName\tISBN\tAuthorName\ttotalshare#\tremaining");
//            for(Book a: PublicationItems.getPublicationHashMap().get(name)){
//                System.out.println(i+"\t"+a.getName() + "\t" + a.getIsbn() + "\t" + a.getAuthorName() + "\t" + a.getMultiplicity()+ "\t" +a.getRemaining());
//                i++;
//            }
//        }
//        else throw new NonExistException("No such cd");
//    }
//    public void displayAvailableByName(String name) throws NonExistException {
//        if(CDGallery.getCdHashMap().containsKey(name)){
//            int i=1;
//            System.out.println("Here is the information of the cd still available");
//            System.out.println("Number\tName\tISBN\tAuthorName\ttotalshare#\tremaining");
//            for(CD a: CDGallery.getCdHashMap().get(name)){
//                if(a.getRemaining()>0) {
//                    System.out.println(i + "\t" + a.getName() + "\t" + a.getIsbn() + "\t" + a.getAuthorName() + "\t" + a.getMultiplicity() + "\t" + a.getRemaining());
//                    i++;
//                }
//            }
//        }
//        else throw new NonExistException("No such cd");
//    }
//    //REQUIRES: Booklist is not null
//    //EFFECTS: Display all books.
//    @Override
//    public void displayAll(){
//        System.out.println("There are ");
//        System.out.println("Name\tISBN\tAuthor\ttotalshares#\tremaining");
//        Iterator iter = CDGallery.getCdHashMap().entrySet().iterator();
//        while (iter.hasNext()) {
//            HashMap.Entry entry = (HashMap.Entry) iter.next();
//            ArrayList<CD> arrayList = (ArrayList<CD>) entry.getValue();
//            for(CD a:arrayList){
//                System.out.println(a.getName()+"\t"+a.getIsbn()+"\t"+a.getAuthorName()+"\t"+a.getMultiplicity()+"\t"+a.getRemaining());
//            }
//        }
//    }
//
//    //MODIFIES: this, Borrower
//    //EFFECTS: Lend a book that matches the name that a verified borrower claims,
//    //         otherwise do nothing.
//    @Override
//    public boolean borrowOrReserveItems(String cdName, String ISBN,Borrower borrower) throws NonExistException, NoSpareAvailable, NoDuplicateLoanException, ExceedMaxLoanCreditException, NegRemainingException {
//        displayAvailableByName(cdName);
//        for(CD a: CDGallery.getCdHashMap().get(cdName)) {
//            if (a.getRemaining() > 0) {
//                System.out.println("Here is a matched cd, and it is in our library now, now maybe we can lend it to you!");
//                borrower.addCDtoCurrent(a);
//                a.remainingDecre();
//                System.out.println("Borrow successfully!");
//                return true;
//            }
//        }
//        throw new NoSpareAvailable("There is no available one in our library, all loaned.");
//    }
//
//    public void ShowAllLoanedCDs(Borrower b){
//        System.out.println("Here are all cds you have loaned");
//        System.out.println("Number\tName\tISBN\tAuthor");
//        int i=1;
//        for(CD a:b.getCurrentCDs()){
//            System.out.println(i+a.getName()+"\t"+a.getIsbn()+"\t"+a.getAuthorName());
//            i++;
//        }
//    }
//    public void saveCdLog(String bkn, String isbn, String author,int multi) throws IOException {
//        List<String> lines = Files.readAllLines(Paths.get("cdlog.txt"));;
//        PrintWriter cdwriter = new PrintWriter("cdlog.txt","UTF-8");
//        lines.add(bkn+","+isbn+","+author+","+multi);
//        for(String line:lines){
//            cdwriter.println(line);
//        }
//        cdwriter.close();
//    }
//
//}
