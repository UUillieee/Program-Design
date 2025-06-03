package GUI;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 */
public class HotelFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public HotelFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //Add Panels to the main panel
        mainPanel.add(new WelcomePanel(this), "Welcome");// String is the name that you call to switch cards
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new BookingPanel(this), "Booking");
        mainPanel.add(new RoomSelectionPanel(this), "RoomSelection");
        mainPanel.add(new UserDashboardPanel(this), "UserDashboard");
        mainPanel.add(new HotelSelectionPanel(this),"HotelSelection");
        
        //Add the main panel to the frame
        add(mainPanel);
        this.setTitle("Hotel Booking System");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);  //Place in middle of monitro   
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.pack();        //Pack paenl snug
        this.setVisible(true);

    }

    public void showPanel(String name) {
        //Each panel can call this to go to a different panel
        cardLayout.show(mainPanel, name);

    }

}
