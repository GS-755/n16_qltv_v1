package com.n16.qltv.patterns.strategy;

import com.n16.qltv.patterns.strategy.interfaces.ILoginStrategy;

public class LoginContext {
    private int mode;
    private ILoginStrategy strategy;

    public LoginContext() { }

    public int getMode() { return this.mode; }
    public ILoginStrategy getStrategy() { return this.strategy; }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setStrategy(ILoginStrategy strategy) {
        this.strategy = strategy;
    }
}
