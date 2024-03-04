package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HomeGUI extends JFrame {
    private User user;

    public HomeGUI(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel searchBarPanel = new JPanel(new FlowLayout());
        searchBarPanel.setBackground(new Color(30, 30, 30));
        JTextField searchBarTextField = new JTextField(40);
        searchBarTextField.setPreferredSize(new Dimension(300, 30));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = searchBarTextField.getText();
                Boolean found = false;
                for (Actor actor : IMDB.getInstance().getActors()) {
                    if (actor.getName().equals(text)) {
                        new ActorDetailsGUI(actor, user);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    for (Production production : IMDB.getInstance().getProductions()) {
                        if (production.getTitle().equals(text)) {
                            if (production instanceof Movie) {
                                new MovieDetailsGUI((Movie) production, user);
                                found = true;
                                break;
                            } else {
                                new SeriesDetailsGUI((Series) production, user);
                                found = true;
                                break;
                            }
                        }
                    }
                }

                if (found) {

                } else {
                    JOptionPane.showMessageDialog(HomeGUI.this,
                            "No matching Actor/Production found for: '" + text + "'");
                }
            }
        });
        searchBarPanel.add(searchBarTextField);
        searchBarPanel.add(searchButton);

        add(searchBarPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new FlowLayout());
        menuPanel.setBackground(new Color(30, 30, 30));
        menuPanel.setPreferredSize(new Dimension(1800, 40));
        JButton logoutButton = new JButton("LOGOUT");
        JButton menuButton = new JButton("MENU");

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserGUI(user.getInformation().getCredentials().getEmail());
            }
        });

        logoutButton.setPreferredSize(new Dimension(170, 30));
        menuButton.setPreferredSize(new Dimension(170, 30));

        JButton notificationsButton = new JButton("NOTIFICATIONS");
        notificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NotificationsGUI(user);
            }
        });

        JButton profileButton = new JButton("VIEW PROFILE");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileGUI(user);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginGUI();
            }
        });
        searchButton.setBackground(Color.YELLOW);
        searchButton.setForeground(Color.BLACK);
        profileButton.setBackground(Color.YELLOW);
        profileButton.setForeground(Color.BLACK);
        logoutButton.setBackground(Color.YELLOW);
        logoutButton.setForeground(Color.BLACK);
        notificationsButton.setBackground(Color.YELLOW);
        notificationsButton.setForeground(Color.BLACK);
        menuButton.setBackground(Color.YELLOW);
        menuButton.setForeground(Color.BLACK);

        Font font = new Font("DejaVu Sans Mono", Font.BOLD, 17);
        searchButton.setFont(font);
        menuButton.setFont(font);
        profileButton.setFont(font);
        logoutButton.setFont(font);
        notificationsButton.setFont(font);

        profileButton.setPreferredSize(new Dimension(170, 30));
        notificationsButton.setPreferredSize(new Dimension(170, 30));

        menuPanel.add(logoutButton);
        menuPanel.add(menuButton);
        menuPanel.add(profileButton);
        menuPanel.add(notificationsButton);

        add(menuPanel, BorderLayout.SOUTH);

        Font welcomeFont = new Font("DejaVu Sans Mono", Font.BOLD, 20);

        JPanel firstRowPanel = new JPanel(new BorderLayout());
        firstRowPanel.setPreferredSize(new Dimension(1800, 500));
        JLabel actionTextLabel = new JLabel("ACTION: ");
        actionTextLabel.setForeground(Color.LIGHT_GRAY);
        actionTextLabel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 0));
        actionTextLabel.setFont(welcomeFont);

        firstRowPanel.add(actionTextLabel, BorderLayout.NORTH);
        firstRowPanel.setBackground(new Color(30, 30, 30));

        ArrayList<Production> actionProductions = new ArrayList<>();
        for (Production production : IMDB.getInstance().getProductions()) {
            if (production.getGenres().contains(Genre.Action)) {
                if (actionProductions.size() < 5) {
                    actionProductions.add(production);
                } else {
                    break;
                }
            }
        }

        ArrayList<Production> dramaProductions = new ArrayList<>();
        for (Production production : IMDB.getInstance().getProductions()) {
            if (production.getGenres().contains(Genre.Drama) && !actionProductions.contains(production)) {
                if (dramaProductions.size() < 5) {
                    dramaProductions.add(production);
                } else {
                    break;
                }
            }
        }

        JPanel actionMoviesPanel = new JPanel(new GridLayout(1, 5));
        actionMoviesPanel.setBackground(new Color(30, 30, 30));
        for (Production production : actionProductions) {
            JPanel movieInfoPanel = new JPanel(new BorderLayout());
            movieInfoPanel.setBackground(new Color(30, 30, 30));

            ImageIcon imageIcon = production.getImageIconHome();
            JLabel imageLabel = new JLabel(imageIcon);

            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (production instanceof Movie) {
                        new MovieDetailsGUI((Movie) production, user);
                    } else if (production instanceof Series) {
                        new SeriesDetailsGUI((Series) production, user);
                    }
                }
            });

            movieInfoPanel.add(imageLabel, BorderLayout.CENTER);
            actionMoviesPanel.add(movieInfoPanel);
        }
        firstRowPanel.add(actionMoviesPanel, BorderLayout.CENTER);


        JPanel secondRowPanel = new JPanel(new BorderLayout());
        JLabel dramaTextLabel = new JLabel("DRAMA: ");
        dramaTextLabel.setForeground(Color.LIGHT_GRAY);
        dramaTextLabel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 0));
        dramaTextLabel.setFont(welcomeFont);

        secondRowPanel.add(dramaTextLabel, BorderLayout.NORTH);
        secondRowPanel.setBackground(new Color(30, 30, 30));

        JPanel dramaMoviesPanel = new JPanel(new GridLayout(1, 5));
        dramaMoviesPanel.setBackground(new Color(30, 30, 30));
        for (Production production : dramaProductions) {
            JPanel movieInfoPanel = new JPanel(new BorderLayout());
            movieInfoPanel.setBackground(new Color(30, 30, 30));

            ImageIcon imageIcon = production.getImageIconHome();
            JLabel imageLabel = new JLabel(imageIcon);

            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (production instanceof Movie) {
                        new MovieDetailsGUI((Movie) production, user);
                    } else if (production instanceof Series) {
                        new SeriesDetailsGUI((Series) production, user);
                    }
                }
            });

            movieInfoPanel.add(imageLabel, BorderLayout.CENTER);
            dramaMoviesPanel.add(movieInfoPanel);
        }
        secondRowPanel.add(dramaMoviesPanel, BorderLayout.CENTER);

        JPanel allFilmsPanel = new JPanel(new GridLayout(2, 1));
        allFilmsPanel.setBackground(new Color(30, 30, 30));
        allFilmsPanel.add(firstRowPanel);
        allFilmsPanel.add(secondRowPanel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome back, " + user.getUsername() + "! Here are some recommendations:");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBackground(new Color(30, 30, 30));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 100, 10, 0));
        welcomeLabel.setFont(welcomeFont);
        contentPanel.add(welcomeLabel, BorderLayout.NORTH);
        contentPanel.add(allFilmsPanel, BorderLayout.CENTER);
        contentPanel.setBackground(new Color(30, 30, 30));

        add(contentPanel, BorderLayout.CENTER);

        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height - 28));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
