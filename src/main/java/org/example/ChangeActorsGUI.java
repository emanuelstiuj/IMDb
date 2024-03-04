package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeActorsGUI extends JFrame {
    private Production production;
    private User user;
    private DefaultListModel<String> actorListModel;
    private JList<String> actorList;
    private JButton addactorButton;
    private JButton saveButton;

    public ChangeActorsGUI(Production production, User user) {
        this.user = user;
        this.production = production;

        setTitle("Change Actors");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(32, 32, 32));

        actorListModel = new DefaultListModel<>();
        ArrayList<String> actors = (ArrayList<String>) production.getActors();
        for (String actor : actors) {
            actorListModel.addElement(actor);
        }

        actorList = new JList<>(actorListModel);
        actorList.setBackground(new Color(100, 100, 100));
        actorList.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(actorList);
        scrollPane.setBackground(new Color(100, 100, 100));
        scrollPane.setForeground(Color.BLACK);

        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(new Color(100, 100, 100));
        viewport.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Delete Actor");
        deleteButton.setBackground(new Color(25, 25, 25));
        deleteButton.setForeground(Color.WHITE);
        JButton changeNameButton = new JButton("Change Name");
        changeNameButton.setBackground(new Color(25, 25, 25));
        changeNameButton.setForeground(Color.WHITE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = actorList.getSelectedIndex();
                if (selectedIndex != -1) {
                    actorListModel.remove(selectedIndex);
                }
            }
        });

        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = actorList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String actorName = actorList.getSelectedValue();
                    int index = actorList.getSelectedIndex();
                    new UpdateFieldGUI(FieldName.Actor, false, production, actorListModel, actorName, index);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(32, 32, 32));
        buttonPanel.add(deleteButton);
        buttonPanel.add(changeNameButton);

        addactorButton = new JButton("Add New Actor");
        addactorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateFieldGUI(FieldName.Actor, true, production, actorListModel, null, -1);
            }
        });
        addactorButton.setBackground(new Color(25, 25, 25));
        addactorButton.setForeground(Color.WHITE);

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> actors = new ArrayList<>(actorListModel.size());
                for (int i = 0; i < actorListModel.size(); i++) {
                    actors.add(actorListModel.getElementAt(i));
                }
                production.setActors(actors);
                dispose();
            }
        });

        saveButton.setBackground(new Color(25, 25, 25));
        saveButton.setForeground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(addactorButton);
        southPanel.add(saveButton);

        add(southPanel, BorderLayout.SOUTH);

        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
