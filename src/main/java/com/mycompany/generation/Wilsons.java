/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author HAMDANE
 */
public class Wilsons extends GenerationAlgorithm {

    private ArrayList<Cell> univisted;
    private final Random random = new Random();
    private Cell SelectedCell;
    public Wilsons(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.univisted = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.univisted.add(cells[i][j]);
            }
        }
        Cell first = this.univisted.get(random.nextInt(this.univisted.size()));
        this.univisted.remove(first);
    }

    @Override
    public void update() {
        if(this.SelectedCell!=null)
            this.SelectedCell.leave();
        Cell cell = this.univisted.get(random.nextInt(this.univisted.size()));
        cell.visit();
        this.SelectedCell=cell;
        ArrayList<Cell> path = new ArrayList<>();
        path.add(cell);
        cell.visit();
        while (this.univisted.contains(cell)) {
            ArrayList<Cell> neighbours = calculateNeighbours(cell);
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
            this.univisted.remove(path.get(i));
        }
        if(this.univisted.isEmpty())
        {
            this.setFinished(true);
            this.SelectedCell.leave();
        }
    }

    public ArrayList<Cell> calculateNeighbours(Cell selectedCell) {
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
            localcells.add(this.getCells()[leftNeighbourI][leftNeighbourJ]);
        }

        if (x + 1 < this.getColumns()) {
            localcells.add(this.getCells()[rightNeighbourI][rightNeighbourJ]);
        }

        if (y - 1 >= 0) {
            localcells.add(this.getCells()[topNeighbourI][topNeighbourJ]);
        }

        if (y + 1 < this.getRows()) {
            localcells.add(this.getCells()[bottomNeighbourI][bottomNeighbourJ]);
        }
        return localcells;
    }

    public ArrayList<Cell> tracing(int position, ArrayList<Cell> arr) {
        ArrayList<Cell> result = new ArrayList<Cell>();
        for (int i = 0; i <= position; i++) {
            result.add(arr.get(i));
        }
        return result;
    }
}
