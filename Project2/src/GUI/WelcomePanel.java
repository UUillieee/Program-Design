package GUI;

import Model.Customer;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Welcome Panel for the Hotel Booking System GUI Provides main menu options for
 * booking, login, and exit
 *
 * @author George
 */
public class WelcomePanel extends JPanel {

    private HotelFrame mainFrame;
    private JLabel userLabel;

    public WelcomePanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
        ActionListener controller = new ActionController(mainFrame);
        createWelcomePanel(controller);
    }

    //USing gridbaglayout
    private void createWelcomePanel(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel welcomeTitle = new JLabel("Hotel Booking System");
        welcomeTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0; // grid X and grid Y to set the 
        gbc.insets = new Insets(20, 20, 10, 20);// Padding between each component
        this.add(welcomeTitle, gbc);

        //Welcome Message, using html to format the message
        JLabel welcomeMessage = new JLabel("<html><center>Welcome! Choose an option below to get started.<br/>Login to view existing bookings or start a new booking.</center></html>");
        welcomeMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1; // Row position first row
        gbc.insets = new Insets(10, 20, 20, 20); // Padding between each component
        this.add(welcomeMessage, gbc);

        // Buttons - new booking, login, and exit
        JButton newBookingButton = new JButton("Start New Booking");
        styleButton(newBookingButton, new Color(60, 179, 113));
        newBookingButton.setActionCommand(Command.SWITCH_PANEL.name());
        newBookingButton.addActionListener(controller);
        newBookingButton.putClientProperty("targetPanel", "HotelSelection");
        gbc.gridy = 2; // Row position 2nd row
        gbc.insets = new Insets(10, 20, 10, 20);// Padding between each component
        this.add(newBookingButton, gbc);

        JButton loginButton = new JButton("Login to View Bookings");
        styleButton(loginButton, new Color(70, 130, 180)); 
        loginButton.setActionCommand(Command.SWITCH_PANEL.name());
        loginButton.addActionListener(controller);
        loginButton.putClientProperty("targetPanel", "Login");
        mainFrame.clearPostLoginTarget(); // only happens once , not every time
        gbc.gridy = 3; // Row position 3rd row 
        this.add(loginButton, gbc);

        JButton logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(112, 128, 144));
        logoutBtn.setActionCommand(Command.LOGOUT.name());
        logoutBtn.addActionListener(controller);
        gbc.gridy = 4;// Row position 4th row
        this.add(logoutBtn, gbc);

        JButton exitButton = new JButton("Exit Application");
        styleButton(exitButton, new Color(220, 20, 60));
        exitButton.setActionCommand(Command.EXIT.name());
        exitButton.addActionListener(controller);
        gbc.gridy = 5;// Row position 4th row
        this.add(exitButton, gbc);

        userLabel = new JLabel();
        userLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        updateUserLabel();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(userLabel, gbc);

    }

    //Called by the buttons to set the color of them.
    private void styleButton(JButton button, Color bgColor) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    public void updateUserLabel() {
        Customer customer = mainFrame.getLoggedInCustomer();
        String displayName = (customer != null) ? customer.getUsername() : "Guest";
        userLabel.setText("Logged in as: " + displayName);
    }
}
