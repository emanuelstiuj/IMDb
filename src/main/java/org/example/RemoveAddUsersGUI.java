package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class RemoveAddUsersGUI extends JFrame {
    private User user;
    private DefaultListModel usersDefaultList;
    private JList<String> usersJList;
    private ArrayList<User> usersArrayCopy;

    public RemoveAddUsersGUI(User user) {
        this.user = user;
        
        usersArrayCopy = new ArrayList<>();
        ArrayList<User> originalList = (ArrayList<User>) IMDB.getInstance().getUsers();

        for (User User : originalList) {
            try {
                usersArrayCopy.add((User) User.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Select an user and choose one option:");
        label.setPreferredSize(new Dimension(450, 40));
        usersDefaultList = new DefaultListModel<>();
        ArrayList<User> users = (ArrayList<User>) IMDB.getInstance().getUsers();

        for (User user1 : users) {
            usersDefaultList.addElement(user1.getUsername());
        }

        usersJList = new JList<>(usersDefaultList);
        usersJList.setForeground(Color.BLACK);
        usersJList.setBackground(new Color(100, 100, 100));
        JScrollPane scrollPane = new JScrollPane(usersJList);

        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.setBackground(new Color(25, 25, 25));
        moreDetailsButton.setForeground(Color.WHITE);
        JButton deleteButton = new JButton("Delete User");
        deleteButton.setBackground(new Color(25, 25, 25));
        deleteButton.setForeground(Color.WHITE);
        JButton addUser = new JButton("Add New User");
        addUser.setForeground(Color.WHITE);
        addUser.setBackground(new Color(25, 25, 25));
        JButton saveButton = new JButton("Save Modifications");
        saveButton.setBackground(new Color(25, 25, 25));
        saveButton.setForeground(Color.WHITE);
        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = usersJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String ep = usersJList.getSelectedValue();
                    int index = usersJList.getSelectedIndex();
                    new UpdateUserInfoGUI(false, usersDefaultList, usersArrayCopy, index,
                            IMDB.getInstance().getUserByUsername(ep));
                }
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = usersJList.getSelectedIndex();
                String ep = usersJList.getSelectedValue();
                new CreateUserGUI(true, usersDefaultList, usersArrayCopy, index,
                        IMDB.getInstance().getUserByUsername(ep));
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedIndex = usersJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete the user " + usersJList.getSelectedValue()
                            + "?" ,null, JOptionPane.YES_NO_OPTION);
                    if (user.getUsername().equals(usersJList.getSelectedValue())) {
                        showWarning("You cannot delete your account!");
                        return;
                    }

                    if (option == JOptionPane.YES_OPTION) {
                        int index = usersJList.getSelectedIndex();
                        User user1 = IMDB.getInstance().getUserByUsername(usersJList.getSelectedValue());

                        //ratings
                        for (Production production : IMDB.getInstance().getProductions()) {
                            production.getRatings().removeIf(rating -> rating.getUsername().equals(user1.getUsername()));
                            production.setAverageRating(production.calculateAverageRating());
                        }

                        for (User user2 : IMDB.getInstance().getUsers()) {
                            if (!user2.getUserType().equals(AccountType.Regular)) {
                                ((Staff)user2).getRequests().removeIf(request ->
                                        ((Request)request).getUsername().equals(user1.getUsername()));
                            }

                            user2.getNotifications().removeIf(notification -> notification.equals("Ai primit o noua " +
                                    "cerere de la utilizatorul \"" + user1.getUsername() + "\""));

                            user2.getNotifications().removeIf(notification -> ((String)notification)
                                    .contains("pe care l-ai evaluat a primit un review de la utilizatorul \"" +
                                    user1.getUsername() + "\" ->"));

                            user2.getNotifications().removeIf(notification -> ((String)notification)
                                    .contains("pe care il ai in lista de favorite a primit un review de la" +
                                            " utilizatorul \"" + user1.getUsername() + "\" ->"));

                            user2.getNotifications().removeIf(notification -> ((String)notification)
                                    .contains("pe care l-ai adaugat a primit un review de la utilizatorul \"" +
                                            user1.getUsername() + "\" ->"));
                        }

                        if (!user1.getUserType().equals(AccountType.Regular)) {
                            Admin.adminContributions.addAll(((Staff) user1).getContributions());

                            for (Object request : ((Staff)user1).getRequests()) {
                                if (request instanceof Request) {
                                    ((Request)request).setTo("ADMIN");
                                    CreateRequestGUI.notifyAdmins((Request) request);
                                }
                            }

                            Admin.RequestsHolder.getRequests().addAll(((Staff)user1).getRequests());
                        }

                        if (!user1.getUserType().equals(AccountType.Admin)) {
                            for (Object request : user1.getUnsolvedRequests()) {
                                if (user1 instanceof Regular) {
                                    ((Regular) user1).removeRequest((Request) request);
                                } else {
                                    ((Contributor) user1).removeRequest((Request) request);
                                }
                            }
                        }

                        if (!user1.getUserType().equals(AccountType.Regular)) {
                            for (Object object : ((Staff) user1).getContributions()) {
                                if (object instanceof Production) {
                                    for (User user2 : IMDB.getInstance().getUsers()) {
                                        if (user2 instanceof Admin) {
                                            ((Production) object).getContributors().add(user2);
                                        }
                                    }
                                }
                            }
                        }

                        IMDB.getInstance().getUsers().remove(user1);
                        usersDefaultList.remove(index);
                    }
                }
            }
            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(moreDetailsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addUser);

        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.add(label);

        buttonPanel.setBackground(new Color(35, 35, 35));

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
