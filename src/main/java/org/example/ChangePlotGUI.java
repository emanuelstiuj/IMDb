package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePlotGUI extends JFrame {

    private Production production;
    private JTextArea plotTextArea;

    public ChangePlotGUI(Production production) {
        this.production = production;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30,30, 30));

        JLabel titleLabel = new JLabel("Change Plot");
        titleLabel.setPreferredSize(new Dimension(350, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(new Color(40, 40, 40));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.add(titleLabel);
        add(panel, BorderLayout.NORTH);

        plotTextArea = new JTextArea(7, 30);
        plotTextArea.setLineWrap(true);
        plotTextArea.setBackground(new Color(160, 160, 160));
        plotTextArea.setForeground(Color.BLACK);
        plotTextArea.setWrapStyleWord(true);
        plotTextArea.setText(production.getPlot());

        JScrollPane scrollPane = new JScrollPane(plotTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                production.setPlot(plotTextArea.getText());
                dispose();
            }
        });

        saveButton.setBackground(new Color(40, 40, 40));
        saveButton.setPreferredSize(new Dimension(350, 30));
        saveButton.setForeground(Color.WHITE);
        add(saveButton, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
