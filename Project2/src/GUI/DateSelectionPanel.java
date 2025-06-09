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
    private JComboBox<Integer> dayCombo, endDayCombo;
    private JComboBox<String> monthCombo, endMonthCombo, timeCombo;
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

        //Check-in Month
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Check-in Month:"), gbc);
        gbc.gridx = 1;
        monthCombo = new JComboBox<>(getMonths());
        add(monthCombo, gbc);
        monthCombo.addActionListener(e -> updateDaysForSelectedMonth()); // Listener to update the available days 
        row++;

        //Check-in Day
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Check-in Day:"), gbc);
        gbc.gridx = 1;
        dayCombo = new JComboBox<>(generateNumbers(1, 28));
        add(dayCombo, gbc);
        row++;

        // Length of Stay
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
        add(new JLabel("Check in time:"), gbc);
        gbc.gridx = 1;
        timeCombo = new JComboBox<>(new String[]{"Morning", "Afternoon", "Evening"});
        add(timeCombo, gbc);
        row++;

        //next button
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        JButton nextBtn = new JButton("Next");
        nextBtn.putClientProperty("targetPanel", "RoomSelection");
        nextBtn.setActionCommand(Command.SWITCH_PANEL.name());
        nextBtn.addActionListener(e -> saveDateInfo());
        nextBtn.addActionListener(controller);
        row++;
        add(nextBtn, gbc);

        //Confirm Button
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        JButton confBtn = new JButton("Confirm");
        confBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextBtn.setEnabled(true);
                saveDateInfo();
                System.out.println(builder.getBookingInfo());
            }
        });

        confBtn.addActionListener(controller);
        add(confBtn, gbc);

    }

    private GridBagConstraints setGbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    // In DateSelectionPanel
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

        int day = (int) dayCombo.getSelectedItem();
        int month = monthCombo.getSelectedIndex() + 1;
        int time = timeCombo.getSelectedIndex(); // 0 = Morning, 1 = Afternoon, 2 = Evening
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

        // Try to reselect previously selected day if valid
        if (currentSelection != null && currentSelection <= daysInMonth) {
            dayCombo.setSelectedItem(currentSelection);
        }
    }

    public static void main(String[] args) {
        HotelFrame f = new HotelFrame();

    }
}
