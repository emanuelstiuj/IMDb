package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ChangeEpisodeInfoGUI extends JFrame {
    private JLabel titleLabel;

    public Episode getEpisode(ArrayList<Episode> episodes, String episode) {
        for (Episode ep : episodes) {
            if (ep.getEpisodeName().equals(episode))
                return ep;
        }
        return null;
    }

    public ChangeEpisodeInfoGUI(String title, String duration, Boolean newInfo, DefaultListModel defaultListModel,
                                ArrayList<Episode> copyEpisodes, int index) {

        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(30, 30, 30));

        if (newInfo)
            titleLabel = new JLabel("New Episode");
        else
            titleLabel = new JLabel("Change Episode Information");

        JPanel titlepanel = new JPanel();
        titleLabel.setForeground(Color.WHITE);
        titlepanel.setForeground(Color.WHITE);
        titlepanel.setBackground(new Color(30, 30, 30));
        titlepanel.add(titleLabel);
        titlepanel.setPreferredSize(new Dimension(300, 30));
        add(titlepanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField nameField;
        JTextField durationField;

        JPanel namePanel = new JPanel();
        namePanel.setBackground(new Color(30, 30, 30));
        JPanel durationPanel = new JPanel();
        durationPanel.setBackground(new Color(30, 30, 30));

        JLabel epLabel = new JLabel("Episode Name:");
        epLabel.setForeground(Color.GRAY);
        namePanel.add(epLabel);
        panel.add(namePanel);

        if (newInfo) {
            nameField = new JTextField();
            panel.add(nameField);
        } else {
            nameField = new JTextField(title);
            panel.add(nameField);
        }

        JLabel durLabel = new JLabel("Duration:");
        durLabel.setForeground(Color.GRAY);
        durationPanel.add(durLabel);
        panel.add(durationPanel);

        if (newInfo) {
            durationField = new JTextField();
            panel.add(durationField);
        } else {
            durationField = new JTextField(duration);
            panel.add(durationField);
        }

        add(panel, BorderLayout.CENTER);

        JButton saveButton = new JButton("SAVE CHANGES");
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(30, 30,30));
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String duration = durationField.getText();

                if (!duration.matches("^\\d+\\sminutes$")) {
                    showWarning("Invalid duration format. Correct format example: 25 minutes");
                    return;
                }

                if (newInfo) {
                    Episode episode = new Episode();
                    episode.setEpisodeName(nameField.getText());
                    episode.setDuration(durationField.getText());
                    copyEpisodes.add(episode);
                    defaultListModel.addElement(nameField.getText());
                } else {
                    Episode epCopy = getEpisode(copyEpisodes, title);
                    epCopy.setEpisodeName(nameField.getText());
                    epCopy.setDuration(durationField.getText());
                    defaultListModel.remove(index);
                    defaultListModel.add(index, nameField.getText());
                }
                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
