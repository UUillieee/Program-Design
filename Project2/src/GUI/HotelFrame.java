package GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Model.Hotel;
import java.util.HashMap;
import java.util.Map;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

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


    //store panels in Map<String, Jpanel> so can refernce them later from the action controller
    private Map<String, JPanel> panels = new HashMap<>();

    private BookingBuilder bookingBuilder = new BookingBuilder(); // shared booking state

    public HotelFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        ActionListener controller = new ActionController(this);

        //welcome Panel
        WelcomePanel welcomePanel = new WelcomePanel(this);
        panels.put("Welcome", welcomePanel);
        mainPanel.add(welcomePanel, "Welcome");

        //login Panel
        LoginPanel loginPanel = new LoginPanel(this);
        panels.put("Login", loginPanel);
        mainPanel.add(loginPanel, "Login");

        //booking Panel
        BookingPanel bookingPanel = new BookingPanel(this);
        panels.put("Booking", bookingPanel);
        mainPanel.add(bookingPanel, "Booking");

        //room Selection Panel
        roomPanel = new RoomSelectionPanel(this);
        roomPanel.initUI(controller);
        panels.put("RoomSelection", roomPanel);
        mainPanel.add(roomPanel, "RoomSelection");

        //hotel Selection Panel
        HotelSelectionPanel hotelPanel = new HotelSelectionPanel(this);
        hotelPanel.initUI(controller);
        panels.put("HotelSelection", hotelPanel);
        mainPanel.add(hotelPanel, "HotelSelection");

        //user Dashboard Panel
        UserDashboardPanel userDashboard = new UserDashboardPanel(this);
        panels.put("UserDashboard", userDashboard);
        mainPanel.add(userDashboard, "UserDashboard");

        //add main panel to the frame
        add(mainPanel);
        setTitle("Hotel Booking System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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

    public JPanel getPanel(String panelName) {
            return panels.get(panelName); // assuming you use a Map<String, JPanel>
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

    @Override
    public void updateBookingInfo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}