package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBarGUI extends JFrame{
    private JTextField searchField;
    private User user;
    private Boolean removePanel;

    public SearchBarGUI(User user, Boolean removePanel) {
        this.removePanel = removePanel;
        this.user = user;

        JLabel searchLabel = new JLabel("Find actor/production by their name:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBackground(new Color(30, 30, 30));
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        JButton searchButton = new JButton("Search");

        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(30, 30, 30));
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(30, 30, 30));
        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(30, 30, 30));

        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        northPanel.add(searchLabel);
        centerPanel.add(searchField);
        southPanel.add(searchButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = searchField.getText();
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
                    JOptionPane.showMessageDialog(SearchBarGUI.this,
                            "No matching Actor/Production found for: '" + text + "'");
                }
            }
        });

        setTitle("Search Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
