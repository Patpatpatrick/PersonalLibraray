package ui.Frames;

import model.Borrower.Borrower;
import model.Borrower.BorrowerList;
import model.publication.Publication;
import model.publication.PublicationItems;
import ui.Frames.resulttables.BorrowerResultTablePanel;
import ui.Frames.resulttables.ItemResultTablePanel;
import ui.listeners.KeyboardActionListener;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class SearchByBorrowerPanel extends JPanel{
    private SearchButtonCombinationPanel searchButtonCombinationPanel;
    private BorrowerResultTablePanel borrowerResultTablePanel;
    private static final String[] columnTitles = {
            "# record",
            "Name",
            "name of borrowed book"
    };

    public SearchByBorrowerPanel( String callingname) {
        this.setLayout(new BorderLayout());
        searchButtonCombinationPanel = new SearchButtonCombinationPanel(callingname);
        borrowerResultTablePanel = new BorrowerResultTablePanel(columnTitles);
        this.add(searchButtonCombinationPanel,BorderLayout.NORTH);
        this.add(borrowerResultTablePanel,BorderLayout.CENTER);
        eventSources();
        setSize(500, 500);
        setLocation(100, 100);

        setVisible(true);
    }

    private void eventSources() {
        searchButtonCombinationPanel.getTextfieldtext().addKeyListener(new KeyboardActionListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if (k == KeyEvent.VK_ENTER) {
                    if (BorrowerList.getBorrowerHashMap().containsKey(searchButtonCombinationPanel.getTextfieldtext().getText())) {
                        borrowerResultTablePanel.showBorrowerInTable(searchButtonCombinationPanel.getTextfieldtext().getText());
                    } else
                        JOptionPane.showMessageDialog(null,
                                "No such user!","warning",JOptionPane.WARNING_MESSAGE);

                }
            }
        });

        searchButtonCombinationPanel.getGoforsearchbtn().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (BorrowerList.getBorrowerHashMap().containsKey(searchButtonCombinationPanel.getTextfieldtext().getText())) {
                    borrowerResultTablePanel.showBorrowerInTable(searchButtonCombinationPanel.getTextfieldtext().getText());
                } else
                    JOptionPane.showMessageDialog(null,
                            "No such item!","warning",JOptionPane.WARNING_MESSAGE);
            }
        });

        searchButtonCombinationPanel.getDisplayall().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                borrowerResultTablePanel.showBorrowerInTable();
            }
        });

    }

}
