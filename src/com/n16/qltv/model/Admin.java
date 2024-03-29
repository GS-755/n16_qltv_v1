package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;

public class Admin implements IModels {
    private String userName;
    private String password;

    public Admin() { }
    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() { return this.userName; }
    public String getPassword() { return this.password; }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getType() {
        return Admin.class;
    }
}
