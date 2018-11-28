package model.publication;

import exception.publicationexception.NegRemainingException;
import exception.publicationexception.RemainingExceedMultiException;

public class SharesInfo {//private boolean loanstatus;
    protected int multiplicity;
    protected int remaining;

    public SharesInfo(int multiplicity) {
        this.multiplicity=multiplicity;
        remaining=multiplicity;
    }

    public SharesInfo(int multiplicity, int remaining) {
        this.multiplicity=multiplicity;
        this.remaining=remaining;
    }

    public int getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public void multiplicityIncre(int k) {
        multiplicity += k;
        remaining += k;
    }

    public void remainingDecre() throws NegRemainingException {
        if (remaining > 0)
            remaining--;
        else
            throw new NegRemainingException("Impossible for negative remaining book.");
    }

    public void remainingIncre() throws RemainingExceedMultiException {
        if (multiplicity > remaining)
            remaining++;
        else
            throw new RemainingExceedMultiException("Remaining can't exceed multiplicity");
    }
}