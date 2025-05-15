package org.example;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main extends JFrame {

    public Main() {
        setTitle("POLCO SAILING");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ✅ Try to load image and debug print
        URL imageUrl = getClass().getResource("/org.example/bg.jpg");
        System.out.println("Image URL: " + imageUrl);

        ImageIcon bgIcon = null;
        if (imageUrl != null) {
            bgIcon = new ImageIcon(imageUrl);
        } else {
            JOptionPane.showMessageDialog(this, "Background image not found at /org/example/bg.jpg", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Image img = (bgIcon != null) ? bgIcon.getImage() : null;

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.RED);
                    g.drawString("Image not loaded!", 20, 20);
                }
            }
        };
        backgroundPanel.setLayout(null); // absolute positioning

        JLabel title = new JLabel("POLCO SAILING");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setBounds(400, 200, 600, 50);
        backgroundPanel.add(title);

        JLabel subtitle = new JLabel("Small Group & Private Sailing Yacht, Catamaran and Rib Charters in Milos");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBounds(250, 260, 800, 30);
        backgroundPanel.add(subtitle);

        JButton bookButton = new JButton("BOOK YOUR CRUISE");
        bookButton.setFont(new Font("Arial", Font.BOLD, 20));
        bookButton.setBackground(Color.ORANGE);
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.setBounds(500, 320, 250, 50);
        backgroundPanel.add(bookButton);

        // ✅ Open BoatBookingSystem on click
        bookButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                BoatBookingSystem bookingSystem = new BoatBookingSystem();
                bookingSystem.setVisible(true);
            });
            // Optional: close the main window
            dispose(); // close current window if desired
        });

        add(backgroundPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
