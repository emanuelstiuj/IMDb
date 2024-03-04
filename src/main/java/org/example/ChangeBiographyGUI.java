package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeBiographyGUI extends JFrame {
    private Actor actor;
    private JTextArea plotTextArea;
    private User user;
    private boolean newInfo;

    public ChangeBiographyGUI(Actor actor, User user, Boolean newInfo) {
        this.actor = actor;
        this.user = user;
        this.newInfo = newInfo;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));
        JLabel titleLabel;

        if (newInfo)
            titleLabel = new JLabel("Add plot");
        else
            titleLabel = new JLabel("Change plot");

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.add(titleLabel);
        panel.setPreferredSize(new Dimension(400, 35));

        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(panel, BorderLayout.NORTH);

        plotTextArea = new JTextArea(7, 30);
        plotTextArea.setLineWrap(true);
        plotTextArea.setWrapStyleWord(true);
        plotTextArea.setText(actor.getBiography());
        plotTextArea.setBackground(new Color(100, 100, 100));
        plotTextArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(plotTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actor.setBiography(plotTextArea.getText());

                if (!newInfo)
                    ((Staff) user).updateActor(actor);

                dispose();
            }
        });

        saveButton.setBackground(new Color(40, 40, 40));
        saveButton.setForeground(Color.WHITE);
        add(saveButton, BorderLayout.SOUTH);

        setSize(new Dimension(430, 240));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
