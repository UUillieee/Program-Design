/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.Customer;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author George
 */
public class ConfirmPanel extends JPanel {

    private final HotelFrame mainFrame;
    private final BookingBuilder builder;
    private final ActionListener controller;
    private final Customer customer;
    private JPanel bookingDetailsPanel;

    public ConfirmPanel(HotelFrame mainFrame, BookingBuilder builder, ActionListener controller) {
        this.mainFrame = mainFrame;
        this.builder = builder;
        this.controller = controller;
        this.customer = mainFrame.getLoggedInCustomer();
        createConfirmPanel();
    }

    private void createConfirmPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        //padding around components
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        
        //title
        String displayName = customer != null ? customer.getUsername() : "Guest";
        JLabel title = new JLabel("Confirm your booking: " + displayName);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);
        
        // Create and add booking details panel
        bookingDetailsPanel = createBookingDetailsPanel();
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(bookingDetailsPanel, gbc);
        
        // Add confirm button
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setActionCommand(Command.SWITCH_PANEL.name());
        confirmButton.addActionListener(controller);
        confirmButton.putClientProperty("targetPanel", "BookingConfirmed");
        StyleButtons.styleNavigationButton(confirmButton, new Color(30, 144, 255)); // Blue 
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(confirmButton, gbc);
        
        // Add back button
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller,"DateSelection",null);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row++;
        add(backNextPanel, gbc);
    }
    
    //panel for showing all booking details
    private JPanel createBookingDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new LineBorder(Color.GRAY, 1));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Add title for details section
        JLabel detailsTitle = new JLabel("Booking Details:");
        detailsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(detailsTitle, gbc);
        
        // Add booking details from builder
        gbc.gridwidth = 1;
        
        
        addDetailLabel(panel, "Room Number:", String.valueOf(builder.getRoomNumber()), gbc, row++);
        String checkInDate = builder.getDay() + "/" + builder.getMonth();
        addDetailLabel(panel, "Check-in Date:", checkInDate, gbc, row++);
        String checkOutDate = builder.getEndDay() + "/" + builder.getEndMonth();
        addDetailLabel(panel, "Check-out Date:", checkOutDate, gbc, row++);
        addDetailLabel(panel, "Nights:", String.valueOf(builder.getLengthOfStay()), gbc, row++);
        addDetailLabel(panel, "Guests:", String.valueOf(builder.getGuests()), gbc, row++);
        addDetailLabel(panel, "Total Price:", String.format("$%.2f", builder.getTotalPrice()), gbc, row++);
        
        return panel;
    }
    //method to add labels uniformly
    //Pas in panel that it adds the label to, label text and value, grid settings, and row position
    private void addDetailLabel(JPanel panel, String labelText, String valueText, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        
        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(value, gbc);
    }
    private void confirmPanel(){
        
    }

    public static void main(String[] args) {
        HotelFrame f = new HotelFrame();
        f.showPanel("ConfirmPanel");
    }
}