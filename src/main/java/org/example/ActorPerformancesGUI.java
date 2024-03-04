package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ActorPerformancesGUI extends JFrame{
    private User user;
    private DefaultListModel listModelPerformances;
    private JList<String> performancesJList;
    private ArrayList<Performance> copyPerformances;

    public String getType(String name) {
        for (Performance performance : copyPerformances) {
            if (performance.getTitle().equals(name))
                return performance.getType();
        }
        return null;
    }

    public ActorPerformancesGUI(Actor actor, User user, Boolean newInfo) {
        this.user = user;
        copyPerformances = new ArrayList<>();
        ArrayList<Performance> originalList = (ArrayList<Performance>) actor.getPerformances();

        for (Performance performance : originalList) {
            try {
                copyPerformances.add((Performance) performance.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Select a performance and choose one option:");
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setForeground(Color.WHITE);

        label.setPreferredSize(new Dimension(450, 40));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.add(label);
        listModelPerformances = new DefaultListModel<>();
        ArrayList<Performance> performances = (ArrayList<Performance>) actor.getPerformances();

        for (Performance performance : performances) {
            listModelPerformances.addElement(performance.getTitle());
        }

        performancesJList = new JList<>(listModelPerformances);
        JScrollPane scrollPane = new JScrollPane(performancesJList);
        performancesJList.setBackground(new Color(100, 100, 100));
        performancesJList.setForeground(Color.WHITE);

        JButton moreDetailsButton = new JButton("More details");
        JButton deleteButton = new JButton("Delete performance");
        JButton addperformance = new JButton("Add new performance");
        JButton saveButton = new JButton("Save Info");
        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = performancesJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String ep = performancesJList.getSelectedValue();
                    int index = performancesJList.getSelectedIndex();
                    String type = getType(ep);
                    new UpdatePerformanceForActorGUI(ep, type, false, listModelPerformances, copyPerformances, index);
                }
            }
        });

        addperformance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = performancesJList.getSelectedIndex();
                String ep = performancesJList.getSelectedValue();
                new UpdatePerformanceForActorGUI(null, null, true, listModelPerformances, copyPerformances, index);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = performancesJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    int index = performancesJList.getSelectedIndex();
                    listModelPerformances.remove(index);
                    copyPerformances.remove(index);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = performancesJList.getSelectedIndex();
                ArrayList<Performance> performanceList = (ArrayList<Performance>) actor.getPerformances();
                Iterator<Performance> iterator = performances.iterator();

                while (iterator.hasNext()) {
                    Performance performance = iterator.next();
                    iterator.remove();
                }

                for (Performance performance : copyPerformances) {
                    performanceList.add(performance);
                }

                if (!newInfo)
                    ((Staff) user).updateActor(actor);

                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());

        moreDetailsButton.setBackground(new Color(25, 25, 25));
        moreDetailsButton.setForeground(Color.WHITE);

        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(25, 25, 25));

        addperformance.setBackground(new Color(25, 25, 25));
        addperformance.setForeground(Color.WHITE);

        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(25, 25, 25));
        buttonPanel.setBackground(new Color(40, 40, 40));

        buttonPanel.add(moreDetailsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addperformance);
        buttonPanel.add(saveButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(650, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
