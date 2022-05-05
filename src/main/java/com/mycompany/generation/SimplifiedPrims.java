/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;


public class SimplifiedPrims extends GenerationAlgorithm {

    private final Stack<Cell> Active;

    public SimplifiedPrims(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.Active = new Stack<>();
        this.SelectedCell = cells[random.nextInt(rows)][random.nextInt(columns)];
        this.Active.push(this.SelectedCell);
    }

    @Override
    public void update(GraphicsContext gc) {
        this.SelectedCell.leave(gc);
        this.SelectedCell = this.Active.get(random.nextInt(Active.size()));
        this.SelectedCell.visit(gc, true);
        ArrayList<Cell> available_neighbors = calculateNeighbours(this.SelectedCell, false);
        if (!available_neighbors.isEmpty()) {
            Cell neighbor = available_neighbors.get(random.nextInt(available_neighbors.size()));
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
