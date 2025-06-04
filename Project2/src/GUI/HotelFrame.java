package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Model.Hotel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 */
public class HotelFrame extends JFrame implements BookingListener {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private RoomSelectionPanel roomPanel;

    private BookingBuilder bookingBuilder = new BookingBuilder(); // shared booking state

    public HotelFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        ActionListener controller = new ActionController(this);

        //Add Panels to the main panel
        mainPanel.add(new WelcomePanel(this), "Welcome");// String is the name that you call to switch cards
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new BookingPanel(this), "Booking");

        roomPanel = new RoomSelectionPanel(this);
        roomPanel.initUI(controller);
        mainPanel.add(roomPanel, "RoomSelection");



        HotelSelectionPanel hotelPanel = new HotelSelectionPanel(this);
        hotelPanel.initUI(controller);
        //hotelPanel.setBookingListener(this);
        mainPanel.add(hotelPanel, "HotelSelection");

        mainPanel.add(new UserDashboardPanel(this), "UserDashboard");

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
        mainPanel.revalidate(); // refresh layout
        mainPanel.repaint();    // force redraw
    }

    public BookingBuilder getBookingBuilder() {
        return bookingBuilder;
    }

    // Add a reset if needed
    public void resetBookingBuilder() {
        bookingBuilder = new BookingBuilder();
    }

    public RoomSelectionPanel getRoomSelectionPanel() {
        return roomPanel;
    }

    public void updateBookingPanels() {
        // For example, you can do:
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof BookingListener updatable) {
                updatable.updateBookingInfo();
            }
        }
    }

    @Override
    public void onHotelSelected(Hotel hotel) {
        bookingBuilder.setHotel(hotel);
        updateBookingPanels();
    }

}
