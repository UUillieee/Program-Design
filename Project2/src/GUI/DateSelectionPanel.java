/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.BookingBuilder;
import Model.DateSelectionData;
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
public class DateSelectionPanel extends JPanel implements ResettablePanel {

    private final HotelFrame mainFrame;
    private JComboBox<Integer> dayCombo, endDayCombo, timeCombo;
    private JComboBox<String> monthCombo, endMonthCombo;
    private final ActionListener controller;
    private JSpinner lengthOfStaySpinner;

    //constructor initializes the panel with date selection UI
    public DateSelectionPanel(HotelFrame mainFrame, ActionListener controller) {
        this.mainFrame = mainFrame;
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
        add(new JLabel("Check-in Time: (24hr)"), gbc);

        gbc.gridx = 1;
        Integer[] timeOptions = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}; // 8 AM – 7 PM
        timeCombo = new JComboBox<>(timeOptions);
        add(timeCombo, gbc);
        row++;

        //navigation Panel, back and next
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller, "RoomSelection", "null"); // Null so panel is made, but doesnt set a default target
        gbc.gridwidth = 3;
        gbc.gridy = ++row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backNextPanel, gbc);
        //Attatch 
        for (Component comp : backNextPanel.getComponents()) {
            if (comp instanceof JButton button && "Next".equals(button.getText())) {
                button.addActionListener(e -> {
                    ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Command.PROCEED_TO_CONFIRMATION.name());
                    controller.actionPerformed(newEvent);
                });
            }
        }

    }

    //return the length of the customer stay end-start - to populate the check in days
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
  public DateSelectionData getDateSelectionInput() {
    int day = (int) dayCombo.getSelectedItem();
    int month = monthCombo.getSelectedIndex() + 1;
    int time = (int) timeCombo.getSelectedItem();
    int lengthOfStay = (int) lengthOfStaySpinner.getValue();

    int year = LocalDate.now().getYear();
    LocalDate startDate = LocalDate.of(year, month, day);
    LocalDate endDate = startDate.plusDays(lengthOfStay);

    int endDay = endDate.getDayOfMonth();
    int endMonth = endDate.getMonthValue();

    // Return a new data object
    return new DateSelectionData(day, month, time, lengthOfStay, endDay, endMonth);
}

    //get days for each month - when month in month combo selecetd, this method is called to update the days.
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

    //After booking proccess, reset the selected items.
    @Override
    public void resetFields() {
        // Reset month to January (index 0)
        if (monthCombo != null) {
            monthCombo.setSelectedIndex(0);
        }
        // Reset day to 1 (will be updated by monthCombo's action listener)
        if (dayCombo != null) {
            dayCombo.setSelectedIndex(0);
        }
        // Reset length of stay spinner to 1
        if (lengthOfStaySpinner != null) {
            lengthOfStaySpinner.setValue(1);
        }
        // Reset time combo to its first option (e.g., 8 AM)
        if (timeCombo != null) {
            timeCombo.setSelectedIndex(0);
        }
        // Ensure days are updated for the reset month
        updateDaysForSelectedMonth();
    }
}
