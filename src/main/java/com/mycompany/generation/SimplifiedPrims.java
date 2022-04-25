/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author HAMDANE
 */
public class SimplifiedPrims extends GenerationAlgorithm{

    private final Random random = new Random();
    private final Stack<Cell> Active;
    private Cell SelectedCell;
    public SimplifiedPrims(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.Active = new Stack<>();
        this.SelectedCell = cells[random.nextInt(rows)][random.nextInt(columns)];
        this.Active.push(this.SelectedCell);
    }
    
    @Override
    public void update()
    {
            this.SelectedCell.leave();
            this.SelectedCell =  this.Active.get(random.nextInt(Active.size()));
            this.SelectedCell.visit();
            ArrayList<Cell> available_neighbors = calculateNeighbours(this.SelectedCell);
            if(!available_neighbors.isEmpty())
            {
                Cell neighbor = available_neighbors.get(random.nextInt(available_neighbors.size()));
                this.SelectedCell.link(neighbor);
                neighbor.setVisited(true);
                this.Active.push(neighbor);
            }else{
                this.Active.remove(this.SelectedCell);
            }
            if(this.Active.isEmpty())
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
