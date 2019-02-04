package observer;

import javax.mail.MessagingException;

public interface ItemReserver {
    void updateWith(String name,String isbn,int remaining) throws MessagingException;
}
