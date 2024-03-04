package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChangeGenresGUI extends JFrame {

    private Production production;
    private User user;
    private DefaultListModel<String> genreListModel;
    private JList<String> genreList;
    private JButton addgenreButton;
    private JButton saveButton;

    public ChangeGenresGUI(Production production, User user) {
        this.user = user;
        this.production = production;

        setTitle("Change Genres");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        genreListModel = new DefaultListModel<>();
        ArrayList<Genre> genres = (ArrayList<Genre>) production.getGenres();
        for (Genre genre : genres) {
            genreListModel.addElement(genre.toString());
        }

        genreList = new JList<>(genreListModel);
        genreList.setBackground(new Color(100, 100, 100));
        genreList.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(genreList);

        JButton deleteButton = new JButton("Delete Genre");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(25, 25, 25));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = genreList.getSelectedIndex();
                if (selectedIndex != -1) {
                    genreListModel.remove(selectedIndex);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(32, 32, 32));
        buttonPanel.add(deleteButton);

        addgenreButton = new JButton("Add New Genre");
        addgenreButton.setBackground(new Color(25, 25, 25));
        addgenreButton.setForeground(Color.WHITE);
        addgenreButton.addActionListener(e -> {
            String[] allGenres = {
                    "Action",
                    "Adventure",
                    "Comedy",
                    "Drama",
                    "Horror",
                    "SF",
                    "Fantasy",
                    "Romance",
                    "Mystery",
                    "Thriller",
                    "Crime",
                    "Biography",
                    "War",
                    "Cooking"
            };

            JPanel parentPanel = new JPanel();

            Point buttonLocation = addgenreButton.getLocationOnScreen();

            int adjustedY = (int) buttonLocation.getY() - 150;

            parentPanel.setLocation((int) buttonLocation.getX(), adjustedY);

            JFrame genreSelectionFrame = new JFrame("Genre Selection");
            genreSelectionFrame.setSize(200, 300);
            genreSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            genreSelectionFrame.setLocationRelativeTo(parentPanel);
            genreSelectionFrame.setResizable(false);

            JPanel contentPane = new JPanel();
            contentPane.setBackground(new Color(30, 30, 30));
            genreSelectionFrame.setContentPane(contentPane);

            JList<String> genreList = new JList<>(allGenres);
            genreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            genreList.setBackground(new Color(30, 30, 30));
            genreList.setForeground(Color.WHITE);

            JScrollPane scrollPane2 = new JScrollPane(genreList);
            scrollPane2.setPreferredSize(new Dimension(180, 200));

            contentPane.add(scrollPane2);

            JButton confirmButton = new JButton("Confirm");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedGenre = genreList.getSelectedValue();
                    if (selectedGenre != null) {
                        if (!genreListModel.contains(selectedGenre))
                            genreListModel.addElement(selectedGenre);
                    } else {

                    }
                    genreSelectionFrame.dispose();
                }
            });

            contentPane.add(confirmButton);

            genreSelectionFrame.setVisible(true);
        });

        saveButton = new JButton("Save Changes");
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(25, 25, 25));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Genre> genres = new ArrayList<>(genreListModel.size());
                for (int i = 0; i < genreListModel.size(); i++) {
                    genres.add(Genre.valueOf(genreListModel.elementAt(i)));
                }
                production.setGenres(genres);
                dispose();
            }
        });

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(addgenreButton);
        southPanel.add(saveButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
