package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public abstract class User<T extends Comparable<T>> implements Cloneable{
    @JsonIgnore
    private Information information;
    private AccountType userType;
    private String username;
    private int experience;
    private List<String> notifications;
    private SortedSet<T> favorites;
    private int requestsNumber;
    private List<Request> unsolvedRequests;

    public User() {
        favorites = new TreeSet<>();
        notifications = new ArrayList<>();
        unsolvedRequests = new ArrayList<>();
        requestsNumber = 0;
    }

    public void update(String notification) {
        notifications.add(notification);
    }

    public List<Request> getUnsolvedRequests() {
        return unsolvedRequests;
    }

    public void setUnsolvedRequests(List<Request> unsolvedRequests) {
        this.unsolvedRequests = unsolvedRequests;
    }

    public String getUsername() {
        return username;
    }

    public int getRequestsNumber() {
        return requestsNumber;
    }

    public void setRequestsNumber(int requestsNumber) {
        this.requestsNumber = requestsNumber;
    }

    public void incrementRequestNumber() {
        requestsNumber++;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountType getUserType() {
        return userType;
    }

    public Information getInformation() {
        return information;
    }

    public int getExperience() {
        return experience;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public SortedSet<T> getFavorites() {
        return favorites;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setFavorites(SortedSet<T> favorites) {
        this.favorites = favorites;
    }

    public void setUserType(AccountType userType) {
        this.userType = userType;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void addFavourite(Object favorite) {
        T fav = (T) favorite;
        favorites.add(fav);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static class Information {
        private Credentials credentials;
        private String name;
        private String country;
        private int age;
        private char gender;
        private LocalDateTime birthDate;

        private Information(InformationBuilder informationBuilder){
            credentials = informationBuilder.credentials;
            name = informationBuilder.name;
            country = informationBuilder.country;
            age = informationBuilder.age;
            gender = informationBuilder.gender;
            birthDate = informationBuilder.birthDate;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setBirthDate(LocalDateTime birthDate) {
            this.birthDate = birthDate;
        }

        public String getCountry() {
            return country;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public char getGender() {
            return gender;
        }

        public LocalDateTime getBirthDate() {
            return birthDate;
        }

        public static class InformationBuilder {
            private Credentials credentials;
            private String name;
            private String country;
            private int age;
            private char gender;
            private LocalDateTime birthDate;

            public InformationBuilder(Credentials credentials) {
                this.credentials = credentials;
            }

            public InformationBuilder setName(String name) {
                this.name = name;
                return this;
            }

            public InformationBuilder setCountry(String country) {
                this.country = country;
                return this;
            }

            public InformationBuilder setAge(Integer age) {
                this.age = age;
                return this;
            }

            public InformationBuilder setGender(char gender) {
                this.gender = gender;
                return this;
            }

            public InformationBuilder setBirthDate(LocalDateTime birthDate) {
                this.birthDate = birthDate;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
}
