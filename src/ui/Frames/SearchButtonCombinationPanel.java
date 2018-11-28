package ui.Frames;

import javax.swing.*;
import java.awt.*;

public class SearchButtonCombinationPanel extends JPanel {
    private JLabel welcome;
    private JTextField text;
    private JButton goforsearchbtn;
    private JButton displayall;
    public SearchButtonCombinationPanel(String s) {
        super(new BorderLayout());
        setSize(500,100);
        makeBasicLayout(s);
        setVisible(true);
    }
    private void makeBasicLayout(String s) {
        welcome = new JLabel("welcome! "+s+" !");
        text = new JTextField(15);
        goforsearchbtn = new JButton("Search by name");
        displayall = new JButton("display all");
        this.add(welcome,BorderLayout.NORTH);
        JPanel panel=new JPanel(new FlowLayout());
        panel.add(text);
        panel.add(goforsearchbtn);
        panel.add(displayall);
        this.add(panel,BorderLayout.SOUTH);
    }
    public JTextField getTextfieldtext() {
        return text;
    }
    public JButton getGoforsearchbtn() {
        return goforsearchbtn;
    }
    public JButton getDisplayall() {
        return displayall;
    }

}