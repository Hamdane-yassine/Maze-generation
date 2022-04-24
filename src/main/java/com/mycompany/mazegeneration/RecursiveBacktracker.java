package com.mycompany.mazegeneration;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;

public class RecursiveBacktracker {

    private int columns, rows;
    private ArrayList<Cell> cellStack = new ArrayList<Cell>();
    private Cell[][] cells;
    private Cell selectedCell;
    private Random random = new Random();
    private boolean finished = false;

    public RecursiveBacktracker(int columns, int rows, Cell[][] cells) {
        
        this.columns = columns;
        this.rows = rows;
        this.cells = cells;
        // pick a start cell?
        selectedCell = cells[0][0];
        selectedCell.visit();
    }

    public void update() {
        boolean hasUnvisitedCells = false;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (!this.cells[i][j].isVisited()) {
                    hasUnvisitedCells = true;
                    break;
                }
            }
        }

        ArrayList<Cell> availableNeighbours = calculateNeighbours();
        Cell selectedNeighbour = null;

        if (!availableNeighbours.isEmpty()) {
            selectedNeighbour = availableNeighbours.get(random.nextInt(availableNeighbours.size()));
        }

        if (selectedNeighbour != null) {
            selectedCell.link(selectedNeighbour);
            selectedCell.leave();
            selectedCell = selectedNeighbour;
            selectedCell.visit();
            cellStack.add(selectedCell);
        } else if (cellStack.size() > 0) {
            selectedCell.leave();
            selectedCell = cellStack.remove(cellStack.size() - 1);
            selectedCell.visit();
        } else {
            selectedCell.leave();
            this.finished = true;
        }

    }

    public ArrayList<Cell> calculateNeighbours() {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        int y = selectedCell.getI();
        int x = selectedCell.getJ();

        // test neighbours
        // id is the index of the cell
        // because we do not know the x and y of the neighbours we need to calculate their id's
        int leftNeighbourI = selectedCell.getI();
        int leftNeighbourJ = selectedCell.getJ() - 1;
        int rightNeighbourI = selectedCell.getI();
        int rightNeighbourJ = selectedCell.getJ() + 1;
        int topNeighbourI = selectedCell.getI() - 1;
        int topNeighbourJ = selectedCell.getJ();
        int bottomNeighbourI = selectedCell.getI() + 1;
        int bottomNeighbourJ = selectedCell.getJ();

        if (x - 1 >= 0) {
            if (!this.cells[leftNeighbourI][leftNeighbourJ].isVisited()) {
                cells.add(this.cells[leftNeighbourI][leftNeighbourJ]);
            }
        }

        if (x + 1 < this.columns) {
            if (!this.cells[rightNeighbourI][rightNeighbourJ].isVisited()) {
                cells.add(this.cells[rightNeighbourI][rightNeighbourJ]);
            }
        }

        if (y - 1 >= 0) {
            if (!this.cells[topNeighbourI][topNeighbourJ].isVisited()) {
                cells.add(this.cells[topNeighbourI][topNeighbourJ]);
            }
        }

        if (y + 1 < this.rows) {
            if (!this.cells[bottomNeighbourI][bottomNeighbourJ].isVisited()) {
                cells.add(this.cells[bottomNeighbourI][bottomNeighbourJ]);
            }
        }
        return cells;
    }

    public boolean isFinished() {
        return this.finished;
    }


}
