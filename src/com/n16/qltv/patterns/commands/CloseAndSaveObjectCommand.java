package com.n16.qltv.patterns.commands;

import com.n16.qltv.patterns.commands.interfaces.ACommand;

import javax.swing.*;

public class CloseAndSaveObjectCommand extends ACommand {
    public CloseAndSaveObjectCommand() {

    }

    public void setCurrentFrame(JFrame currentFrame) {
        super.setCurrentFrame(currentFrame);
    }

    @Override
    public void execute(JFrame targetFrame, Object params) {
        super.setParams(params);
        super.getCurrentFrame().dispose();
        targetFrame.setLocationRelativeTo(null);
        targetFrame.setVisible(true);
    }
}
