package GUI;

import Model.Customer;
import java.awt.CardLayout;
import java.awt.Component;
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
    private ActionListener controller;
    private JPanel mainPanel;
    private RoomSelectionPanel roomPanel;
    private Customer loggedInCustomer;

    //store panels in Map<String, Jpanel> so can refernce them later from the action controller
    private Map<String, JPanel> panels = new HashMap<>();

    private BookingBuilder bookingBuilder = new BookingBuilder(); // shared booking state

    public HotelFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        controller = new ActionController(this); // Need to pass this 1 reference to all the panels. (Shared)

        //welcome Panel
        WelcomePanel welcomePanel = new WelcomePanel(this);
        panels.put("Welcome", welcomePanel);
        mainPanel.add(welcomePanel, "Welcome");

        //login Panel
        LoginPanel loginPanel = new LoginPanel(this);
        panels.put("Login", loginPanel);
        mainPanel.add(loginPanel, "Login");

        //room Selection Panel
        roomPanel = new RoomSelectionPanel(this);
        roomPanel.initUI(controller);
        panels.put("RoomSelection", roomPanel);
        mainPanel.add(roomPanel, "RoomSelection");

        //hotel Selection Panel
        HotelSelectionPanel hotelPanel = new HotelSelectionPanel(this, controller);
        hotelPanel.initUI(controller);
        panels.put("HotelSelection", hotelPanel);
        mainPanel.add(hotelPanel, "HotelSelection");
        hotelPanel.setBookingListener(roomPanel);

        //date selection panel
        DateSelectionPanel datePanel = new DateSelectionPanel(this, bookingBuilder, controller);
        panels.put("DateSelection", datePanel);
        mainPanel.add(datePanel, "DateSelection");

        //Booking Confirmation panel
        ConfirmPanel confPanel = new ConfirmPanel(this, bookingBuilder, controller);
        panels.put("ConfirmPanel", confPanel);
        mainPanel.add(confPanel, "ConfirmPanel");

        //Booking Confirmation panel
        BookingConfirmedPanel bookingConfirmed = new BookingConfirmedPanel(this, bookingBuilder, controller);
        panels.put("BookingConfirmed", bookingConfirmed);
        mainPanel.add(bookingConfirmed, "BookingConfirmed");

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

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    //return the customer that clicked login
    public Customer getLoggedInCustomer() {
        return this.loggedInCustomer;
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
        //get panel name stored in a map
        return panels.get(panelName);
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
        //hotelframe may not need to do anything
    }

    public void showUpdatedConfirmPanel() {
        ConfirmPanel confirmPanel = (ConfirmPanel) panels.get("ConfirmPanel");
        if (confirmPanel != null) {
            confirmPanel.refresh(); // Update booking data display
        }
        showPanel("ConfirmPanel");
    }

}
