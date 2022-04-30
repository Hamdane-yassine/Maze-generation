/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author HAMDANE
 */
public class Dijkstra extends SolvingAlgorithm{


    private final HashMap<Cell, Integer> cells;
    private Cell current;
    private ArrayList<Cell> frontier;
    private ArrayList<Cell> newFrontier;
    public Dijkstra(Cell root, Cell target,Cell[][] grid) {
        super(root,target,grid);
        this.cells = new HashMap<>();
        this.cells.put(this.getRoot(), 0);
        this.current = this.getTarget();
        this.frontier = new ArrayList<>();
        this.frontier.add(root);
    }

    @Override
    public void update() {
        if (!frontier.isEmpty()) {
            newFrontier = new ArrayList<>();
            for (Cell cell : frontier) {
                ArrayList<Cell> links =  getLinks(cell);
                for (Cell linked : links) {
                    if (this.cells.get(linked) == null) {
                        this.cells.put(linked, this.cells.get(cell) + 1);
                        newFrontier.add(linked);
                        linked.visit();
                    }
                }
            }
            frontier = newFrontier;
        } else {
            ArrayList<Cell> links =  getLinks(this.current);
            for (Cell neighbor : links) {
                if (this.cells.get(neighbor) < this.cells.get(this.current)) {
                    this.current = neighbor;
                    this.current.setInpath(true);
                    break;
                }
            }
            if (this.current == this.getRoot()) {
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

    public HashMap<Cell, Integer> cells() {
        return this.cells();
    }

}
