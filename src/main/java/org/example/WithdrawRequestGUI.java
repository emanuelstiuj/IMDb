package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class WithdrawRequestGUI extends JFrame {
    private User user;
    private JPanel requestsPanel;
    private JScrollPane scrollPane;

    public WithdrawRequestGUI (User user) {
        this.user = user;
        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBackground(new Color(40, 40, 40));

        ArrayList<Request> myUnsolvedRequests = (ArrayList<Request>) user.getUnsolvedRequests();
        int countRequests = 0;
        for (Request request : myUnsolvedRequests) {
            JPanel requestInfoPanel = createRequestInfoPanel(request);
            requestsPanel.add(requestInfoPanel);
            countRequests++;
        }

        if (countRequests == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);

            JLabel label = new JLabel("YOU HAVE NOT SENT ANY REQUEST");
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setPreferredSize(new Dimension(690, 690));
            panel.add(label, BorderLayout.CENTER);
            getContentPane().add(panel, BorderLayout.CENTER);
        } else {

            requestsPanel.setPreferredSize(new Dimension(680, 230 * countRequests));

            scrollPane = new JScrollPane(requestsPanel);
            scrollPane.setPreferredSize(new Dimension(690, scrollPane.getPreferredSize().height));
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setBackground(new Color(15, 15, 15));
            requestsPanel.setBackground(new Color(15, 15, 15));

            getContentPane().setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(40, 40, 40));
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
        }

            setTitle("requests Information");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(700, 700);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);

    }

    private JPanel createRequestInfoPanel(Request request) {
        JPanel requestInfoPanel = new JPanel(new BorderLayout());
        requestInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requestInfoPanel.setBackground(new Color(35, 35, 35));
        Border border =  new LineBorder(Color.BLACK, 6);
        requestInfoPanel.setBorder(border);

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JTextArea bioTextArea = new JTextArea();
        bioTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        if (request.getDescription() != null) {
            bioTextArea.append("From: " + request.getUsername() + "\n");
            bioTextArea.append("To: " + request.getTo() +"\n");
            bioTextArea.append("Request Type: " + request.getType() + "\n");
            if (request.getType().equals(RequestTypes.ACTOR_ISSUE)) {
                bioTextArea.append("Actor Name: " + request.getActorName() + "\n");
            }
            if (request.getType().equals(RequestTypes.MOVIE_ISSUE)) {
                bioTextArea.append("Production Title: " + request.getMovieTitle() + "\n");
            }
            bioTextArea.append("Date: " + request.getCreatedDate().format(formatter) + "\n");
            bioTextArea.append("Description: " + request.getDescription() + "\n");
            bioTextArea.setFont(labelFont);
            bioTextArea.setForeground(Color.LIGHT_GRAY);
            bioTextArea.setBackground(new Color(35, 35, 35));
            bioTextArea.setLineWrap(true);
            bioTextArea.setWrapStyleWord(true);
            bioTextArea.setEditable(false);
            bioTextArea.setCaretPosition(0);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(35, 35, 35));

        JButton deleteButton = new JButton("Delete Request");
        deleteButton.setBackground(new Color(10, 10, 10));
        deleteButton.setForeground(new Color(220, 220, 220));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (User user2 : IMDB.getInstance().getUsers()) {
                    user2.getNotifications().removeIf(notification -> notification.equals("Ai primit o noua " +
                            "cerere de la utilizatorul \"" + user.getUsername() + "\": " + request.getDescription()));
                }

                user.getUnsolvedRequests().remove(request);
                bioTextArea.setCaretPosition(0);

                if (user instanceof Regular) {
                    ((Regular) user).removeRequest(request);
                } else {
                    ((Contributor) user).removeRequest(request);
                }

                refreshInfo();
            }
        });

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });

        buttonPanel.add(deleteButton);

        requestInfoPanel.add(bioTextArea, BorderLayout.CENTER);
        requestInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        requestInfoPanel.setMinimumSize(new Dimension(690, 0));
        requestInfoPanel.setMaximumSize(new Dimension(690, 220));

        return requestInfoPanel;
    }

    private void refreshInfo() {
        getContentPane().removeAll();

        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBackground(new Color(40, 40, 40));

        ArrayList<Request> myUnsolvedRequests = (ArrayList<Request>) user.getUnsolvedRequests();
        int countRequests = 0;
        for (Request request : myUnsolvedRequests) {
            JPanel requestInfoPanel = createRequestInfoPanel(request);
            requestsPanel.add(requestInfoPanel);
            countRequests++;
        }

        if (countRequests == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);

            JLabel label = new JLabel("YOU HAVE NOT SENT ANY REQUEST");
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setPreferredSize(new Dimension(690, 690));
            panel.add(label, BorderLayout.CENTER);
            getContentPane().add(panel, BorderLayout.CENTER);
        } else {

            requestsPanel.setPreferredSize(new Dimension(680, 230 * countRequests));

            scrollPane = new JScrollPane(requestsPanel);
            scrollPane.setPreferredSize(new Dimension(690, scrollPane.getPreferredSize().height));
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setBackground(new Color(15, 15, 15));
            requestsPanel.setBackground(new Color(15, 15, 15));

            getContentPane().setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(40, 40, 40));
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
        }

            setTitle("requests Information");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(700, 700);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
    }
}
