/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mazegeneration;

/**
 *
 * @author HAMDANE
 */
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
