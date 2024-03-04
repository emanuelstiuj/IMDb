package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CreateRequestGUI extends JFrame {
    private User user;
    private JTextArea descriptionTextArea;
    private JComboBox<String> requestTypeComboBox;
    private JComboBox<String> actorList;
    private JComboBox<String> productionList;
    private JPanel listsPanel;

    public static void notifyAdmins(Request request) {
        String notification = "Ai primit o noua cerere de la utilizatorul \"" + request.getUsername() + "\": " +
                request.getDescription();
        for (User user1 : IMDB.getInstance().getUsers()) {
            if (user1.getUserType().equals(AccountType.Admin)) {
                user1.update(notification);
            }
        }
    }

    public static void notifyContributor(Request request) {
        String notification = "Ai primit o noua cerere de la utilizatorul \"" + request.getUsername() + "\": " +
                request.getDescription();
        IMDB.getInstance().getUserByUsername(request.getTo()).update(notification);
    }

    public CreateRequestGUI(User user) {
        this.user = user;
        setTitle("Request Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel requestTypeLabel = new JLabel("Select the request type:");
        requestTypeLabel.setForeground(Color.WHITE);
        String[] requestTypes = {"ACTOR_ISSUE", "MOVIE_ISSUE", "DELETE_ACCOUNT", "OTHERS"};
        requestTypeComboBox = new JComboBox<>(requestTypes);
        requestTypeComboBox.setBackground(new Color(40, 40, 40));
        requestTypeComboBox.setForeground(Color.WHITE);

        ArrayList<String> actorNames = new ArrayList<>();
        for (Actor actor : IMDB.getInstance().getActors()) {
            actorNames.add(actor.getName());
        }
        String[] actorNamesArray = actorNames.toArray(new String[0]);
        actorList = new JComboBox<>(actorNamesArray);
        actorList.setForeground(Color.WHITE);
        actorList.setBackground(new Color(40, 40, 40));
        actorList.setPreferredSize(new Dimension(330, 30));

        ArrayList<String> productionTitles =  new ArrayList<>();
        for (Production production : IMDB.getInstance().getProductions()) {
            productionTitles.add(production.getTitle());
        }
        String[] productionTitlesArray = productionTitles.toArray(new String[0]);
        productionList = new JComboBox<>(productionTitlesArray);
        productionList.setBackground(new Color(40, 40, 40));
        productionList.setForeground(Color.WHITE);
        productionList.setPreferredSize(new Dimension(330, 30));

        descriptionTextArea = new JTextArea(5, 20);
        descriptionTextArea.setBackground(new Color(170, 170, 170));
        descriptionTextArea.setForeground(Color.BLACK);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        int maxLength = 180;

        descriptionTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (descriptionTextArea.getText().length() >= maxLength) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });



        JButton sendButton = new JButton("Send Request");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) requestTypeComboBox.getSelectedItem();
                String description = descriptionTextArea.getText();
                handleRequest(selectedType, description);
            }
        });

        sendButton.setBackground(new Color(40, 40, 40));
        sendButton.setForeground(Color.WHITE);

        JPanel requestTypePanel = new JPanel(new FlowLayout());
        requestTypePanel.setPreferredSize(new Dimension(330, 80));
        requestTypePanel.setBackground(new Color(40, 40, 40));
        requestTypePanel.add(requestTypeLabel);
        requestTypePanel.add(requestTypeComboBox);

        JPanel cotentPanel = new JPanel(new BorderLayout());

        listsPanel = new JPanel(new CardLayout());
        listsPanel.add(new JScrollPane(actorList), "ACTOR_ISSUE");
        listsPanel.add(new JScrollPane(productionList), "MOVIE_ISSUE");

        requestTypePanel.add(listsPanel);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(new Color(40, 40, 40));

        JLabel desLabel = new JLabel("Description: (maximum 180 characters)");
        desLabel.setHorizontalAlignment(JLabel.CENTER);
        desLabel.setForeground(Color.WHITE);
        JPanel desPanel = new JPanel();
        desPanel.setBackground(new Color(40, 40, 40));
        desPanel.add(desLabel);
        desPanel.setPreferredSize(new Dimension(300, 25));

        descriptionPanel.add(desLabel, BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(descriptionTextArea), BorderLayout.CENTER);

        cotentPanel.add(descriptionPanel, BorderLayout.CENTER);

        add(requestTypePanel, BorderLayout.NORTH);
        add(cotentPanel, BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);

        setResizable(false);
        setSize(new Dimension(350, 240));
        setLocationRelativeTo(null);
        setVisible(true);

        requestTypeComboBox.addItemListener(e -> {
            String selectedItem = (String) e.getItem();
            CardLayout cardLayout = (CardLayout) listsPanel.getLayout();

            if ("OTHERS".equals(selectedItem) || "DELETE_ACCOUNT".equals(selectedItem)) {
                listsPanel.setVisible(false);
            } else {
                cardLayout.show(listsPanel, selectedItem);
                listsPanel.setVisible(true);
            }
        });
    }

    private void handleRequest(String requestType, String description) {
        switch (requestType) {
            case "ACTOR_ISSUE":
                String selectedActor = (String) actorList.getSelectedItem();
                if (actorList.getSelectedIndex() != -1) {
                    Request request = new Request();
                    request.setMovieTitle(null);
                    request.setActorName(selectedActor);
                    request.setUsername(user.getUsername());
                    request.setDescription(description);
                    request.setCreatedDate(LocalDateTime.now());
                    request.setType(RequestTypes.ACTOR_ISSUE);

                    user.incrementRequestNumber();
                    request.setId(user.getRequestsNumber());

                    Actor actor = IMDB.getInstance().getActor(selectedActor);

                    for (User user1 : IMDB.getInstance().getUsers()) {
                        if (user1.getUserType() != AccountType.Regular) {
                            if (user1 instanceof Contributor) {
                                if (((Contributor)user1).getContributions().contains(actor)) {
                                    if (user1.getUsername().equals(user.getUsername())) {
                                        JOptionPane.showMessageDialog(null,
                                                "You are responsible for this! You cannot send a request to yourself!",
                                                "Warning", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        request.setTo(user1.getUsername());
                                        user.getUnsolvedRequests().add(request);

                                        if (user instanceof Regular) {
                                            ((Regular) user).createRequest(request);
                                        } else {
                                            ((Contributor) user).createRequest(request);
                                        }

                                        notifyContributor(request);
                                    }
                                    break;
                                }
                            } else {
                                if (((Admin)user1).getContributions().contains(actor)) {
                                    if (user1.getUsername().equals(user.getUsername())) {
                                        JOptionPane.showMessageDialog(null,
                                                "You are responsible for this! You cannot send a request to yourself!",
                                                "Warning", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        request.setTo(user1.getUsername());
                                        user.getUnsolvedRequests().add(request);

                                        if (user instanceof Regular) {
                                            ((Regular) user).createRequest(request);
                                        } else {
                                            ((Contributor) user).createRequest(request);
                                        }

                                        notifyContributor(request);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    dispose();
                }
                break;

            case "MOVIE_ISSUE":
                String selectedMovie = (String) productionList.getSelectedItem();
                if (productionList.getSelectedIndex() != -1) {
                    Request request = new Request();
                    request.setMovieTitle(selectedMovie);
                    request.setActorName(null);
                    request.setUsername(user.getUsername());
                    request.setDescription(description);
                    request.setCreatedDate(LocalDateTime.now());
                    request.setType(RequestTypes.MOVIE_ISSUE);

                    user.incrementRequestNumber();
                    request.setId(user.getRequestsNumber());

                    Production production = IMDB.getInstance().getProduction(selectedMovie);

                    for (User user1 : IMDB.getInstance().getUsers()) {
                        if (user1.getUserType() != AccountType.Regular) {
                            if (user1 instanceof Contributor) {
                                if (((Contributor)user1).getContributions().contains(production)) {
                                    if (user1.getUsername().equals(user.getUsername())) {
                                        JOptionPane.showMessageDialog(null,
                                                "You are responsible for this! You cannot send a request to yourself!",
                                                "Warning", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        request.setTo(user1.getUsername());
                                        user.getUnsolvedRequests().add(request);

                                        if (user instanceof Regular) {
                                            ((Regular) user).createRequest(request);
                                        } else {
                                            ((Contributor) user).createRequest(request);
                                        }

                                        notifyContributor(request);
                                    }
                                    break;
                                }
                            } else if (((Admin) user1).getContributions().contains(production)) {
                                if (user1.getUsername().equals(user.getUsername())) {
                                    JOptionPane.showMessageDialog(null,
                                            "You are responsible for this! You cannot send a request to yourself!",
                                            "Warning", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    request.setTo(user1.getUsername());
                                    user.getUnsolvedRequests().add(request);

                                    if (user instanceof Regular) {
                                        ((Regular) user).createRequest(request);
                                    } else {
                                        ((Contributor) user).createRequest(request);
                                    }

                                    notifyContributor(request);
                                }
                                break;
                            }
                        }
                    }
                    dispose();
                }
                break;

            case "DELETE_ACCOUNT":
                Request request = new Request();
                request.setMovieTitle(null);
                request.setActorName(null);
                request.setUsername(user.getUsername());
                request.setDescription(description);
                request.setCreatedDate(LocalDateTime.now());
                request.setType(RequestTypes.DELETE_ACCOUNT);
                request.setTo("ADMIN");
                user.incrementRequestNumber();
                request.setId(user.getRequestsNumber());
                user.getUnsolvedRequests().add(request);

                if (user instanceof Regular) {
                    ((Regular) user).createRequest(request);
                } else {
                    ((Contributor) user).createRequest(request);
                }

                notifyAdmins(request);
                dispose();
                break;
            case "OTHERS":
                request = new Request();
                request.setMovieTitle(null);
                request.setActorName(null);
                request.setUsername(user.getUsername());
                request.setDescription(description);
                request.setCreatedDate(LocalDateTime.now());
                request.setType(RequestTypes.OTHERS);
                request.setTo("ADMIN");
                user.incrementRequestNumber();
                request.setId(user.getRequestsNumber());

                if (user instanceof Regular) {
                    ((Regular) user).createRequest(request);
                } else {
                    ((Contributor) user).createRequest(request);
                }

                user.getUnsolvedRequests().add(request);
                notifyAdmins(request);
                dispose();
                break;
        }
    }
}
