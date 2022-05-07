package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public abstract class SolvingAlgorithm {

    private boolean finished;
    private Cell root;
    private Cell target;
    private Cell[][] grid;
    protected Cell current;

    public void setRoot(Cell root) {
        this.root = root;
    }

    public void setTarget(Cell target) {
        this.target = target;
    }

    public SolvingAlgorithm(Cell root, Cell target, Cell[][] grid, GraphicsContext gc) {
        this.root = root;
        this.target = target;
        this.root.RootTarged(gc);
        this.target.RootTarged(gc);
        this.finished = false;
        this.grid = grid;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public Cell getRoot() {
        return root;
    }

    public Cell getTarget() {
        return target;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public abstract void update(GraphicsContext gc);

    public boolean isFinished() {
        return finished;
    }

    public ArrayList<Cell> getLinks(Cell cell) {
        ArrayList<Cell> links = new ArrayList<>();
        ArrayList<ArrayList<Integer>> celllinks = cell.getLinks();
        for (ArrayList<Integer> arr : celllinks) {
            links.add(this.grid[arr.get(0)][arr.get(1)]);
        }
        return links;
    }

}
