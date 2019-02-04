package ui.Frames.resulttables;

import model.Borrower.Borrower;
import model.Borrower.PublicationLocalDateTuple;
import model.publication.Publication;
import model.publication.PublicationItems;
import ui.Frames.detailpanel.ItemDetailPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class BorrowedItemResultPanel extends JPanel {
    private JPanel panelfortable=new JPanel();
    private JTable table=new JTable();
    private ItemDetailPanel itemDetailPanel;
    private String[] columns;
    private Borrower borrowerinoprtn;


    public BorrowedItemResultPanel(String [] columnTitles,Borrower borrower) {
        setLayout(new BorderLayout());
        borrowerinoprtn=borrower;
        columns=columnTitles;

        JLabel label=new JLabel("Below are the items that you have borrowed");
        JPanel panel=new JPanel();
        panel.add(label);
        add(panel,BorderLayout.NORTH);
        add(panelfortable,BorderLayout.CENTER);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panelfortable.add(table);

        JPanel p=new JPanel();
        itemDetailPanel=new ItemDetailPanel();
        p.add(itemDetailPanel);
        add(p,BorderLayout.SOUTH);
        eventSources();
        setVisible(true);
        //f
    }

    private void eventSources() {
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(table.getSelectedRow()>-1) {
                String name = table.getValueAt(table.getSelectedRow(), 1).toString();
                String isbn = table.getValueAt(table.getSelectedRow(), 2).toString();
                ArrayList<Publication> a = PublicationItems.getPublicationHashMap().get(name);
                for (Publication p : a) {
                    if (p.getIsbn().equals(isbn)) {
                        chooseSelectedRowtoOperate(p);
                        break;
                    }
                }
            }
        });
    }

    private void chooseSelectedRowtoOperate(Publication selectedone) {
        itemDetailPanel.updateItemDetailPanel(selectedone);
        //this.add(itemDetailPanel);
        setVisible(true);
    }

    public void showItemsInTable() {
        panelfortable.removeAll();
        DefaultTableModel model=new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane=new JScrollPane(table);
        pane.setPreferredSize(new Dimension(800,400));
        panelfortable.add(pane);
        HashMap<String,PublicationLocalDateTuple<Publication,LocalDate>> itemlist=borrowerinoprtn.getBorrowingInfo().getCurrentitemsborrowlog();
        Iterator iter = itemlist.entrySet().iterator();
        int i = 1;
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            PublicationLocalDateTuple<Publication,LocalDate> itemtuple = (PublicationLocalDateTuple<Publication,LocalDate>) entry.getValue();
            Publication a = itemtuple.getFirst();
            LocalDate date = itemtuple.getSecond();
            Vector<String> vector = new Vector<String>();
            vector.add(Integer.toString(i));
            vector.add(a.getName());
            vector.add(a.getIsbn());
            vector.add(a.getAuthorName());
            vector.add(Integer.toString(a.getShares().getMultiplicity()));
            vector.add(Integer.toString(a.getShares().getRemaining()));
            vector.add(borrowerinoprtn.getNameofbrwr());
            vector.add(date.toString());
            model.addRow(vector);
            i++;
        }
        revalidate();
        setVisible(true);
    }
    public JTable getTable() {
        return table;
    }
}