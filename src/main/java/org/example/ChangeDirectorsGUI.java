package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ChangeDirectorsGUI extends JFrame {
    private Production production;
    private User user;
    private DefaultListModel<String> directorListModel;
    private JList<String> directorList;
    private JButton addDirectorButton;
    private JButton saveButton;

    public ChangeDirectorsGUI(Production production, User user) {
        this.user = user;
        this.production = production;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        directorListModel = new DefaultListModel<>();
        ArrayList<String> directors = (ArrayList<String>) production.getDirectors();
        for (String director : directors) {
            directorListModel.addElement(director);
        }

        directorList = new JList<>(directorListModel);
        directorList.setForeground(Color.BLACK);
        directorList.setBackground(new Color(100, 100, 100));
        JScrollPane scrollPane = new JScrollPane(directorList);

        JButton deleteButton = new JButton("Delete Director");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(25, 25, 25));
        JButton changeNameButton = new JButton("Change Name");
        changeNameButton.setBackground(new Color(25, 25, 25));
        changeNameButton.setForeground(Color.WHITE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = directorList.getSelectedIndex();
                if (selectedIndex != -1) {
                    directorListModel.remove(selectedIndex);
                }
            }
        });

        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = directorList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String directorName = directorList.getSelectedValue();
                    int index = directorList.getSelectedIndex();
                    new UpdateFieldGUI(FieldName.Director, false, production, directorListModel, directorName, index);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(changeNameButton);
        buttonPanel.setBackground(new Color(32, 32, 32));

        addDirectorButton = new JButton("Add New Director");
        addDirectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateFieldGUI(FieldName.Director, true, production, directorListModel, null, -1);
            }
        });
        addDirectorButton.setForeground(Color.WHITE);
        addDirectorButton.setBackground(new Color(25, 25, 25));

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> directors = new ArrayList<>(directorListModel.size());
                for (int i = 0; i < directorListModel.size(); i++) {
                    directors.add(directorListModel.getElementAt(i));
                }
                production.setDirectors(directors);
                dispose();
            }
        });
        saveButton.setBackground(new Color(25, 25, 25));
        saveButton.setForeground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(addDirectorButton);
        southPanel.add(saveButton);

        add(southPanel, BorderLayout.SOUTH);

        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
