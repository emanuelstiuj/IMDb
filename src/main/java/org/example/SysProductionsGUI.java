package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SysProductionsGUI extends JFrame {
    private JPanel filmsPanel;
    private JScrollPane scrollPane;
    private User user;

    public SysProductionsGUI(User user) {
        this.user = user;
        filmsPanel = new JPanel();
        filmsPanel.setLayout(new BoxLayout(filmsPanel, BoxLayout.Y_AXIS));
        filmsPanel.setBackground(new Color(40, 40, 40));
        filmsPanel.setPreferredSize(new Dimension(680, 160 * IMDB.getInstance().getProductions().size()));

        for (Production production : IMDB.getInstance().getProductions()) {
            JPanel filmInfoPanel = createFilmInfoPanel(production);
            filmsPanel.add(filmInfoPanel);
        }
        scrollPane = new JScrollPane(filmsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton filterByRatingButton = new JButton("Filter by Rating");
        filterByRatingButton.setForeground(new Color(50, 50, 50));
        JButton filterByGenreButton = new JButton("Filter by Genre");
        filterByGenreButton.setForeground(new Color(50, 50, 50));
        JButton resetButton = new JButton("Refresh Frame");
        resetButton.setForeground(new Color(50, 50, 50));


        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filmsPanel.removeAll();
                int countProductions = 0;
                for (Production production : IMDB.getInstance().getProductions()) {
                    JPanel filmInfoPanel = createFilmInfoPanel(production);
                    countProductions++;
                    filmsPanel.add(filmInfoPanel);
                }
                filmsPanel.setPreferredSize(new Dimension(680, 160 * countProductions));
                filmsPanel.revalidate();
                filmsPanel.repaint();
            }
        });

        filterByRatingButton.addActionListener(e -> {
            JFrame ratingSelectionFrame = new JFrame("Rating Selection");
            ratingSelectionFrame.setSize(250, 110);
            ratingSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ratingSelectionFrame.setLocationRelativeTo(null);
            ratingSelectionFrame.setResizable(false);

            JPanel contentPane = new JPanel();
            contentPane.setBackground(new Color(30, 30, 30));
            contentPane.setLayout(new BorderLayout());
            ratingSelectionFrame.setContentPane(contentPane);

            JLabel selectRatingLabel = new JLabel("Select minimum rating:");
            selectRatingLabel.setForeground(Color.WHITE);
            selectRatingLabel.setHorizontalAlignment(SwingConstants.CENTER);

            Font labelFont = new Font("DejaVu Sans Mono", Font.BOLD, 16);
            selectRatingLabel.setFont(labelFont);

            JTextField ratingTextField = new JTextField();
            ratingTextField.setHorizontalAlignment(JTextField.CENTER);

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setBackground(new Color(40, 40, 40));
            confirmButton.setForeground(Color.LIGHT_GRAY);
            confirmButton.addActionListener(event -> {
                 try {
                    double minRating = Double.parseDouble(ratingTextField.getText());

                    filmsPanel.removeAll();
                    int countProductions = 0;
                    for (Production production : IMDB.getInstance().getProductions()) {
                        if (production.getAverageRating() >= minRating) {
                            JPanel filmInfoPanel = createFilmInfoPanel(production);
                            countProductions++;
                            filmsPanel.add(filmInfoPanel);
                        }
                    }
                    filmsPanel.setPreferredSize(new Dimension(680, 160 * countProductions));
                    filmsPanel.revalidate();
                    filmsPanel.repaint();

                    ratingSelectionFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ratingSelectionFrame, "Please enter a valid number for the rating.");
                }
            });

            contentPane.add(selectRatingLabel, BorderLayout.NORTH);
            contentPane.add(ratingTextField, BorderLayout.CENTER);
            contentPane.add(confirmButton, BorderLayout.SOUTH);

            ratingSelectionFrame.setVisible(true);
        });

        filterByGenreButton.addActionListener(e -> {
            String[] genres = {
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

            Point buttonLocation = filterByGenreButton.getLocationOnScreen();

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

            JList<String> genreList = new JList<>(genres);
            genreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            genreList.setBackground(new Color(30, 30, 30));
            genreList.setForeground(Color.WHITE);

            JScrollPane scrollPane = new JScrollPane(genreList);
            scrollPane.setPreferredSize(new Dimension(180, 200));

            contentPane.add(scrollPane);

            JButton confirmButton = new JButton("Confirm");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedGenre = genreList.getSelectedValue();
                    if (selectedGenre != null) {
                        filmsPanel.removeAll();
                        int countProductions = 0;
                        for (Production production : IMDB.getInstance().getProductions()) {
                            if (production.getGenres().toString().contains(selectedGenre)) {
                                JPanel filmInfoPanel = createFilmInfoPanel(production);
                                countProductions++;
                                filmsPanel.add(filmInfoPanel);
                            }
                        }
                        filmsPanel.setPreferredSize(new Dimension(680, 160 * countProductions));
                        filmsPanel.revalidate();
                        filmsPanel.repaint();
                    } else {


                    }
                    genreSelectionFrame.dispose();
                }
            });

            contentPane.add(confirmButton);

            genreSelectionFrame.setVisible(true);
        });

        filterPanel.setBackground(new Color(25, 25, 25));
        filterPanel.add(filterByRatingButton);
        filterPanel.add(filterByGenreButton);
        filterPanel.add(resetButton);

        getContentPane().add(filterPanel, BorderLayout.SOUTH);

        setTitle("Film Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createFilmInfoPanel(Production production) {
        JPanel filmInfoPanel = new JPanel(new GridLayout(6, 1));
        filmInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filmInfoPanel.setBackground(new Color(30, 30, 30));
        filmInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 25, 5));

        Font labelFont = new Font("DejaVu Sans Mono", Font.BOLD, 16);

        JLabel nameLabel = new JLabel(production.getTitle());
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(Color.WHITE);
        filmInfoPanel.add(nameLabel);

        if (production instanceof Movie) {
            JLabel durationLabel = new JLabel("Duration: " + ((Movie) production).getDuration());
            durationLabel.setFont(labelFont);
            durationLabel.setForeground(Color.GRAY);
            JLabel releaseYearLabel = new JLabel("Release Year: " + ((Movie) production).getReleaseYear());
            releaseYearLabel.setFont(labelFont);
            releaseYearLabel.setForeground(Color.GRAY);
            filmInfoPanel.add(durationLabel);
            filmInfoPanel.add(releaseYearLabel);
        } else {
            JLabel numOfSeasonLabel = new JLabel("Number of seasons: " + ((Series) production).getNumSeasons());
            numOfSeasonLabel.setFont(labelFont);
            numOfSeasonLabel.setForeground(Color.GRAY);
            JLabel releaseYearLabel = new JLabel("Release Year: " + ((Series) production).getReleaseYear());
            releaseYearLabel.setFont(labelFont);
            releaseYearLabel.setForeground(Color.GRAY);
            filmInfoPanel.add(numOfSeasonLabel);
            filmInfoPanel.add(releaseYearLabel);
        }

        JLabel genresLabel = new JLabel("Genres: " + production.getGenres().toString());
        genresLabel.setFont(labelFont);
        genresLabel.setForeground(Color.GRAY);

        JLabel ratingLabel = new JLabel("Average Rating: " + production.getAverageRating().toString());
        ratingLabel.setFont(labelFont);
        ratingLabel.setForeground(Color.GRAY);

        JButton detailsButton;
            detailsButton = new JButton("More Details");
        detailsButton.setFont(labelFont);
        detailsButton.setForeground(Color.LIGHT_GRAY);
        detailsButton.setBackground(new Color(60, 50, 60));
        detailsButton.setFocusable(false);

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (production instanceof Movie) {
                        new MovieDetailsGUI((Movie) production, user);
                    } else {
                        new SeriesDetailsGUI((Series) production, user);
                    }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        mainPanel.setMinimumSize(new Dimension(0, 160));
        mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        JLabel imageLabel = new JLabel(production.getImageIconDescription());
        imageLabel.setPreferredSize(new Dimension(100, 150));
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(100, 150));
        imagePanel.add(imageLabel);
        imagePanel.setBackground(new Color(30, 30, 30));

        mainPanel.add(imagePanel, BorderLayout.WEST);

        filmInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        filmInfoPanel.setMinimumSize(new Dimension(0, 160));
        filmInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        filmInfoPanel.add(genresLabel);
        filmInfoPanel.add(ratingLabel);
        filmInfoPanel.add(detailsButton);

        mainPanel.add(filmInfoPanel, BorderLayout.CENTER);

        return mainPanel;
    }

}
