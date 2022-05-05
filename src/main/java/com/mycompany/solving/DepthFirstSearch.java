/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author HAMDANE
 */
public class DepthFirstSearch extends SolvingAlgorithm {

    private final Stack<Cell> path;
    private final ArrayList<Cell> visited;
    private ArrayList<Cell> possibleMoves;

    public DepthFirstSearch(Cell root, Cell target, Cell[][] grid, GraphicsContext gc) {
        super(root, target, grid, gc);
        this.path = new Stack<>();
        this.current = this.getRoot();
        this.path.push(this.current);
        this.getRoot().visit(gc, true);
        this.visited = new ArrayList<>();
        this.visited.add(this.current);
    }

    @Override
    public void update(GraphicsContext gc) {
        possibleMoves = availableMoves(current);
        if (!possibleMoves.isEmpty()) {
            this.current = possibleMoves.get(possibleMoves.size() - 1);
            this.visited.add(this.current);
            this.current.select(gc);
            this.path.push(this.current);

        } else {
            this.path.pop().leave(gc);
            this.current = this.path.peek();
        }
        if (this.getTarget() == this.current) {
            for (Cell cell : path) {
                cell.InPath(gc);
            }
            this.setFinished(true);
        }
    }

    public ArrayList<Cell> availableMoves(Cell cell) {
        ArrayList<Cell> available_moves = new ArrayList<>();
        ArrayList<Cell> links = getLinks(cell);
        for (Cell available_neighbor : links) {
            if (!this.visited.contains(available_neighbor)) {
                available_moves.add(available_neighbor);
            }
        }
        return available_moves;
    }

}
