package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserGUI extends JFrame {
    private User user;
    private JFrame frame;
    private JButton menuButton;
    private JLabel welcomeLabel;
    private JLabel phraseLabel;
    private JPanel buttonPanel;

    private class ButtonMouseListener extends MouseAdapter {
        private final JButton button;

        public ButtonMouseListener(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(button.getBackground().brighter());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(button.getBackground().darker());
        }
    }

    public UserGUI(String email) {
        user = IMDB.getInstance().getUser(email);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        welcomeLabel = new JLabel("Hello, " + IMDB.getInstance().getUser(email).getUsername());
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        add(welcomeLabel, BorderLayout.NORTH);

        phraseLabel = new JLabel("Select one option");
        phraseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phraseLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        phraseLabel.setForeground(Color.WHITE);
        add(phraseLabel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        if (user.getUserType() == AccountType.Admin)
            buttonPanel.setLayout(new GridLayout(10, 1, 0, 0));
        else if (user.getUserType() == AccountType.Regular)
            buttonPanel.setLayout(new GridLayout(5, 1, 0, 0));
        else
            buttonPanel.setLayout(new GridLayout(11, 1, 0, 0));
        buttonPanel.setBackground(new Color(30, 30, 30));

        addButton("View details about all productions on the system");
        addButton("View details about all actors on the system");
        addButton("Favourite actors and productions");

        if (user.getUserType() == AccountType.Admin) {
            addButton("Remove actor/production from the system");
            addButton("Update information about actors/productions");
            addButton("Remove/Add/Update users");
            addButton("Add new movie in the system");
            addButton("Add new series in the system");
            addButton("Add new actor in the system");
            addButton("Solve request");
        } else if (user.getUserType() == AccountType.Contributor) {
            addButton("Remove actor/production from the system");
            addButton("Create request");
            addButton("Withdraw request");
            addButton("Solve request");
            addButton("Update information about actors/productions");
            addButton("Add new actor in the system");
            addButton("Add new movie in the system");
            addButton("Add new series in the system");
        } else {
            addButton("Create request");
            addButton("Withdraw request");
        }

        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addMouseListener(new ButtonMouseListener(button));
                button.setFocusPainted(false);
            }
        }

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (user.getUserType() == AccountType.Contributor)
            setSize(600, 370);
        else if (user.getUserType() == AccountType.Regular)
            setSize(600, 270);
        else
            setSize(600, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (buttonText) {
                    case "View details about all productions on the system":
                        new SysProductionsGUI(user);
                        break;
                    case "View details about all actors on the system":
                        new SysActorsGUI(user);
                        break;
                    case "Favourite actors and productions":
                        new FavoritesGUI(user, true, false, false);
                        break;
                    case "Remove actor/production from the system":
                        new FavoritesGUI(user, false, true, false);
                        break;
                    case "Update information about actors/productions":
                        new FavoritesGUI(user, false, false, true);
                        break;
                    case "Create request":
                        new CreateRequestGUI(user);
                        break;
                    case "Withdraw request":
                        new WithdrawRequestGUI(user);
                        break;
                    case "Remove/Add/Update users":
                        new RemoveAddUsersGUI(user);
                        break;
                    case "Add new movie in the system":
                        Movie movie = new Movie();
                        new ProductionUpdateInfoGUI(movie, user, true);
                        break;
                    case "Add new series in the system":
                        Series series = new Series();
                        new ProductionUpdateInfoGUI(series, user, true);
                        break;
                    case "Solve request":
                        new SolveRequestsGUI(user);
                        break;
                    case "Add new actor in the system":
                        Actor actor = new Actor();
                        new ActorUpdateInfoGUI(actor, user, true);
                        break;
                }
            }
        });
        buttonPanel.add(button);
    }


}
