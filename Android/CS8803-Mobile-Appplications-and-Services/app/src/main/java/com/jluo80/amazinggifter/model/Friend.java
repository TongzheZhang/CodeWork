package com.jluo80.amazinggifter.model;

/**
 * Created by Jiahao on 7/19/2016.
 */
public class Friend {
    private String facebookId;
    private String name;
    private String friendPictureUrl;

    public Friend() {}

    public Friend(String facebookId, String name) {
        this.facebookId = facebookId;
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getFriendPictureUrl() {
//        return friendPictureUrl;
//    }
//
//    public void setFriendPictureUrl(String friendPictureUrl) {
//        this.friendPictureUrl = friendPictureUrl;
//    }
}