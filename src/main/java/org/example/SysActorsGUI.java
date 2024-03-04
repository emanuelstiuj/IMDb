package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class SysActorsGUI extends JFrame {
    private User user;
    private JPanel actorsPanel;
    private JScrollPane scrollPane;

    public SysActorsGUI(User user) {
        this.user = user;
        actorsPanel = new JPanel();
        actorsPanel.setLayout(new BoxLayout(actorsPanel, BoxLayout.Y_AXIS));
        actorsPanel.setBackground(new Color(40, 40, 40));

        for (Actor actor : IMDB.getInstance().getActors()) {
            JPanel actorInfoPanel = createActorInfoPanel(actor);
            actorsPanel.add(actorInfoPanel);
        }

        scrollPane = new JScrollPane(actorsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 40, 40));
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton sortByNameButton = new JButton("Sort by Name");
        JButton resetButton = new JButton("Refresh Frame");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(resetButton);

        sortByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Actor> sortedActors = new ArrayList<>(IMDB.getInstance().getActors());
                Collections.sort(sortedActors);

                actorsPanel.removeAll();

                for (Actor actor : sortedActors) {
                    JPanel actorInfoPanel = createActorInfoPanel(actor);
                    actorsPanel.add(actorInfoPanel);
                }

                scrollPane.getViewport().setView(actorsPanel);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actorsPanel.removeAll();

                for (Actor actor : IMDB.getInstance().getActors()) {
                    JPanel actorInfoPanel = createActorInfoPanel(actor);
                    actorsPanel.add(actorInfoPanel);
                }

                scrollPane.getViewport().setView(actorsPanel);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Actors Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createActorInfoPanel(Actor actor) {
        JPanel actorInfoPanel = new JPanel(new BorderLayout());
        actorInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        actorInfoPanel.setBackground(new Color(35, 35, 35));
        actorInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        JLabel nameLabel = new JLabel("Name: " + actor.getName());
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(Color.CYAN);

        JTextArea bioTextArea = new JTextArea();
        if (actor.getBiography() != null) {
            bioTextArea.append("Biography: " + actor.getBiography());
            bioTextArea.setFont(labelFont);
            bioTextArea.setForeground(Color.LIGHT_GRAY);
            bioTextArea.setBackground(new Color(35, 35, 35));
            bioTextArea.setLineWrap(true);
            bioTextArea.setWrapStyleWord(true);
            bioTextArea.setEditable(false);
            bioTextArea.setCaretPosition(0);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(35, 35, 35));

        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.setBackground(new Color(10, 10, 10));
        moreDetailsButton.setForeground(new Color(220, 220, 220));
        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActorDetailsGUI(actor, user);
            }
        });

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });

        buttonPanel.add(moreDetailsButton);

        actorInfoPanel.add(nameLabel, BorderLayout.NORTH);
        if (actor.getBiography() != null) {
            actorInfoPanel.add(bioTextArea, BorderLayout.CENTER);
        }

        actorInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        return actorInfoPanel;
    }
}
