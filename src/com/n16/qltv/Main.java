package com.n16.qltv;

import com.n16.qltv.frame.admin.LoginFrame;
import com.n16.qltv.frame.config.ConfigFrame;
import com.n16.qltv.vendor.Session;

public class Main {
    public static void checkPropertiesFile() {

        if(!(boolean) Session.get("config-enabled")) {
            ConfigFrame configFrame = new ConfigFrame();
        }
    }
    public static void main(String[] args) {
        checkPropertiesFile();
        LoginFrame loginFrame = new LoginFrame();
    }
}