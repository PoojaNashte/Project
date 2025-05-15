package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoatBookingSystem extends JFrame {
    // Form components
    private JComboBox<String> boatNameCombo;
    private JComboBox<String> journeyPathCombo;
    private JSpinner dateSpinner;
    private JComboBox<String> timeSlotCombo;
    private JSpinner durationSpinner;
    private JTextField customerNameField;
    private JFormattedTextField contactNumberField;
    private JTextArea receiptArea;

    // Buttons
    private JButton bookButton;
    private JButton cancelButton;
    private JButton printButton;
    private JButton clearButton;
    private JButton viewBookingsButton;

    // Booking list
    private List<String> bookingReceipts = new ArrayList<>();

    // Colors
    private final Color BG_COLOR = new Color(240, 248, 255);
    private final Color PANEL_COLOR = new Color(173, 216, 230);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color BUTTON_TEXT_COLOR = Color.WHITE;

    public BoatBookingSystem() {
        setTitle("Boat Booking System");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        String[] boatNames = {
                "Ocean Queen", "Sea Explorer", "Wave Rider",
                "Blue Dolphin", "Sunset Cruiser", "Marine Voyager"
        };
        boatNameCombo = new JComboBox<>(boatNames);

        String[] journeyPaths = {
                "Harbor Tour (1 hour)", "Island Cruise (2 hours)",
                "Sunset Journey (3 hours)", "Fishing Trip (4 hours)",
                "Full Day Expedition (8 hours)"
        };
        journeyPathCombo = new JComboBox<>(journeyPaths);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        dateSpinner.setValue(new Date());

        String[] timeSlots = {
                "06:00 AM", "08:00 AM", "10:00 AM",
                "12:00 PM", "02:00 PM", "04:00 PM",
                "06:00 PM", "08:00 PM"
        };
        timeSlotCombo = new JComboBox<>(timeSlots);

        durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        customerNameField = new JTextField(20);
        contactNumberField = new JFormattedTextField(20);

        receiptArea = new JTextArea(10, 50);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        bookButton = createStyledButton("Book Boat");
        bookButton.addActionListener(e -> bookBoat());

        cancelButton = createStyledButton("Cancel Booking");
        cancelButton.addActionListener(e -> cancelBooking());

        printButton = createStyledButton("Print Receipt");
        printButton.addActionListener(e -> printReceipt());

        clearButton = createStyledButton("Clear Form");
        clearButton.addActionListener(e -> clearForm());

        viewBookingsButton = createStyledButton("View All Bookings");
        viewBookingsButton.addActionListener(e -> showAllBookings());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(BG_COLOR);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        formPanel.setBackground(PANEL_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(formPanel, gbc, 0, "Boat Name:", boatNameCombo);
        addFormRow(formPanel, gbc, 1, "Journey Path:", journeyPathCombo);
        addFormRow(formPanel, gbc, 2, "Booking Date:", dateSpinner);
        addFormRow(formPanel, gbc, 3, "Time Slot:", timeSlotCombo);
        addFormRow(formPanel, gbc, 4, "Duration (hours):", durationSpinner);
        addFormRow(formPanel, gbc, 5, "Customer Name:", customerNameField);
        addFormRow(formPanel, gbc, 6, "Contact Number:", contactNumberField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(printButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(viewBookingsButton);

        formPanel.add(buttonPanel, createGbc(0, 7, 2, 1));

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Booking Receipt"));
        receiptPanel.setBackground(PANEL_COLOR);
        receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(receiptPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private GridBagConstraints createGbc(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void bookBoat() {
        String boatName = (String) boatNameCombo.getSelectedItem();
        String journeyPath = (String) journeyPathCombo.getSelectedItem();
        Date bookingDate = (Date) dateSpinner.getValue();
        String timeSlot = (String) timeSlotCombo.getSelectedItem();
        int duration = (int) durationSpinner.getValue();
        String customerName = customerNameField.getText().trim();
        int contactNumber = (int) contactNumberField.getValue();

        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        double price = calculatePrice(journeyPath, duration);

        String receipt = "===================================\n" +
                "       BOAT BOOKING RECEIPT\n" +
                "===================================\n" +
                String.format("%-20s: %s\n", "Booking Date", dateFormat.format(bookingDate)) +
                String.format("%-20s: %s\n", "Time Slot", timeSlot) +
                String.format("%-20s: %s\n", "Boat Name", boatName) +
                String.format("%-20s: %s\n", "Journey Path", journeyPath) +
                String.format("%-20s: %d hours\n", "Duration", duration) +
                String.format("%-20s: $%.2f\n", "Price", price) +
                "-----------------------------------\n" +
                String.format("%-20s: %s\n", "Customer Name", customerName) +
                String.format("%-20s: %s\n", "Contact Number", contactNumber) +
                "===================================\n" +
                "   Thank you for your booking!\n" +
                "===================================\n";

        receiptArea.setText(receipt);
        bookingReceipts.add(receipt);
        JOptionPane.showMessageDialog(this, "Booking confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelBooking() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?",
                "Cancel Booking", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            receiptArea.setText("");
            JOptionPane.showMessageDialog(this, "Booking cancelled", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void printReceipt() {
        if (receiptArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No booking to print", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean complete = receiptArea.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, "Printing complete", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Printing cancelled", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Printing failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        boatNameCombo.setSelectedIndex(0);
        journeyPathCombo.setSelectedIndex(0);
        dateSpinner.setValue(new Date());
        timeSlotCombo.setSelectedIndex(0);
        durationSpinner.setValue(1);
        customerNameField.setText("");
        contactNumberField.setValue(1);
        receiptArea.setText("");
    }

    private void showAllBookings() {
        if (bookingReceipts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings available", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextArea allReceipts = new JTextArea(25, 60);
        allReceipts.setFont(new Font("Monospaced", Font.PLAIN, 12));
        allReceipts.setEditable(false);

        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String receipt : bookingReceipts) {
            sb.append("Booking #").append(index++).append(":\n").append(receipt).append("\n");
        }

        allReceipts.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(allReceipts);
        JOptionPane.showMessageDialog(this, scrollPane, "All Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    private double calculatePrice(String journeyPath, int duration) {
        double basePrice;
        if (journeyPath.contains("Harbor Tour")) basePrice = 50;
        else if (journeyPath.contains("Island Cruise")) basePrice = 80;
        else if (journeyPath.contains("Sunset Journey")) basePrice = 100;
        else if (journeyPath.contains("Fishing Trip")) basePrice = 120;
        else if (journeyPath.contains("Full Day")) basePrice = 250;
        else basePrice = 60;
        return basePrice * duration;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BoatBookingSystem().setVisible(true);
        });
    }
}
