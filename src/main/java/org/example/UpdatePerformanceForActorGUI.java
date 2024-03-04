package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdatePerformanceForActorGUI extends JFrame {

    private JLabel titleLabel;

    public Performance getPerformance(ArrayList<Performance> performances, String performance) {
        for (Performance p : performances) {
            if (p.getTitle().equals(performance))
                return p;
        }
        return null;
    }

    public UpdatePerformanceForActorGUI(String title, String type, Boolean newInfo, DefaultListModel defaultListModel,
                                ArrayList<Performance> copyperformances, int index) {

        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(40, 40, 40));

        if (newInfo)
            titleLabel = new JLabel("New performance");
        else
            titleLabel = new JLabel("Change Performance Information");

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(40, 40, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel1.add(titleLabel);
        panel1.setPreferredSize(new Dimension(350, 35));

        add(panel1, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBackground(new Color(40, 40, 40));
        JTextField nameField;
        JTextField durationField;

        JLabel titleLabel = new JLabel("Performane Title:");
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(40, 40, 40));
        titlePanel.add(titleLabel);
        panel.add(titlePanel);

        if (newInfo) {
            nameField = new JTextField();
            panel.add(nameField);
        } else {
            nameField = new JTextField(title);
            panel.add(nameField);
        }

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setForeground(Color.LIGHT_GRAY);
        typeLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel typePanel = new JPanel();
        typePanel.setBackground(new Color(40, 40, 40));
        typePanel.add(typeLabel);
        panel.add(typePanel);

        if (newInfo) {
            durationField = new JTextField();
            panel.add(durationField);
        } else {
            durationField = new JTextField(type);
            panel.add(durationField);
        }

        add(panel, BorderLayout.CENTER);

        JButton saveButton = new JButton("SAVE CHANGES");
        saveButton.setBackground(new Color(25, 25, 25));
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(350, 35));

        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!nameField.getText().matches("^[\\p{L}0-9\\s.,'\"()-]+$")) {
                    showWarning("Invalid title format");
                    return;
                }

                if (!durationField.getText().equals("Movie") && !durationField.getText().equals("Series")) {
                    showWarning("Type must be \"Movie\" or \"Series\"");
                    return;
                }

                if (newInfo) {
                    Performance performance = new Performance();
                    performance.setTitle(nameField.getText());
                    performance.setType(durationField.getText());
                    copyperformances.add(performance);
                    defaultListModel.addElement(nameField.getText());
                } else {
                    Performance epCopy = getPerformance(copyperformances, title);
                    epCopy.setTitle(nameField.getText());
                    epCopy.setType(durationField.getText());
                    defaultListModel.remove(index);
                    defaultListModel.add(index, nameField.getText());
                }
                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        setSize(new Dimension(350, 250));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
