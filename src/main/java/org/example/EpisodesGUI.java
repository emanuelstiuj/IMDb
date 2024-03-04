package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class EpisodesGUI extends JFrame {
    private DefaultListModel listModelEpisodes;
    private JList<String> jListEpisodes;
    private ArrayList<Episode> copyEpisodes;

    public String getDuration(String episodeName) {

        for (Episode ep : copyEpisodes) {
            if (ep.getEpisodeName().equals(episodeName))
                return ep.getDuration();
        }
        return null;
    }

    public EpisodesGUI(String seasonName, Series series) {
        copyEpisodes = new ArrayList<>();
        ArrayList<Episode> originalList = (ArrayList<Episode>) series.getSeasons().get(seasonName);

        for (Episode episode : originalList) {
            try {
                copyEpisodes.add((Episode) episode.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Select an episode and choose one option:");
        label.setPreferredSize(new Dimension(450, 40));
        listModelEpisodes = new DefaultListModel<>();
        ArrayList<Episode> episodes = (ArrayList<Episode>) series.getSeasons().get(seasonName);

        for (Episode episode : episodes) {
            listModelEpisodes.addElement(episode.getEpisodeName());
        }

        jListEpisodes = new JList<>(listModelEpisodes);
        jListEpisodes.setForeground(Color.BLACK);
        jListEpisodes.setBackground(new Color(100, 100, 100));
        JScrollPane scrollPane = new JScrollPane(jListEpisodes);

        JButton moreDetailsButton = new JButton("More details");
        moreDetailsButton.setBackground(new Color(25, 25, 25));
        moreDetailsButton.setForeground(Color.WHITE);
        JButton deleteButton = new JButton("Delete Episode");
        deleteButton.setBackground(new Color(25, 25, 25));
        deleteButton.setForeground(Color.WHITE);
        JButton addEpisode = new JButton("Add new episode");
        addEpisode.setForeground(Color.WHITE);
        addEpisode.setBackground(new Color(25, 25, 25));
        JButton saveButton = new JButton("Save Info");
        saveButton.setBackground(new Color(25, 25, 25));
        saveButton.setForeground(Color.WHITE);
        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jListEpisodes.getSelectedIndex();
                if (selectedIndex != -1) {
                    String ep = jListEpisodes.getSelectedValue();
                    int index = jListEpisodes.getSelectedIndex();
                    String duration = getDuration(ep);
                    new ChangeEpisodeInfoGUI(ep, duration, false, listModelEpisodes, copyEpisodes, index);
                }
            }
        });

        addEpisode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = jListEpisodes.getSelectedIndex();
                String ep = jListEpisodes.getSelectedValue();

                new ChangeEpisodeInfoGUI(null, null, true, listModelEpisodes, copyEpisodes, index);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jListEpisodes.getSelectedIndex();
                if (selectedIndex != -1) {
                    int index = jListEpisodes.getSelectedIndex();
                    listModelEpisodes.remove(index);
                    copyEpisodes.remove(index);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int index = jListEpisodes.getSelectedIndex();
                    ArrayList<Episode> episodeList = (ArrayList<Episode>) series.getSeasons().get(seasonName);
                    Iterator<Episode> iterator = episodes.iterator();


                    while (iterator.hasNext()) {
                        Episode episode = iterator.next();
                        iterator.remove();
                    }

                    for (Episode episode : copyEpisodes) {
                        episodeList.add(episode);
                    }

                    dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(moreDetailsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addEpisode);
        buttonPanel.add(saveButton);

        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.add(label);

        buttonPanel.setBackground(new Color(35, 35, 35));

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(550, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
