package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class IMDB {
    private List<User> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    private static IMDB instance;

    private IMDB(){
        ObjectMapper objectMapper = new ObjectMapper().
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File fileProduction = new File("src/main/resources/input/production.json");
        File fileActors = new File("src/main/resources/input/actors.json");
        File fileRequests = new File("src/main/resources/input/requests.json");
        File fileAccounts = new File("src/main/resources/input/accounts.json");

        try {
            actors = new ArrayList<>();
            actors = objectMapper.readValue(fileActors, new TypeReference<List<Actor>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            productions = new ArrayList<>();

            JsonNode root = objectMapper.readTree(fileProduction);
            for (JsonNode node : root) {
                if (node.has("type")) {
                    String type = node.get("type").asText();
                    Movie m = null;
                    Series s = null;

                    ImageIcon imageIconDes = null;
                    ImageIcon imageIconHome = null;
                    String imagePath = "src/main/resources/images/" + node.get("title").asText();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (type.equals("Movie")) {
                        m = objectMapper.treeToValue(node, Movie.class);
                        m.setImageIconDescription(imageIconDes);
                        m.setImageIconHome(imageIconHome);
                        productions.add(m);
                    } else {
                        s = objectMapper.treeToValue(node, Series.class);
                        s.setImageIconDescription(imageIconDes);
                        s.setImageIconHome(imageIconHome);
                        productions.add(s);
                    }

                    JsonNode nodeActors = node.get("actors");
                    for (JsonNode actorNode : nodeActors) {
                        String actorName = actorNode.asText();
                        if (getActor(actorName) == null) {
                            Request request = new Request();
                            request.setUsername("testRegular");
                            request.setActorName(actorName);
                            request.setType(RequestTypes.OTHERS);
                            request.setDescription("Actorul " + actorName + " nu se gaseste in sistem");
                            LocalDateTime date = LocalDateTime.now();
                            request.setCreatedDate(date);
                            request.setTo("ADMIN");
                            Admin.RequestsHolder.getRequests().add(request);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            users = new ArrayList<>();
            UserFactory userFactory = new UserFactory();

            JsonNode rootNode = objectMapper.readTree(fileAccounts);
            for (JsonNode node : rootNode) {
                if (node.has("userType")) {
                    String userType = node.get("userType").asText();
                    AccountType accountType = AccountType.valueOf(userType);
                    User user = userFactory.createUser(accountType);
                    Regular regular;
                    Contributor contributor;
                    Admin admin;

                    if (user instanceof Regular) {
                        regular = (Regular) user;
                    } else if (user instanceof Contributor) {
                        contributor = (Contributor) user;
                    } else {
                        admin = (Admin) user;
                    }

                    ArrayList<Actor> favActorsList = new ArrayList<>();
                    ArrayList<Actor> conActorsList = new ArrayList<>();
                    ArrayList<Production> favProdList = new ArrayList<>();
                    ArrayList<Production> conProdList = new ArrayList<>();

                    JsonNode infoNode = node.get("information");

                    JsonNode genderNode = infoNode.get("gender");
                    char gen = 'N';

                    if (genderNode != null) {
                        String gender = genderNode.asText();
                        switch (gender) {
                            case "Female":
                                gen = 'F';
                                break;
                            case "Male":
                                gen = 'M';
                                break;
                            default:
                                gen = 'N';
                        }
                    }

                    JsonNode credentialsNode = infoNode.get("credentials");
                    String email = credentialsNode.get("email").asText();
                    String password = credentialsNode.get("password").asText();
                    Credentials credentials = new Credentials();
                    credentials.setPassword(password);
                    credentials.setEmail(email);

                    String name = infoNode.get("name").asText();
                    String country = infoNode.get("country").asText();
                    int age = infoNode.get("age").asInt();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime date = LocalDate.parse(infoNode.get("birthDate").asText(), formatter).atStartOfDay();

                    User.Information info = new User.Information.InformationBuilder(credentials)
                            .setName(name)
                            .setCountry(country)
                            .setAge(age)
                            .setGender(gen)
                            .setBirthDate(date)
                            .build();

                    JsonNode favActors = node.get("favoriteActors");
                    if (favActors != null) {
                        for (JsonNode actorNode : favActors)
                            favActorsList.add(getActor(actorNode.asText()));
                    }
                    JsonNode favProductions = node.get("favoriteProductions");
                    if (favProductions != null) {
                        for (JsonNode prodNode : favProductions)
                            favProdList.add(getProduction(prodNode.asText()));
                    }
                    JsonNode actorsContribution = node.get("actorsContribution");
                    if (actorsContribution != null) {
                        for (JsonNode actorNode : actorsContribution)
                            conActorsList.add(getActor(actorNode.asText()));
                    }
                    JsonNode productionsContribution = node.get("productionsContribution");
                    if (productionsContribution != null) {
                        for (JsonNode prodNode : productionsContribution)
                            conProdList.add(getProduction(prodNode.asText()));
                    }

                    switch (userType) {
                        case "Regular":
                            regular = objectMapper.treeToValue(node, Regular.class);
                            for (Actor actor : favActorsList) {
                                regular.addFavourite(actor);
                            }
                            for (Production production : favProdList) {
                                regular.addFavourite(production);
                            }

                            regular.setInformation(info);
                            users.add(regular);
                            break;

                        case "Contributor":
                            contributor = objectMapper.treeToValue(node, Contributor.class);
                            for (Actor actor : favActorsList) {
                                contributor.addFavourite(actor);
                            }
                            for (Production production : favProdList) {
                                contributor.addFavourite(production);
                            }
                            for (Actor actor : conActorsList) {
                                contributor.addContributions(actor);
                            }
                            for (Production production : conProdList) {
                                contributor.addContributions(production);
                            }

                            contributor.setInformation(info);
                            users.add(contributor);
                            break;

                        case "Admin":
                            admin = objectMapper.treeToValue(node, Admin.class);
                            for (Actor actor : favActorsList) {
                                admin.addFavourite(actor);
                            }
                            for (Production production : favProdList) {
                                admin.addFavourite(production);
                            }
                            for (Actor actor : conActorsList) {
                                admin.addContributions(actor);
                            }
                            for (Production production : conProdList) {
                                admin.addContributions(production);
                            }

                            admin.setInformation(info);
                            admin.setExperience(Integer.MAX_VALUE);
                            users.add(admin);
                            break;

                    }
                }
            }

            User user = getUserByUsername("testRegular");
            for (Request request : Admin.RequestsHolder.getRequests()) {
                user.getUnsolvedRequests().add(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            requests = new ArrayList<>();

            JsonNode rootNode = objectMapper.readTree(fileRequests);
            for (JsonNode node : rootNode) {
                if (node.has("createdDate")) {
                    Request request = objectMapper.treeToValue(node, Request.class);
                    request.setCreatedDate(LocalDateTime
                            .parse(node.get("createdDate").asText()));
                    requests.add(request);
                }
            }

            ArrayList<Request> requestArrayList = (ArrayList<Request>) requests;
            for (Request request : requestArrayList) {
                if (request.getTo().equals("ADMIN")) {
                    request.setId(1);
                    Admin.RequestsHolder.getRequests().add(request);
                    User user = getUserByUsername(request.getUsername());
                    user.getUnsolvedRequests().add(request);
                } else {
                    request.setId(1);
                    User user = getUserByUsername(request.getTo());
                    ((Staff)user).getRequests().add(request);
                    user = getUserByUsername(request.getUsername());
                    user.getUnsolvedRequests().add(request);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (User user : users) {
            if (!user.getUserType().equals(AccountType.Regular)) {
                if (!((Staff)user).getContributions().isEmpty()) {
                    TreeSet<Object> treeSet = (TreeSet<Object>) ((Staff)user).getContributions();
                    for (Object object : treeSet) {
                        if (object instanceof Production) {
                            Production production = getProduction(((Production)object).getTitle());
                            production.getContributors().add(user);
                        }
                    }
                }
            }
        }

        for (Production production : productions) {
            for (Rating rating : production.getRatings()) {
                User user = getUserByUsername(rating.getUsername());
                production.getRatingUsers().add(user);
            }
        }

        for (User user : users) {
            for (Object object : user.getFavorites()) {
                if (object instanceof Movie) {
                    ((Movie)object).getFavoriteUsers().add(user);
                } else if (object instanceof Series) {
                    ((Series)object).getFavoriteUsers().add(user);
                }
            }
        }

        for (Request request : Admin.RequestsHolder.getRequests()) {
            String string = "Ai primit o noua cerere de la utilizatorul \"" + request.getUsername() + "\"";
            for (User user : users) {
                if (user.getUserType().equals(AccountType.Admin)) {
                    user.update(string);
                }
            }
        }

        for (Production production : productions) {
            for (Rating rating : production.getRatings()) {
                if (!production.getRatingUsers().contains(getUserByUsername(rating.getUsername())))
                    production.getRatingUsers().add(getUserByUsername(rating.getUsername()));
                production.notifyAll(rating);
            }
        }
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<User> getUsers() {
        return users;
    }

    public Actor getActor(String actorName) {
        for (Actor actor : actors) {
            if (actor.getName().equals(actorName)) {
                return actor;
            }
        }
        return null;
    }

    public Production getProduction(String productionName) {
        for (Production prod : productions) {
            if (prod.getTitle().equals(productionName)) {
                return prod;
            }
        }
        return null;
    }

    public boolean confirmLogin(char[] enteredPassword, String email) {
        for (User user : users) {
            if (user.getInformation().getCredentials().getEmail().equals(email)) {
                char[] correctPassword = user.getInformation().getCredentials().getPassword().toCharArray();
                if (correctPassword.length != enteredPassword.length) {
                    return false;
                }
                for (int i = 0; i < correctPassword.length; i++) {
                    if (correctPassword[i] != enteredPassword[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public User getUser(String email) {
        for (User user : users) {
            if (user.getInformation().getCredentials().getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void run() {
        new LoginGUI();
    }
    public static void main(String[] args) {
        IMDB imdb = IMDB.getInstance();
        imdb.run();
    }
}
