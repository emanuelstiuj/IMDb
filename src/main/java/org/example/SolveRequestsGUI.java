package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class SolveRequestsGUI extends JFrame implements ExperienceStrategy {
    private User user;
    private JPanel requestsPanel;
    private JScrollPane scrollPane;

    @Override
    public int calculateExperience() {
        return 2;
    }

    public static void notifySolvedRequest(Boolean solved, Request request) {
        String requstInfo = " catre userul \"" + request.getTo() + "\"";
        requstInfo = requstInfo + " (type: " + request.getType();
        if (request.getType().equals(RequestTypes.MOVIE_ISSUE)) {
            requstInfo = requstInfo + " production: " + request.getMovieTitle();
        } else if (request.getType().equals(RequestTypes.ACTOR_ISSUE)) {
            requstInfo = requstInfo + " actor: " + request.getActorName();
        }
        requstInfo = requstInfo + " description: " + request.getDescription() + ")";

        if (solved) {
            String notification = "Cererea: " + requstInfo + " a fost rezolvata";
            IMDB.getInstance().getUserByUsername(request.getUsername()).update(notification);
        } else {
            String notification = "Cererea: " + requstInfo + " a fost refuzata";
            IMDB.getInstance().getUserByUsername(request.getUsername()).update(notification);
        }
    }

    public SolveRequestsGUI(User user) {
        this.user = user;
        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBackground(new Color(40, 40, 40));

        ArrayList<Request> myUnsolvedRequests = (ArrayList<Request>) ((Staff)user).getRequests();
        Collections.reverse(myUnsolvedRequests);
        int countRequests = 0;
        for (Request request : myUnsolvedRequests) {
            JPanel requestInfoPanel = createRequestInfoPanel(request);
            requestsPanel.add(requestInfoPanel);
            countRequests++;
        }
        Collections.reverse(myUnsolvedRequests);

        Collections.reverse(Admin.RequestsHolder.getRequests());
        if (user.getUserType().equals(AccountType.Admin)) {
            for (Request request : Admin.RequestsHolder.getRequests()) {
                JPanel requestInfoPanel = createRequestInfoPanel(request);
                requestsPanel.add(requestInfoPanel);
                countRequests++;
            }
        }
        Collections.reverse(Admin.RequestsHolder.getRequests());

        if (countRequests == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);

            JLabel label = new JLabel("YOU HAVE NOT RECEIVED ANY REQUEST");
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
                bioTextArea.append("Actor: " + request.getActorName() + "\n");
            } else if (request.getType().equals(RequestTypes.MOVIE_ISSUE)) {
                bioTextArea.append("Production: " + request.getMovieTitle() + "\n");
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

        JButton deleteButton = new JButton("Reject Request");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (request.getTo().equals("ADMIN")) {
                    Admin.RequestsHolder.getRequests().remove(request);
                    notifySolvedRequest(false, request);
                } else {
                    ((Staff) user).getRequests().remove(request);
                    notifySolvedRequest(false, request);
                }

                User user1 = IMDB.getInstance().getUserByUsername(request.getUsername());
                ArrayList<Request> requests = new ArrayList<>(user1.getUnsolvedRequests());
                requests.removeIf(unsolvedReq -> unsolvedReq.getTo().equals(request.getTo()) && unsolvedReq.getId() == request.getId());
                user1.setUnsolvedRequests(requests);

                refreshInfo();
            }
        });

        JButton solvedButton = new JButton("Request Solved");
        solvedButton.setBackground(Color.GREEN);
        solvedButton.setForeground(Color.BLACK);
        solvedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (request.getTo().equals("ADMIN")) {
                    Admin.RequestsHolder.getRequests().remove(request);
                    notifySolvedRequest(true, request);
                } else {
                    IMDB.getInstance().getUserByUsername(request.getUsername()).setExperience(
                            IMDB.getInstance().getUserByUsername(request.getUsername()).getExperience()
                                    + calculateExperience());
                    ((Staff) user).getRequests().remove(request);
                    notifySolvedRequest(true, request);
                }

                User user1 = IMDB.getInstance().getUserByUsername(request.getUsername());
                ArrayList<Request> requests = new ArrayList<>(user1.getUnsolvedRequests());
                requests.removeIf(unsolvedReq -> unsolvedReq.getTo().equals(request.getTo()) && unsolvedReq.getId() == request.getId());
                user1.setUnsolvedRequests(requests);

                refreshInfo();
            }
        });

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });

        buttonPanel.add(deleteButton);
        buttonPanel.add(solvedButton);

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

        ArrayList<Request> myUnsolvedRequests = (ArrayList<Request>) ((Staff) user).getRequests();
        int countRequests = 0;
        for (Request request : myUnsolvedRequests) {
            JPanel requestInfoPanel = createRequestInfoPanel(request);
            requestsPanel.add(requestInfoPanel);
            countRequests++;
        }

        if (user.getUserType().equals(AccountType.Admin)) {
            for (Request request : Admin.RequestsHolder.getRequests()) {
                JPanel requestInfoPanel = createRequestInfoPanel(request);
                requestsPanel.add(requestInfoPanel);
                countRequests++;
            }
        }

        if (countRequests == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);

            JLabel label = new JLabel("YOU HAVE NOT RECEIVED ANY REQUEST");
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

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(700, 700);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
    }
}

