/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.Customer;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * User Dashboard Panel for managing hotel bookings
 * Provides options to view, edit, cancel bookings and navigation
 * 
 * @author George
 */
public class UserDashboardPanel extends JPanel {
    private final HotelFrame mainFrame;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JButton viewDetailsButton;
    private JButton editBookingButton;
    private JButton cancelBookingButton;
    private JPanel navigationPanel;
    private JLabel userInfoLabel;
    
    public UserDashboardPanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
        ActionListener controller = new ActionController(mainFrame);
        createUserDashboardPanel(controller);
    }
    
    private void createUserDashboardPanel(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBackground(Color.WHITE);
        
        // Title
        JLabel welcomeTitle = new JLabel("Your Bookings Dashboard");
        welcomeTitle.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeTitle.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.insets = new Insets(20, 20, 10, 20); //padding 
        gbc.anchor = GridBagConstraints.CENTER; //put in center
        this.add(welcomeTitle, gbc);
        
        // User info label (placeholder for database integration)
        //set text as place holder
        userInfoLabel = new JLabel("Welcome back, Guest"); 
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfoLabel.setForeground(Color.GRAY);
        gbc.gridy = 1; 
        gbc.insets = new Insets(0, 20, 20, 20);
        this.add(userInfoLabel, gbc);
        
        // Bookings table section
        createBookingsTable();
        JScrollPane tableScrollPane = new JScrollPane(bookingsTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 200));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Current Bookings", 
            TitledBorder.LEFT, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(10, 20, 20, 20);
        this.add(tableScrollPane, gbc);
        
        // Booking management buttons panel
        JPanel bookingActionsPanel = createBookingActionsPanel(controller);
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 20, 20, 20);
        this.add(bookingActionsPanel, gbc);
        
        // Navigation buttons panel
        navigationPanel = NavigationPanel.createMenuExitPanel(controller);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 20, 20);
        this.add(navigationPanel, gbc);
        
        //load the bookings 
        loadBookingsFromDatabase();
    }
    
    //update the customer username from guest to 
    public void updateUserGreeting(Customer customer) {
        //get username from Customer
        String displayName = customer != null ? customer.getUsername() : "Guest";
        userInfoLabel.setText("Welcome back, " + displayName);
    }
    
    private void createBookingsTable() {
        // Define column names for the bookings table
        String[] columnNames = {
            "Booking ID", 
            "Customer ID",
            "Hotel Name", 
            "Check-in Date", 
            "Check-out Date", 
            "Room Type", 
            "Total Cost",
            "Status"
        };
        
        // Create table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        // Create table
        bookingsTable = new JTable(tableModel);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.setRowHeight(25);
        bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Set column widths
        bookingsTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Booking ID
        bookingsTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Hotel Name
        bookingsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Check-in
        bookingsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Check-out
        bookingsTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Room Type
        bookingsTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Cost
        bookingsTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Status
    }
    
    private JPanel createBookingActionsPanel(ActionListener controller) {
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Booking Actions",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        // View Details Button
        viewDetailsButton = new JButton("View Details");
        StyleButtons.styleActionButton(viewDetailsButton, new Color(70, 130, 180)); // Steel blue
       // viewDetailsButton.setActionCommand(Command.VIEW_DETAILS.name());
        viewDetailsButton.addActionListener(controller);
        viewDetailsButton.putClientProperty("targetPanel", "BookingDetails");
        actionsPanel.add(viewDetailsButton);
        
        // Edit Booking Button
        editBookingButton = new JButton("Edit Booking");
        StyleButtons.styleActionButton(editBookingButton, new Color(255, 140, 0)); // Dark orange
        //editBookingButton.setActionCommand(Command.EDIT.name());
        editBookingButton.addActionListener(controller);
        editBookingButton.putClientProperty("targetPanel", "EditBooking");
        actionsPanel.add(editBookingButton);
        
        // Cancel Booking Button
        cancelBookingButton = new JButton("Cancel Booking");
         StyleButtons.styleActionButton(cancelBookingButton, new Color(220, 20, 60)); // Crimson
        //cancelBookingButton.setActionCommand(Command.CANCEL.name());
        cancelBookingButton.addActionListener(controller);
        actionsPanel.add(cancelBookingButton);
        
        // Initially disable buttons until a booking is selected
        enableBookingActions(false);
        
        // Add selection listener to enable/disable buttons
        bookingsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                enableBookingActions(bookingsTable.getSelectedRow() != -1);
            }
        });
        
        return actionsPanel;
    }
    
  
  
    //Enable booking buttons
    private void enableBookingActions(boolean enabled) {
        viewDetailsButton.setEnabled(enabled);
        editBookingButton.setEnabled(enabled);
        cancelBookingButton.setEnabled(enabled);
    }
    
    /**
     * Load sample booking data for demonstration, replace with database call
     */
    private void loadBookingsFromDatabase() {
        tableModel.setRowCount(0); // Clear table

        Customer user = mainFrame.getLoggedInCustomer();
        if (user == null) {
            System.out.println("No logged-in user.");
            return;
        }       

        int customerId = user.getId();  // Make sure your Customer class has getId()

        java.util.List<Object[]> bookings = dbpackage.RetrieveBookings.getBookingsByCustomerId(customerId);
        for (Object[] row : bookings) {
            tableModel.addRow(row);
        }
    }
    
    /**
     * Method to refresh the bookings display
     */
    public void refreshBookings() {
        // Clear existing data
        tableModel.setRowCount(0);
        // Reload sample data
        loadBookingsFromDatabase();
    }
    
    //Methods for when selecting booking in table.
    
    /**
     * Get the currently selected booking ID
     * @return Selected booking ID or null if none selected
     */
    public String getSelectedBookingId() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }
    
    /**
     * Get the currently selected booking data
     * @return Array of selected booking data or null if none selected
     */
    public Object[] getSelectedBookingData() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] bookingData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                bookingData[i] = tableModel.getValueAt(selectedRow, i);
            }
            return bookingData;
        }
        return null;
    }
}