/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import static GUI.Command.CREATE_USER;
import static GUI.Command.EXIT;
import static GUI.Command.LOGIN;
import static GUI.Command.LOGOUT;
import static GUI.Command.SWITCH_PANEL;
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
//Class to monitor all the buttons on the GUI, shows next panel on press, provides login button actions etc etc
public class ActionController implements ActionListener {

    private HotelFrame mainFrame;

    public ActionController(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Command command = Command.valueOf(e.getActionCommand()); //use enum to Get what action event e.g whether to exit, switch panel or login
            switch (command) {
                case SWITCH_PANEL: //To
                    //Target panel is passed to action controller - use that to switch to the desired panel
                    if (e.getSource() instanceof JButton btn) { //if coming from button
                        Object panelName = btn.getClientProperty("targetPanel"); // get target panel from the value that is attatched to "taragetPanel" key.
                        if (panelName instanceof String) { //If the key is a string
                            mainFrame.showPanel((String) panelName); // Switch to that panel
                        } else {
                            System.out.println("No target panel set for this button."); // error
                        }
                    }
                    break;
                case LOGIN:
                    //get username & password
                    LoginPanel loginPanel = (LoginPanel) mainFrame.getPanel("Login");
                    String loginUser = loginPanel.getUsername();
                    String loginPassword = loginPanel.getPassword();               
                    //check if username or password fields are empty
                    if (loginUser.isEmpty() || loginPassword.isEmpty()) {
                        System.out.println("Username or password cannot be empty.");
                        return;
                    }

                    //Get customer from database
                    CustomerUpdateInfo customerDb = new CustomerUpdateInfo();
                    Model.Customer customer = customerDb.getCustomer(loginUser, loginPassword);

                    if (customer != null) {
                        System.out.println("Login successful! Welcome " + customer.getUsername());

                        mainFrame.setLoggedInCustomer(customer);
                        mainFrame.getBookingBuilder().setCustomer(customer);
                        mainFrame.showPanel("UserDashboard");

                        UserDashboardPanel dashboard = (UserDashboardPanel) mainFrame.getPanel("UserDashboard");
                        dashboard.updateUserGreeting(customer);
                        dashboard.refreshBookings();
                        //mainFrame
                    } 
                    else {
                        JOptionPane.showMessageDialog(loginPanel, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        return; // Keep showing login panel, dont allow switch to next panel till customer valid
                    }
                    
                    //So login panel knows where to direct to next, e.g go to bookingconfirmed or userdashboard
                    //Based on where login panel is entered from, 1) if it is entered from booking proccess, go booking confirmed,2) entered from main menu, go user dashboard.
                    String postLogin = mainFrame.getPostLoginTarget();
                    if (postLogin != null) {
                        mainFrame.clearPostLoginTarget();
                        if (postLogin.equals("ConfirmPanel")) {
                            mainFrame.showUpdatedConfirmPanel();
                            
                        } else {
                            mainFrame.showPanel(postLogin);
                        }
                    }

                    break;
                case LOGOUT:
                    mainFrame.showPanel("Login");
                    mainFrame.setLoggedInCustomer(null);
                    break;
                case CREATE_USER:
                    //get username & password
                    LoginPanel createPanel = (LoginPanel) mainFrame.getPanel("Login");
                    String newUser = createPanel.getUsername();
                    String newPassword = createPanel.getPassword();
                    //check if username or passwords fields are empty
                    if (newUser.isEmpty() || newPassword.isEmpty()) {
                        System.out.println("Username or password cannot be empty.");
                        return;
                    }
                    
                    //create customerDB object to pass to CustomerUpdateInfo
                    CustomerUpdateInfo customerDB = new CustomerUpdateInfo();
                    
                    //check if the username already exists so no duplicate accounts can be created
                    Model.Customer existingUser = customerDB.getCustomerByUsername(newUser);
                    if (existingUser != null) {
                        System.out.println("Error: Username already exists. Please choose another one.");
                        return;
                    }
                    
                    //pass the username and password to insertCustomer() to put in the database
                    customerDB.insertCustomer(newUser, newPassword);

                    //immediately fetch the newly created user
                    Model.Customer newCustomer = customerDB.getCustomer(newUser, newPassword);
                    if (newCustomer == null) {
                        System.out.println("Error: newly created user not found!");
                        return;
                    }

                    //set new user as the logged-in customer
                    mainFrame.setLoggedInCustomer(newCustomer);
                    mainFrame.getBookingBuilder().setCustomer(newCustomer);

                    //update greeting and reload bookings
                    UserDashboardPanel db = (UserDashboardPanel) mainFrame.getPanel("UserDashboard");
                    db.updateUserGreeting(newCustomer);
                    db.refreshBookings();

                    //switch to dashboard panels
                    mainFrame.showPanel("UserDashboard");
                    //Same logic as login case.
                    postLogin = mainFrame.getPostLoginTarget();
                    if (postLogin != null) {
                        mainFrame.clearPostLoginTarget();
                        if (postLogin.equals("ConfirmPanel")) {
                            mainFrame.showUpdatedConfirmPanel();
                        } else {
                            mainFrame.showPanel(postLogin);
                        }
                    }
                    break;

                case CONFIRM_BOOKING:
                    Model.Customer currentCustomer = mainFrame.getLoggedInCustomer();
                    if (currentCustomer == null) {
                        javax.swing.JOptionPane.showMessageDialog(mainFrame, "You must be logged in to confirm a booking.", "Login Required", javax.swing.JOptionPane.WARNING_MESSAGE);
                    } else {
                        mainFrame.getBookingBuilder().setCustomer(currentCustomer);
                        BookingUpdateInfo bookingDb = new BookingUpdateInfo();
                        bookingDb.insertUpdate(mainFrame.getBookingBuilder());
                        System.out.println("Booking is saved to database");
                        mainFrame.showPanel("BookingConfirmed");
                        mainFrame.resetBookingBuilder(); // Clear booking builder for new booking.
                    }
                    break;
                case CANCEL:
                    //remove the booking from the database bookings table
                    UserDashboardPanel dashboardPanel = (UserDashboardPanel) mainFrame.getPanel("UserDashboard");
                    //selected row in the panels table
                    int selectedRow = dashboardPanel.getSelectedRow(); 
                    if (selectedRow == -1) {
                        System.out.println("No booking selected to cancel.");
                        return;
                    }
                    
                    //confirmation panel to double check with the user if they want to delete the booking
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this booking?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) return;

                    int bookingId = (int) dashboardPanel.getTableModel().getValueAt(selectedRow, 0);
                    BookingUpdateInfo cancelDb = new BookingUpdateInfo();
                    //pass the bookingId to deleteBookingById() to delete
                    cancelDb.deleteBookingById(bookingId);
                    //refresh bookings within the userpanel
                    dashboardPanel.refreshBookings();
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                // Add more cases as needed
                default:
                    System.out.println("Unhandled command: " + command);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown command: " + e.getActionCommand());
        }
    }
}
