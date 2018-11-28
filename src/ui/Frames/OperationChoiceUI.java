package ui.Frames;

import javax.swing.*;
import java.awt.*;

public class OperationChoiceUI extends JFrame {
    JButton additembtn;//borrowbookforuser
    JButton publicationinfo;//displayborrowedbook
    JButton borrowerinfo;//returnbook

    public OperationChoiceUI(String s1,String s2,String s3) {
        super("Personal Library");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        makeBasicLayout( s1, s2, s3);
        setSize(300,200);
        setLocation(700,100);
        setVisible(true);
    }


    private void makeBasicLayout(String s1,String s2,String s3) {
        Container cp = getContentPane();
        additembtn = new JButton(s1);
        publicationinfo = new JButton(s2);
        borrowerinfo = new JButton(s3);
        cp.add(additembtn);
        cp.add(publicationinfo);
        cp.add(borrowerinfo);
    }

    public JButton getAdditembtn() {
        return additembtn;
    }

    public JButton getPublicationinfo() {
        return publicationinfo;
    }

    public JButton getBorrowerinfo() {
        return borrowerinfo;
    }
}