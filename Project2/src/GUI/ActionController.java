/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

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
                    mainFrame.showPanel("Booking"); // del this
                    break;
                case LOGOUT:
                    mainFrame.showPanel("Login");
                    break;
                case CREATE_USER:
                    //Call Database logic 
                    //Create new user with username and password in login panel
                    mainFrame.showPanel("BookingPanel"); //del this
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
