/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 *
 * @author billw
 */
//date panel or the user to choose their check in dates
//save input to the booking builder
public class DateSelectionPanel extends JPanel {

    private final HotelFrame mainFrame;
    private JComboBox<Integer> dayCombo, endDayCombo, timeCombo;
    private JComboBox<String> monthCombo, endMonthCombo;
    private final BookingBuilder builder;
    private final ActionListener controller;
    private JSpinner lengthOfStaySpinner;

    //constructor initializes the panel with date selection UI
    public DateSelectionPanel(HotelFrame mainFrame, BookingBuilder builder, ActionListener controller) {
        this.mainFrame = mainFrame;
        this.builder = builder;
        this.controller = controller;
        //build the UI
        createDateSelectionUI();
    }

    //build the layout and add UI components for selecting dates 
    private void createDateSelectionUI() {
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
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        //reset gridwidth and anchor
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        //check-in month
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Check-in Month:"), gbc);
        gbc.gridx = 1;
        monthCombo = new JComboBox<>(getMonths());
        add(monthCombo, gbc);
        monthCombo.addActionListener(e -> updateDaysForSelectedMonth()); // Listener to update the available days 
        row++;

        //check-in day
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Check-in Day:"), gbc);
        gbc.gridx = 1;
        dayCombo = new JComboBox<>(generateNumbers(1, 28));
        add(dayCombo, gbc);
        row++;

        //length of stay
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Length of Stay (nights):"), gbc);
        gbc.gridx = 1;
        lengthOfStaySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        add(lengthOfStaySpinner, gbc);
        row++;

        //time
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Check-in Time:"), gbc);

        gbc.gridx = 1;
        Integer[] timeOptions = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}; // 8 AM – 7 PM
        timeCombo = new JComboBox<>(timeOptions);
        add(timeCombo, gbc);
        row++;
        
        //navigation Panel, back and next
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller, "RoomSelection", "ConfirmPanel");
        gbc.gridwidth = 3;
        gbc.gridy = ++row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backNextPanel, gbc);
        //saveDateInfo() only when next is clicked
        for (Component comp : backNextPanel.getComponents()) {
            if (comp instanceof JButton button && "ConfirmPanel".equals(button.getClientProperty("targetPanel"))) {
                button.addActionListener(e -> saveDateInfo());
            }
        }
        
    }
 
    private GridBagConstraints setGbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    //DateSelectionPanel
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

        //back button to return to room panel 
        JButton nextButton = new JButton("Back");
        nextButton.setActionCommand(Command.SWITCH_PANEL.name());
        nextButton.putClientProperty("targetPanel", "RoomSelection");
        nextButton.addActionListener(controller);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        this.add(nextButton, gbc);
    }

    //return the length of the customer stay end-start
    private Integer[] generateNumbers(int start, int end) {
        Integer[] nums = new Integer[end - start + 1];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = start + i;
        }
        return nums;
    }

    //array of months
    private String[] getMonths() {
        return new String[]{"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    }

    //save date info to the booking builder 
    private void saveDateInfo() {

        int day = (int) dayCombo.getSelectedItem();
        int month = monthCombo.getSelectedIndex() + 1;
        int time = (int) timeCombo.getSelectedItem();
        int lengthOfStay = (int) lengthOfStaySpinner.getValue();

        int year = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = startDate.plusDays(lengthOfStay);

        int endDay = endDate.getDayOfMonth();
        int endMonth = endDate.getMonthValue();

        builder.setDay(day);
        builder.setMonth(month);
        builder.setEndDay(endDay);
        builder.setEndMonth(endMonth);
        builder.setLengthOfStay(lengthOfStay);
        builder.setTime(time);
        
        System.out.println("Date info saved to BookingBuilder:");

    }

    //get days for each month 
    //Feb - 28
    //March... - 31
    //November... - 30
    private void updateDaysForSelectedMonth() {
        int selectedMonth = monthCombo.getSelectedIndex() + 1;
        int daysInMonth;

        switch (selectedMonth) {
            case 2 ->
                daysInMonth = 28;
            case 4, 6, 9, 11 ->
                daysInMonth = 30;
            default ->
                daysInMonth = 31;
        }

        Integer currentSelection = (Integer) dayCombo.getSelectedItem();
        dayCombo.setModel(new DefaultComboBoxModel<>(generateNumbers(1, daysInMonth)));

        //try to reselect previously selected day if valid
        if (currentSelection != null && currentSelection <= daysInMonth) {
            dayCombo.setSelectedItem(currentSelection);
        }
    }

    public static void main(String[] args) {
        HotelFrame f = new HotelFrame();

    }
}
