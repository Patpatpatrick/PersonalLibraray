package control.frontcenter;

import exception.borrowexception.ExceedMaxLoanCreditException;
import exception.borrowexception.NoDuplicateLoanException;
import model.Borrower.Borrower;
import model.Borrower.BorrowerList;
import model.publication.*;
import observer.ItemReserver;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

// Handler for XML publication parsing
public class PublicationHandler extends DefaultHandler {
    private StringBuilder accumulator;

    private Boolean isbook;

    private String name;
    private String isbn;
    private String authorname;

    private int multiplicity;
    private int remaining;

    //  private String currentborrowername;
//  private String reservername;
    private ArrayList<ItemReserver> reservers=new ArrayList<>();
    private ArrayList<Borrower> borrowers=new ArrayList<>();

    // EFFECTS: constructs publication handler for XML parser
    public PublicationHandler( ) {
        accumulator = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        System.out.println("StartElement: " + qName);

        if (qName.equals("publication")) {
            name = null;
            isbn = null;
            multiplicity = 0;
            remaining = 0; // if the apparant type of multiplicity & remaining is double (a primitive type), then null cannot be assigned to them
            authorname = null;
            reservers = new ArrayList<>();
            borrowers=new ArrayList<>();// after this initialization statement, the services is not null, but its content is null
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // System.out.println("now is endelement"+qName);
        super.endElement(uri, localName, qName);
        String data = accumulator.toString().trim();
        accumulator.setLength(0);                      // clear accumulator
        switch (qName) {
            case "isbook":
                isbook=Boolean.parseBoolean(data);
            case "name":
                name = data;
                break;
            case "isbn":
                isbn = data;
                break;
            case "authorname":
                authorname = data;
                break;
            case "multiplicity":
                multiplicity = Integer.parseInt(data);
                // NumberFormatException can be thrown from the Double.parseDouble(), but since it is an unchecked exception,
                // it doesn't have to be caught or declared thrown.
                // If a NumberFormatException is thrown, the pase() will catch it and throw a resourceParsingException
                break;
            case "remaining":
                remaining = Integer.parseInt(data);
                break;
            case "currentborrowername":
                borrowers.add(BorrowerList.getBorrowerHashMap().get(data));
                break;
            case "returndate":

            case "reservername":
                reservers.add(BorrowerList.getBorrowerHashMap().get(data));
                break;
//            case "service":
//                services.add(string2Service(data));
//                break;
            case "item":
                SharesInfo sharesinfo =new SharesInfo(multiplicity,remaining);
                Publication publication;
                if(isbook){
                    publication =new Book(name,isbn,authorname, sharesinfo);
                }
                else
                    publication =new CD(name,isbn,authorname, sharesinfo);
                if(borrowers!=null) {
                    for (Borrower b : borrowers) {
                        try {
                            publication.getBorrowRegistrationCard().getBorrowlog().put(b);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    borrowers=new ArrayList<>();
                }
                if(reservers!=null) {
                    for (ItemReserver b : reservers) {
                        publication.addReserver(b);
                    }
                    reservers=new ArrayList<>();
                }
                if (!isAnyFieldOfResourceMissing()){
                    if (PublicationItems.getPublicationHashMap().containsKey(name)) {
                        boolean dual = false;
                        for (Publication a : PublicationItems.getPublicationHashMap().get(name)) {
                            if (a.equals(publication)) {
                                a.multiplicityIncre(multiplicity);
                                dual = true;
                                if(isbook) {
                                    LibraryFrontCenter.bookcount += multiplicity;
                                    System.out.println("Book added successfully.");
                                    //saveItemLog(Name, isbn, authorName, multi,"booklog.txt");
                                }
                                if(!isbook){
                                    LibraryFrontCenter.cdcount+=multiplicity;
                                    System.out.println("CD added succesfully.");
                                    //saveItemLog(Name, isbn, authorName, multi,"cdlog.txt");
                                }
                                break;
                            }
                        }
                        if (!dual) {
                            PublicationItems.getPublicationHashMap().get(name).add(publication);
                            LibraryFrontCenter.cdcount+=multiplicity;
                        }
                    }else {
                        ArrayList<Publication> newbookarraylist = new ArrayList<>();
                        newbookarraylist.add(publication);
                        PublicationItems.getPublicationHashMap().put(name, newbookarraylist);
                        LibraryFrontCenter.cdcount+=multiplicity;
                    }

                }
                System.out.println("Finish parsing a publication");
                break;
            default:
                return;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        accumulator.append(ch, start, length);
    }


    private boolean isAnyFieldOfResourceMissing() {
        return name==null|| isbn ==null|| multiplicity ==0|| authorname ==null;
    }
}