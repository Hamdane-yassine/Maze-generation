/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author HAMDANE
 */
public class AldousBroder extends GenerationAlgorithm {

    private int unvisited;
    private boolean firstCell = true;

    public AldousBroder(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.SelectedCell = cells[random.nextInt(rows)][random.nextInt(columns)];
        this.unvisited = rows * columns - 1;
    }

    @Override
    public void update(GraphicsContext gc) {
        if (this.firstCell) {
            this.SelectedCell.visit(gc, true);
            this.firstCell = false;
        } else {
            ArrayList<Cell> neighbours = calculateNeighbours(this.SelectedCell, true);
            Cell neighbour = neighbours.get(random.nextInt(neighbours.size()));
            if (!neighbour.isLinked()) {
                this.SelectedCell.link(neighbour);
                this.unvisited--;
            }
            this.SelectedCell.leave(gc);
            this.SelectedCell = neighbour;
            this.SelectedCell.visit(gc, true);
            if (this.unvisited <= 0) {
                this.setFinished(true);
                this.SelectedCell.leave(gc);
            }
        }
    }

}
