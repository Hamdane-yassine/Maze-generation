package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;

public class TruePrims extends GenerationAlgorithm {

    private final Stack<Cell> Active;

    public TruePrims(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.getCells()[i][j].setCost(random.nextInt(100));
            }
        }
        this.Active = new Stack<>();
        this.SelectedCell = cells[random.nextInt(rows)][random.nextInt(columns)];
        this.Active.push(this.SelectedCell);

    }

    @Override
    public void update(GraphicsContext gc) {
        this.SelectedCell.leave(gc);
        this.SelectedCell = Collections.min(this.Active, Comparator.comparing(Cell::getCost));
        this.SelectedCell.visit(gc, true);
        ArrayList<Cell> available_neighbors = calculateNeighbours(this.SelectedCell, false);
        if (!available_neighbors.isEmpty()) {
            Cell neighbor = Collections.min(available_neighbors, Comparator.comparing(Cell::getCost));
            this.SelectedCell.link(neighbor);
            neighbor.visit(gc, false);
            this.Active.push(neighbor);
        } else {
            this.Active.remove(this.SelectedCell);
        }
        if (this.Active.isEmpty()) {
            this.setFinished(true);
            this.SelectedCell.leave(gc);
        }

    }

}
