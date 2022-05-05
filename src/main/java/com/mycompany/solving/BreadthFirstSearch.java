/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javafx.scene.canvas.GraphicsContext;

public class BreadthFirstSearch extends SolvingAlgorithm {

    private final Queue<Cell> queue;
    private final HashMap<Cell, Cell> visited;
    private final LinkedList<Cell> path;
    private Cell assist;
    private ArrayList<Cell> availableMoves;

    public BreadthFirstSearch(Cell root, Cell target, Cell[][] grid, GraphicsContext gc) {
        super(root, target, grid, gc);
        this.queue = new LinkedList<>();
        this.current = this.getRoot();
        this.getRoot().visit(gc, true);
        this.visited = new HashMap<>();
        this.visited.put(this.getRoot(), null);
        this.current = this.getRoot();
        this.path = new LinkedList<>();
        this.path.addFirst(this.getTarget());
        this.assist = this.getTarget();
    }

    @Override
    public void update(GraphicsContext gc) {

        if (this.current != this.getTarget()) {
            this.availableMoves = availableMoves(current);
            for (Cell cell : this.availableMoves) {
                visited.put(cell, current);
                queue.add(cell);
                cell.visit(gc, true);
            }
            this.current = queue.remove();
        } else {
            this.assist.InPath(gc);
            this.assist = visited.get(this.assist);
            this.path.addFirst(this.assist);
            if (this.assist == this.getRoot()) {
                this.assist.InPath(gc);
                this.setFinished(true);
            }
        }

    }

    public ArrayList<Cell> availableMoves(Cell cell) {
        ArrayList<Cell> available_moves = new ArrayList<>();
        ArrayList<Cell> links = this.getLinks(cell);
        for (Cell available_neighbor : links) {
            if (!this.visited.containsKey(available_neighbor)) {
                available_moves.add(available_neighbor);
            }
        }
        return available_moves;
    }

}
