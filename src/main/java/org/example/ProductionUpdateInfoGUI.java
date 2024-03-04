package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProductionUpdateInfoGUI extends JFrame implements ExperienceStrategy {
    private Boolean newInfo;
    private Production production;
    private JLabel titleLabel;
    private JButton titleSeriesButton, titleMovieButton, directorsButton, actorsButton, durationButton,
            genresButton, releaseYearButton, plotButton, numSeasons, infoSeasonsButton;
    private User user;

    @Override
    public int calculateExperience() {
        return 3;
    }

    public ProductionUpdateInfoGUI(Production production, User user, Boolean newInfo) {
        this.newInfo = newInfo;
        this.production = production;
        this.user = user;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(32, 32, 32));
        JPanel menuPanel = new JPanel();
        if (production instanceof Movie)
            menuPanel.setLayout(new GridLayout(7, 1));
        else
            menuPanel.setLayout(new GridLayout(7, 1));

        menuPanel.setBackground(new Color(32, 32, 32));
        Font font = new Font("Arial", Font.BOLD, 13);
        if (newInfo && production instanceof Movie)
            titleLabel = new JLabel("ADD NEW MOVIE");
        else if (newInfo && production instanceof Series)
            titleLabel = new JLabel("ADD NEW SERIES");
        else
            titleLabel = new JLabel("Select a field and make changes");

        titleLabel.setPreferredSize(new Dimension(300, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        infoSeasonsButton = new JButton("Seasons");
        infoSeasonsButton.setBackground(new Color(25, 25, 25));
        infoSeasonsButton.setForeground(Color.WHITE);
        infoSeasonsButton.setFont(font);

        titleMovieButton = new JButton("Movie Title");
        titleMovieButton.setBackground(new Color(25, 25, 25));
        titleMovieButton.setForeground(Color.WHITE);
        titleMovieButton.setFont(font);

        titleSeriesButton = new JButton("Series Title");
        titleSeriesButton.setBackground(new Color(25, 25, 25));
        titleSeriesButton.setForeground(Color.WHITE);
        titleSeriesButton.setFont(font);

        directorsButton = new JButton("Directors");
        directorsButton.setBackground(new Color(25, 25, 25));
        directorsButton.setForeground(Color.WHITE);
        directorsButton.setFont(font);

        actorsButton = new JButton("Actors");
        actorsButton.setBackground(new Color(25, 25, 25));
        actorsButton.setForeground(Color.WHITE);
        actorsButton.setFont(font);

        durationButton = new JButton("Duration");
        durationButton.setBackground(new Color(25, 25, 25));
        durationButton.setForeground(Color.WHITE);
        durationButton.setFont(font);

        genresButton = new JButton("Genres");
        genresButton.setBackground(new Color(25, 25, 25));
        genresButton.setForeground(Color.WHITE);
        genresButton.setFont(font);

        releaseYearButton = new JButton("Release Year");
        releaseYearButton.setBackground(new Color(25, 25, 25));
        releaseYearButton.setForeground(Color.WHITE);
        releaseYearButton.setFont(font);

        plotButton = new JButton("Plot");
        plotButton.setBackground(new Color(25, 25, 25));
        plotButton.setForeground(Color.WHITE);
        plotButton.setFont(font);

        if (production instanceof Movie) {
            titleMovieButton.addActionListener(new ButtonClickListener("Movie Title"));
            durationButton.addActionListener(new ButtonClickListener("Duration"));
        } else {
            titleSeriesButton.addActionListener(new ButtonClickListener("Series Title"));
            infoSeasonsButton.addActionListener(new ButtonClickListener("Seasons"));
        }

        directorsButton.addActionListener(new ButtonClickListener("Directors"));
        actorsButton.addActionListener(new ButtonClickListener("Actors"));

        genresButton.addActionListener(new ButtonClickListener("Genres"));
        releaseYearButton.addActionListener(new ButtonClickListener("Release Year"));
        plotButton.addActionListener(new ButtonClickListener("Plot"));

        if (production instanceof Movie) {
            menuPanel.add(titleMovieButton);
            menuPanel.add(durationButton);
        } else {
            menuPanel.add(titleSeriesButton);
            menuPanel.add(infoSeasonsButton);
        }

        menuPanel.add(directorsButton);
        menuPanel.add(actorsButton);
        menuPanel.add(genresButton);
        menuPanel.add(releaseYearButton);
        menuPanel.add(plotButton);

        mainPanel.add(menuPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("SAVE");
        saveButton.setBackground(Color.YELLOW);
        saveButton.setForeground(Color.BLACK);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (production.getTitle() == null || production.getTitle().isEmpty() ||
                    production.getGenres() == null || production.getGenres().isEmpty() ||
                    production.getActors() == null || production.getActors().isEmpty() ||
                    production.getDirectors() == null || production.getDirectors().isEmpty() ||
                    production.getPlot() == null || production.getPlot().isEmpty()) {

                    showWarning("Complete all the fields before saving the production");
                    return;
                }

                if (IMDB.getInstance().getProduction(production.getTitle()) != null) {
                    showWarning(production.getTitle() + " already exists in the system!");
                    return;
                }

                if (production instanceof Series) {
                    if (((Series)production).getReleaseYear() == 0 || (((Series)production).getSeasons().isEmpty())) {
                        showWarning("Complete all the fields before saving the production");
                        return;
                    }
                } else {
                    if (((Movie)production).getReleaseYear() == 0 || (((Movie)production).getDuration() == null) ||
                            ((Movie)production).getDuration().isEmpty()) {
                        showWarning("Complete all the fields before saving the production");
                        return;
                    }
                }

                user.setExperience(user.getExperience() + calculateExperience());
                production.setAverageRating(1.0);

                //
                ImageIcon imageIconDes = null;
                ImageIcon imageIconHome = null;
                String imagePath = "src/main/resources/images/" + production.getTitle();
                File imageFile = new File(imagePath);
                if (!imageFile.exists()) {
                    imageFile = new File("src/main/resources/images/image_not_found");
                }

                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image scaledImage1 = bufferedImage.getScaledInstance(110, 150, Image.SCALE_SMOOTH);
                    imageIconDes = new ImageIcon(scaledImage1);
                    Image scaledImage2 = bufferedImage.getScaledInstance(280, 380, Image.SCALE_SMOOTH);
                    imageIconHome = new ImageIcon(scaledImage2);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                production.setImageIconDescription(imageIconDes);
                production.setImageIconHome(imageIconHome);

                IMDB.getInstance().getProductions().add(production);
                ((Staff)user).addContributions(production);

                dispose();
            }

            private void showWarning(String message) {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        if (newInfo)
            mainPanel.add(saveButton, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(300, 250);
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
                case "Movie Title":
                    new UpdateFieldGUI(FieldName.Title, newInfo, production, null, null, -1);
                    break;
                case "Series Title":
                    new UpdateFieldGUI(FieldName.Title, newInfo, production, null, null, -1);
                    break;
                case "Directors":
                    new ChangeDirectorsGUI(production, user);
                    break;
                case "Actors":
                    new ChangeActorsGUI(production, user);
                    break;
                case "Plot":
                    new ChangePlotGUI(production);
                    break;
                case "Duration":
                    new UpdateFieldGUI(FieldName.Duration, newInfo, production, null, null, -1);
                    break;
                case "Genres":
                    new ChangeGenresGUI(production, user);
                    break;
                case "Release Year":
                    new UpdateFieldGUI(FieldName.ReleaseYear, newInfo, production, null, null, -1);
                    break;
                case "Seasons":
                    new ChangeSeasonsGUI((Series) production);
            }
        }
    }
}
