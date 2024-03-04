package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeriesDetailsGUI extends JFrame implements ExperienceStrategy {
    private User user;
    private Series series;
    private Boolean removePanel;

    @Override
    public int calculateExperience() {
        return 2;
    }

    public SeriesDetailsGUI(Series series, User user) {
        this.removePanel = removePanel;
        this.user = user;
        this.series = series;
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        JPanel SeriesInfoPanel = createSeriesInfoPanel(series);
        add(SeriesInfoPanel, BorderLayout.NORTH);

        JPanel reviewsPanel = createReviewsPanel((ArrayList<Rating>) series.getRatings());
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsPanel);
        reviewsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(reviewsScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = createButtonsPanel();
        buttonsPanel.setBackground(new Color(30, 30, 30));
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Series Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(680, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createSeriesInfoPanel(Series series) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel seriesInfoPanel = new JPanel(new GridLayout(7, 1));
        seriesInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        seriesInfoPanel.setBackground(new Color(30, 30, 30));
        seriesInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Color labelColor = Color.GRAY;

        JLabel titleLabel = new JLabel("Title: " + series.getTitle());
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel durationLabel = new JLabel("Number of seasons: " + series.getNumSeasons());
        durationLabel.setFont(labelFont);
        durationLabel.setForeground(labelColor);
        durationLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel ratingLabel = new JLabel("Average Rating: " + series.getAverageRating());
        ratingLabel.setFont(labelFont);
        ratingLabel.setForeground(labelColor);
        ratingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel releaseYearLabel = new JLabel("Release Year: " + series.getReleaseYear());
        releaseYearLabel.setFont(labelFont);
        releaseYearLabel.setForeground(labelColor);
        releaseYearLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel actorsLabel = new JLabel("Actors: " + String.join(", ", series.getActors()));
        actorsLabel.setFont(labelFont);
        actorsLabel.setForeground(labelColor);
        actorsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel genresLabel = new JLabel("Genres: " + series.getGenres());
        genresLabel.setFont(labelFont);
        genresLabel.setForeground(labelColor);
        genresLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel directorsLabel = new JLabel("Directors: " + series.getDirectors());
        directorsLabel.setFont(labelFont);
        directorsLabel.setForeground(labelColor);
        directorsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        ArrayList<Rating> ratings = (ArrayList<Rating>) series.getRatings();
        JPanel reviewsPanel = createReviewsPanel(ratings);
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsPanel);
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewsScrollPane.setBorder(BorderFactory.createTitledBorder("Reviews"));

        JTextArea plotTextArea = new JTextArea("Plot: " + series.getPlot());
        plotTextArea.setFont(labelFont);
        plotTextArea.setForeground(Color.LIGHT_GRAY);
        plotTextArea.setBackground(new Color(30, 30, 30));
        plotTextArea.setLineWrap(true);
        plotTextArea.setWrapStyleWord(true);
        plotTextArea.setEditable(false);

        Font seasonsFont = new Font("DejaVu Sans Mono", Font.PLAIN, 15);
        JPanel plotPanel = new JPanel(new BorderLayout());
        plotPanel.add(plotTextArea);
        plotPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        plotPanel.setBackground(new Color(30, 30, 30));

        seriesInfoPanel.add(titleLabel);
        seriesInfoPanel.add(durationLabel);
        seriesInfoPanel.add(releaseYearLabel);
        seriesInfoPanel.add(ratingLabel);
        seriesInfoPanel.add(actorsLabel);
        seriesInfoPanel.add(genresLabel);
        seriesInfoPanel.add(directorsLabel);

        JPanel finalPanel = new JPanel(new BorderLayout());
        finalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        finalPanel.setBackground(new Color(30, 30, 30));
        finalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel imageLabel = new JLabel(series.getImageIconDescription());
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(120, 160));
        imagePanel.setBackground(new Color(10, 10, 10));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        finalPanel.add(imagePanel, BorderLayout.WEST);
        finalPanel.add(seriesInfoPanel, BorderLayout.CENTER);

        mainPanel.add(finalPanel, BorderLayout.NORTH);
        mainPanel.add(plotPanel, BorderLayout.SOUTH);


        JTextArea episodesTextArea = new JTextArea();
        episodesTextArea.setFont(seasonsFont);
        episodesTextArea.setBounds(0, 0, 5, 5);
        episodesTextArea.setForeground(Color.LIGHT_GRAY);
        episodesTextArea.setBackground(new Color(40, 40, 40));
        episodesTextArea.setLineWrap(true);
        episodesTextArea.setWrapStyleWord(true);
        episodesTextArea.setEditable(false);

        for (Map.Entry<String, List<Episode>> entry : series.getSeasons().entrySet()) {
            String key = entry.getKey();
            List<Episode> episodes = entry.getValue();
            episodesTextArea.append(key + "\n");
            Integer countEp = 0;
            for (Episode episode : episodes) {
                countEp++;
                episodesTextArea.append("Episode " + countEp.toString() + ": \"" +
                        episode.getEpisodeName() + "\" (" + episode.getDuration() + ")\n");
            }
            episodesTextArea.append("\n");
        }
        episodesTextArea.setCaretPosition(0);

        JScrollPane episodesScrollPane = new JScrollPane(episodesTextArea);
        episodesScrollPane.setViewportView(episodesTextArea);
        episodesScrollPane.setPreferredSize(new Dimension(660, 190));
        episodesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        episodesScrollPane.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 0));
        episodesScrollPane.setBackground(new Color(40, 40, 40));
        episodesScrollPane.setForeground(Color.LIGHT_GRAY);
        episodesScrollPane.getVerticalScrollBar().setValue(episodesScrollPane.getVerticalScrollBar().getMinimum());

        mainPanel.add(finalPanel, BorderLayout.NORTH);
        mainPanel.add(episodesScrollPane, BorderLayout.CENTER);
        mainPanel.add(plotPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createReviewsPanel(ArrayList<Rating> ratings) {
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setBackground(new Color(25, 25, 25));

        if (ratings.isEmpty()) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(25, 25, 25));
            JLabel label = new JLabel("There are no reviews yet");
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 17);
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
            return panel;
        }

        for (Rating rating : ratings) {
            JPanel singleReviewPanel = new JPanel();
            singleReviewPanel.setLayout(new BoxLayout(singleReviewPanel, BoxLayout.Y_AXIS));
            singleReviewPanel.setBackground(new Color(25, 25, 25));
            singleReviewPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

            Font labelFont = new Font("DejaVu Sans Mono", Font.PLAIN, 14);

            JLabel reviewLabel = new JLabel("Username: " + rating.getUsername());
            reviewLabel.setForeground(Color.LIGHT_GRAY);
            reviewLabel.setFont(labelFont);
            singleReviewPanel.add(reviewLabel);

            JLabel ratingLabel = new JLabel("Rating: " + rating.getRating());
            ratingLabel.setForeground(Color.LIGHT_GRAY);
            ratingLabel.setFont(labelFont);
            singleReviewPanel.add(ratingLabel);

            JLabel descriptionArea = new JLabel("Description: " + rating.getComment());
            descriptionArea.setForeground(Color.LIGHT_GRAY);
            descriptionArea.setFont(labelFont);
            singleReviewPanel.add(descriptionArea);

            reviewsPanel.add(singleReviewPanel);
            reviewsPanel.add(new JSeparator());
        }

        return reviewsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        JButton addToFavouritesButton;

        Font labelFont = new Font("Arial", Font.BOLD, 16);

            if (user.getFavorites().contains(series))
                addToFavouritesButton = new JButton("Remove from Favorites");
            else
                addToFavouritesButton = new JButton("Add to Fravorites");

            addToFavouritesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (user.getFavorites().contains(series)) {
                        user.getFavorites().remove(series);
                        series.getFavoriteUsers().remove(user);
                        addToFavouritesButton.setText("Add to Favorites");
                    } else {
                        user.getFavorites().add(series);
                        series.getFavoriteUsers().add(user);
                        addToFavouritesButton.setText("Remove form Favorites");
                    }
                }
            });
            buttonsPanel.add(addToFavouritesButton);

        JButton refreshButton = new JButton("Refresh Window");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SeriesDetailsGUI(series, user);
            }
        });

            if (user.getUserType() == AccountType.Regular) {
                JButton leaveReviewButton;
                if (series.findRating(user))
                    leaveReviewButton = new JButton("Delete Review");
                else
                    leaveReviewButton = new JButton("Leave Review");
                leaveReviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (series.findRating(user)) {
                            for (User user2  : IMDB.getInstance().getUsers()) {
                                user2.getNotifications().removeIf(notification -> ((String)notification)
                                        .contains(series.getTitle() + " pe care l-ai evaluat a primit un review de la utilizatorul \"" +
                                                user.getUsername() + "\" ->"));

                                user2.getNotifications().removeIf(notification -> ((String)notification)
                                        .contains(series.getTitle() + " pe care il ai in lista de favorite a primit un review de la" +
                                                " utilizatorul \"" + user.getUsername() + "\" ->"));

                                user2.getNotifications().removeIf(notification -> ((String)notification)
                                        .contains(series.getTitle() + " pe care l-ai adaugat a primit un review de la utilizatorul \"" +
                                                user.getUsername() + "\" ->"));
                            }
                            series.getRatings().remove(series.getRating(user.getUsername()));
                            series.setAverageRating(series.calculateAverageRating());
                            leaveReviewButton.setText("Leave Review");
                            user.setExperience(user.getExperience() - calculateExperience());
                        } else {
                            new LeaveReviewGUI(user, series, leaveReviewButton);
                        }
                    }
                });
                buttonsPanel.add(leaveReviewButton);
            }

        buttonsPanel.add(refreshButton);

        return buttonsPanel;
    }
}
