package model.publication;

import observer.ItemReserver;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public abstract class RsvSubject {
    protected List<ItemReserver> reservers =new ArrayList<>();
    public abstract void notifyReservers(String name, String isbn, int remaining) throws MessagingException;

    public void addReserver(ItemReserver itemReserver){
        if(!reservers.contains(itemReserver)){
            reservers.add(itemReserver);
        }
    }

    public List<ItemReserver> getReservers() {
        return reservers;
    }
}
