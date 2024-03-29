package com.n16.qltv.patterns.commands.interfaces;

import javax.swing.*;

public abstract class ACommand {
    private JFrame currentFrame, targetFrame;
    private Object params;

    public ACommand() { }

    public JFrame getCurrentFrame() { return this.currentFrame; }
    public JFrame getTargetFrame() { return this.targetFrame; }
    public Object getParams() { return this.params; }
    public void setCurrentFrame(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }
    public void setTargetFrame(JFrame targetFrame) {
        this.targetFrame = targetFrame;
    }
    public void setParams(Object params) {
        this.params = params;
    }

    public abstract void execute(JFrame frame, Object params);
}
