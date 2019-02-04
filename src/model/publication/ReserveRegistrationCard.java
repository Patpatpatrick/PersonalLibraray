package model.publication;

import model.Borrower.Borrower;
import observer.ItemReserver;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReserveRegistrationCard extends RsvSubject {

    public List<ItemReserver> getReservelog() {
        return reservers;
    }

    @Override
    public void notifyReservers(String name, String isbn, int remaining) throws MessagingException {
        for(ItemReserver itemReserver: reservers){
            itemReserver.updateWith(name,isbn,remaining);
        }
    }
}