package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;
import java.util.jar.JarEntry;

public class UpdateFieldGUI extends JFrame {
    private JTextField textField;
    private JLabel textLabel;

    public UpdateFieldGUI(FieldName fieldName, Boolean newInfo, Production production, DefaultListModel list,
                          String element, int index) {

        switch (fieldName.toString()) {
            case "Title":
                if (newInfo) {
                    textLabel = new JLabel("Add title:");
                    textField = new JTextField(production.getTitle());
                } else {
                    textLabel = new JLabel("Change title:");
                    textField = new JTextField(production.getTitle());
                }
                break;
            case "Director":
                if (newInfo) {
                    textLabel = new JLabel("Add new director");
                    textField = new JTextField(element);
                } else {
                    textLabel = new JLabel("Change director's name:");
                    textField = new JTextField(element);
                }
                break;
            case "Actor":
                if (newInfo) {
                    textLabel = new JLabel("Add new actor:");
                    textField = new JTextField(element);
                } else {
                    textLabel = new JLabel("Change actor's name:");
                    textField = new JTextField(element);
                }
                break;
            case "Duration":
                if (!newInfo) {
                    textLabel = new JLabel("Change movie's duration:");
                    textField = new JTextField(((Movie) production).getDuration());
                } else {
                    textLabel = new JLabel("Add movie duration");
                    textField = new JTextField(((Movie) production).getDuration());
                }
                break;
            case "ReleaseYear":
                if (newInfo) {
                    textLabel = new JLabel("Add release Year");
                    if (production instanceof Movie) {
                        textField = new JTextField(((Integer) ((Movie) production).getReleaseYear()).toString());
                    } else {
                        textField = new JTextField(((Integer) ((Series) production).getReleaseYear()).toString());
                    }
                } else {
                    textLabel = new JLabel("Change release year:");
                    if (production instanceof Movie) {
                        textField = new JTextField(((Integer) ((Movie) production).getReleaseYear()).toString());
                    } else {
                        textField = new JTextField(((Integer) ((Series) production).getReleaseYear()).toString());
                    }
                }
                break;
        }
        textLabel.setHorizontalAlignment(JLabel.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();

                if (fieldName.equals(FieldName.Title)) {
                    if (!text.matches( "^[\\p{L}0-9\\s.,'\"()-]+$")) {
                        showWarning("Invalid title format");
                        return;
                    }
                    if (IMDB.getInstance().getProduction(text) != null) {
                        showWarning(text + " already exists");
                        return;
                    }
                }

                if ((fieldName.equals(FieldName.Actor) || fieldName.equals(FieldName.Director))
                        && !text.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
                    showWarning("Invalid name format. Correct format example: Brad Pitt");
                    return;
                }

                if (fieldName.equals(FieldName.Duration) && !text.matches("^\\d+\\sminutes$")) {
                    showWarning("Invalid duration format. Correct format example: 25 minutes");
                    return;
                }

                if (fieldName.equals(FieldName.ReleaseYear)) {
                    try {
                        int releaseYear = Integer.parseInt(text);
                        if (releaseYear < 1 || releaseYear > 2024) {
                            showWarning("Please enter a valid year!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(UpdateFieldGUI.this,
                                "Please enter a valid year!");
                    }
                }

                if (fieldName.equals(FieldName.Director) || fieldName.equals(FieldName.Actor)) {
                    if (!newInfo) {
                        list.remove(index);
                        list.add(index, text);
                    } else {
                        list.addElement(text);
                    }
                } else {
                    switch (fieldName.toString()) {
                        case "Title":
                            production.setTitle(text);
                            break;
                        case "Duration":
                            ((Movie)production).setDuration(text);
                            break;
                        case "ReleaseYear":
                            int releaseYear = Integer.parseInt(textField.getText());
                            if (production instanceof Movie) {
                                ((Movie)production).setReleaseYear(releaseYear);
                            } else {
                                ((Series) production).setReleaseYear(releaseYear);
                            }
                    }
                }
                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        textLabel.setBackground(new Color(40, 40, 40));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
        inputPanel.setBackground(new Color(40, 40, 40));
        inputPanel.add(textLabel);
        inputPanel.add(textField);

        saveButton.setBackground(new Color(40, 40, 40));
        saveButton.setForeground(Color.WHITE);
        inputPanel.add(saveButton);

        add(inputPanel);
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
