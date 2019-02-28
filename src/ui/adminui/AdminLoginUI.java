package ui.adminui;

import ui.Frames.LoginRemainingCntr;
import ui.listeners.KeyboardActionListener;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class AdminLoginUI extends JFrame {
    private static final String password = "admin";
    private final LoginRemainingCntr loginRemainingCntr = new LoginRemainingCntr();
    private JLabel entrancenotion =new JLabel("Welcome! You need to input admin password in 3 times");
    private JPasswordField inputtextfield=new JPasswordField(15);
    private JButton adminbtn=new JButton("AdminLogin");

    private void makeBasicLayout(){
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());   // The content-pane sets its layout
        loginRemainingCntr.remainingattemptnotice = new JLabel("You have " + loginRemainingCntr.remainingattempts + " attempts remaining.");
        cp.add(entrancenotion);
        cp.add(inputtextfield);
        cp.add(adminbtn);
        cp.add(loginRemainingCntr.remainingattemptnotice);
    }


    private void eventSources(){
        adminbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verify();
            }
        });
        inputtextfield.addKeyListener(new KeyboardActionListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if(k == KeyEvent.VK_ENTER){
                    verify();
                }
            }
        });
    }

    private void verify() {
        if(isAdmin(new String(inputtextfield.getPassword()))){
            loginRemainingCntr.remainingattemptnotice.setText("Correct Password");
            new AdminOperationUI();
        }
        else{
            loginRemainingCntr.AttemptsFailureResponse();
            if(loginRemainingCntr.remainingattempts==0){
                dispose();
            }
        }
    }

    public AdminLoginUI(){
        this.setTitle("Enter Administrator Password");
        this.setLayout(new BorderLayout());
        makeBasicLayout();
        eventSources();
        setSize(500,200);
        setLocation(300,300);
        setVisible(true);
    }
    private boolean isAdmin(String adminpasswordreceiver) {
        return adminpasswordreceiver.equals(password);
    }
}
