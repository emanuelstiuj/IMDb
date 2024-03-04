package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActorDetailsGUI extends JFrame {
    private User user;
    private Actor actor;
    private Boolean removePanel;

    public ActorDetailsGUI(Actor actor, User user) {
        this.actor = actor;
        this.user = user;
        this.removePanel = removePanel;

        setTitle("Actor Details: " + actor.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(40, 40, 40));

        JLabel nameLabel = new JLabel("Name: " + actor.getName());
        Font nameFont = new Font("DejaVu Sans Mono", Font.BOLD, 16);
        nameLabel.setFont(nameFont);
        nameLabel.setForeground(Color.CYAN);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        mainPanel.add(nameLabel, BorderLayout.NORTH);

        Font infoFont = new Font("DejaVu Sans Mono", Font.PLAIN, 15);
        JTextArea bioTextArea = new JTextArea();
        if (actor.getBiography() != null)
            bioTextArea.append("BIOGRAPHY: \n" + actor.getBiography() + "\n\n");

        if (actor.hasMovies()) {
            bioTextArea.append("MOVIES: \n");
            for (Performance performance : actor.getPerformances()) {
                if (performance.getType().equals("Movie")) {
                    bioTextArea.append("\"" + performance.getTitle() + "\"\n");
                }
            }
            bioTextArea.append("\n");
        }

        if (actor.hasSeries()) {
            bioTextArea.append("SERIES: \n");
            for (Performance performance : actor.getPerformances()) {
                if (performance.getType().equals("Series")) {
                    bioTextArea.append("\"" + performance.getTitle() + "\"\n");
                }
            }
        }

        bioTextArea.setEditable(false);
        bioTextArea.setFont(infoFont);
        bioTextArea.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setBackground(new Color(35, 35, 35));
        bioTextArea.setForeground(Color.LIGHT_GRAY);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(40, 40, 40));

        JScrollPane scrollPane = new JScrollPane(bioTextArea);
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });
        detailsPanel.add(scrollPane);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(40, 40, 40));
        JButton addToFavoritesButton;


        if (!user.getFavorites().contains(actor))
            addToFavoritesButton = new JButton("Add to Favorites");
        else
            addToFavoritesButton = new JButton("Remove from Favorites");

        addToFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getFavorites().contains(actor)) {
                    user.getFavorites().remove(actor);
                    addToFavoritesButton.setText("Add to Favorites");
                } else {
                    user.getFavorites().add(actor);
                    addToFavoritesButton.setText("Remove form Favorites");
                }
            }
        });

        JButton refreshButton = new JButton("Refresh Window");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ActorDetailsGUI(actor, user);
            }
        });

        buttonPanel.add(addToFavoritesButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.setBackground(new Color(40, 40, 40));

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
