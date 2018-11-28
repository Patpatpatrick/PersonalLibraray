package ui.adminui;

import ui.Frames.OperationChoiceUI;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class AdminOperationUI extends JFrame {
    private OperationChoiceUI operationChoiceUI;
    public AdminOperationUI(){
        operationChoiceUI = new OperationChoiceUI("Add a new publication!","Manage publication records","Manage borrower records.");
        eventSources();
    }
    private void eventSources() {
        operationChoiceUI.getAdditembtn().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AddItemUI();
            }
        });
        operationChoiceUI.getPublicationinfo().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ManagePublicationPanel();
            }
        });
        operationChoiceUI.getBorrowerinfo().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ManageBorrowerUI();
            }
        });
    }


    public static void main(String[] args) {
        new AdminOperationUI();
    }
}
