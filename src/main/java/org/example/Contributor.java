package org.example;

import java.util.ArrayList;
import java.util.Iterator;

public class Contributor extends Staff implements RequestsManager {

    public Contributor() {
        super();
    }

    public void createRequest(Request r) {
        if (r.getTo().equals("ADMIN")) {
            Admin.RequestsHolder.getRequests().add(r);
        } else {
            User user = IMDB.getInstance().getUserByUsername(r.getTo());
            ((Staff) user).getRequests().add(r);
        }
    }

    public void removeRequest(Request r) {
        ArrayList<Request> requests;

        if (r.getTo().equals("ADMIN")) {
            requests = new ArrayList<>(Admin.RequestsHolder.getRequests());
            Iterator<Request> iterator = requests.iterator();
            while (iterator.hasNext()) {
                Request request1 = iterator.next();
                if (request1.getId() == r.getId() && request1.getUsername().equals(r.getUsername())) {
                    iterator.remove();
                }
            }
            Admin.RequestsHolder.setRequests(requests);
        } else {
            Staff staff = (Staff) IMDB.getInstance().getUserByUsername(r.getTo());
            requests = new ArrayList<>(staff.getRequests());
            Iterator<Request> iterator = requests.iterator();
            while (iterator.hasNext()) {
                Request request1 = iterator.next();
                if (request1.getId() == r.getId() && request1.getUsername().equals(r.getUsername())) {
                    iterator.remove();
                }
            }
            staff.setRequests(requests);
        }
    }
}
