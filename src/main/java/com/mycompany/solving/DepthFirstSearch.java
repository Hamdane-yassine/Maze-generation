/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author HAMDANE
 */
public class DepthFirstSearch extends SolvingAlgorithm {

    private Stack<Cell> path;
    private Cell current;
    private ArrayList<Cell> visited;
    private ArrayList<Cell> possibleMoves;

    public DepthFirstSearch(Cell root, Cell target, Cell[][] grid) {
        super(root, target, grid);
        this.path = new Stack<>();
        this.current = this.getRoot();
        this.path.push(this.current);
        this.getRoot().visit();
        this.visited = new ArrayList<>();
    }

    @Override
    public void update() {
        possibleMoves = availableMoves(current);
        if (!possibleMoves.isEmpty()) {
            this.current = possibleMoves.get(possibleMoves.size() - 1);
            this.visited.add(this.current);
            this.current.setSelected(true);
            this.path.push(this.current);

        } else {
            this.path.pop().leave();
            this.current = this.path.peek();
        }
        if (this.getTarget() == this.current) {
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
