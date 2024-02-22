package com.n16.qltv.model;

import java.sql.Date;

public class Customer {
    private int idcus;
    private String nameCus, phoneCus;
    private String addressCus;
    private Date dobCus;
    private String usrName, password;
    private char gender;

    public Customer() {
        this.idcus = 1;
        this.gender = 'm';
        this.dobCus = Date.valueOf("2000-1-1");
    }

    public int getIdCus() { return this.idcus; }
    public String getNameCus() { return this.nameCus; }
    public String getPhoneCus() { return this.phoneCus; }
    public String getAddressCus() { return this.addressCus; }
    public Date getDobCus() { return this.dobCus; }
    public String getStrGender() {
        if(this.gender == 'f')
            return "Nữ";

        return "Nam";
    }
    public String getUsrName() { return this.usrName; }
    public String getPassword() { return this.password; }
    public char getGender() { return this.gender; }
    public void setIdcus(int idcus) { this.idcus = idcus; }
    public void setGender(char gender) { this.gender = gender; }
    public void setNameCus(String nameCus) { this.nameCus = nameCus; }
    public void setPhoneCus(String phoneCus) { this.phoneCus = phoneCus; }
    public void setAddressCus(String addressCus) { this.addressCus = addressCus; }
    public void setDobCus(Date dobCus) { this.dobCus = dobCus; }
    public void setUsrName(String usrName) { this.usrName = usrName; }
    public void setPassword(String password) { this.password = password; }
}
