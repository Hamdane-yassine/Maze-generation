package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;

public class Dijkstra extends SolvingAlgorithm {

    private final HashMap<Cell, Integer> cells;
    private ArrayList<Cell> frontier;
    private ArrayList<Cell> newFrontier;

    public Dijkstra(Cell root, Cell target, Cell[][] grid, GraphicsContext gc) {
        super(root, target, grid, gc);
        this.cells = new HashMap<>();
        this.cells.put(this.getRoot(), 0);
        this.current = this.getTarget();
        this.frontier = new ArrayList<>();
        this.frontier.add(root);
    }

    @Override
    public void update(GraphicsContext gc) {
        if (!frontier.isEmpty()) {
            newFrontier = new ArrayList<>();
            for (Cell cell : frontier) {
                ArrayList<Cell> links = getLinks(cell);
                for (Cell linked : links) {
                    if (this.cells.get(linked) == null) {
                        this.cells.put(linked, this.cells.get(cell) + 1);
                        newFrontier.add(linked);
                        linked.visit(gc, true);
                    }
                }
            }
            frontier = newFrontier;
        } else {
            this.current.InPath(gc);
            ArrayList<Cell> links = getLinks(this.current);
            for (Cell neighbor : links) {
                if (this.cells.get(neighbor) < this.cells.get(this.current)) {
                    this.current = neighbor;
                    break;
                }
            }
            if (this.current == this.getRoot()) {
                this.current.InPath(gc);
                this.setFinished(true);
            }
        }

    }

    public Integer getDistance(Cell cell) {
        return this.cells.get(cell);
    }

    public void setDistance(Cell cell, int distance) {
        this.cells.put(cell, distance);
    }

}
