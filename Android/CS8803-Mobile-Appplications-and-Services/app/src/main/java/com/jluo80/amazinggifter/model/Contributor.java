package com.jluo80.amazinggifter.model;


public class Contributor {
    private String contributor_id;
    private double amount;
    private String time;
    private String contributor_name;

    public Contributor() {

    }

    public Contributor(String contributor_id, double amount, String time, String contributor_name) {
        this.contributor_id = contributor_id;
        this.amount = amount;
        this.time = time;
        this.contributor_name = contributor_name;
    }

    public String getContributor_id() {
        return contributor_id;
    }

    public void setContributor_id(String contributor_id) {
        this.contributor_id = contributor_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContributor_name() {
        return contributor_name;
    }

    public void setContributor_name(String contributor_name) {
        this.contributor_name = contributor_name;
    }
}
