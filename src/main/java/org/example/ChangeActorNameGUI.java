package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeActorNameGUI extends JFrame {
    private JTextField textField;
    private JLabel textLabel;
    private Actor actor;
    private User user;

    public ChangeActorNameGUI(Boolean newInfo, Actor actor, User user) {
        this.user = user;
        textLabel = new JLabel("Actor's name:");
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.add(textLabel, BorderLayout.SOUTH);

        textField = new JTextField(actor.getName());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(40, 40, 40));
        saveButton.setForeground(Color.WHITE);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();

                if (!text.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
                    showWarning("Invalid name format. Correct format example: Brad Pitt");
                    return;
                }

                actor.setName(text);

                if (!newInfo)
                    ((Staff) user).updateActor(actor);

                dispose();
            }
            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
        inputPanel.add(panel);
        inputPanel.add(textField);
        inputPanel.add(saveButton);

        add(inputPanel);
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
