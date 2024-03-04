package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UpdateUserInfoGUI extends JFrame {
    private ArrayList<User> usersArrayCopy;
    private DefaultListModel defaultList;
    private JLabel titleLabel;
    private JTextField nameTextField;
    private JTextField countryTextField;
    private JTextField ageTextField;
    private JTextField genderTextField;
    private JTextField birthTextField;
    private JLabel nameLabel;
    private JLabel countryLabel;
    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel birthLabel;
    private JButton saveButton;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private User user;
    private int index;

    private User getUser(String username) {
        for (User user1 : usersArrayCopy) {
            if (user1.getUsername().equals(username)) {
                return user1;
            }
        }
        return null;
    }

    public UpdateUserInfoGUI(Boolean newInfo, DefaultListModel defaultList, ArrayList<User> usersArrayCopy, int index,
                             User user) {
        this.usersArrayCopy = usersArrayCopy;
        this.user = user;
        this.defaultList = defaultList;
        this.index = index;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(30, 30, 30));

        if (newInfo)
            titleLabel = new JLabel("New User");
        else
            titleLabel = new JLabel("UPDATE USER INFORMATION");

        JPanel titlepanel = new JPanel();
        titleLabel.setForeground(Color.WHITE);
        titlepanel.setForeground(Color.WHITE);
        titlepanel.setBackground(new Color(30, 30, 30));
        titlepanel.add(titleLabel);
        titlepanel.setPreferredSize(new Dimension(300, 30));
        add(titlepanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(10, 1));
        panel.setBackground(new Color(40, 40, 40));

        nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.GRAY);
        panel1 = new JPanel();
        panel1.setBackground(new Color(30, 30, 30));
        panel1.add(nameLabel);
        panel.add(panel1);

        nameTextField = new JTextField(user.getInformation().getName());
        panel.add(nameTextField);

        countryLabel = new JLabel("Country: ");
        countryLabel.setForeground(Color.GRAY);
        panel2 = new JPanel();
        panel2.setBackground(new Color(30, 30, 30));
        panel2.add(countryLabel);
        panel.add(panel2);

        countryTextField = new JTextField(user.getInformation().getCountry());
        panel.add(countryTextField);

        ageLabel = new JLabel("Age: ");
        ageLabel.setForeground(Color.GRAY);
        panel3 = new JPanel();
        panel3.setBackground(new Color(30, 30, 30));
        panel3.add(ageLabel);
        panel.add(panel3);

        ageTextField = new JTextField(((Integer)user.getInformation().getAge()).toString());
        panel.add(ageTextField);

        genderLabel = new JLabel("Gender: ");
        genderLabel.setForeground(Color.GRAY);
        panel4 = new JPanel();
        panel4.setBackground(new Color(30, 30, 30));
        panel4.add(genderLabel);
        panel.add(panel4);

        genderTextField = new JTextField(((Character)user.getInformation().getGender()).toString());
        panel.add(genderTextField);

        birthLabel = new JLabel("Birthday: ");
        birthLabel.setForeground(Color.GRAY);
        panel5 = new JPanel();
        panel5.setBackground(new Color(30, 30, 30));
        panel5.add(birthLabel);
        panel.add(panel5);

        birthTextField = new JTextField(user.getInformation().getBirthDate().toLocalDate().toString());
        panel.add(birthTextField);

        saveButton = new JButton("SAVE CHANGES");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String country = countryTextField.getText();
                String ageStr = ageTextField.getText();
                String gender = genderTextField.getText();
                String birthDate = birthTextField.getText();

                // Validate Name
                if (!name.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
                    showWarning("Invalid name format. Please use Firstname Lastname.");
                    return;
                }

                // Validate Age
                try {
                    int age = Integer.parseInt(ageStr);
                    if (age < 18 || age > 100) {
                        showWarning("Invalid age. Age should be between 18 and 100.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showWarning("Invalid age format. Please enter a valid integer.");
                    return;
                }

                // Validate Gender
                if (!gender.matches("^[FMN]$")) {
                    showWarning("Invalid gender. Use 'F' for female, 'M' for male, or 'N' for non-binary.");
                    return;
                }

                // Validate Country
                if (!country.matches("^[A-Z]{2}$")) {
                    showWarning("Invalid country format. Please use two capital letters.");
                    return;
                }

                try {
                    LocalDateTime date = LocalDateTime.parse(birthDate + "T00:00:00");
                } catch (DateTimeParseException exx) {
                    showWarning("Invalid birth date format. Please use YYYY-MM-DD.");
                    return;
                }

                User userToModify = getUser(user.getUsername());
                userToModify.getInformation().setName(name);
                userToModify.getInformation().setAge(Integer.parseInt(ageStr));
                userToModify.getInformation().setCountry(country);
                userToModify.getInformation().setGender(gender.charAt(0));
                userToModify.getInformation().setBirthDate(LocalDateTime.parse(birthDate + "T00:00:00"));
                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }

        });

        saveButton.setBackground(new Color(30, 30, 30));
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(70, 30));

        add(panel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        setSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
