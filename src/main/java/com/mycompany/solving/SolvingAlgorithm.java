/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solving;

import com.mycompany.models.Cell;

/**
 *
 * @author HAMDANE
 */
public class SolvingAlgorithm {

    private boolean finished;
    private Cell root;
    private Cell target;

    public void setRoot(Cell root) {
        this.root = root;
    }

    public void setTarget(Cell target) {
        this.target = target;
    }

    public SolvingAlgorithm(Cell root, Cell target) {
        this.root = root;
        this.target = target;
        this.finished = false;
    }

    public Cell getRoot() {
        return root;
    }

    public Cell getTarget() {
        return target;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void update() {
        //To do
    }

    public boolean isFinished() {
        return finished;
    }

}
