package org.example;

public class UserFactory {
    public UserFactory() {}

    public User createUser(AccountType accountType) {
        switch (accountType) {
            case Admin:
                return new Admin();
            case Contributor:
                return new Contributor();
            case Regular:
                return new Regular();
            default:
                return null;
        }
    }
}
