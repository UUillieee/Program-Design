/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author billw
 */

//date panel or the user to choose their check in dates
//save input to the booking builder
public class DateSelectionPanel extends JPanel {
    private final HotelFrame mainFrame;
    private JComboBox<Integer> dayCombo, endDayCombo;
    private JComboBox<String> monthCombo, endMonthCombo, timeCombo;

    //constructor initializes the panel with date selection UI
    public DateSelectionPanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
        ActionListener controller = new ActionController(mainFrame);
        //build the UI
        createDateSelectionUI(controller);
    }
    
    //build the layout and add UI components for selecting dates 
    private void createDateSelectionUI(ActionListener controller) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        //padding around components
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //track row positions
        int row = 0; 

        //title
        JLabel title = new JLabel("Select Your Booking Dates");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        //reset gridwidth and anchor
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        //Check-in Day
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Check-in Day:"), gbc);
        gbc.gridx = 1;
        dayCombo = new JComboBox<>(generateNumbers(1, 31));
        add(dayCombo, gbc);
        row++;

        //Check-in Month
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Check-in Month:"), gbc);
        gbc.gridx = 1;
        monthCombo = new JComboBox<>(getMonths());
        add(monthCombo, gbc);
        row++;

        //Check-out Day
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Check-out Day:"), gbc);
        gbc.gridx = 1;
        endDayCombo = new JComboBox<>(generateNumbers(1, 31));
        add(endDayCombo, gbc);
        row++;

        //Check-out Month
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Check-out Month:"), gbc);
        gbc.gridx = 1;
        endMonthCombo = new JComboBox<>(getMonths());
        add(endMonthCombo, gbc);
        row++;

        //time
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Time:"), gbc);
        gbc.gridx = 1;
        timeCombo = new JComboBox<>(new String[]{"Morning", "Afternoon", "Evening"});
        add(timeCombo, gbc);
        row++;

        //next button
        gbc.gridx = 1; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        JButton nextBtn = new JButton("Next");
        nextBtn.putClientProperty("targetPanel", "RoomSelection");
        nextBtn.setActionCommand(Command.SWITCH_PANEL.name());
        nextBtn.addActionListener(e -> saveDateInfo());
        nextBtn.addActionListener(controller);
        add(nextBtn, gbc);
    }
    

    private GridBagConstraints setGbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
    
    // In DateSelectionPanel.java
    public void initUI(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Select Booking Dates:");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(title, gbc);
        
        //Back button to return to room panel 
        JButton nextButton = new JButton("Back");
        nextButton.setActionCommand(Command.SWITCH_PANEL.name());
        nextButton.putClientProperty("targetPanel", "RoomSelection");
        nextButton.addActionListener(controller);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        this.add(nextButton, gbc);
    }   

    private Integer[] generateNumbers(int start, int end) {
        Integer[] nums = new Integer[end - start + 1];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = start + i;
        }
        return nums;
    }

    private String[] getMonths() {
        return new String[]{"January", "February", "March", "April", "May", "June",
                            "July", "August", "September", "October", "November", "December"};
    }
    
    
    private void saveDateInfo() {
        BookingBuilder builder = mainFrame.getBookingBuilder();

        int day = (int) dayCombo.getSelectedItem();
        int month = monthCombo.getSelectedIndex() + 1;

        int endDay = (int) endDayCombo.getSelectedItem();
        int endMonth = endMonthCombo.getSelectedIndex() + 1;

        int time = timeCombo.getSelectedIndex(); // 0 = Morning, 1 = Afternoon, 2 = Evening

        builder.setDay(day);
        builder.setMonth(month);
        builder.setEndDay(endDay);
        builder.setEndMonth(endMonth);
        builder.setTime(time);

        System.out.println("Date info saved to BookingBuilder");
    }
}
