package com.n16.qltv.patterns.commands;

import com.n16.qltv.patterns.commands.interfaces.ACommand;
import javax.swing.*;

public class OpenFrameCommand extends ACommand {
    private JFrame currentFrame;

    public OpenFrameCommand(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    @Override
    public void execute(JFrame targetFrame, Object params) {
        try {
            currentFrame.dispose();
            targetFrame.setLocationRelativeTo(null);
            targetFrame.setVisible(true);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
