
package GUI;


import GUI.ActionController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 */


//CREATE NEW BOOKING PANEL - not used yet
public class BookingPanel extends JPanel {

    
    public BookingPanel(HotelFrame mainFrame) {
        ActionListener controller = new ActionController(mainFrame);
        createBookingPanel(controller);
    }

    private void createBookingPanel(ActionListener controller) {
        JLabel bookingLabel = new JLabel("Booking");
        bookingLabel.setBounds(50,50,100,20);
        this.add(bookingLabel);
        
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(30, 30, 80, 20);
        backBtn.setActionCommand(Command.SWITCH_PANEL.name());
        backBtn.addActionListener(controller);
        backBtn.putClientProperty("targetPanel", "Login"); // tell controller where to go - (key, target) - update second string to tell controller which panel to switch to
        this.add(backBtn);

        JButton nextBtn = new JButton("Next");
        nextBtn.setBounds(30, 130, 80, 20);
        nextBtn.setActionCommand(Command.SWITCH_PANEL.name());
        nextBtn.addActionListener(controller);
        this.add(nextBtn);
    }

}
