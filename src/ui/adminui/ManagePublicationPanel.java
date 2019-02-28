package ui.adminui;

import ui.Frames.SearchByItemsPanel;

import javax.swing.*;

public class ManagePublicationPanel extends JFrame {

    private SearchByItemsPanel searchByItemsPanel;

    public ManagePublicationPanel(){
        super("Manage Borrowers");
        searchByItemsPanel = new SearchByItemsPanel("admin");
        add(searchByItemsPanel);
        setSize(1000,800);
        setVisible(true);
    }

}
