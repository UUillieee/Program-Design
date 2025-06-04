package GUI;

import Model.Hotel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomSelectionPanel extends JPanel  implements BookingListener {

    private HotelFrame mainFrame;
    NavigationPanel navigationPanel;
    private String hotelName;
    private JLabel title; // <-- Add this line so we can update it later

    public RoomSelectionPanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.hotelName = "No hotel selected";
    }

    protected void initUI(ActionListener controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        this.setBackground(Color.WHITE);

        //Update label to include selected hotel
        title = new JLabel("Please Select from Rooms in Hotel: " + hotelName);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // Room options - Need to load from database based on selected hotel
        //Each sub-array has: {hotelName, roomType, cost, maxGuests, quantity availible} 
        String[][] rooms = {
            {"Azure", "Penthouse", "500", "5", "1"},
            {"Azure", "Suite", "300", "4", "3"},
            {"Azure", "Single", "150", "5", "30"},
            {"SkyCity", "Penthouse", "500", "5", "10"}
        };
        String[] columnNames = {"Hotel", "Room Type", "Cost", "MaxGuests", "Availability"};

        //Create table that displays hotels and location
        JTable table = new JTable(rooms, columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0; // controls extra space
        gbc.weighty = 0.5;
        scrollPane.setPreferredSize(new Dimension(600, 200));
        gbc.fill = GridBagConstraints.BOTH; // expand horizontally not vertically- so no empty space
        this.add(scrollPane, gbc);

        // settings for backnext panel
        gbc.gridy = rooms.length + 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        //Create booking proccess navigation buttons at bottom                          /Back       /Next           - make sure to change
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller, "HotelSelection", "RoomSelection");
        gbc.gridwidth = 3;
        add(backNextPanel, gbc);
    }
    
    @Override
    public void updateBookingInfo() {
        Hotel selectedHotel = mainFrame.getBookingBuilder().getHotel();
        if (selectedHotel != null && title != null) {
            hotelName = selectedHotel.getName(); // update field (optional)
            title.setText("Please Select from Rooms in Hotel: " + hotelName);
            revalidate();
            repaint();
        }
    }

}
