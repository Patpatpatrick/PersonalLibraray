package ui.runlibraryui;

import exception.publicationexception.NonExistException;
import ui.Frames.SearchByItemsPanel;
import ui.Frames.resulttables.ItemResultTablePanel;

import javax.swing.*;

public class VisitorBrowsingPanel extends JFrame {
    private static final String[] columnTitles = {
            "# record",
            "Name",
            "Isbn",
            "Author",
            "Multiplicity",
            "Remaining",
            "name of borrower"
    };
    private ItemResultTablePanel itemResultTablePanel;
    public VisitorBrowsingPanel(String item) {
        itemResultTablePanel=new ItemResultTablePanel(columnTitles);
        try {
            itemResultTablePanel.showItemsInTable(item);
            add(itemResultTablePanel);
            setSize(900, 700);
            setLocation(300, 100);
            setVisible(true);
        } catch (NonExistException e) {
            JOptionPane.showMessageDialog(null,
                    "Sorry, we don't have this book/cd","warning",JOptionPane.WARNING_MESSAGE);
            setVisible(false);
        }
    }
}
