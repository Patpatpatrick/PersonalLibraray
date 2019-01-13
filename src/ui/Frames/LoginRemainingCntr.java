package ui.Frames;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginRemainingCntr extends JPanel implements Serializable {
    private List<InvalidationListener> LoginUIs = new ArrayList<>();
    public JLabel remainingattemptnotice;
    public int remainingattempts = 3;

    public LoginRemainingCntr() {
    }
    public void AttemptsFailureResponse() {
        remainingattempts--;
        remainingattemptnotice.setText("Wrong! You have "+remainingattempts+" attempts remaining.");
        if(remainingattempts==0) {
            remainingattemptnotice.setText("Quit");
        }
    }
}