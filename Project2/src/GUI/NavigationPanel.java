/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author gcoll This panel class can be put at the bottom of any panel Has
 * return to main menu and logout buttons
 */
public class NavigationPanel {

    HotelFrame mainFrame;

    public NavigationPanel(HotelFrame mainFrame, ActionListener controller) {
        this.mainFrame = mainFrame;
        createMenuExitPanel(controller);
    }

    public static JPanel createMenuExitPanel(ActionListener controller) {
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Navigation",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        // Return to Main Menu Button
        JButton mainMenuButton = new JButton("Return to Main Menu");
        StyleButtons.styleNavigationButton(mainMenuButton, new Color(60, 179, 113)); // green
        mainMenuButton.setActionCommand(Command.SWITCH_PANEL.name());
        mainMenuButton.addActionListener(controller);
        mainMenuButton.putClientProperty("targetPanel", "Welcome");
        navigationPanel.add(mainMenuButton);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        StyleButtons.styleNavigationButton(logoutButton, new Color(128, 128, 128)); // Gray
        logoutButton.setActionCommand(Command.LOGOUT.name()); //previously logout
        logoutButton.addActionListener(controller);
        logoutButton.putClientProperty("targetPanel", "Welcome");
        navigationPanel.add(logoutButton);

        return navigationPanel;
    }

    public static JPanel createBookingProccessButtons(
            ActionListener controller,
            String backTarget,
            String nextTarget)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setActionCommand(Command.SWITCH_PANEL.name());
        backBtn.addActionListener(controller);
        backBtn.putClientProperty("targetPanel", backTarget);
        StyleButtons.styleNavigationButton(backBtn,new Color(255, 102, 102)); //  red
        panel.add(backBtn);

        // Next Button
        JButton nextBtn = new JButton("Next");
        nextBtn.setActionCommand(Command.SWITCH_PANEL.name());
        nextBtn.addActionListener(controller);
        nextBtn.putClientProperty("targetPanel", nextTarget);
        StyleButtons.styleNavigationButton(nextBtn, new Color(30, 144, 255)); // Blue 
        panel.add(nextBtn);
        
        // Return to Main Menu Button
        JButton mainMenuButton = new JButton("Return to Main Menu");
        StyleButtons.styleNavigationButton(mainMenuButton, new Color(60, 179, 113)); // green
        mainMenuButton.setActionCommand(Command.SWITCH_PANEL.name());
        mainMenuButton.addActionListener(controller);
        mainMenuButton.putClientProperty("targetPanel", "Welcome");
        panel.add(mainMenuButton);

        return panel;
    }

}
