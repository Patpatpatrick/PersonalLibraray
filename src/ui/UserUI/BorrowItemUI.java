package ui.UserUI;

import control.backendcenter.PublicationOperationCenter;
import exception.borrowexception.AlreadyLoanException;
import exception.borrowexception.BorrowUnsuccessfulException;
import exception.borrowexception.ExceedMaxLoanCreditException;
import exception.borrowexception.NoDuplicateLoanException;
import model.Borrower.Borrower;
import ui.Frames.SearchByItemsPanel;
import ui.listeners.MouseActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BorrowItemUI extends JFrame{
    private SearchByItemsPanel searchpanel;
    private Borrower borrowerinoperation;
    private JButton confirm=new JButton("Confirm button");
    private String name;
    private String isbn;

    public BorrowItemUI(Borrower borrowerinoperation) {
        this.setLayout(new BorderLayout());
        this.borrowerinoperation=borrowerinoperation;
        searchpanel=new SearchByItemsPanel("Library","welcome!"+borrowerinoperation.getNameofbrwr(),"items");
        this.add(searchpanel);
        JPanel jp=new JPanel(new FlowLayout());
        jp.add(confirm);
        this.add(jp,BorderLayout.SOUTH);
        eventResources();
        setSize(1000,800);
        setVisible(true);
    }

    private void eventResources() {
        confirm.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                name= searchpanel.getItemResultTablePanel().getTable().getValueAt(searchpanel.getItemResultTablePanel().getTable().getSelectedRow(), 1).toString();
                isbn= searchpanel.getItemResultTablePanel().getTable().getValueAt(searchpanel.getItemResultTablePanel().getTable().getSelectedRow(), 2).toString();
                int remainshares= Integer.parseInt((String)(searchpanel.getItemResultTablePanel().getTable().getValueAt(searchpanel.getItemResultTablePanel().getTable().getSelectedRow(), 5)));

                try {
                    if(remainshares>0){
                        PublicationOperationCenter operationCenter=PublicationOperationCenter.getInstance();
                        operationCenter.borrowOrReserveItems(name,isbn,borrowerinoperation);
                        JOptionPane.showMessageDialog(null,
                                "borrow successfully!");

                    }
                    else
                        JOptionPane.showMessageDialog(null,
                                "reserve successfully!");

                }
                catch (NoDuplicateLoanException e1){
                    JOptionPane.showMessageDialog(null,
                            "No duplicate loan please.","warning",JOptionPane.WARNING_MESSAGE);

                }
                catch (ExceedMaxLoanCreditException e2){
                    JOptionPane.showMessageDialog(null,
                            "You are only allowed to borrow five books","warning",JOptionPane.WARNING_MESSAGE);

                }
                catch (BorrowUnsuccessfulException e3) {
                    JOptionPane.showMessageDialog(null,
                            "borrow failed!","warning",JOptionPane.WARNING_MESSAGE);

                }
                catch (AlreadyLoanException e4) {
                    JOptionPane.showMessageDialog(null,
                            "You have already borrow that book, you cannot make reservation now.","warning",JOptionPane.WARNING_MESSAGE);

                }
                catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new BorrowItemUI(new Borrower("12sd","22"));
    }
}
