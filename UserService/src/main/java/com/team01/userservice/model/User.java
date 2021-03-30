package com.team01.userservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class User {

    @Id
    @Column(name = "userId")
    private String userId;
    @Column(name = "userPassword")
    private String userPassword;
    @Column(name = "userEmail")
    private String userEmail;
    @Column(name = "profilePicture")
    private String profilePictureUrl;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setprofilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

}
