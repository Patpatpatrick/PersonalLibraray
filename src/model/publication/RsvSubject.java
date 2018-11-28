package model.publication;

import observer.ItemReserver;

import java.util.ArrayList;
import java.util.List;

public abstract class RsvSubject {
    protected List<ItemReserver> reservers =new ArrayList<>();
    public void notifyReservers(String name, String isbn, int remaining){
        for(ItemReserver itemReserver: reservers){
            itemReserver.updateWith(name,isbn,remaining);
        }
    }

    public void addReserver(ItemReserver itemReserver){
        if(!reservers.contains(itemReserver)){
            reservers.add(itemReserver);
        }
    }

    public List<ItemReserver> getReservers() {
        return reservers;
    }
}
