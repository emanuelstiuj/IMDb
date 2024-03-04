package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame{
    private ImageIcon imdbIcon;
    private Image imdbImage;
    private JLabel myLabel;
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        String fileName = "src/main/resources/images/imdb_background";
        imdbIcon = new ImageIcon(fileName);
        imdbImage = imdbIcon.getImage();
        myLabel = new JLabel();

        int width = imdbIcon.getIconWidth();
        int height = imdbIcon.getIconHeight();
        myLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLabel.setVerticalAlignment(SwingConstants.CENTER);
        myLabel.setIcon(imdbIcon);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        Color whiteColor = Color.WHITE;
        emailLabel.setForeground(whiteColor);
        passwordLabel.setForeground(whiteColor);

        emailField = new JTextField(35);
        passwordField = new JPasswordField(35);

        loginButton = new JButton("Login");

        myLabel.setLayout(null);

        int textFieldWidth = 240;
        int textFieldHeight = 25;
        int buttonWidth = 80;
        int buttonHeight = 25;
        int labelWidth = 80;
        int labelHeight = 25;

        emailLabel.setBounds((width - textFieldWidth) / 2 - labelWidth + 15, 3, labelWidth, labelHeight);
        emailField.setBounds((width - textFieldWidth) / 2 + 20, 3, textFieldWidth, textFieldHeight);

        passwordLabel.setBounds((width - textFieldWidth) / 2 - labelWidth + 15, 29, labelWidth, labelHeight);
        passwordField.setBounds((width - textFieldWidth) / 2 + 20, 29, textFieldWidth, textFieldHeight);

        loginButton.setBounds((width - buttonWidth) / 2 + 20, 55, buttonWidth, buttonHeight);

        myLabel.add(emailLabel);
        myLabel.add(emailField);
        myLabel.add(passwordLabel);
        myLabel.add(passwordField);
        myLabel.add(loginButton);

        JLabel messageLabel = new JLabel("Incorrect email or password!");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] password = passwordField.getPassword();

                if (IMDB.getInstance().confirmLogin(password, email)) {
                    new HomeGUI(IMDB.getInstance().getUser(email));
                    SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setBounds((width - textFieldWidth) / 2 - labelWidth + 13, 80, 400, 25);
                    myLabel.add(messageLabel);
                    myLabel.repaint();
                }
            }
        });

        frame = new JFrame("IMDB");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(myLabel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
