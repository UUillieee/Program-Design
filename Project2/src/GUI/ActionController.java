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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import dbpackage.CustomerUpdateInfo;
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
                    //Call database logic
                    //check if fields are empty
                    LoginPanel loginPanel = (LoginPanel) mainFrame.getPanel("Login");
                    String loginUser = loginPanel.getUsername();
                    String loginPassword = loginPanel.getPassword();
                    
                    if (loginUser.isEmpty() || loginPassword.isEmpty()) {
                        System.out.println("Username or password cannot be empty.");
                        return;
                    }
                    
                   //Get customer from database
                    CustomerUpdateInfo customerDb = new CustomerUpdateInfo();
                    Model.Customer loggedInCustomer = customerDb.getCustomer(loginUser, loginPassword);

                    if (loggedInCustomer == null) {
                        System.out.println("Login failed: Invalid credentials.");
                        return;
                     }

                     //Save logged-in customer to BookingBuilder
                    mainFrame.getBookingBuilder().setCustomer(loggedInCustomer);

                    System.out.println("Login success: Welcome " + loggedInCustomer.getName());

                    //go to the dashboard
                     mainFrame.showPanel("UserDashboard");
                    break;
                case LOGOUT:
                    mainFrame.showPanel("Login");
                    break;
                case CREATE_USER:
                    //Call Database logic 
                    //Create new user with username and password in login panel
                    LoginPanel createPanel = (LoginPanel) mainFrame.getPanel("Login");
                    String newUser = createPanel.getUsername();
                    String newPassword = createPanel.getPassword();
                    //check if fields are empty
                    if (newUser.isEmpty() || newPassword.isEmpty()) {
                        System.out.println("Username or password cannot be empty.");
                        return;
                    }

                    //insert the username and password into the database
                    new dbpackage.CustomerUpdateInfo().insertCustomer(newUser, newPassword);

                    //switch to the next panel
                    mainFrame.showPanel("Booking");
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