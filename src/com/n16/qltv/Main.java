package com.n16.qltv;

import com.n16.qltv.frame.admin.LoginFrame;
import com.n16.qltv.frame.config.ConfigFrame;
import javax.swing.*;
import java.io.File;

public class Main {
    public static boolean isConfigAvailable() {
        File file = new File("app.properties");

        return file.exists();
    }
    public static void main(String[] args) {
        if(isConfigAvailable()) {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file app.properties!\n" +
                    "Vui lòng điền thông tin kết nối CSDL.");
            ConfigFrame configFrame = new ConfigFrame();
        }
    }
}
