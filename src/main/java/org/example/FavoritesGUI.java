package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoritesGUI extends JFrame {
    private User user;
    private Boolean favorites, remove, update;

    public FavoritesGUI(User user, Boolean favorites, Boolean remove, Boolean update) {
        this.user = user;
        this.favorites = favorites;
        this.update = update;
        this.remove = remove;

        JScrollPane actorsScrollPane = createActorsScrollPane();
        JScrollPane productionsScrollPane = createProductionsScrollPane();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, actorsScrollPane, productionsScrollPane);
        splitPane.setResizeWeight(0.5);

        splitPane.setDividerSize(0);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitPane, BorderLayout.CENTER);

        JPanel refreshPanel = new JPanel(new FlowLayout());
        refreshPanel.setBackground(new Color(30, 30, 30));
        JButton refreshButton = new JButton("Refresh Window");
        refreshButton.setFocusable(false);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshInformation();
            }
        });

        refreshPanel.add(refreshButton);
        getContentPane().add(refreshPanel, BorderLayout.SOUTH);

        if (favorites)
            setTitle("Favorite actors and productions");
        else
            setTitle("Contributions");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1500, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JScrollPane createActorsScrollPane() {
        JPanel actorsPanel = new JPanel();
        actorsPanel.setLayout(new BoxLayout(actorsPanel, BoxLayout.Y_AXIS));
        actorsPanel.setBackground(new Color(40, 40, 40));

        int countActors = 0;
        if (favorites) {
            for (Actor actor : IMDB.getInstance().getActors()) {
                if (user.getFavorites().contains(actor)) {
                    countActors++;
                    JPanel actorInfoPanel = createActorInfoPanel(actor);
                    actorsPanel.add(actorInfoPanel);
                }
            }
        } else {
            Set<Object> contributions;
            if (user instanceof Contributor) {
                contributions = ((Contributor) user).getContributions();
            } else {
                contributions = ((Admin) user).getContributions();
            }

            ArrayList<Object> contributionsList = new ArrayList<>(contributions);

            for (Object object : contributionsList) {
                if (object instanceof Actor) {
                    JPanel actorInfoPanel = createActorInfoPanel((Actor) object);
                    actorsPanel.add(actorInfoPanel);
                    countActors++;
                }
            }

            if (user instanceof Admin) {
                for (Object object : Admin.adminContributions) {
                    if (object instanceof Actor) {
                        JPanel actorInfoPanel = createActorInfoPanel((Actor) object);
                        actorsPanel.add(actorInfoPanel);
                        countActors++;
                    }
                }

            }

        }

        JScrollPane scrollPane;
        if (countActors == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);
            JLabel label;

            if (favorites)
                label = new JLabel("YOU HAVE NOT ANY FAVORITE ACTOR");
            else

                label = new JLabel("YOU HAVE NO CONTRIBUTIONS TO ANY ACTOR");
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setPreferredSize(new Dimension(730, 450));
            panel.add(label, BorderLayout.CENTER);
            scrollPane = new JScrollPane(panel);
            scrollPane.setPreferredSize(new Dimension(750, 450));

        } else {

            scrollPane = new JScrollPane(actorsPanel);
        }

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(750, scrollPane.getPreferredSize().height));

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });
        return scrollPane;
    }

    private JScrollPane createProductionsScrollPane() {
        JPanel productionsPanel = new JPanel();
        productionsPanel.setLayout(new BoxLayout(productionsPanel, BoxLayout.Y_AXIS));
        productionsPanel.setBackground(new Color(40, 40, 40));

        int countProductions = 0;

        if (favorites) {
            for (Production production : IMDB.getInstance().getProductions()) {
                if (user.getFavorites().contains(production)) {
                    JPanel productionInfoPanel = createProductionInfoPanel(production);
                    countProductions++;
                    productionsPanel.add(productionInfoPanel);
                }
            }
        } else {
            Set<Object> contributions;
            if (user instanceof Contributor) {
                contributions = ((Contributor) user).getContributions();
            } else {
                contributions = ((Admin) user).getContributions();
            }

            ArrayList<Object> contributionsList = new ArrayList<>(contributions);
            for (Object object : contributions) {
                if (object instanceof Production) {
                    JPanel productionInfoPanel = createProductionInfoPanel((Production) object);
                    countProductions++;
                    productionsPanel.add(productionInfoPanel);
                }
            }

            if (user instanceof Admin) {
                for (Object object : Admin.adminContributions) {
                    if (object instanceof Production) {
                        JPanel productionInfoPanel = createProductionInfoPanel((Production) object);
                        countProductions++;
                        productionsPanel.add(productionInfoPanel);
                    }
                }
            }
        }
        JScrollPane scrollPane;
        if (countProductions == 0) {
            Font font = new Font("DevaVu Sans Mono", Font.BOLD, 20);
            JLabel label;

            if (favorites)
                label = new JLabel("YOU HAVE NOT ANY FAVORITE PRODUCTION");
            else
                label = new JLabel("YOU HAVE NO CONTRIBUTIONS TO ANY PRODUCTION");

            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setPreferredSize(new Dimension(730, 450));
            panel.add(label, BorderLayout.CENTER);
            scrollPane = new JScrollPane(panel);
            scrollPane.setPreferredSize(new Dimension(750, 450));

        } else {

            scrollPane = new JScrollPane(productionsPanel);
        }

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(750, scrollPane.getPreferredSize().height));

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });
        return scrollPane;
    }

    private JPanel createActorInfoPanel(Actor actor) {
        JPanel actorInfoPanel = new JPanel(new BorderLayout());
        actorInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        actorInfoPanel.setBackground(new Color(35, 35, 35));
        actorInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        JLabel nameLabel = new JLabel(actor.getName());
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(Color.CYAN);

        JTextArea bioTextArea = new JTextArea();
        if (actor.getBiography() != null) {
            bioTextArea.append("Biography: " + actor.getBiography());
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

        JButton moreDetailsButton;
        if (favorites) {
            moreDetailsButton = new JButton("More Details");
        } else if (remove) {
            moreDetailsButton = new JButton("Remove from system");
        } else {
            moreDetailsButton = new JButton("Update information");
        }
        moreDetailsButton.setBackground(new Color(10, 10, 10));
        moreDetailsButton.setForeground(new Color(220, 220, 220));
        moreDetailsButton.setFocusable(false);
        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (favorites) {
                    new ActorDetailsGUI(actor, user);
                } else if (update) {
                    new ActorUpdateInfoGUI(actor, user, false);
                } else {
                    if (user instanceof Contributor) {
                        ((Contributor) user).removeActorSystem(actor.getName());
                        ((Contributor) user).getContributions().remove(actor);
                    } else {
                        if (Admin.adminContributions.contains(actor)) {
                            ((Admin)user).removeActorSystem(actor.getName());
                            Admin.adminContributions.remove(actor);
                        } else {
                            ((Admin)user).removeActorSystem(actor.getName());
                            ((Admin)user).getContributions().remove(actor);
                        }
                    }
                    refreshInformation();
                }
            }
        });

        buttonPanel.add(moreDetailsButton);

        actorInfoPanel.add(nameLabel, BorderLayout.NORTH);
        if (actor.getBiography() != null) {
            actorInfoPanel.add(bioTextArea, BorderLayout.CENTER);
        }

        actorInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));
        actorInfoPanel.setMinimumSize(new Dimension(0, 190));


        actorInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        return actorInfoPanel;
    }

    private JPanel createProductionInfoPanel(Production production) {
        JPanel filmInfoPanel = new JPanel(new GridLayout(6, 1));
        filmInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filmInfoPanel.setBackground(new Color(30, 30, 30));
        filmInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));

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
        if (favorites) {
            detailsButton = new JButton("More Details");
        } else if (remove) {
            detailsButton = new JButton("Remove from system");
        } else {
            detailsButton = new JButton("Update information");
        }
        detailsButton.setFont(labelFont);
        detailsButton.setForeground(Color.LIGHT_GRAY);
        detailsButton.setBackground(new Color(60, 50, 60));
        detailsButton.setFocusable(false);

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (favorites) {
                    if (production instanceof Movie) {
                        new MovieDetailsGUI((Movie) production, user);
                    } else {
                        new SeriesDetailsGUI((Series) production, user);
                    }
                } else if (update) {
                    new ProductionUpdateInfoGUI(production, user, false);
                } else {
                    if (user instanceof Contributor) {
                        ((Contributor) user).removeProductionSystem(production.getTitle());
                        ((Contributor) user).getContributions().remove(production);
                    } else {
                        if (Admin.adminContributions.contains(production)) {
                            ((Admin)user).removeProductionSystem(production.getTitle());
                            Admin.adminContributions.remove(production);
                        } else {
                            ((Admin)user).removeProductionSystem(production.getTitle());
                            ((Admin)user).getContributions().remove(production);
                        }
                    }
                    refreshInformation();
                }
            }
        });

        filmInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        filmInfoPanel.setMinimumSize(new Dimension(0, 160));

        filmInfoPanel.add(genresLabel);
        filmInfoPanel.add(ratingLabel);
        filmInfoPanel.add(detailsButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        mainPanel.setMinimumSize(new Dimension(0, 160));
        mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        JLabel imageLabel = new JLabel(production.getImageIconDescription());
        imageLabel.setPreferredSize(new Dimension(100, 130));
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(100, 130));
        imagePanel.add(imageLabel);
        imagePanel.setBackground(new Color(30, 30, 30));

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(filmInfoPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private void refreshInformation() {
        getContentPane().removeAll();

        JScrollPane actorsScrollPane = createActorsScrollPane();
        JScrollPane productionsScrollPane = createProductionsScrollPane();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, actorsScrollPane, productionsScrollPane);
        splitPane.setResizeWeight(0.5);

        splitPane.setDividerSize(0);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitPane, BorderLayout.CENTER);

        JPanel refreshPanel = new JPanel(new FlowLayout());
        refreshPanel.setBackground(new Color(30, 30, 30));
        JButton refreshButton = new JButton("Refresh Window");
        refreshButton.setFocusable(false);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshInformation();
            }
        });

        refreshPanel.add(refreshButton);
        getContentPane().add(refreshPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

}
