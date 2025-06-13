package GUI;

import static GUI.Command.*;
import Model.BookingBuilder;
import Model.DateSelectionData;
import dbpackage.BookingUpdateInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import dbpackage.CustomerUpdateInfo;
import javax.swing.JOptionPane;


/**
 *
 * @author George
 */
public class ActionController implements ActionListener {

    private final HotelFrame mainFrame; // Make final as it's set in constructor
    private final BookingBuilder builder; // Make final as it's set in constructor

    public ActionController(HotelFrame mainFrame, BookingBuilder builder) {
        this.mainFrame = mainFrame;
        this.builder = builder;
    }

    //When buttons are interacted with, get the command assigned to the button to run the method for the button.
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Command command = Command.valueOf(e.getActionCommand());

            switch (command) {
                case SWITCH_PANEL:
                    handleSwitchPanel(e);
                    break;
                case LOGIN:
                    handleLogin();
                    break;
                case LOGOUT:
                    handleLogout();
                    break;
                case CREATE_USER:
                    handleCreateUser();
                    break;
                case CONFIRM_BOOKING:
                    handleConfirmBooking();
                    break;
                case CANCEL:
                    handleCancelBooking();
                    break;
                case PROCEED_TO_CONFIRMATION:
                    handleProceedToConfirmation();
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unhandled command: " + command);
            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Unknown command received by ActionController: " + e.getActionCommand());
        }
    }

    //Method to switch between panels
    private void handleSwitchPanel(ActionEvent e) {
        if (e.getSource() instanceof JButton btn) {
            Object panelName = btn.getClientProperty("targetPanel");
            if (panelName instanceof String targetPanelName) {
                if (targetPanelName.equals("Login")) {
                    LoginPanel loginPanel = (LoginPanel) mainFrame.getPanel("Login");
                    loginPanel.clearFields();
                }
                mainFrame.showPanel(targetPanelName);
            } else {
                System.out.println("No target panel set for this button.");
            }
        }
    }

    //Method to manage the login procces, doing nessacary login checks, and make sure the correct panel is show after login.
    private void handleLogin() {
        LoginPanel loginPanel = (LoginPanel) mainFrame.getPanel("Login");
        String loginUser = loginPanel.getUsername();
        String loginPassword = loginPanel.getPassword();

        if (loginUser.isEmpty() || loginPassword.isEmpty()) { // Combine checks
            String message = "";
            if (loginUser.isEmpty() && loginPassword.isEmpty()) {
                message = "Username and password fields must not be empty.";
            } else if (loginUser.isEmpty()) {
                message = "Username field must not be empty.";
            } else { // loginPassword.isEmpty()
                message = "Password field must not be empty.";
            }
            JOptionPane.showMessageDialog(loginPanel, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerUpdateInfo customerDb = new CustomerUpdateInfo();
        Model.Customer customer = customerDb.getCustomer(loginUser, loginPassword);

        if (customer != null) {
            System.out.println("Login successful! Welcome " + customer.getUsername());
            mainFrame.setLoggedInCustomer(customer);
            mainFrame.getBookingBuilder().setCustomer(customer); // This uses the builder from HotelFrame, consider if this is always desired or if this.builder should be used.
            // Given this.builder is passed in, it should be the same instance.
            UserDashboardPanel dashboard = (UserDashboardPanel) mainFrame.getPanel("UserDashboard");
            dashboard.updateUserGreeting(customer);
            dashboard.refreshBookings();

            WelcomePanel welcomePanel = (WelcomePanel) mainFrame.getPanel("Welcome");
            welcomePanel.updateUserLabel();

            //
            String postLoginTarget = mainFrame.getPostLoginTarget();
            if (postLoginTarget != null) {
                mainFrame.clearPostLoginTarget();
                if (postLoginTarget.equals("ConfirmPanel")) {
                    mainFrame.showUpdatedConfirmPanel();
                } else {
                    mainFrame.showPanel(postLoginTarget);
                }
            } else { // If no specific target, go to UserDashboard by default
                mainFrame.showPanel("UserDashboard");
            }
            loginPanel.clearFields();
        } else {
            JOptionPane.showMessageDialog(loginPanel, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {
        if (mainFrame.getLoggedInCustomer() != null) {
            mainFrame.setLoggedInCustomer(null);
            mainFrame.showPanel("Welcome");
            LoginPanel loginPanel = (LoginPanel) mainFrame.getPanel("Login"); // Get here as needed
            loginPanel.clearFields();
            WelcomePanel welcomePanel = (WelcomePanel) mainFrame.getPanel("Welcome"); // Get here as needed
            welcomePanel.updateUserLabel();
        }
    }

    private void handleCreateUser() {
        LoginPanel createPanel = (LoginPanel) mainFrame.getPanel("Login");
        String newUser = createPanel.getUsername();
        String newPassword = createPanel.getPassword();

        if (newUser.isEmpty() || newPassword.isEmpty()) { // Combine checks
            String message = "";
            if (newUser.isEmpty() && newPassword.isEmpty()) {
                message = "Username and password fields must not be empty.";
            } else if (newUser.isEmpty()) {
                message = "Username field must not be empty.";
            } else { // newPassword.isEmpty()
                message = "Password field must not be empty.";
            }
            JOptionPane.showMessageDialog(createPanel, message, "New User Creation Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerUpdateInfo customerDB = new CustomerUpdateInfo();
        Model.Customer existingUser = customerDB.getCustomerByUsername(newUser);
        if (existingUser != null) {
            JOptionPane.showMessageDialog(createPanel, "Username taken, choose another.", "New User Creation Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        customerDB.insertCustomer(newUser, newPassword);
        Model.Customer newCustomer = customerDB.getCustomer(newUser, newPassword);

        if (newCustomer == null) {
            System.err.println("Error: newly created user not found after insertion!"); // Use err for errors
            JOptionPane.showMessageDialog(createPanel, "An error occurred during user creation.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        mainFrame.setLoggedInCustomer(newCustomer);
        mainFrame.getBookingBuilder().setCustomer(newCustomer);

        UserDashboardPanel db = (UserDashboardPanel) mainFrame.getPanel("UserDashboard");
        db.updateUserGreeting(newCustomer);
        db.refreshBookings();
        WelcomePanel welcomePanel = (WelcomePanel) mainFrame.getPanel("Welcome"); // Get here as needed
        welcomePanel.updateUserLabel();

        String postLogin = mainFrame.getPostLoginTarget();
        if (postLogin != null) {
            mainFrame.clearPostLoginTarget();
            if (postLogin.equals("ConfirmPanel")) {
                mainFrame.showUpdatedConfirmPanel();
            } else {
                mainFrame.showPanel(postLogin);
            }
        } else { // If no specific target, go to UserDashboard by default
            mainFrame.showPanel("UserDashboard");
        }
        createPanel.clearFields(); // clear fields after creation attempt
    }

    private void handleConfirmBooking() {
        Model.Customer currentCustomer = mainFrame.getLoggedInCustomer();
        if (currentCustomer == null) {
            JOptionPane.showMessageDialog(mainFrame, "You must be logged in to confirm a booking.", "Login Required", JOptionPane.WARNING_MESSAGE);
            return; // Exit early if not logged in
        }

        builder.setCustomer(currentCustomer);

        BookingUpdateInfo bookingDb = new BookingUpdateInfo();
        bookingDb.insertUpdate(builder);
        System.out.println("Booking is saved to database");

        mainFrame.showPanel("BookingConfirmed");
        mainFrame.resetAllBookingPanels();
        mainFrame.updateBookingPanels(); // refresh RoomSelectionPanel
        mainFrame.resetBookingBuilder(); // clear booking builder for new booking.
    }

    private void handleCancelBooking() {
        UserDashboardPanel dashboardPanel = (UserDashboardPanel) mainFrame.getPanel("UserDashboard");
        int selectedRow = dashboardPanel.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Please select a booking to cancel.", "No Booking Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to cancel this booking?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int bookingId = (int) dashboardPanel.getTableModel().getValueAt(selectedRow, 0);

        BookingUpdateInfo cancelDb = new BookingUpdateInfo();
        cancelDb.deleteBookingById(bookingId);

        JOptionPane.showMessageDialog(mainFrame, "Booking cancelled successfully.", "Cancellation Confirmed", JOptionPane.INFORMATION_MESSAGE);
        dashboardPanel.refreshBookings(); // This will refresh the table display
        mainFrame.updateBookingPanels(); 
    }

    private void handleProceedToConfirmation() {
        DateSelectionPanel datePanel = (DateSelectionPanel) mainFrame.getPanel("DateSelection");
        DateSelectionData dateData = datePanel.getDateSelectionInput();
        Model.Customer customer = mainFrame.getLoggedInCustomer();

        // Populate the builder with date information
        builder.setDay(dateData.getDay());
        builder.setCustomer(customer);
        builder.setMonth(dateData.getMonth());
        builder.setEndDay(dateData.getEndDay());
        builder.setEndMonth(dateData.getEndMonth());
        builder.setLengthOfStay(dateData.getLengthOfStay());
        builder.setTime(dateData.getTime());
        builder.setTotalPrice();

        if (customer == null) {
            mainFrame.setPostLoginTarget("ConfirmPanel");
            mainFrame.showPanel("Login");
        } else {
            mainFrame.showUpdatedConfirmPanel();
        }
    }

}
