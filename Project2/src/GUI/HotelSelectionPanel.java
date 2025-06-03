package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class HotelSelectionPanel extends JPanel {

    private HotelFrame mainFrame;
    //private NavigationPanel navigationPanel;
    // Hotel selectedHotel;

    public HotelSelectionPanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
        ActionListener controller = new ActionController(mainFrame);
        //this.navigationPanel = new NavigationPanel(mainFrame, controller);
        initUI(controller);

    }

    // test
    private void createHotelSelectionPanel(ActionListener controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

    }

    private void initUI(ActionListener controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        this.setBackground(Color.WHITE);

        JLabel title = new JLabel("Select a Hotel:");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // Hotel options - Need to load from database
        String[][] hotels = {
            {"Azure", "Auckland"},
            {"Celestial", "Taupo"},
            {"The Crown", "Wellington"},
            {"SkyCity", "Auckland"},
            {"Skyline Suites", "Rotorua"}
        };

        String[] columnNames = {"Hotel", "Location"};
        //Create table that displays hotels and location
        JTable hotelTable = new JTable(hotels, columnNames);
        hotelTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        hotelTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(hotelTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0; // controls extra space
        gbc.weighty = 0.5;
        scrollPane.setPreferredSize(new Dimension(600, 200));
        gbc.fill = GridBagConstraints.BOTH; // expand horizontally not vertically- so no empty space
        this.add(scrollPane, gbc);

        // settings for backnext panel
        gbc.gridy = hotels.length + 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        //Create booking proccess navigation buttons at bottom
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller, "Welcome", "RoomSelection");
        gbc.gridwidth = 3;
        add(backNextPanel, gbc);

      

    }

    public static void main(String[] args) {
        HotelFrame f = new HotelFrame();

    }
}
