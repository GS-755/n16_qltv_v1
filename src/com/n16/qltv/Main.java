package com.n16.qltv;

import com.n16.qltv.frame.login.LoginFrame;
import com.n16.qltv.patterns.commands.interfaces.ACommand;
import com.n16.qltv.patterns.commands.OpenFrameCommand;
import com.n16.qltv.frame.config.ConfigFrame;
import javax.swing.*;
import java.io.File;

public class Main {
    private static ACommand command;

    public static boolean isConfigAvailable() {
        File file = new File("app.properties");

        return file.exists();
    }
    public static void main(String[] args) {
        command = new OpenFrameCommand(new LoginFrame());
        if(isConfigAvailable()) {
            command.execute(new LoginFrame(), null);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file app.properties!\n" +
                    "Vui lòng điền thông tin kết nối CSDL.");
            command.execute(new ConfigFrame(), null);
        }
    }
}
