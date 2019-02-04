package control.backendcenter;

import model.Borrower.Borrower;
import model.publication.Publication;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailCenter {

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;
    private static EmailCenter EmailCenterinstance = new EmailCenter();

    private EmailCenter(){
    }

//    public static void main(String args[]) throws AddressException,
//            MessagingException {
//
//        EmailCenter emailCenter = new EmailCenter();
//
//        emailCenter.setMailServerProperties();
//        emailCenter.createEmailMessageToReservers();
//        emailCenter.sendEmail();
//    }
    public static EmailCenter getInstance() {
    return EmailCenterinstance;
}

    public void setMailServerProperties() {

        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessageToReservers(String toEmails,String username,String bookname,String isbn,int remaining) throws AddressException,
            MessagingException {
        String emailSubject = "Reserved Item Now Available!" + username;
        String emailBody1 = "This is an email sent by our Library. Please note that one book that you reserved is now available!";
        String emailBody2 = bookname+" "+isbn+"is now available with "+remaining+" shares!";
        String emailBody3 = "You can go and get it now!!";
        StringBuffer buffer = new StringBuffer();
        buffer.append(emailBody1);
        buffer.append(emailBody2);
        buffer.append(emailBody3);
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails));
        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(buffer, "text/html");//for a html email
        //emailMessage.setText(emailBody);// for a text email

    }

    public void sendEmail() throws AddressException, MessagingException {

        String emailHost = "smtp.gmail.com";
        String fromUser = "nostalcturne";//just the id alone without @gmail.com
        String fromUserEmailPassword = "joehisaishiandyiruma";
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }

}