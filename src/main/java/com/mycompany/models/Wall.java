package com.mycompany.models;

public class Wall {

    private boolean broken;

    public Wall(boolean broken) {
        super();
        this.broken = broken;
    }

    public Wall() {
    }

    public boolean isBroken() {
        return this.broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
