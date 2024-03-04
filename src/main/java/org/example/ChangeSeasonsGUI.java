package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChangeSeasonsGUI extends JFrame {
    private Series series;
    private DefaultListModel<String> seasonListModel;
    private JList<String> seasonList;

    public ChangeSeasonsGUI(Series series) {
        this.series = series;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        JLabel label = new JLabel("Select a season and view more details:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(350, 40));
        seasonListModel = new DefaultListModel<>();
        for (int i = 1; i <= series.getNumSeasons(); i++) {
            seasonListModel.add(i - 1, "Season " + ((Integer)i));
        }
        seasonList = new JList<>(seasonListModel);
        seasonList.setBackground(new Color(100, 100, 100));
        seasonList.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(seasonList);

        JButton moreDetailsButton = new JButton("More Details");

        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = seasonList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String seasonName = seasonList.getSelectedValue();
                    int index = seasonList.getSelectedIndex();
                    new EpisodesGUI(seasonName, series);
                }
            }
        });

        JButton addSeasonButton = new JButton("Add New Season");
        addSeasonButton.setForeground(Color.WHITE);
        addSeasonButton.setBackground(new Color(25, 25, 25));

        addSeasonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numSeasons = series.getNumSeasons();
                String newSeason = "Season " + (numSeasons + 1);

                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to add a new season?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    List<Episode> episodes = new ArrayList<>();
                    series.addSeason(newSeason, episodes);
                    series.setNumSeasons(series.getSeasons().size());
                    seasonListModel.addElement(newSeason);
                }
            }
        });

        JButton deleteSeasonButton = new JButton("Delete Last Season");
        deleteSeasonButton.setBackground(new Color(25, 25, 25));
        deleteSeasonButton.setForeground(Color.WHITE);

        deleteSeasonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numSeasons = series.getNumSeasons();
                String newSeason = "Season " + numSeasons;

                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete last season?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    seasonListModel.remove(numSeasons - 1);
                    series.getSeasons().remove("Season " + numSeasons);
                    series.setNumSeasons(series.getSeasons().size());
                }
            }
        });

        moreDetailsButton.setForeground(Color.WHITE);
        moreDetailsButton.setBackground(new Color(25, 25, 25));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(moreDetailsButton);
        buttonPanel.add(addSeasonButton);
        buttonPanel.add(deleteSeasonButton);
        buttonPanel.setBackground(new Color(35, 35, 35));

        label.setPreferredSize(new Dimension(490, 30));
        label.setBackground(new Color(30, 30, 30));
        label.setForeground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.add(label);

        buttonPanel.setBackground(new Color(35, 35, 35));

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 230);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
