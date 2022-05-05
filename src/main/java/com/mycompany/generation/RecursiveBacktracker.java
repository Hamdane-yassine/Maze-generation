package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;

public class RecursiveBacktracker extends GenerationAlgorithm {

    private final Stack<Cell> cellStack = new Stack<>();
    private boolean firstCell = true;

    public RecursiveBacktracker(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
    }

    @Override
    public void update(GraphicsContext gc) {
        if (firstCell) {
            SelectedCell = this.getCells()[0][0];
            SelectedCell.visit(gc, true);
            this.cellStack.push(SelectedCell);
            this.firstCell = false;
        } else {
            ArrayList<Cell> availableNeighbours = calculateNeighbours(this.SelectedCell, false);
            if (!availableNeighbours.isEmpty()) {
                Cell selectedNeighbour = availableNeighbours.get(random.nextInt(availableNeighbours.size()));
                SelectedCell.link(selectedNeighbour);
                SelectedCell.leave(gc);
                SelectedCell = selectedNeighbour;
                SelectedCell.visit(gc, true);
                this.cellStack.push(SelectedCell);
            } else if (cellStack.isEmpty()) {
                SelectedCell.leave(gc);
                this.setFinished(true);
            } else {
                SelectedCell.leave(gc);
                SelectedCell = cellStack.pop();
                SelectedCell.visit(gc, true);
            }
        }

    }

}
