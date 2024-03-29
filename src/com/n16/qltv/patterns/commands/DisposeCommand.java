package com.n16.qltv.patterns.commands;

import com.n16.qltv.patterns.commands.interfaces.ACommand;

import javax.swing.*;

public class DisposeCommand extends ACommand {
    @Override
    public void execute(JFrame frame, Object params) {
        frame.dispose();
    }
}
