package com.n16.qltv.utils;

import com.n16.qltv.model.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Validation {
    private static ArrayList<String> errorArrayList = new ArrayList<>();

    public static void createValidation(String newErr) { errorArrayList.add(newErr); }
    public static void clearValidation() { errorArrayList.clear(); }
    public static int getErrCount() { return errorArrayList.size(); }

    public static void authorValidation(Author author) {
        if(author.getAuthorName().isEmpty()
                || author.getAuthorName().isBlank()) {
            createValidation("Tên tác giả KHÔNG được để trống");
        }
        else if(!isValidName(author.getAuthorName())) {
            createValidation("Tên tác giả KHÔNG hợp lệ");
        }
        else if(author.getAuthorName().length() < 4
                || author.getAuthorName().length() > 20) {
            createValidation("Tên tác giả phải từ 4 -> 20 ký tự");
        }
        if(author.getAuthorSite().isEmpty()
                || author.getAuthorSite().isBlank()) {
            createValidation("Website KHÔNG được để trống");
        }
        else if(!isValidWebsite(author.getAuthorSite())) {
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
        // Kiểm tra tên Nhân viên
        if(staff.getStaffName().isEmpty()
                || staff.getStaffName().isBlank()) {
            createValidation("Tên nhân viên KHÔNG để trống");
        }
        else if(!isValidName(staff.getStaffName())) {
            createValidation("Tên nhân viên KHÔNG hợp lệ.");
        }
        // Kiểm tra tài khoản Nhân viên
        if(staff.getUsrName().isEmpty()
                || staff.getUsrName().isBlank()) {
            createValidation("Tên người dùng KHÔNG được để trống");
        }
        else if(staff.getUsrName().length() < 4
                || staff.getUsrName().length() > 20) {
            createValidation("Tên người dùng phải từ 4 -> 20 ký tự");
        }
        // Kiểm tra mật khẩu Nhân viên
        if(staff.getPassword().isEmpty()
                || staff.getPassword().isBlank()) {
            createValidation("Mật khẩu KHÔNG được để trống");
        }
        else if(staff.getPassword().length() < 8) {
            createValidation("Mật khẩu phải từ 8 ký tự trở lên");
        }
        // Kiểm tra số điện thoại Nhân viên
        if(!isDigit(staff.getStaffPhone())) {
            createValidation("Số điện thoại KHÔNG hợp lệ");
        }
    }
    public static void bookValidation(Book book) {
        if(book.getBookName().isEmpty()
                || book.getBookName().isBlank()) {
            createValidation("Tên sách KHÔNG để trống.");
        }
        else if(!isValidName(book.getBookName())) {
            createValidation("Tên sách KHÔNG hợp lệ");
        }
    }
    public static void customerValidation(Customer customer) {
        if(customer.getNameCus().isEmpty()
                || customer.getNameCus().isBlank()) {
            createValidation("Tên khách hàng KHÔNG để trống");
        }
        else if(!isValidName(customer.getNameCus())) {
            createValidation("Tên khách hàng KHÔNG hợp lệ");
        }
        if(customer.getUsrName().isEmpty()
                || customer.getUsrName().isBlank()) {
            createValidation("Tài khoản KHÔNG để trống");
        }
        else if(customer.getUsrName().length() < 4
                || customer.getUsrName().length() > 20) {
            createValidation("Tài khoản PHẢI từ 4 -> 20 ký tự");
        }
        if(customer.getPassword().isEmpty()
                || customer.getPassword().isBlank()) {
            createValidation("Mật khẩu KHÔNG để trống");
        }
    }
    public static void publisherValidation(Publisher publisher) {
        if(publisher.getPublisherName().isEmpty()) {
            createValidation("Hãy Điền Tên NXB");
        }
        else if(!isValidName(publisher.getPublisherName())) {
            createValidation("Tên NXB không được có ký tự đặc biệt");
        }
        if(publisher.getPublisherAddress().isEmpty()) {
            createValidation("Địa chỉ NXB không được để trống");
        }
        if(publisher.getPublisherEmail().isEmpty()) {
            createValidation("Email không được để trống");
        }
        else if(!isValidEmail(publisher.getPublisherEmail())) {
            createValidation("Email không hợp lệ");
        }
        if(publisher.getRepresent().isEmpty()) {
            createValidation("Hãy nhập tên người đại diện");
        }
    }
    public static void categoryValidation(Category category) {
        if(category.getNameCate().isEmpty()) {
            createValidation("Hãy điền Tên thể loại");
        }
    }
    public static String getStrValidation() {
        int errorCount = 0;
        String strValidation = "";
        for(String item : errorArrayList) {
            errorCount++;
            strValidation += String.format("%d. %s\n", errorCount, item);
        }

        return strValidation;
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(email).matches();
    }
    public static boolean isValidName(String name) {
        if (name.matches(".*\\d.*")) {
            return false;
        }
        if (name.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }

        return true;
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
    public static boolean isValidWebsite(String input) {
        // Kiểm tra chuỗi theo mẫu regex
        String regex = "^[a-zA-Z0-9]+([-.][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(input).matches();
    }
    public static boolean isDigit(String mobileNo) {
        return mobileNo.chars()
                .allMatch(Character::isDigit);
    }
}
