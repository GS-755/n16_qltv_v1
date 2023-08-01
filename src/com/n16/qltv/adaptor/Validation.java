package com.n16.qltv.adaptor;

import com.n16.qltv.model.Staff;

import java.util.ArrayList;

public class Validation {
    private static ArrayList<String> err = new ArrayList<>();

    public static ArrayList<String> getErr() { return err; }
    public static void setErr(ArrayList<String> newErr) { err = newErr; }

    public static void createValidation(String newErr) { err.add(newErr); }
    public static void staffValidation(Staff staff) {
        if(staff.getUsrName().isEmpty()
                || staff.getUsrName().isBlank()) {
            createValidation("Tên người dùng KHÔNG được để trống");
        }
        else if(staff.getUsrName().length() < 4
                || staff.getUsrName().length() > 20) {
            createValidation("Tên người dùng phải từ 4 -> 20 ký tự");
        }
        if(staff.getPassword().isEmpty()
                || staff.getPassword().isBlank()) {
            createValidation("Mật khẩu KHÔNG được để trống");
        }
        else if(staff.getPassword().length() < 4
                || staff.getPassword().length() > 20) {
            createValidation("Mật khẩu phải từ 4 -> 20 ký tự");
        }
    }
    public static String getStrValidation() {
        String strValidation = "";
        for(String s : getErr())
            strValidation += String.format("%s\n", s);

        return strValidation;
    }
    public static void clearValidation() { err.clear(); }
    public static int getErrCount() { return err.size(); }
}
