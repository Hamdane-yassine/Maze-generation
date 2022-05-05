/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

public abstract class GenerationAlgorithm {

    private Cell[][] cells;
    private int rows;
    private int columns;
    private boolean finished = false;
    public Cell SelectedCell;
    protected Random random = new Random();

    public GenerationAlgorithm(Cell[][] cells, int rows, int columns) {
        this.cells = cells;
        this.rows = rows;
        this.columns = columns;
    }

    public ArrayList<Cell> calculateNeighbours(Cell selectedCell, boolean any) {
        ArrayList<Cell> localcells = new ArrayList<>();
        int y = selectedCell.getI();
        int x = selectedCell.getJ();
        int leftNeighbourI = selectedCell.getI();
        int leftNeighbourJ = selectedCell.getJ() - 1;
        int rightNeighbourI = selectedCell.getI();
        int rightNeighbourJ = selectedCell.getJ() + 1;
        int topNeighbourI = selectedCell.getI() - 1;
        int topNeighbourJ = selectedCell.getJ();
        int bottomNeighbourI = selectedCell.getI() + 1;
        int bottomNeighbourJ = selectedCell.getJ();

        if (x - 1 >= 0) {
            if (!this.getCells()[leftNeighbourI][leftNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[leftNeighbourI][leftNeighbourJ]);
            }
        }

        if (x + 1 < this.getColumns()) {
            if (!this.getCells()[rightNeighbourI][rightNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[rightNeighbourI][rightNeighbourJ]);
            }
        }

        if (y - 1 >= 0) {
            if (!this.getCells()[topNeighbourI][topNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[topNeighbourI][topNeighbourJ]);
            }
        }

        if (y + 1 < this.getRows()) {
            if (!this.getCells()[bottomNeighbourI][bottomNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[bottomNeighbourI][bottomNeighbourJ]);
            }
        }
        return localcells;
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

    public abstract void update(GraphicsContext gc);

}
