/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.scene.canvas.GraphicsContext;

public class AStar extends SolvingAlgorithm {

    private final List<Node> nodes;
    private Node rootNode = null;
    private Node targetNode = null;
    private final List<Cell> path;
    private Node currentNode;
    private final Queue<Node> notTestedNodes;
    private Node parentNode;

    public AStar(Cell root, Cell target, Cell[][] grid, GraphicsContext gc) {
        super(root, target, grid, gc);
        this.nodes = new ArrayList<>();
        this.path = new ArrayList<>();
        this.notTestedNodes = new PriorityQueue<>();
        this.setupAlgo();
    }

    public final void setupAlgo() {
        for (Cell[] arr : this.getGrid()) {
            for (Cell cell : arr) {
                Node node = new Node(cell);
                if (cell == getRoot()) {
                    this.rootNode = node;
                } else if (cell == getTarget()) {
                    this.targetNode = node;
                    this.parentNode = node;
                }
                this.nodes.add(node);
            }
        }

        this.nodes.forEach((node) -> {
            node.setNeighbors(this.nodes);
        });
        this.currentNode = this.rootNode;
        this.currentNode.setLocalGoal(0.0);
        this.currentNode.setGlobalGoal(this.computeHeuristic(rootNode.getCell(), targetNode.getCell()));
        this.notTestedNodes.add(currentNode);

    }

    @Override
    public void update(GraphicsContext gc) {

        if (!notTestedNodes.isEmpty()) {
            if (currentNode != targetNode) {
                while (!notTestedNodes.isEmpty() && notTestedNodes.peek().isIsVisited()) {
                    notTestedNodes.poll();
                }
                if (!notTestedNodes.isEmpty()) {
                    currentNode = notTestedNodes.poll();
                    currentNode.setIsVisited(true);
                    currentNode.getCell().select(gc);
                    for (Node nodeNeighbor : currentNode.getNeighbors()) {

                        if (!nodeNeighbor.isIsVisited()) {
                            notTestedNodes.add(nodeNeighbor);
                        }

                        double goal = currentNode.getLocalGoal() + 1;

                        if (goal < nodeNeighbor.getLocalGoal()) {
                            nodeNeighbor.setParent(currentNode);
                            nodeNeighbor.setLocalGoal(goal);
                            nodeNeighbor.setGlobalGoal(nodeNeighbor.getLocalGoal() + this.computeHeuristic(nodeNeighbor.getCell(), targetNode.getCell()));
                        }
                    }
                } else {
                    this.notTestedNodes.clear();
                }
            } else {
                this.notTestedNodes.clear();
            }
        } else {
            if (parentNode != null) {
                parentNode.getCell().InPath(gc);
                path.add(parentNode.getCell());
                parentNode = parentNode.getParent();
            } else {
                this.setFinished(true);
            }

        }

    }

    //Manhattan strategy
    public double computeHeuristic(Cell root, Cell target) {
        double D = 1.0;
        double dx = Math.abs(root.getX() - target.getX());
        double dy = Math.abs(root.getY() - target.getY());

        return D * (dx + dy);
    }

    private class Node implements Comparable<Node> {

        private final Cell cell;
        private final List<Node> neighbors;
        private Node parent;
        private boolean isVisited;
        private double globalGoal;
        private double localGoal;

        public Node(Cell cell) {
            this.cell = cell;
            this.parent = null;
            this.neighbors = new ArrayList<>();
            this.isVisited = false;
            this.globalGoal = Double.MAX_VALUE;
            this.localGoal = Double.MAX_VALUE;
        }

        public double getGlobalGoal() {
            return globalGoal;
        }

        public void setGlobalGoal(double globalGoal) {
            this.globalGoal = globalGoal;
        }

        public double getLocalGoal() {
            return localGoal;
        }

        public void setLocalGoal(double localGoal) {
            this.localGoal = localGoal;
        }

        public boolean isIsVisited() {
            return isVisited;
        }

        public void setIsVisited(boolean isVisited) {
            this.isVisited = isVisited;
        }

        public List<Node> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(List<Node> nodes) {
            List<Cell> tileNeighbors = getLinks(this.getCell());

            for (Node node : nodes) {
                if (tileNeighbors.contains(node.getCell())) {
                    this.neighbors.add(node);
                }
            }
        }

        public Cell getCell() {
            return cell;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public double getX() {
            return this.cell.getX();
        }

        public double getY() {
            return this.cell.getY();
        }

        @Override
        public int compareTo(Node other) {
            if (this.getGlobalGoal() < other.getGlobalGoal()) {
                return -1;
            }
            if (this.getGlobalGoal() > other.getGlobalGoal()) {
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Node{" + "parent=" + parent + ", isVisited=" + isVisited + ", globalGoal=" + globalGoal + '}';
        }
    }
}
