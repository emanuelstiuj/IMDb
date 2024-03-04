package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin<T extends Comparable<T>> extends Staff{

    public static List<Object> adminContributions = new ArrayList<>();

    public Admin() {
        super();
    }

    public static class RequestsHolder {

        private static List<Request> requests = new ArrayList<>();

        public static List<Request> getRequests() {
            return requests;
        }

        public static void setRequests(List<Request> reqs) {
            requests = reqs;
        }
    }
}
