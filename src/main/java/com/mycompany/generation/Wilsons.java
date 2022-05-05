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
public class Wilsons extends GenerationAlgorithm {

    private final ArrayList<Cell> univisted;

    public Wilsons(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.univisted = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.univisted.add(cells[i][j]);
            }
        }
        Cell first = this.univisted.get(random.nextInt(this.univisted.size()));
        this.SelectedCell = first;
        this.univisted.remove(first);
    }

    @Override
    public void update(GraphicsContext gc) {
        this.SelectedCell.leave(gc);
        Cell cell = this.univisted.get(random.nextInt(this.univisted.size()));
        cell.visit(gc, true);
        this.SelectedCell = cell;
        ArrayList<Cell> path = new ArrayList<>();
        path.add(cell);
        while (this.univisted.contains(cell)) {
            ArrayList<Cell> neighbours = calculateNeighbours(cell, true);
            cell = neighbours.get(random.nextInt(neighbours.size()));
            int position = path.indexOf(cell);
            if (position >= 0) {
                path = tracing(position, path);
            } else {
                path.add(cell);
            }

        }
        for (int i = 0; i < path.size() - 1; i++) {
            path.get(i).link(path.get(i + 1));
            path.get(i).visit(gc, false);
            path.get(i + 1).visit(gc, false);
            this.univisted.remove(path.get(i));
        }
        if (this.univisted.isEmpty()) {
            this.setFinished(true);
            this.SelectedCell.leave(gc);
        }
    }

    public ArrayList<Cell> tracing(int position, ArrayList<Cell> arr) {
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = 0; i <= position; i++) {
            result.add(arr.get(i));
        }
        return result;
    }
}
