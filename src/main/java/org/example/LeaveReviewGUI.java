package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class LeaveReviewGUI extends JFrame implements ExperienceStrategy {
    private User user;
    private Production production;
    JButton button;

    @Override
    public int calculateExperience() {
        return 1;
    }

    public LeaveReviewGUI(User user, Production production, JButton button) {
        this.user = user;
        this.production = production;
        this.button = button;

        setTitle("Add Review");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(40, 40, 40));

        JLabel ratingLabel = new JLabel("Rating:         ");
        ratingLabel.setForeground(Color.WHITE);
        JTextField ratingTextField = new JTextField(20);
        JPanel ratingPanel = new JPanel();
        ratingPanel.add(ratingLabel);
        ratingPanel.add(ratingTextField);
        ratingPanel.setBackground(new Color(40, 40, 40));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.WHITE);
        JTextArea descriptionTextArea = new JTextArea(5, 20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionScrollPane);
        descriptionPanel.setBackground(new Color(40, 40, 40));

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ratingText = ratingTextField.getText();
                String descriptionText = descriptionTextArea.getText();

                try {
                    int rating = Integer.parseInt(ratingText);
                    if (rating < 1 || rating > 10) {
                        JOptionPane.showMessageDialog(LeaveReviewGUI.this,
                                "Rating must be between 1 and 10", "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Rating review = new Rating();
                    review.setComment(descriptionText);
                    review.setRating(rating);
                    review.setUsername(user.getUsername());
                    user.setExperience(user.getExperience() + calculateExperience());

                    production.getRatings().add(review);
                    Collections.sort(production.getRatings());
                    production.setAverageRating(production.calculateAverageRating());
                    production.getRatingUsers().add(user);
                    button.setText("Delete Review");
                    production.notifyAll(review);

                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LeaveReviewGUI.this,
                            "Rating must be a number", "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(25, 25, 25));

        mainPanel.add(ratingPanel);
        mainPanel.add(descriptionPanel);
        mainPanel.add(sendButton);

        add(mainPanel, BorderLayout.CENTER);

        pack();
        setSize(new Dimension(400, 200));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
