package ui.Frames.detailpanel;

import model.publication.Publication;

import javax.swing.*;
import java.awt.*;

public class ItemDetailPanel extends JPanel{
    private JLabel name;
    private JLabel isbn;
    private JLabel author;
    private JLabel multiplicity;
    private JLabel remaining;
    private JLabel info;


    public void updateItemDetailPanel(Publication selectedone) {
        this.setLayout(new GridLayout(6,2));
        name.setText(selectedone.getName());
        isbn.setText(selectedone.getIsbn());
        author.setText(selectedone.getAuthorName());
        multiplicity.setText(Integer.toString(selectedone.getShares().getMultiplicity()));
        remaining.setText(selectedone.getShares().getRemaining()+"");
        if(selectedone.getShares().getRemaining()>0){
            info.setText("Now available in our library!");
        }
        else {
            info.setText("Please make a reserveItem!");
        }
        setVisible(true);
    }

    public ItemDetailPanel() {
        this.setLayout(new GridLayout(6,2));
        JLabel nameindicator = new JLabel("Name:");
        name=new JLabel("");
        JLabel isbnindicator = new JLabel("Isbn: ");
        isbn=new JLabel("");
        JLabel authorindicator = new JLabel("Author: ");
        author=new JLabel("");
        JLabel multiplicityindicator = new JLabel("Total shares in the library");
        multiplicity=new JLabel("");
        JLabel remainingindicator = new JLabel("Current shares remain availabe");
        remaining=new JLabel("");
        info=new JLabel("");

        add(nameindicator);
        add(name);
        add(isbnindicator);
        add(isbn);
        add(authorindicator);
        add(author);
        add(multiplicityindicator);
        add(multiplicity);
        add(remainingindicator);
        add(remaining);
        add(info);
        setVisible(false);
    }
}
