/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author gcoll
 */

//Class to house methods to style buttons uniformly across all the panels.
public class StyleButtons {
    
    // Default style for nav buttons, only color changes based on argument
    public static void styleNavigationButton(JButton button, Color bgColor) {
        button.setPreferredSize(new Dimension(160, 35));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }
      //Default style for each action button, only the color changes based on arguments / slightly smaller
    public static void styleActionButton(JButton button, Color bgColor) {
        button.setPreferredSize(new Dimension(130, 35));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }
}
