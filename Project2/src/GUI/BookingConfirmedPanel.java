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
    private JLabel titleLabel;

    public BookingConfirmedPanel(HotelFrame mainFrame, ActionListener controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        createConfirmedPanel();
    }

    private void createConfirmedPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Success message
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setText("Confirmed your booking! Thank you!");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Add navigation buttons - just the main menu button
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller, null, null);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(backNextPanel, gbc);
    }

  
}
