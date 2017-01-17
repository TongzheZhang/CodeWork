package com.jluo80.amazinggifter.model;

/**
 * Created by Jiahao on 7/2/2016.
 */
public class Gift {
    private String unique_key;
    private String category;
    private String due_date;
    private String initiator_id;
    private String item_id;
    private String item_url;
    private String name;
    private String picture_url;
    private String post_time;
    private double price;
    private double progress;
    private String reason;
    private String receiver_id;

    public Gift() {}


    public Gift(String category, String due_date, String initiator_id, String item_id, String item_url, String name, String picture_url, String post_time, double price, double progress, String reason, String receiver_id) {
        this.category = category;
        this.due_date = due_date;
        this.initiator_id = initiator_id;
        this.item_id = item_id;
        this.item_url = item_url;
        this.name = name;
        this.picture_url = picture_url;
        this.post_time = post_time;
        this.price = price;
        this.progress = progress;
        this.reason = reason;
        this.receiver_id = receiver_id;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getInitiator_id() {
        return initiator_id;
    }

    public void setInitiator_id(String initiator_id) {
        this.initiator_id = initiator_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_url(){
        return item_url;
    }

    public void setItem_url(String item_url){
        this.item_url = item_url;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPicture_url(){
        return picture_url;
    }

    public void setPicture_url(String picture_url){
        this.picture_url = picture_url;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

}