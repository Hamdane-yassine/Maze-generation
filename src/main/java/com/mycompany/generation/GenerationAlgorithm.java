/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generation;

import com.mycompany.models.Cell;

/**
 *
 * @author HAMDANE
 */
public abstract class  GenerationAlgorithm {
    private Cell[][] cells;
    private int rows;
    private int columns;
    private boolean finished = false;

    public GenerationAlgorithm(Cell[][] cells, int rows, int columns) {
        this.cells = cells;
        this.rows = rows;
        this.columns = columns;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isFinished() {
        return finished;
    }
    


    public abstract void update();

}
