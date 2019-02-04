package ui.UserUI;

import control.backendcenter.PublicationOperationCenter;
import exception.publicationexception.RemainingExceedMultiException;
import model.Borrower.Borrower;
//import ui.Frames.WarningUI;
import ui.Frames.resulttables.BorrowedItemResultPanel;
import ui.Frames.resulttables.BorrowerResultTablePanel;
import ui.Frames.resulttables.ItemResultTablePanel;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ReturnItemsUI extends JFrame{
    private Borrower borrowerinoperation;
    private BorrowedItemResultPanel itemResultTablePanel;
    private JButton confirm=new JButton("Confirm button");
    private static final String[] columnTitles = {
            "# record",
            "Name",
            "Isbn",
            "Author"
    };
    ReturnItemsUI(Borrower borrowerinoperation) {
        this.setLayout(new BorderLayout());
        this.borrowerinoperation=borrowerinoperation;
        itemResultTablePanel=new BorrowedItemResultPanel(columnTitles,borrowerinoperation);

        this.add(itemResultTablePanel,BorderLayout.NORTH);
        itemResultTablePanel.showItemsInTable();
        this.add(confirm,BorderLayout.SOUTH);
        eventSources();
        setSize(1000,790);
        setLocation(300,200);
        setVisible(true);
    }

    private void eventSources() {
        confirm.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String returnedbookname= (String)itemResultTablePanel.getTable().getValueAt(itemResultTablePanel.getTable().getSelectedRow(), 1);
                    String returnedbookisbn= (String)itemResultTablePanel.getTable().getValueAt(itemResultTablePanel.getTable().getSelectedRow(), 2);
                    PublicationOperationCenter.getInstance().returnBook(borrowerinoperation,returnedbookname);
                    JOptionPane.showMessageDialog(null, "Return Successfully");
                    itemResultTablePanel.showItemsInTable();
                }
                catch (RemainingExceedMultiException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
