package ui.Frames.resulttables;

import exception.publicationexception.NonExistException;
import model.Borrower.Borrower;
import model.publication.Publication;
import model.publication.PublicationItems;
import ui.Frames.detailpanel.ItemDetailPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class ItemResultTablePanel extends JPanel {
    private JPanel panelfortable=new JPanel();
    private JTable table=new JTable();
    private ItemDetailPanel itemDetailPanel;
    private String[] columns;


    public ItemResultTablePanel(String [] columnTitles) {
        setLayout(new BorderLayout());
        columns=columnTitles;
        add(panelfortable,BorderLayout.NORTH);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panelfortable.add(table);

        JPanel p=new JPanel();
        itemDetailPanel=new ItemDetailPanel();
        p.add(itemDetailPanel);
        add(p,BorderLayout.CENTER);
        eventSources();
        setVisible(true);
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
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(800,400));

        panelfortable.add(pane);
        Iterator iter = PublicationItems.getPublicationHashMap().entrySet().iterator();
        int i = 1;
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            ArrayList<Publication> itemlist = (ArrayList<Publication>) entry.getValue();
            if(itemlist.size()!=0) {
                for (Publication a : itemlist) {
                    if (a.getCurrentborrowers().size() != 0) {
                        for (Borrower c : a.getCurrentborrowers()) {
                            Vector<String> vector = new Vector<String>();
                            vector.add(Integer.toString(i));
                            vector.add(a.getName());
                            vector.add(a.getIsbn());
                            vector.add(a.getAuthorName());
                            vector.add(Integer.toString(a.getShares().getMultiplicity()));
                            vector.add(Integer.toString(a.getShares().getRemaining()));
                            vector.add(c.getNameofbrwr());
                            model.addRow(vector);
                            i++;
                        }
                    } else {
                        Vector<String> vector = new Vector<String>();
                        vector.add(Integer.toString(i));
                        vector.add(a.getName());
                        vector.add(a.getIsbn());
                        vector.add(a.getAuthorName());
                        vector.add(Integer.toString(a.getShares().getMultiplicity()));
                        vector.add(Integer.toString(a.getShares().getRemaining()));
                        vector.add("No borrower");
                        model.addRow(vector);
                        i++;
                    }
                }
            }
        }

        revalidate();
        setVisible(true);
    }

    public void showItemsInTable(String s) throws NonExistException {
        panelfortable.removeAll();
        DefaultTableModel model=new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(800,400));
        panelfortable.add(pane);

        ArrayList<Publication> b = PublicationItems.getPublicationHashMap().get(s);
        if(b==null){
            throw new NonExistException();
        }
        if(b.size()!=0) {
            int i = 1;
            for (Publication a : b) {
                if (a.getCurrentborrowers().size() != 0) {
                    for (Borrower c : a.getCurrentborrowers()) {
                        Vector<String> vector = new Vector<String>();
                        vector.add(Integer.toString(i));
                        vector.add(s);
                        vector.add(a.getIsbn());
                        vector.add(a.getAuthorName());
                        vector.add(Integer.toString(a.getShares().getMultiplicity()));
                        vector.add(Integer.toString(a.getShares().getRemaining()));
                        vector.add(c.getNameofbrwr());
                        model.addRow(vector);
                        i++;
                    }
                } else {
                    Vector<String> vector = new Vector<String>();
                    vector.add(Integer.toString(i));
                    vector.add(s);
                    vector.add(a.getIsbn());
                    vector.add(a.getAuthorName());
                    vector.add(Integer.toString(a.getShares().getMultiplicity()));
                    vector.add(Integer.toString(a.getShares().getRemaining()));
                    vector.add("No borrower");
                    model.addRow(vector);
                    i++;
                }
            }
        }
        revalidate();
        setVisible(true);
    }

    public JTable getTable() {
        return table;
    }
}