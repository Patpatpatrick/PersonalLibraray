package ui.UserUI;

import control.backendcenter.PublicationOperationCenter;
import control.backendcenter.OperationCenter;
import model.Borrower.BorrowerList;
//import ui.Frames.WarningUI;
import ui.Frames.LoginRemainingCntr;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class UserLoginUI extends JFrame {
    private JLabel entrancenotion;

    private LoginRemainingCntr loginRemainingCntr = new LoginRemainingCntr();

    private JLabel usernamelable;
    private JTextField usernametextfield;
    private JLabel passwordlable;
    private JPasswordField passwordtextfield;
    private JButton userloginbtn;

    private JLabel signup;
    private JTextField newusername;
    private JLabel newusernamelabel;
    private JPasswordField newpassword;
    private JLabel newpasswordlable;
    private JPasswordField confirmpassword;
    private JLabel confirmpasswordlabel;
    private JLabel email;
    private JTextField newuseremail;
    private JButton signupbtn;

    private void makeBasicLayout(){
        JPanel jPanel=new JPanel(new GridLayout(2,1));
        JPanel j1=new JPanel(new FlowLayout());
        JPanel j11=new JPanel(new GridLayout(2,2));
        JPanel j22=new JPanel(new GridLayout(3,2));
        JPanel j2=new JPanel(new FlowLayout());

        j1.add(entrancenotion);
        j11.add(usernamelable);
        j11.add(usernametextfield);
        j11.add(passwordlable);
        j11.add(passwordtextfield);
        j1.add(j11);
        j1.add(userloginbtn);
        j1.add(loginRemainingCntr.remainingattemptnotice);

        j2.add(signup);
        j22.add(newusernamelabel);
        j22.add(newusername);
        j22.add(newpasswordlable);
        j22.add(newpassword);
        j22.add(confirmpasswordlabel);
        j22.add(confirmpassword);
        j22.add(email);
        j22.add(newuseremail);
        j2.add(j22);
        j2.add(signupbtn);

        jPanel.add(j1);
        jPanel.add(j2);
        this.add(jPanel);
    }

    private void eventSources(){
        userloginbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String loginpassword=new String(passwordtextfield.getPassword());
                String username=usernametextfield.getText();
                if(username.equals("")||loginpassword.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "No blank please.","warning",JOptionPane.WARNING_MESSAGE);

                }
                else{
                    if(BorrowerList.getBorrowerHashMap().containsKey(username)&&
                            BorrowerList.getBorrowerHashMap().get(username).getPassword().equals(loginpassword)) {
                        JOptionPane.showMessageDialog(null,
                                "Login successfully! Welcome back "+username+ "!");
                        new UserOpeUI(BorrowerList.getBorrowerHashMap().get(username));
                        dispose();
                    }
                    else{
                        loginRemainingCntr.AttemptsFailureResponse();
                        if(loginRemainingCntr.remainingattempts==0){
                            dispose();
                        }
                    }
                }
            }
        });
        signupbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String signuppassword=new String(newpassword.getPassword());
                String confirmsignuppassword=new String(confirmpassword.getPassword());
                String enteredusername=newusername.getText();
                String emailaddress=newuseremail.getText();

                if(enteredusername.equals("")||signuppassword.equals("")||confirmsignuppassword.equals(""))
                    JOptionPane.showMessageDialog(null,
                            "No blank please.","warning",JOptionPane.WARNING_MESSAGE);
                else if(BorrowerList.getBorrowerHashMap().containsKey(enteredusername))
                    JOptionPane.showMessageDialog(null,
                            "Username already used","warning",JOptionPane.WARNING_MESSAGE);

                else if(!signuppassword.equals(confirmsignuppassword))
                    JOptionPane.showMessageDialog(null,
                            "Your passwords input are not the same","warning",JOptionPane.WARNING_MESSAGE);
                else{
                    OperationCenter operationCenter=PublicationOperationCenter.getInstance();
                    try {
                        operationCenter.addBorrower(enteredusername,signuppassword,emailaddress);
                        new UserOpeUI(BorrowerList.getBorrowerHashMap().get(enteredusername));
                        dispose();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });
    }



    public UserLoginUI(){
        this.setTitle("Enter Username and Password");
        this.setLayout(new BorderLayout());
        passwordtextfield = new JPasswordField(10);
        usernametextfield = new JTextField(10);
        usernamelable = new JLabel("Username");
        loginRemainingCntr.remainingattemptnotice = new JLabel();
        entrancenotion = new JLabel("To login, you need to input username and corresponding password in 3 times");
        passwordlable = new JLabel("Password");
        userloginbtn = new JButton("UserLogin");
        signup = new JLabel("Create your new account for the personal library!");
        newusername = new JTextField(10);
        newusernamelabel = new JLabel("Input Username");
        newpassword = new JPasswordField(10);
        newpasswordlable = new JLabel("Input Password");
        confirmpassword = new JPasswordField(10);
        confirmpasswordlabel = new JLabel("Confirm you password");
        email = new JLabel("Please input your email");
        newuseremail = new JTextField(20);
        signupbtn = new JButton("SignUP");
        makeBasicLayout();
        eventSources();
        setSize(500,500);
        setLocation(100,100);
        setVisible(true);
    }
}
