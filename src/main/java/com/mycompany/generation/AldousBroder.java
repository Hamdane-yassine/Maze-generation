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
public class AldousBroder extends GenerationAlgorithm {

    private int unvisited;
    private Cell SelectedCell;
    private final Random random = new Random();
    private boolean firstCell=true;
    public AldousBroder(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.SelectedCell = cells[random.nextInt(rows)][random.nextInt(columns)];
        this.unvisited = rows * columns - 1;
    }

    @Override
    public void update() {
        if(this.firstCell)
        {
            this.SelectedCell.visit();
            this.firstCell=false;
        }else{
            ArrayList<Cell> neighbours = calculateNeighbours(this.SelectedCell);
            Cell neighbour = neighbours.get(random.nextInt(neighbours.size()));
            if(!neighbour.isLinked())
            {
                this.SelectedCell.link(neighbour);
                this.unvisited--;
            }
            this.SelectedCell.leave();
            this.SelectedCell= neighbour;
            this.SelectedCell.visit();
            if(this.unvisited <= 0)
            {
                this.setFinished(true);
                this.SelectedCell.leave();
            }
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

}
