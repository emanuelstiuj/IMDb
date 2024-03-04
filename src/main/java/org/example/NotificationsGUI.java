package org.example;

import javax.management.Notification;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class NotificationsGUI extends JFrame {
    private User user;
    private JPanel notificationsPanel;
    private JScrollPane scrollPane;

    public NotificationsGUI (User user) {
        this.user = user;
        notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));
        notificationsPanel.setBackground(new Color(40, 40, 40));

        ArrayList<String> myUnsolvednotifications = (ArrayList<String>) user.getNotifications();
        Collections.reverse(myUnsolvednotifications);
        int countnotifications = 0;
        for (String notification : myUnsolvednotifications) {
            JPanel notificationInfoPanel = createnotificationInfoPanel(notification);
            notificationsPanel.add(notificationInfoPanel);
            countnotifications++;
        }
        Collections.reverse(myUnsolvednotifications);

        if (countnotifications == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);

            JLabel label = new JLabel("YOU HAVE NOT RECEIVED ANY NOTIFICATION");
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setPreferredSize(new Dimension(690, 690));
            panel.add(label, BorderLayout.CENTER);
            getContentPane().add(panel, BorderLayout.CENTER);
        } else {

            notificationsPanel.setPreferredSize(new Dimension(680, 160 * countnotifications));

            scrollPane = new JScrollPane(notificationsPanel);
            scrollPane.setPreferredSize(new Dimension(690, scrollPane.getPreferredSize().height));
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setBackground(new Color(15, 15, 15));
            notificationsPanel.setBackground(new Color(15, 15, 15));

            getContentPane().setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(40, 40, 40));
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
        }

        setTitle("My Notifications");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createnotificationInfoPanel(String notification) {
        JPanel notificationInfoPanel = new JPanel(new BorderLayout());
        notificationInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        notificationInfoPanel.setBackground(new Color(35, 35, 35));
        Border border =  new LineBorder(Color.BLACK, 6);
        notificationInfoPanel.setBorder(border);

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JTextArea bioTextArea = new JTextArea(notification);
        bioTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bioTextArea.setFont(labelFont);
        bioTextArea.setForeground(Color.LIGHT_GRAY);
        bioTextArea.setBackground(new Color(35, 35, 35));
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setEditable(false);
        bioTextArea.setCaretPosition(0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(35, 35, 35));

        JButton deleteButton = new JButton("Delete Notification");
        deleteButton.setBackground(new Color(10, 10, 10));
        deleteButton.setForeground(new Color(220, 220, 220));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.getNotifications().remove(notification);
                bioTextArea.setCaretPosition(0);

                refreshInfo();
            }
        });

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });

        buttonPanel.add(deleteButton);

        notificationInfoPanel.add(bioTextArea, BorderLayout.CENTER);
        notificationInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        notificationInfoPanel.setMinimumSize(new Dimension(690, 0));
        notificationInfoPanel.setMaximumSize(new Dimension(690, 160));

        return notificationInfoPanel;
    }

    private void refreshInfo() {
        getContentPane().removeAll();

        notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));
        notificationsPanel.setBackground(new Color(40, 40, 40));

        ArrayList<String> myUnsolvednotifications = (ArrayList<String>) user.getNotifications();
        Collections.reverse(myUnsolvednotifications);
        int countnotifications = 0;
        for (String notification : myUnsolvednotifications) {
            JPanel notificationInfoPanel = createnotificationInfoPanel(notification);
            notificationsPanel.add(notificationInfoPanel);
            countnotifications++;
        }
        Collections.reverse(myUnsolvednotifications);

        if (countnotifications == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);

            JLabel label = new JLabel("YOU HAVE NOT RECEIVED ANY NOTIFICATION");
            label.setFont(font);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setForeground(Color.WHITE);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setPreferredSize(new Dimension(690, 690));
            panel.add(label, BorderLayout.CENTER);
            getContentPane().add(panel, BorderLayout.CENTER);
        } else {

            notificationsPanel.setPreferredSize(new Dimension(680, 160 * countnotifications));

            scrollPane = new JScrollPane(notificationsPanel);
            scrollPane.setPreferredSize(new Dimension(690, scrollPane.getPreferredSize().height));
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setBackground(new Color(15, 15, 15));
            notificationsPanel.setBackground(new Color(15, 15, 15));

            getContentPane().setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(40, 40, 40));
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
        }

        setTitle("My Notifications");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
