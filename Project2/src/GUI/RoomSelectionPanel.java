package GUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Model.Hotel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author George
 */

public class RoomSelectionPanel extends JPanel implements BookingListener {

    private HotelFrame mainFrame;
    private JLabel title;
    private DefaultTableModel tableModel;
    private JTable table;

    public RoomSelectionPanel(HotelFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    protected void initUI(ActionListener controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        setBackground(Color.WHITE);

        // Title label
        title = new JLabel("Please Select from Rooms in Hotel:");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // Table model (start empty)
        String[] columnNames = {"Hotel", "Room Type", "Cost Per Night", "Max Guests", "Availability"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, gbc);

        // Navigation panel
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        JPanel backNextPanel = NavigationPanel.createBookingProccessButtons(controller, "HotelSelection", "DateSelection");

        JButton nextButton = getNextButtonFromBackNextPanel(backNextPanel);
        if (nextButton != null) {
            nextButton.setEnabled(false); // Disable BEFORE adding to layout
        }
        add(backNextPanel, gbc);

        table.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Extract room number, cost, and guests from selected row
                String roomType = (String) table.getValueAt(selectedRow, 1);
                double cost = Double.parseDouble(table.getValueAt(selectedRow, 2).toString());
                int guests = Integer.parseInt(table.getValueAt(selectedRow, 3).toString());
                int roomNumber = selectedRow + 1; // or use actual room number from DB if provided

                // Store room info into BookingBuilder
                mainFrame.getBookingBuilder().setRoomNumber(roomNumber);
                mainFrame.getBookingBuilder().setRoomPrice(cost);
                mainFrame.getBookingBuilder().setGuests(guests);

                System.out.println("Room Selected: #" + roomNumber + " | Type: " + roomType + " | Cost: " + cost + " | Guests: " + guests);

                nextButton.setEnabled(true); // Enable the Next button
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
    
    //when hotel is selected, call updateBookingInfo
    public void onHotelSelected(Hotel hotel) {
        updateBookingInfo();
        System.out.println("updateBookingInfo triggered");
    }
    
    @Override
    public void updateBookingInfo() {
        Hotel selectedHotel = mainFrame.getBookingBuilder().getHotel();

        if (selectedHotel != null) {
            title.setText("Please Select from Rooms in Hotel: " + selectedHotel.getName());

            //clear existing rows
            tableModel.setRowCount(0);

            //fetch rooms from DB
            java.util.List<String[]> roomList = dbpackage.RetrieveRooms.getRoomsByHotelId(selectedHotel.getId());
            
            
            for (String[] row : roomList) {
                tableModel.addRow(row);
            }

            revalidate();
            repaint();
        }
    }
}
