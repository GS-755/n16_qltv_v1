package com.n16.qltv;

import com.n16.qltv.frame.admin.LoginFrame;
import com.n16.qltv.frame.config.ConfigFrame;

import javax.swing.*;
import java.io.File;

public class Main {
    public static boolean isValidConfig() {
        File file = new File("app.properties");

        return file.exists();
    }
    public static void main(String[] args) {
        if(isValidConfig()) {
            LoginFrame loginFrame = new LoginFrame();
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy tập tin app.properties!\n" +
                    "Vui lòng điền các thông tin kết nối CSDL");

            ConfigFrame configFrame = new ConfigFrame();
        }
    }
}