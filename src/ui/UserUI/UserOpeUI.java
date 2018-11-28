package ui.UserUI;

import model.Borrower.Borrower;
import ui.Frames.OperationChoiceUI;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class UserOpeUI extends JFrame {
    private Borrower borrowerinoperation;
    private OperationChoiceUI operationChoiceUI;

    public UserOpeUI(Borrower borrower) {
        borrowerinoperation=borrower;
        operationChoiceUI = new OperationChoiceUI("borrow items","display borrowed items","returnm items");
        eventSources();
    }
    private void eventSources() {
        //borrow a book
        operationChoiceUI.getAdditembtn().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new BorrowItemUI(borrowerinoperation);
            }
        });
        //display borrowed items
        operationChoiceUI.getPublicationinfo().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new DisplayBorrowedItemsUI(borrowerinoperation);
            }
        });
        //return items.
        operationChoiceUI.getBorrowerinfo().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ReturnItemsUI(borrowerinoperation);
            }
        });
    }
}
