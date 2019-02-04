package ui.Frames;
import ui.listeners.MouseActionListener;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

public class CalendarPanel extends JPanel {

    private JComboBox monthbox;
    private JComboBox yearbox;

    private JLabel yearlabel;
    private JLabel monthlabel;

    private JButton buttonok;
    private JButton button_today;

    private JLabel[] button_week = new JLabel[7];
    private JButton[] button_day= new JButton[42];

    private LocalDate nowdate;

    private int nowyear;
    private int nowmonth;

    private LocalDate selecteddate;

    private final static String[] weekdays = new DateFormatSymbols().getShortWeekdays();

    private final static String[] months = new DateFormatSymbols().getMonths();


    private static final int YEAR_Range=20;

    public CalendarPanel(){
        nowdate=LocalDate.now();
        makeBasicLayout();
        eventSources();
        setSize(700,280);
        setLocation(100,100);
        setVisible(true);
    }

    private void eventSources() {
        button_today.addMouseListener(new MouseActionListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paintDay(nowdate);

            }
        });

        for(int i=0;i<42;i++){
            if(button_day[i].isEnabled()){
                JButton a=button_day[i];
                button_day[i].addMouseListener(new MouseActionListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        generateDate((int)yearbox.getSelectedItem(),monthbox.getSelectedIndex()+1,Integer.parseInt(a.getText()));
                    }
                });
            }
        }


        monthbox.addItemListener(e -> paintDay((int)yearbox.getSelectedItem(),monthbox.getSelectedIndex()+1));

        yearbox.addItemListener(e -> paintDay((int)yearbox.getSelectedItem(),monthbox.getSelectedIndex()+1));
    }

    private void makeBasicLayout() {
        monthbox = new JComboBox();
        yearbox = new JComboBox();
        yearlabel = new JLabel("Year：");
        monthlabel = new JLabel("Month：");
        buttonok = new JButton("Confirm");
        button_today = new JButton("Today");
        nowyear = nowdate.getYear();
        nowmonth = nowdate.getMonthValue();

        for(int i = nowyear - YEAR_Range; i <= nowyear + YEAR_Range; i++){ yearbox.addItem(i);}
        for(int i = 0;i < 12;i++){monthbox.addItem(months[i]);}
        JPanel panelym = new JPanel();
        panelym.add(yearlabel);
        panelym.add(yearbox);
        panelym.add(monthlabel);
        panelym.add(monthbox);
        panelym.add(button_today);
        panelym.add(buttonok);

        JPanel panelday = new JPanel();
        panelday.setLayout(new GridLayout(7, 7, 0, 0));
        for(int i = 0; i < 7; i++) {
            button_week[i] = new JLabel(weekdays[i+1],SwingConstants.CENTER);
            button_week[i].setForeground(Color.black);
            panelday.add(button_week[i]);
        }
        button_week[0].setForeground(Color.red);
        button_week[6].setForeground(Color.red);

        for(int i = 0; i < 42;i++){
            button_day[i] = new JButton(" ");
            if(i%7==0||i%7==6){
                button_day[i].setForeground(Color.red);
            }
            panelday.add(button_day[i]);
        }

        paintDay(nowdate);

        this.setLayout(new BorderLayout());
        this.add(panelday,BorderLayout.SOUTH);
        this.add(panelym,BorderLayout.NORTH);
    }

    private void paintDay(LocalDate nowdate) {
        for(JButton a:button_day){
            a.setText(" ");
            a.setEnabled(false);
        }
        yearbox.setSelectedIndex(YEAR_Range);
        monthbox.setSelectedIndex(nowmonth-1);

        LocalDate start = nowdate.with(firstDayOfMonth());
        fillInButtons(nowyear, nowmonth, start);
    }

    private void paintDay(int year,int month) {
        for(JButton a:button_day){
            a.setText(" ");
            a.setEnabled(false);
        }
        LocalDate start= LocalDate.of(year,month,1);
        fillInButtons(year, month, start);
    }

    private void fillInButtons(int year, int month, LocalDate start) {
        int dayOfWeekIntValue = start.getDayOfWeek().getValue();

        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        int j=1;
        for(int i=dayOfWeekIntValue;i<daysInMonth+dayOfWeekIntValue;i++,j++){
            button_day[i].setEnabled(true);
            button_day[i].setText(Integer.toString(j));
        }
        setVisible(true);
    }

    public LocalDate getSelecteddate() {
        return selecteddate;
    }

    private void generateDate(int year, int month, int day){
        selecteddate=LocalDate.of(year,month,day);
    }


}
