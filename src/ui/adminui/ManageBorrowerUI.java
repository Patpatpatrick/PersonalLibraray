package ui.adminui;

import ui.Frames.SearchByBorrowerPanel;

import javax.swing.*;
import java.awt.*;


public class ManageBorrowerUI extends JFrame{
    private SearchByBorrowerPanel manageUI;

    public ManageBorrowerUI() throws HeadlessException {
        super("Manage Borrowers");
        this.manageUI = new SearchByBorrowerPanel("Administrator!");
        add(manageUI);
        setSize(1000,800);
        setVisible(true);
    }

}