package ui.adminui;

import control.backendcenter.PublicationOperationCenter;
import ui.Frames.CalendarPanel;
//import ui.Frames.WarningUI;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;

public class AddItemUI extends JFrame{

    private JLabel itemnm =new JLabel("Input name");
    private JTextField itemnmtext =new JTextField(20);
    private JLabel isbn =new JLabel("Input isbn");
    private JTextField isbntext =new JTextField(20);
    private JLabel author =new JLabel("Input author name");
    private JTextField authortext =new JTextField(20);
    private JLabel multiplicity =new JLabel("Input identical shares of this item");
    private JTextField multitextfield =new JTextField(20);

    private CalendarPanel calendarui;
    private JRadioButton bkbtn=new JRadioButton("Book",true);
    private JRadioButton cdbtn=new JRadioButton("CD");

    private JButton addbtn=new JButton("Add New Item");


    public AddItemUI(){
        this.setLayout(new FlowLayout());
        addBookLayout();
        eventSources();
        setSize(560,500);
        setLocation(300,300);
        setVisible(true);
    }

    private void addBookLayout(){
        JPanel jPanel1,jPanel2,jPanel4;
        jPanel1=new JPanel(new GridLayout(4,2));
        jPanel2=new JPanel(new FlowLayout());
        jPanel4=new JPanel(new FlowLayout());

        jPanel1.add(itemnm);
        jPanel1.add(itemnmtext);
        jPanel1.add(isbn);
        jPanel1.add(isbntext);
        jPanel1.add(author);
        jPanel1.add(authortext);
        jPanel1.add(multiplicity);
        jPanel1.add(multitextfield);

        ButtonGroup bg = new ButtonGroup();
        bg.add(bkbtn);
        bg.add(cdbtn);

        jPanel2.add(bkbtn);
        jPanel2.add(cdbtn);
        calendarui=new CalendarPanel();
        jPanel4.add(addbtn);

        Container cp = getContentPane();
        cp.add(jPanel1);
        cp.add(jPanel2);
        cp.add(calendarui);
        cp.add(jPanel4);

        setVisible(true);
    }

    private void eventSources() {
        addbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(itemnmtext.getText().equals("")){
                    JOptionPane.showMessageDialog(null,
                            "You must at least input a name to make a record.","warning",JOptionPane.WARNING_MESSAGE);
                }
                if(!bkbtn.isSelected()&&!cdbtn.isSelected()){
                    JOptionPane.showMessageDialog(null,
                            "You must at least select one box, either book or cd","warning",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    try {
                        int a = Integer.parseInt(multitextfield.getText());

                        if(a<=0){
                            throw new NumberFormatException();
                        }
                        LocalDate bookimportdate = (LocalDate) calendarui.getSelecteddate();
                        PublicationOperationCenter operationCenter = PublicationOperationCenter.getInstance();
                        if(operationCenter.addItem(itemnmtext.getText(), isbntext.getText(), authortext.getText(),a,bkbtn.isSelected(),bookimportdate
                        )){
                            JOptionPane.showMessageDialog(null, "added!");
                        }
                    } catch (NumberFormatException inputerror) {
                        JOptionPane.showMessageDialog(null,
                                "You should a positive integer e.g. 2 for the last box.","warning",JOptionPane.WARNING_MESSAGE);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
