package com.n16.qltv.adaptor;

import com.n16.qltv.model.Staff;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static ArrayList<String> err = new ArrayList<>();

    public static ArrayList<String> getErr() { return err; }
    public static void setErr(ArrayList<String> newErr) { err = newErr; }

    public static void createValidation(String newErr) { err.add(newErr); }
    public static void authorValidation(Author author) {
        if(author.getAuthorName().isEmpty()
                || author.getAuthorName().isBlank()) {
            createValidation("Tên tác giả KHÔNG được để trống");
        }
        else if(!isString(author.getAuthorName())) {
            createValidation("Tên tác giả KHÔNG hợp lệ");
        }
        else if(author.getAuthorName().length() < 4
                || author.getAuthorName().length() > 20) {
            createValidation("Tên tác giả phải từ 4 -> 20 ký tự");
        }
        if(author.getAuthorAddress().isEmpty()
                || author.getAuthorAddress().isBlank()) {
            createValidation("Website KHÔNG được để trống");
        }
        else if(!isValidWebsite(author.getAuthorAddress())) {
            createValidation("Website KHÔNG hợp lệ");
        }
    }
    public static void loginValidation(String usrName, String password) {
        if(usrName.isEmpty()
                || usrName.isBlank()) {
            createValidation("Tên đăng nhập KHÔNG để trống");
        }
        if(password.isEmpty()
                || password.isBlank())
            createValidation("Mật khẩu KHÔNG để trống");
    }
    public static void staffValidation(Staff staff) {
        if(staff.getStaffName().isEmpty()
                || staff.getStaffName().isBlank()) {
            createValidation("Tên nhân viên KHÔNG để trống");
        }
        else if(!isString(staff.getStaffName())) {
            createValidation("Tên nhân viên KHÔNG hợp lệ.");
        }
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
        else if(staff.getPassword().length() < 8) {
            createValidation("Mật khẩu phải từ 8 ký tự trở lên");
        }
        if(!isDigit(staff.getStaffPhone())) {
            createValidation("Số điện thoại KHÔNG hợp lệ");
        }
    }
    public static String getStrValidation() {
        int errCount = 0;
        String strValidation = "";
        for(String err : getErr()) {
            errCount++;
            strValidation += String.format("%d. %s\n", errCount, err);
        }

        return strValidation;
    }
    public static void clearValidation() { err.clear(); }
    public static int getErrCount() { return err.size(); }
    public static boolean isValidEmail(String email) {
        String ePatern = "^[a-z0-9.!#$%&'*+/=?^_`{|}~-]+0((\\[0-9]{1,3}\\.]"
                + "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)"
                + "+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePatern);
        Matcher m = p.matcher(email);

        return m.matches();
    }
    public static boolean isString (String name) {
        String ePatten = "^[||p{L} '-]+$";
        Pattern p = Pattern.compile(ePatten);
        Matcher m = p.matcher(name);

        return m.matches();
    }
    public static boolean isStrongPassword(String password) {
        // Kiểm tra chứa chữ số
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Kiểm tra chứa chữ thường
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Kiểm tra chứa chữ hoa
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Kiểm tra chứa ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*()-+=|{}\\[\\]\"':;<>,.?/~`].*")) {
            return false;
        }

        return true;
    }
    public static boolean isDigit(String mobileNo) {
        return mobileNo.chars()
                .allMatch(Character::isDigit);
    }
}
