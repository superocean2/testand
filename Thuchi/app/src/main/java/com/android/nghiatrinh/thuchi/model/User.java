package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;

/**
 * Created by NghiaTrinh on 3/17/2015.
 */
public class User extends SugarRecord<User>{
    String username;
    String password;
    String displayname;
    String hashgroup;

    public User() {
    }

    public User(String username, String password, String displayname, String hashgroup) {
        this.username = username;
        this.password = password;
        this.displayname = displayname;
        this.hashgroup = hashgroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getHashgroup() {
        return hashgroup;
    }

    public void setHashgroup(String hashgroup) {
        this.hashgroup = hashgroup;
    }
}

