import javax.swing.*;
import java.awt.*;

public class HotelGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;
    private boolean loggedIn = false; // tracks login status

    public HotelGUI() {
        setTitle("Hotel Booking");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Panels
        JPanel mainMenuPanel = createMainMenuPanel();
        JPanel bookingPanel = createBookingPanel();

        // Add panels to container
        container.add(mainMenuPanel, "MainMenu");
        container.add(bookingPanel, "Booking");

        add(container);
        cardLayout.show(container, "MainMenu");
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Main Menu", SwingConstants.CENTER);
        JButton bookingBtn = new JButton("Make Booking");

        bookingBtn.addActionListener(e -> {
            if (!loggedIn) {
                showLoginDialog();
            }
            if (loggedIn) {
                cardLayout.show(container, "Booking");
            }
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(bookingBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Booking Panel", SwingConstants.CENTER);
        JButton backBtn = new JButton("Back");

        backBtn.addActionListener(e -> cardLayout.show(container, "MainMenu"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);
        return panel;
    }

    private void showLoginDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Login Required",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            //Check against database for credentials - e.g table Users
            if (user.equals("admin") && pass.equals("admin")) {
                loggedIn = true;
                JOptionPane.showMessageDialog(this, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelGUI().setVisible(true));
    }
}
