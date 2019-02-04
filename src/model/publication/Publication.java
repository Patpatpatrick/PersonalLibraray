package model.publication;

import exception.publicationexception.NegRemainingException;
import exception.publicationexception.RemainingExceedMultiException;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Publication {



    private ReserveRegistrationCard reserveRegistrationCard;
    private BorrowRegistrationCard borrowRegistrationCard;
    private String name, isbn, authorname;
    private SharesInfo shares;

    protected boolean isbook;

    Publication() {
        name ="Unknown";
        isbn ="Unknown";
        shares =new SharesInfo(1);
        borrowRegistrationCard = new BorrowRegistrationCard(LocalDate.now());
        reserveRegistrationCard = new ReserveRegistrationCard();
    }

    Publication(String name) {
        this.name = name;
        shares =new SharesInfo(1);
        borrowRegistrationCard = new BorrowRegistrationCard(LocalDate.now());
    }

    Publication(String name, String isbn, String authorName, int multiplicity) {
        this.name = name;
        this.isbn = isbn;
        authorname = authorName;
        shares =new SharesInfo(multiplicity);
        borrowRegistrationCard = new BorrowRegistrationCard(LocalDate.now());
    }


//    public Publication(String name, String isbn, String authorname, SharesInfo shares, ArrayList<Borrower> currentborrowers, ArrayList<ItemReserver> reservers) {
//        this.name = name;
//        this.isbn = isbn;
//        this.authorname = authorname;
//        this.shares = shares;
//        this.borrowRegistrationCard.currentborrowers = currentborrowers;
//        super.reservers=reservers;
//    }

    public Publication(String name, String isbn, String authorname, SharesInfo shares) {
        this.name = name;
        this.isbn = isbn;
        this.authorname = authorname;
        this.shares = shares;
        borrowRegistrationCard = new BorrowRegistrationCard(LocalDate.now());
    }

    Publication(String name, String isbn, String authorName, int multiplicity, LocalDate itemimportdate) {
        this.name = name;
        this.isbn = isbn;
        authorname = authorName;
        shares =new SharesInfo(multiplicity);
        borrowRegistrationCard = new BorrowRegistrationCard(itemimportdate);
    }

    public SharesInfo getShares() {
        return shares;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthorName() {
        return authorname;
    }

    public BorrowRegistrationCard getBorrowRegistrationCard() {
        return borrowRegistrationCard;
    }
    public ReserveRegistrationCard getReserveRegistrationCard() {
        return reserveRegistrationCard;
    }

    public boolean isIsbook() {
        return isbook;
    }

    public void setIsbook(boolean isbook) {
        this.isbook = isbook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(authorname, that.authorname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, isbn, authorname);
    }
    public void multiplicityIncre(int k){
        shares.multiplicityIncre(k);
    }
    public void remainingDecre() throws NegRemainingException {
        shares.remainingDecre();
    }
    public void remainingIncre() throws RemainingExceedMultiException {
        shares.remainingIncre();
    }

}
