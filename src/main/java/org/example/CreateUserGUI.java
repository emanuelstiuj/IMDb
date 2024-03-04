package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;

public class CreateUserGUI extends JFrame {
    private ArrayList<User> usersArrayCopy;
    private ArrayList<User> userArrayList;
    private DefaultListModel defaultList;
    private JLabel titleLabel;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField countryTextField;
    private JTextField ageTextField;
    private JTextField genderTextField;
    private JTextField birthTextField;
    private JTextField passwordTextField;
    private JTextField usernameTextField;
    private JComboBox accountTypeBox;
    private JLabel nameLabel;
    private JLabel accountTypeLabel;
    private JLabel countryLabel;
    private JLabel emailLabel;
    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel birthLabel;
    private JButton saveButton;
    private JButton passwordButton;
    private JButton usernameButton;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private User user;
    private int index;
    private final String characters = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890!@#$%^&*()-=][.,/?><:";

    private String generatePassword(String username) {
        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder();

        for(char c : username.toCharArray()) {
            passwordBuilder.append((char) (c + 1));
        }

        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString().replace(' ', characters.charAt(random.nextInt(characters.length())));
    }

    private String generateUsername(String name) {
        StringBuilder username = new StringBuilder();
        String lowercase = name.toLowerCase();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            username.append(randomChar);
        }

        username.append('_');
        username.append(lowercase);
        username.append('_');

        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            username.append(randomChar);
        }

        return username.toString().replace(' ', characters.charAt(random.nextInt(characters.length())));
    }

    public CreateUserGUI(Boolean newInfo, DefaultListModel defaultList, ArrayList<User> usersArrayCopy, int index,
                             User user) {
        this.usersArrayCopy = usersArrayCopy;
        this.user = user;
        this.defaultList = defaultList;
        this.index = index;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(40, 40, 40));

        if (newInfo)
            titleLabel = new JLabel("NEW USER");
        else
            titleLabel = new JLabel("UPDATE USER INFORMATION");

        this.userArrayList = (ArrayList<User>) IMDB.getInstance().getUsers();

        JPanel titlepanel = new JPanel();
        titleLabel.setForeground(Color.WHITE);
        titlepanel.setForeground(Color.WHITE);
        titlepanel.setBackground(new Color(40, 40, 40));
        titlepanel.add(titleLabel);
        titlepanel.setPreferredSize(new Dimension(300, 30));
        add(titlepanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(18, 1));

        nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.LIGHT_GRAY);
        panel1 = new JPanel();
        panel1.setBackground(new Color(40, 40, 40));
        panel1.add(nameLabel);
        panel.add(panel1);

        nameTextField = new JTextField();
        panel.add(nameTextField);

        emailLabel = new JLabel("Email: ");
        emailLabel.setForeground(Color.LIGHT_GRAY);
        panel6 = new JPanel();
        panel6.setBackground(new Color(40, 40, 40));
        panel6.add(emailLabel);
        panel.add(panel6);

        emailTextField = new JTextField();
        panel.add(emailTextField);

        accountTypeLabel = new JLabel("Account Type: ");
        accountTypeLabel.setForeground(Color.LIGHT_GRAY);
        String[] types = {"Regular", "Contributor", "Admin"};
        accountTypeBox = new JComboBox<>(types);

        panel7 = new JPanel();
        panel7.setBackground(new Color(40, 40, 40));
        panel7.add(accountTypeLabel);
        panel.add(panel7);
        panel.add(accountTypeBox);

        countryLabel = new JLabel("Country: ");
        countryLabel.setForeground(Color.LIGHT_GRAY);
        panel2 = new JPanel();
        panel2.setBackground(new Color(40, 40, 40));
        panel2.add(countryLabel);
        panel.add(panel2);

        countryTextField = new JTextField();
        panel.add(countryTextField);

        ageLabel = new JLabel("Age: ");
        ageLabel.setForeground(Color.LIGHT_GRAY);
        panel3 = new JPanel();
        panel3.setBackground(new Color(40, 40, 40));
        panel3.add(ageLabel);
        panel.add(panel3);

        ageTextField = new JTextField();
        panel.add(ageTextField);

        genderLabel = new JLabel("Gender: ");
        genderLabel.setForeground(Color.LIGHT_GRAY);
        panel4 = new JPanel();
        panel4.setBackground(new Color(40, 40, 40));
        panel4.add(genderLabel);
        panel.add(panel4);

        genderTextField = new JTextField();
        panel.add(genderTextField);

        birthLabel = new JLabel("Birthday: ");
        birthLabel.setForeground(Color.LIGHT_GRAY);
        panel5 = new JPanel();
        panel5.setBackground(new Color(40, 40, 40));
        panel5.add(birthLabel);
        panel.add(panel5);

        birthTextField = new JTextField();
        panel.add(birthTextField);

        passwordButton = new JButton("GENERATE PASSWORD");
        passwordButton.setBackground(new Color(25, 25, 25));
        passwordButton.setForeground(Color.WHITE);
        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                if (username.isEmpty()) {
                    showWarning("Generate username!");
                    return;
                }

                passwordTextField.setText(generatePassword(username));
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });


        passwordTextField = new JTextField();
        passwordTextField.setEditable(false);


        usernameButton = new JButton("GENERATE USERNAME");
        usernameButton.setBackground(new Color(25, 25, 25));
        usernameButton.setForeground(Color.WHITE);
        usernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                if (!name.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
                    showWarning("Invalid name format. Please use Firstname Lastname.");
                    return;
                }

                String username = generateUsername(name);
                while (IMDB.getInstance().getUserByUsername(username) != null) {
                    username = generateUsername(name);
                }

                usernameTextField.setText(generateUsername(name));
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(usernameButton);

        usernameTextField = new JTextField();
        usernameTextField.setEditable(false);

        panel.add(usernameTextField);

        panel.add(passwordButton);
        panel.add(passwordTextField);

        saveButton = new JButton("Create New User");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String country = countryTextField.getText();
                String ageStr = ageTextField.getText();
                String gender = genderTextField.getText();
                String birthDate = birthTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();
                String username = usernameTextField.getText();

                // Validate Name
                if (!name.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
                    showWarning("Invalid name format. Please use Firstname Lastname.");
                    return;
                }

                if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {

                    showWarning("Invalid mail format.");
                    return;
                }

                if (IMDB.getInstance().getUser(email) != null) {
                    showWarning("This email already exists in the system");
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

                if (password.isEmpty() || username.isEmpty()) {
                    showWarning("Generate an username and a password before creating an account");
                }

                UserFactory userFactory = new UserFactory();
                User userToCreate = userFactory.createUser(AccountType.valueOf(accountTypeBox.getSelectedItem().toString()));

                Credentials credentials = new Credentials();
                credentials.setEmail(email.toString());
                credentials.setPassword(password.toString());

                User.Information information = new User.Information.InformationBuilder(credentials)
                        .setName(name).setCountry(country).setAge(Integer.parseInt(ageStr)).setGender(gender.charAt(0))
                                .setBirthDate(LocalDateTime.parse(birthDate + "T00:00:00")).build();

                userToCreate.setInformation(information);
                userToCreate.setUserType(AccountType.valueOf(accountTypeBox.getSelectedItem().toString()));
                if (userToCreate.getUserType() == AccountType.Admin)
                    userToCreate.setExperience(-1);
                else
                    userToCreate.setExperience(0);

                userToCreate.setUsername(username);
                userToCreate.setRequestsNumber(0);

                defaultList.addElement(username);
                IMDB.getInstance().getUsers().add(userToCreate);

                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }

        });

        saveButton.setBackground(new Color(40, 40, 40));
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(70, 30));

        panel.setBackground(new Color(40, 40, 40));
        add(panel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        setSize(new Dimension(400, 650));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
