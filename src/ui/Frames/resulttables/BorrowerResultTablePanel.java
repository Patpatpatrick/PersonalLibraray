package ui.Frames.resulttables;

import model.Borrower.Borrower;
import model.Borrower.BorrowerList;
import model.publication.Publication;
import model.publication.PublicationItems;
import ui.Frames.detailpanel.ItemDetailPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class BorrowerResultTablePanel extends JPanel {
  //  private DefaultTableModel model=new DefaultTableModel();
    private JTable table=new JTable();
    private String[] columns;

    public BorrowerResultTablePanel(String [] columnTitles) {
        setLayout(new FlowLayout());
        columns=columnTitles;
        this.add(table);
        eventSources();
        setVisible(true);
    }

    private void eventSources() {
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(table.getSelectedRow()>-1) {
//                String name = table.getValueAt(table.getSelectedRow(), 1).toString();
//                String isbn = table.getValueAt(table.getSelectedRow(), 2).toString();
//                ArrayList<Publication> a = PublicationItems.getPublicationHashMap().get(name);
//                for (Publication p : a) {
//                    if (p.getIsbn().equals(isbn)) {
//                        chooseSelectedRowtoOperate(p);
//                        break;
//                    }
//                }
            }
        });
    }

    private void chooseSelectedRowtoOperate(Publication selectedone) {
//        itemDetailPanel.updateItemDetailPanel(selectedone);
        //this.add(itemDetailPanel);
        setVisible(true);
    }

    public void showBorrowerInTable(String s){
        this.removeAll();
//        while (model.getRowCount()>0){
//            model.removeRow(0);
//        }
        DefaultTableModel model=new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
        Borrower b= BorrowerList.getBorrowerHashMap().get(s);
        int i=1;
        if(b.getCurrentitems().size()!=0){
            for(Publication a:b.getCurrentitems()){
                Vector<String> vector =new Vector<>();
                vector.add(Integer.toString(i));
                vector.add(s);
                vector.add(a.getName());
                model.addRow(vector);
                i++;
            }
        }
        else{
            Vector<String> vector =new Vector<>();
            vector.add(Integer.toString(i));
            vector.add(s);
            vector.add("No book loan yet!");
            model.addRow(vector);
            i++;
        }

        this.revalidate();
    }
    public void showBorrowerInTable(){
//        this.removeAll();
//        while (model.getRowCount()>0){
//            model.removeRow(0);
//        }
//        model.setColumnIdentifiers(columns);
//        table.setModel(model);


        this.removeAll();
//        while (model.getRowCount()>0){
//            model.removeRow(0);
//        }
        DefaultTableModel model=new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
        Iterator iter = BorrowerList.getBorrowerHashMap().entrySet().iterator();
        int i=1;
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Borrower borrower = (Borrower) entry.getValue();
            if(borrower.getCurrentitems().size()!=0){
                for(Publication a:borrower.getCurrentitems()){
                    Vector<String> vector =new Vector<String>();
                    vector.add(Integer.toString(i));
                    vector.add(borrower.getNameofbrwr());
                    vector.add(a.getName());
                    model.addRow(vector);
                    i++;
                }
            }
            else{
                Vector<String> vector =new Vector<>();
                vector.add(Integer.toString(i));
                vector.add(borrower.getNameofbrwr());
                vector.add("No book loan yet!");
                model.addRow(vector);
                i++;
            }
        }
        this.revalidate();
    }

    public JTable getTable() {
        return table;
    }
}