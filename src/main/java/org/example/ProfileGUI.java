package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ProfileGUI extends JFrame {
    private User user;

    public ProfileGUI(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Font titleFont = new Font("DejaVu Sans Mono", Font.BOLD, 22);

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(400, 70));
        JLabel titleLabel = new JLabel("YOUR PROFILE");
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setFont(titleFont);
        titlePanel.setBackground(new Color(30, 30, 30));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        if (user instanceof Admin)
            infoPanel.setLayout(new GridLayout(7, 1));
        else
            infoPanel.setLayout(new GridLayout(8, 1));

        setBackground(new Color(30, 30, 30));

        Font font = new Font("DejaVu Sans Mono", Font.BOLD, 16);

        JPanel namePanel = new JPanel();
        namePanel.setBackground(new Color(30, 30, 30));
        JLabel nameLabel  = new JLabel("Name: " + user.getInformation().getName());
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);
        namePanel.add(nameLabel);
        infoPanel.add(namePanel);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setBackground(new Color(30, 30, 30));
        JLabel usernameLabel  = new JLabel("Username: " + user.getUsername());
        usernameLabel.setFont(font);
        usernameLabel.setForeground(Color.WHITE);
        usernamePanel.add(usernameLabel);
        infoPanel.add(usernamePanel);

        JPanel countryPanel = new JPanel();
        countryPanel.setBackground(new Color(30, 30, 30));
        JLabel countryLabel  = new JLabel("Country: " + user.getInformation().getCountry());
        countryLabel.setFont(font);
        countryLabel.setForeground(Color.WHITE);
        countryPanel.add(countryLabel);
        infoPanel.add(countryPanel);

        JPanel genderPanel = new JPanel();
        genderPanel.setBackground(new Color(30, 30, 30));
        JLabel genderLabel  = new JLabel("Gender: " + user.getInformation().getGender());
        genderLabel.setFont(font);
        genderLabel.setForeground(Color.WHITE);
        genderPanel.add(genderLabel);
        infoPanel.add(genderPanel);

        JPanel datePanel = new JPanel();
        datePanel.setBackground(new Color(30, 30, 30));
        JLabel dateLabel  = new JLabel("Birthdate: " + user.getInformation().getBirthDate().toLocalDate());
        dateLabel.setFont(font);
        dateLabel.setForeground(Color.WHITE);
        datePanel.add(dateLabel);
        infoPanel.add(datePanel);

        JPanel agePanel = new JPanel();
        agePanel.setBackground(new Color(30, 30, 30));
        JLabel ageLabel  = new JLabel("Age: " + user.getInformation().getAge() + " years old");
        ageLabel.setFont(font);
        ageLabel.setForeground(Color.WHITE);
        agePanel.add(ageLabel);
        infoPanel.add(agePanel);

        JPanel usertypePanel = new JPanel();
        usertypePanel.setBackground(new Color(30, 30, 30));
        JLabel usertypeLabel  = new JLabel("User Type: " + user.getUserType());
        usertypeLabel.setFont(font);
        usertypeLabel.setForeground(Color.WHITE);
        usertypePanel.add(usertypeLabel);
        infoPanel.add(usertypePanel);

        JPanel exPanel = new JPanel();
        exPanel.setBackground(new Color(30, 30, 30));
        JLabel exLabel  = new JLabel("Experience: " + user.getExperience());
        exLabel.setFont(font);
        exLabel.setForeground(Color.WHITE);
        exPanel.add(exLabel);
        if (user.getUserType() != AccountType.Admin) {
            infoPanel.add(exPanel);
        }

        add(infoPanel, BorderLayout.CENTER);

        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
