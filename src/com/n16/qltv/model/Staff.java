package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;
import java.sql.Date;

public class Staff implements IModels {
    private int staffId;
    private String staffName;
    private String staffPhone, staffAddress;
    private Date staffDob;
    private char gender;
    private String usrName, password;

    public Staff() {  }
    public Staff(String staffName, char gender, String staffPhone,
                 String staffAddress, Date staffDob, String usrName, String password) {
        this.staffName = staffName;
        this.gender = gender;
        this.staffPhone = staffPhone;
        this.staffAddress = staffAddress;
        this.staffDob = staffDob;
        this.usrName = usrName;
        this.password = password;
    }

    public int getStaffId() { return this.staffId; }
    public String getStaffName() { return this.staffName; }
    public String getStaffPhone() { return this.staffPhone; }
    public String getStaffAddress() { return this.staffAddress; }
    public Date getStaffDob() { return this.staffDob; }
    public String getUsrName() { return this.usrName; }
    public String getPassword() { return this.password; }
    public char getGender() { return this.gender; }
    public String getStrGender() {
        if(this.gender == 'f')
            return "Nữ";
        return "Nam";
    }
    public void setGender(char gender) { this.gender = gender; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public void setStaffPhone(String staffPhone) { this.staffPhone = staffPhone; }
    public void setStaffAddress(String staffAddress) { this.staffAddress = staffAddress; }
    public void setStaffDob(Date staffDob) { this.staffDob = staffDob; }
    public void setUsrName(String usrName) { this.usrName = usrName; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                ", staffDob='" + staffDob + '\'' +
                ", gender=" + gender +
                ", usrName='" + usrName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Class<Staff> getType() {
        return Staff.class;
    }
}
