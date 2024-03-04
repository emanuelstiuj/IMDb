package org.example;

import java.util.*;

public abstract class Staff<T extends Comparable<T>> extends User implements StaffInterface {
    private List<Request> requests;
    private SortedSet<T> contributions;

    public Staff() {
        contributions = new TreeSet<>();
        requests = new ArrayList<>();
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public SortedSet<T> getContributions() {
        return contributions;
    }

    public void addContributions(Object contribution) {
        T contrib = (T) contribution;
        contributions.add(contrib);
    }

    public void addProductionSystem(Production p) {
        IMDB.getInstance().getProductions().add(p);
    }

    public void addActorSystem(Actor a) {
        IMDB.getInstance().getActors().add(a);
    }

    public void removeProductionSystem(String name) {
        Production p = IMDB.getInstance().getProduction(name);
        IMDB.getInstance().getProductions().remove(p);
    }

    public void removeActorSystem(String name) {
        Actor a = IMDB.getInstance().getActor(name);
        IMDB.getInstance().getActors().remove(a);
    }

    public void updateProduction(Production p) {
        String prodName = p.getTitle();
        Production prodOld = IMDB.getInstance().getProduction(prodName);
        if (prodOld != null) {
            IMDB.getInstance().getProductions().remove(prodOld);
        }
        IMDB.getInstance().getProductions().add(p);
    }

    public void updateActor(Actor a) {
        String actorName = a.getName();
        Actor actorOld = IMDB.getInstance().getActor(actorName);
        if (actorOld != null) {
            IMDB.getInstance().getActors().remove(actorOld);
        }
        IMDB.getInstance().getActors().add(a);
    }

}
