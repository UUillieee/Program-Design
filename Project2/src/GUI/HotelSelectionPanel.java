package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import dbpackage.RetrieveHotels;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import Model.Hotel;

public class HotelSelectionPanel extends JPanel implements ResettablePanel {

    private HotelFrame mainFrame;
    //private NavigationPanel navigationPanel;
    // Hotel selectedHotel;
    private BookingListener bookingListener;
    private ActionListener controller;
    private JTable hotelTable;
    private JButton nextButton;

    public void setBookingListener(BookingListener listener) {
        this.bookingListener = listener;
    }

    public HotelSelectionPanel(HotelFrame mainFrame, ActionListener controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        //this.navigationPanel = new NavigationPanel(mainFrame, controller);
    }

    protected void initUI(ActionListener controller) {
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

        // Hotel options retrieving from database
        String[][] hotels = new RetrieveHotels().getAllHotels();

        String[] columnNames = {"ID", "Hotel", "Location"};
        //Create table that displays hotels and location

        DefaultTableModel tableModel = new DefaultTableModel(hotels, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        hotelTable = new JTable(tableModel);
        hotelTable.getTableHeader().setResizingAllowed(false); // Cant resize the columns
        hotelTable.getTableHeader().setReorderingAllowed(false);
        hotelTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        hotelTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        hotelTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        hotelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only select one at a time
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

        //Get hotel from selected one and give to room selection to display rooms in that hotel.
        nextButton = getNextButtonFromBackNextPanel(backNextPanel);
        nextButton.setEnabled(false); // initially disabled

        //Doesnt move on until valid hotel is selected - so room panel can display rooms in that hotel
        //Figure out how to move this code elsewhere
        hotelTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = hotelTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int hotelId = Integer.parseInt(hotelTable.getValueAt(selectedRow, 0).toString());
                        String hotelName = (String) hotelTable.getValueAt(selectedRow, 1);
                        String hotelLocation = (String) hotelTable.getValueAt(selectedRow, 2);
                        Hotel selectedHotel = new Hotel(hotelId, hotelName, hotelLocation); // create your Hotel object
                        mainFrame.getBookingBuilder().setHotel(selectedHotel);
                        //show hotel choosen and id choosen 
                        System.out.println("Hotel: set " + selectedHotel.getName() + " (ID: " + selectedHotel.getId() + ")");
                        nextButton.setEnabled(true); // enable navigation after valid selection
                        //Notify listeners
                        if (bookingListener != null) {
                            bookingListener.onHotelSelected(selectedHotel);
                        }

                    }
                }
            }
        });

    }

    private JButton getNextButtonFromBackNextPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton btn && "Next".equals(btn.getText())) {
                return btn;
            }
        }
        return null;
    }

    @Override
    public void resetFields() {
        if (hotelTable != null) {
            hotelTable.clearSelection(); // Clear any row selection
        }
        // Reset next button to false
        if (nextButton != null) {
            nextButton.setEnabled(false);
        }
    }
}
