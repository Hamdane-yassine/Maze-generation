package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;

public class Kruskals extends GenerationAlgorithm {

    private Stack<Paire<Cell, Cell>> neighbors;
    private ArrayList<Integer> setForCells;
    private ArrayList<ArrayList<Cell>> cellsForSet;
    private Cell leftSelected, rightSelected;

    public Kruskals(Cell[][] cells, int rows, int columns) {
        super(cells, rows, columns);
        this.neighbors = new Stack<>();
        this.setForCells = new ArrayList<>();
        this.cellsForSet = new ArrayList<>();
        this.Initial();
        Collections.shuffle(this.neighbors);
    }

    public final void Initial() {
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                int set = this.setForCells.size();
                this.setForCells.add(this.getCells()[i][j].getId(), set);
                ArrayList<Cell> cell = new ArrayList<>();
                cell.add(this.getCells()[i][j]);
                this.cellsForSet.add(set, cell);
                if (i + 1 < this.getRows()) {
                    this.neighbors.push(new Paire(this.getCells()[i][j], this.getCells()[i + 1][j]));
                }
                if (j + 1 < this.getColumns()) {
                    this.neighbors.push(new Paire(this.getCells()[i][j], this.getCells()[i][j + 1]));
                }
            }
        }
    }

    public boolean canMerge(Cell left, Cell right) {
        return !Objects.equals(this.setForCells.get(left.getId()), this.setForCells.get(right.getId()));
    }

    public void merge(Cell left, Cell right, GraphicsContext gc) {
        if (this.leftSelected != null && this.rightSelected != null) {
            this.leftSelected.leave(gc);
            this.rightSelected.leave(gc);
        }
        left.link(right);
        left.visit(gc, true);
        right.visit(gc, true);
        int winner = this.setForCells.get(left.getId());
        int loser = this.setForCells.get(right.getId());
        ArrayList<Cell> losers = this.cellsForSet.get(loser);
        for (Cell cell : losers) {
            this.cellsForSet.get(winner).add(cell);
            this.setForCells.set(cell.getId(), winner);
        }
        this.cellsForSet.get(loser).clear();
        this.leftSelected = left;
        this.rightSelected = right;
    }

    public Cell getLeftSelected() {
        return leftSelected;
    }

    public Cell getRightSelected() {
        return rightSelected;
    }

    @Override
    public void update(GraphicsContext gc) {
        Paire<Cell, Cell> pair = this.neighbors.pop();
        if (this.canMerge(pair.getFirst(), pair.getSecond())) {
            this.merge(pair.getFirst(), pair.getSecond(), gc);
        }
        if (this.neighbors.isEmpty()) {
            this.setFinished(true);
            this.leftSelected.leave(gc);
            this.rightSelected.leave(gc);
        }

    }

    public class Paire<U, V> {

        private U first;
        private V second;

        public V getSecond() {
            return second;

        }

        public void setsecond(V second) {
            this.second = second;

        }

        public U getFirst() {
            return first;
        }

        public void setFirst(U first) {
            this.first = first;

        }

        public Paire(U first, V second) {
            this.first = first;
            this.second = second;
        }

        public Paire() {
        }

        public String tostring() {
            return "<" + first + "," + second + ">";
        }

    }

}
