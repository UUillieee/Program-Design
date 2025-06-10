package GUI;

import Model.Customer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel to show after successful booking confirmation
 */
public class BookingConfirmedPanel extends JPanel {
    
    private final HotelFrame mainFrame;
    private final ActionListener controller;
    private final Customer customer;

    public BookingConfirmedPanel(HotelFrame mainFrame, ActionListener controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        this.customer = mainFrame.getLoggedInCustomer();
        createConfirmedPanel();
    }

    private void createConfirmedPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Success message
        String displayName = customer != null ? customer.getUsername() : "Guest";
        JLabel successLabel = new JLabel("Booking Confirmed for " + displayName + "!");
        successLabel.setFont(new Font("Arial", Font.BOLD, 24));
        successLabel.setForeground(new Color(0, 100, 0)); // Dark green
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(successLabel, gbc);

        // Thank you message
        JLabel thankYouLabel = new JLabel("Thank you for your booking!");
        thankYouLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        
        gbc.gridy = 1;
        add(thankYouLabel, gbc);

        // Add navigation buttons - just the main menu button
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller,null,null);
       

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(backNextPanel, gbc);
    }
    
 
}