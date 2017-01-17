package com.jluo80.amazinggifter.model;

/**
 * Created by Jiahao on 6/30/2016.
 */
public class User {
    private String facebookId;
    private String name;
    private String profilePictureUrl;
    private String coverPictureUrl;
    private String mobile;
    private String addressFirst;
    private String addressSecond;
    private String birthday;
    private String city;
    private String country;
    private String email;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String birthday, String pictureUrl, String coverPictureUrl) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.profilePictureUrl = pictureUrl;
        this.coverPictureUrl = coverPictureUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getCoverPictureUrl() {
        return coverPictureUrl;
    }

    public void setCoverPictureUrl(String coverPictureUrl) {
        this.coverPictureUrl = coverPictureUrl;
    }



    /** Save them for future usage. */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressFirst() {
        return addressFirst;
    }

    public void setAddressFirst(String addressFirst) {
        this.addressFirst = addressFirst;
    }

    public String getAddressSecond() {
        return addressSecond;
    }

    public void setAddressSeconde(String addressSecond) {
        this.addressSecond = addressSecond;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
