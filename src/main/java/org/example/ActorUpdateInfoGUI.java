package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActorUpdateInfoGUI extends JFrame {

    private Actor actor;
    private JLabel titleLabel;
    private JButton actorNameButton, biographyButton, performancesButton;
    private User user;
    private Boolean newInfo;

    public ActorUpdateInfoGUI(Actor actor, User user, Boolean newInfo) {
        this.actor = actor;
        this.user = user;
        this.newInfo = newInfo;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        if (newInfo)
            titleLabel = new JLabel("ADD NEW ACTOR");
        else
            titleLabel = new JLabel("Actor Information");

        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.add(titleLabel);

        actorNameButton = new JButton("Actor Name");
        actorNameButton.setBackground(new Color(25, 25, 25));
        actorNameButton.setForeground(Color.WHITE);
        biographyButton = new JButton("Biography");
        biographyButton.setBackground(new Color(25, 25, 25));
        biographyButton.setForeground(Color.WHITE);
        performancesButton = new JButton("Performances");
        performancesButton.setBackground(new Color(25, 25, 25));
        performancesButton.setForeground(Color.WHITE);

        actorNameButton.addActionListener(new ActorUpdateInfoGUI.ButtonClickListener("Actor name"));
        biographyButton.addActionListener(new ActorUpdateInfoGUI.ButtonClickListener("Biography"));
        performancesButton.addActionListener(new ActorUpdateInfoGUI.ButtonClickListener("Performances"));

        mainPanel.add(panel);
        mainPanel.add(actorNameButton);
        mainPanel.add(biographyButton);
        mainPanel.add(performancesButton);

        JButton saveButton = new JButton("SAVE ACTOR");
        saveButton.setForeground(Color.YELLOW);
        saveButton.setBackground(Color.BLACK);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actor.getName() == null || actor.getName().isEmpty() || actor.getPerformances() == null ||
                    actor.getPerformances().isEmpty()) {
                    showWarning("Complete all the fields before saving the actor!");
                    return;
                }

                if (IMDB.getInstance().getActor(actor.getName()) != null) {
                    showWarning(actor.getName() + " already exists in the system");
                    return;
                }

                IMDB.getInstance().getActors().add(actor);
                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        if (newInfo) {
            add(saveButton, BorderLayout.SOUTH);
        }

        add(mainPanel, BorderLayout.CENTER);

        setSize(300, 180);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private String buttonName;

        public ButtonClickListener(String name) {
            buttonName = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (buttonName) {
                case "Actor name":
                    new ChangeActorNameGUI(newInfo, actor, user);
                    break;
                case "Biography":
                    new ChangeBiographyGUI(actor, user, newInfo);
                    break;
                case "Performances":
                    new ActorPerformancesGUI(actor, user, newInfo);
                    break;
            }
        }
    }
}
