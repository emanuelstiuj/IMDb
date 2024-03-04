package org.example;

import java.util.ArrayList;
import java.util.Iterator;

public class Regular extends User implements RequestsManager{

    public Regular() {
        super();
    }

    @Override
    public void removeRequest(Request r) {
        ArrayList<Request> requests;

        if (r.getTo().equals("ADMIN")) {
            requests = new ArrayList<>(Admin.RequestsHolder.getRequests());
            requests.removeIf(request1 -> request1.getId() == r.getId() && request1.getUsername().equals(r.getUsername()));
            Admin.RequestsHolder.setRequests(requests);
        } else {
            Staff staff = (Staff) IMDB.getInstance().getUserByUsername(r.getTo());
            requests = new ArrayList<>(staff.getRequests());
            requests.removeIf(request1 -> request1.getId() == r.getId() && request1.getUsername().equals(r.getUsername()));
            staff.setRequests(requests);
        }
    }

    @Override
    public void createRequest(Request r) {
        if (r.getTo().equals("ADMIN")) {
            Admin.RequestsHolder.getRequests().add(r);
        } else {
            User user = IMDB.getInstance().getUserByUsername(r.getTo());
            ((Staff) user).getRequests().add(r);
        }
    }
}
