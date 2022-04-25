package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class RecursiveBacktracker extends GenerationAlgorithm {

    private final Stack<Cell> cellStack = new Stack<>();
    private Cell selectedCell;
    private final Random random = new Random();
    private boolean firstCell = true;

    public RecursiveBacktracker(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
    }

    @Override
    public void update() {
        if (firstCell) {
            selectedCell = this.getCells()[0][0];
            selectedCell.visit();
            this.cellStack.push(selectedCell);
            this.firstCell = false;
        } else {
            ArrayList<Cell> availableNeighbours = calculateNeighbours();
            if (!availableNeighbours.isEmpty()) {
                Cell selectedNeighbour = availableNeighbours.get(random.nextInt(availableNeighbours.size()));
                selectedCell.link(selectedNeighbour);
                selectedCell.leave();
                selectedCell = selectedNeighbour;
                selectedCell.visit();
                this.cellStack.push(selectedCell);
            } else if (cellStack.isEmpty()) {
                selectedCell.leave();
                this.setFinished(true);
            } else {
                selectedCell.leave();
                selectedCell = cellStack.pop();
                selectedCell.setPoped(true);
                selectedCell.visit();
            }
        }

    }

    public ArrayList<Cell> calculateNeighbours() {
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
            if (!this.getCells()[leftNeighbourI][leftNeighbourJ].isVisited()) {
                localcells.add(this.getCells()[leftNeighbourI][leftNeighbourJ]);
            }
        }

        if (x + 1 < this.getColumns()) {
            if (!this.getCells()[rightNeighbourI][rightNeighbourJ].isVisited()) {
                localcells.add(this.getCells()[rightNeighbourI][rightNeighbourJ]);
            }
        }

        if (y - 1 >= 0) {
            if (!this.getCells()[topNeighbourI][topNeighbourJ].isVisited()) {
                localcells.add(this.getCells()[topNeighbourI][topNeighbourJ]);
            }
        }

        if (y + 1 < this.getRows()) {
            if (!this.getCells()[bottomNeighbourI][bottomNeighbourJ].isVisited()) {
                localcells.add(this.getCells()[bottomNeighbourI][bottomNeighbourJ]);
            }
        }
        return localcells;
    }

}
