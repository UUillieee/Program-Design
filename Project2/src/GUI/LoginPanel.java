package GUI;
import dbpackage.CustomerUpdateInfo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * Login Panel for Hotel Booking System
 * Provides user authentication interface
 * 
 * @author George - used
 */
public class LoginPanel extends JPanel {

    private HotelFrame mainFrame;

    private JTextField username = new JTextField(15);
    private JPasswordField password = new JPasswordField(15);

    public LoginPanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
        ActionListener controller = new ActionController(mainFrame);
        createLoginPanel(controller);
    }
    public static void main(String[] args) {
        HotelFrame f = new HotelFrame();
        f.showPanel("Login");
    }

    //return the username to what ever calls it
    public String getUsername() {
        return username.getText();
    }

    //return the password to what ever calls it
    public String getPassword() {
        return new String(password.getPassword());
    }

    private void createLoginPanel(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBackground(Color.WHITE);
        // Title
        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across both columns - make extra wide (cus title)
        gbc.insets = new Insets(20, 20, 30, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);
        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset to single column
        gbc.anchor = GridBagConstraints.EAST;//position within cell
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(userLabel, gbc);

        // Username TextField
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; //position within cell
        gbc.fill = GridBagConstraints.HORIZONTAL;// Doesnt expand to fill
        gbc.insets = new Insets(10, 10, 10, 20);
        this.add(username, gbc);

        //Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(passLabel, gbc);
        
        //Password Field (using JPasswordField for security)
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 20);
        this.add(password, gbc);
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String user = username.getText();
            String pass = new String(password.getPassword());

            CustomerUpdateInfo customerDB = new CustomerUpdateInfo();
            Model.Customer customer = customerDB.getCustomer(user, pass);

            if (customer != null) {
                System.out.println("Login successful! Welcome " + customer.getUsername());
                mainFrame.setCurrentCustomer(customer); // Optional: store for session
                mainFrame.showPanel("UserDashboard");
            } 
            else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 10, 10, 20);
        this.add(loginButton, gbc);
        // Create User Button
        JButton createUserButton = new JButton("Create New User");
        styleButton(createUserButton, new Color(255, 127, 80)); // Medium sea green
        createUserButton.setActionCommand(Command.CREATE_USER.name());
        createUserButton.addActionListener(controller);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 10, 10);
        this.add(createUserButton, gbc);
        // Back Button
        JButton backBtn = new JButton("Back to Main Menu");
        styleButton(backBtn, new Color(60, 179, 113)); // Green
        backBtn.setActionCommand(Command.SWITCH_PANEL.name());
        backBtn.addActionListener(controller);
        backBtn.putClientProperty("targetPanel", "Welcome");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(backBtn, gbc);
        //Decided not to put an exit button
        
        /*/ Exit Button 
        JButton exitButton = new JButton("Exit Application");
        styleButton(exitButton, new Color(220, 20, 60)); // Crimson
        exitButton.setActionCommand(Command.EXIT.name());
        exitButton.addActionListener(controller);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 20);
        this.add(exitButton, gbc);
        */
    }
    /**
     * Styles buttons with consistent appearance
     * @param button The button to style
     * @param bgColor Background color for the button
     */
    private void styleButton(JButton button, Color bgColor) {
        button.setPreferredSize(new Dimension(180, 35));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }
}