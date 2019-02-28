package ui.Frames;

import exception.publicationexception.NonExistException;
import model.publication.PublicationItems;
import ui.Frames.resulttables.ItemResultTablePanel;
import ui.listeners.KeyboardActionListener;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SearchByItemsPanel extends JPanel {
    private SearchButtonCombinationPanel searchButtonCombinationPanel;
    private ItemResultTablePanel itemResultTablePanel;
    private static final String[] columnTitles = {
            "# record",
            "Name",
            "Isbn",
            "Author",
            "Multiplicity",
            "Remaining",
            "name of borrower"
    };

    public SearchByItemsPanel(String callingname) {
        this.setLayout(new BorderLayout());
        searchButtonCombinationPanel = new SearchButtonCombinationPanel(callingname);
        itemResultTablePanel = new ItemResultTablePanel(columnTitles);
        this.add(searchButtonCombinationPanel,BorderLayout.NORTH);
        this.add(itemResultTablePanel,BorderLayout.CENTER);
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
                    if (PublicationItems.getPublicationHashMap().containsKey(searchButtonCombinationPanel.getTextfieldtext().getText())) {
                        try {
                            itemResultTablePanel.showItemsInTable(searchButtonCombinationPanel.getTextfieldtext().getText());
                        } catch (NonExistException e1) {
                            JOptionPane.showMessageDialog(null,
                                    "No such item!","warning",JOptionPane.WARNING_MESSAGE);

                        }
                    } else
                        JOptionPane.showMessageDialog(null,
                                "No such item!","warning",JOptionPane.WARNING_MESSAGE);

                }
            }
        });

        searchButtonCombinationPanel.getGoforsearchbtn().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (PublicationItems.getPublicationHashMap().containsKey(searchButtonCombinationPanel.getTextfieldtext().getText())) {
                    try {
                        itemResultTablePanel.showItemsInTable(searchButtonCombinationPanel.getTextfieldtext().getText());
                    } catch (NonExistException e1) {
                        JOptionPane.showMessageDialog(null,
                                "No such item!","warning",JOptionPane.WARNING_MESSAGE);

                    }
                } else

                    JOptionPane.showMessageDialog(null,
                            "No such item!","warning",JOptionPane.WARNING_MESSAGE);
            }
        });

        searchButtonCombinationPanel.getDisplayall().addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                itemResultTablePanel.showItemsInTable();
            }
        });
    }

    public ItemResultTablePanel getItemResultTablePanel() {
        return itemResultTablePanel;
    }

}