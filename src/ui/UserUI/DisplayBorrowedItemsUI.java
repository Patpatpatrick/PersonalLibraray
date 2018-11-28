package ui.UserUI;

import model.Borrower.Borrower;
import ui.Frames.resulttables.BorrowedItemResultPanel;

import javax.swing.*;
import java.awt.*;

public class DisplayBorrowedItemsUI extends JFrame{
    private BorrowedItemResultPanel panel;
    private static final String[] columnTitles = {
            "# record",
            "Name",
            "Isbn",
            "Author",
            "Multiplicity",
            "Remaining"
    };
    public DisplayBorrowedItemsUI(Borrower borrowerinoperation) {
        this.setLayout(new BorderLayout());
        panel=new BorrowedItemResultPanel(columnTitles,borrowerinoperation);
        setSize(1000,590);
        setLocation(300,200);
        add(panel);
        panel.showItemsInTable();
        setVisible(true);
    }
}
