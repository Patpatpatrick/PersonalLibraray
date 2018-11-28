package ui.runlibraryui;

import control.backendcenter.PublicationOperationCenter;
//import control.CDOperationCenter;
import exception.publicationexception.NonExistException;
import control.frontcenter.LibraryFrontCenter;
import ui.adminui.AdminLoginUI;
import ui.UserUI.UserLoginUI;
import ui.listeners.KeyboardActionListener;
import ui.listeners.MouseActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class RunlibGUI extends JFrame{
    private JLabel wlcm;
    private JPanel jpanel,jp1,jp2,jp3;
    private JTextField text;
    private JButton goforsearchbtn,adminbtn,userbtn;

    public RunlibGUI() throws IOException {
        super("Personal Library");
        LibraryFrontCenter.getInstance();
        this.setLayout(new BorderLayout());
        makeBasicLayout();
        eventSources();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(560,590);
        setLocation(100,100);
        setVisible(true);

    }
    public void makeBasicLayout() throws IOException {
        wlcm=new JLabel("Welcome! Search by name to check out a publication",SwingConstants.CENTER);
        text=new JTextField(15);
        goforsearchbtn=new JButton("Search");
        adminbtn=new JButton("AdminLogin");
        userbtn=new JButton("UserLogin");
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jp1 = new JPanel(new BorderLayout());
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp1.add(wlcm,BorderLayout.NORTH);
        Image myPicture = ImageIO.read(new File("1600px-Herjangsfjorden_&_Ofotfjorden,_wide,_2009_09.jpg"));
        Image newImage = myPicture.getScaledInstance(560, 200, Image.SCALE_DEFAULT);
        JLabel picLabel = new JLabel(new ImageIcon(newImage));
        jp1.add(picLabel,BorderLayout.SOUTH);
        jp2.add(text,BorderLayout.CENTER);
        jp2.add(goforsearchbtn,BorderLayout.CENTER);
        jp3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jp3.add(adminbtn);
        jp3.add(userbtn);
        jpanel.add(jp1,BorderLayout.NORTH);
        jpanel.add(jp2,BorderLayout.CENTER);
        jpanel.add(jp3,BorderLayout.SOUTH);
        this.add(jpanel);
    }

    public JTextField getText() {
        return text;
    }

    public JButton getGoforsearchbtn() {
        return goforsearchbtn;
    }

    public JButton getAdminbtn() {
        return adminbtn;
    }

    public JButton getUserbtn() {
        return userbtn;
    }

    private void eventSources() {
        //EFFECTS:In the text field you would be able to type in sth to search for.
        text.addKeyListener(new KeyboardActionListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if(k == KeyEvent.VK_ENTER){
                    new VisitorBrowsingPanel(text.getText());
                }
            }
        });

        goforsearchbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(text.getText());
                    new VisitorBrowsingPanel(text.getText());
            }
        });

        adminbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminLoginUI();
            }
        });
        userbtn.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new UserLoginUI();
            }
        });
    }


    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new RunlibGUI(); // Let the constructor do the job
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
