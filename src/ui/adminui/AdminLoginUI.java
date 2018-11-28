package ui.adminui;

import ui.listeners.KeyboardActionListener;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AdminLoginUI extends JFrame {
    private static final String password = "admin";
    private JLabel entrancenotion =new JLabel("Welcome! You need to input admin password in 3 times");
    private JLabel remainingattemptnotice;
    private JPasswordField inputtextfield=new JPasswordField(15);
    private JButton adminbtn=new JButton("AdminLogin");
    private int remainingattempts=3;

    private void makeBasicLayout(){
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());   // The content-pane sets its layout
        remainingattemptnotice=new JLabel("You have "+remainingattempts+" attempts remaining.");
        cp.add(entrancenotion);
        cp.add(inputtextfield);
        cp.add(adminbtn);
        cp.add(remainingattemptnotice);
    }


    private void eventSources(){
        adminbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isAdmin(new String(inputtextfield.getPassword()))){
                    remainingattemptnotice.setText("Correct Password");
                    new AdminOperationUI();
                }
                else{
                    remainingattempts--;
                    remainingattemptnotice.setText("Wrong! You have "+remainingattempts+" attempts remaining.");
                    if(remainingattempts==0) {
                        remainingattemptnotice.setText("Quit");
                        dispose();
                    }
                }
            }
        });
        inputtextfield.addKeyListener(new KeyboardActionListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if(k == KeyEvent.VK_ENTER){
                    if(isAdmin(new String(inputtextfield.getPassword()))){
                        remainingattemptnotice.setText("Correct Password");
                        new AdminOperationUI();
                    }
                    else{
                        remainingattempts--;
                        remainingattemptnotice.setText("Wrong! You have "+remainingattempts+" attempts remaining.");
                        if(remainingattempts==0) {
                            remainingattemptnotice.setText("Quit");
                            dispose();
                        }
                    }
                }
            }
        });
    }

    public AdminLoginUI(){
        this.setTitle("Enter Administrator Password");
        this.setLayout(new BorderLayout());
        makeBasicLayout();
        eventSources();
        setSize(500,500);
        setLocation(300,300);
        setVisible(true);
    }
    private boolean isAdmin(String adminpasswordreceiver) {
        return adminpasswordreceiver.equals(password);
    }
}
