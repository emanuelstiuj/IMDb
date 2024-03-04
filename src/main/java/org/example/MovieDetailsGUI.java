package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MovieDetailsGUI extends JFrame implements ExperienceStrategy {
    private User user;
    private Movie movie;
    private Boolean removePanel;

    @Override
    public int calculateExperience() {
        return 1;
    }

    public MovieDetailsGUI(Movie movie, User user) {
        this.user = user;
        this.movie = movie;
        this.removePanel = removePanel;

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 40, 40));

        JPanel movieInfoPanel = createMovieInfoPanel(movie);
        add(movieInfoPanel, BorderLayout.NORTH);

        JPanel reviewsPanel = createReviewsPanel((ArrayList<Rating>) movie.getRatings());
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsPanel);
        reviewsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(reviewsScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = createButtonsPanel();
        buttonsPanel.setBackground(new Color(30, 30, 30));
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Movie Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 430);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createMovieInfoPanel(Movie movie) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel movieInfoPanel = new JPanel(new GridLayout(7, 1));
        movieInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        movieInfoPanel.setBackground(new Color(30, 30, 30));
        movieInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Color labelColor = Color.GRAY;

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel durationLabel = new JLabel("Duration: " + movie.getDuration());
        durationLabel.setFont(labelFont);
        durationLabel.setForeground(labelColor);
        durationLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel releaseYearLabel = new JLabel("Release Year: " + movie.getReleaseYear());
        releaseYearLabel.setFont(labelFont);
        releaseYearLabel.setForeground(labelColor);
        releaseYearLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel ratingLabel = new JLabel("Average Rating: " + movie.getAverageRating());
        ratingLabel.setFont(labelFont);
        ratingLabel.setForeground(labelColor);
        ratingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel actorsLabel = new JLabel("Actors: " + String.join(", ", movie.getActors()));
        actorsLabel.setFont(labelFont);
        actorsLabel.setForeground(labelColor);
        actorsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel genresLabel = new JLabel("Genres: " + movie.getGenres());
        genresLabel.setFont(labelFont);
        genresLabel.setForeground(labelColor);
        genresLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JLabel directorsLabel = new JLabel("Directors: " + movie.getDirectors());
        directorsLabel.setFont(labelFont);
        directorsLabel.setForeground(labelColor);
        directorsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        ArrayList<Rating> ratings = (ArrayList<Rating>) movie.getRatings();
        JPanel reviewsPanel = createReviewsPanel(ratings);
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsPanel);
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewsScrollPane.setBorder(BorderFactory.createTitledBorder("Reviews"));

        JTextArea plotTextArea = new JTextArea("Plot: " + movie.getPlot());
        plotTextArea.setFont(labelFont);
        plotTextArea.setForeground(Color.LIGHT_GRAY);
        plotTextArea.setBackground(new Color(40, 40, 40));
        plotTextArea.setLineWrap(true);
        plotTextArea.setWrapStyleWord(true);
        plotTextArea.setEditable(false);

        JPanel plotPanel = new JPanel(new BorderLayout());
        plotPanel.add(plotTextArea);
        plotPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        plotPanel.setBackground(new Color(40,40, 40));

        movieInfoPanel.add(titleLabel);
        movieInfoPanel.add(durationLabel);
        movieInfoPanel.add(releaseYearLabel);
        movieInfoPanel.add(ratingLabel);
        movieInfoPanel.add(actorsLabel);
        movieInfoPanel.add(genresLabel);
        movieInfoPanel.add(directorsLabel);

        JPanel finalPanel = new JPanel(new BorderLayout());
        finalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        finalPanel.setBackground(new Color(30, 30, 30));
        finalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel imageLabel = new JLabel(movie.getImageIconDescription());
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(120, 160));
        imagePanel.setBackground(new Color(10, 10, 10));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        finalPanel.add(imagePanel, BorderLayout.WEST);
        finalPanel.add(movieInfoPanel, BorderLayout.CENTER);

        mainPanel.add(finalPanel, BorderLayout.NORTH);
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
            singleReviewPanel.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 0));

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
        buttonsPanel.setBackground(new Color(30, 30, 30));
        JButton addToFavouritesButton;

            if (user.getFavorites().contains(movie))
                addToFavouritesButton = new JButton("Remove from Favorites");
            else
                addToFavouritesButton = new JButton("Add to Favorites");

            addToFavouritesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (user.getFavorites().contains(movie)) {
                        user.getFavorites().remove(movie);
                        movie.getFavoriteUsers().remove(user);
                        addToFavouritesButton.setText("Add to Favorites");
                    } else {
                        user.getFavorites().add(movie);
                        movie.getFavoriteUsers().add(user);
                        addToFavouritesButton.setText("Remove form Favorites");
                    }
                }
            });
            buttonsPanel.add(addToFavouritesButton);

            if (user.getUserType() == AccountType.Regular) {
                JButton leaveReviewButton;
                if (movie.findRating(user))
                    leaveReviewButton = new JButton("Delete Review");
                else
                    leaveReviewButton = new JButton("Leave Review");
                leaveReviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (movie.findRating(user)) {
                            for (User user2  : IMDB.getInstance().getUsers()) {
                                user2.getNotifications().removeIf(notification -> ((String)notification)
                                        .contains(movie.getTitle() + " pe care l-ai evaluat a primit un review de la utilizatorul \"" +
                                                user.getUsername() + "\" ->"));

                                user2.getNotifications().removeIf(notification -> ((String)notification)
                                        .contains(movie.getTitle() + " pe care il ai in lista de favorite a primit un review de la" +
                                                " utilizatorul \"" + user.getUsername() + "\" ->"));

                                user2.getNotifications().removeIf(notification -> ((String)notification)
                                        .contains(movie.getTitle() + " pe care l-ai adaugat a primit un review de la utilizatorul \"" +
                                                user.getUsername() + "\" ->"));
                            }
                            movie.getRatings().remove(movie.getRating(user.getUsername()));
                            movie.setAverageRating(movie.calculateAverageRating());
                            leaveReviewButton.setText("Leave Review");
                            user.setExperience(user.getExperience() - calculateExperience());
                        } else {
                            new LeaveReviewGUI(user, movie, leaveReviewButton);
                        }
                    }
                });
                buttonsPanel.add(leaveReviewButton);
            }

        JButton refreshButton = new JButton("Refresh Window");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MovieDetailsGUI(movie, user);
            }
        });

        buttonsPanel.add(refreshButton);

        return buttonsPanel;
    }
}
