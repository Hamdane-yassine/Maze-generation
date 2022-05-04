package com.mycompany.models;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {

    public static Color VISITED_COLOR = Color.WHITE;
    public static Color UNVISITED_COLOR = Color.AZURE;
    public static Color SELECTED_COLOR = Color.CADETBLUE;
    public static Color POPED_UP = Color.WHITE;
    public static Color WALL_COLOR = Color.BLACK;
    public static Color PATH_COLOR = Color.BROWN;
    public static Color ROOT_TARGET_COLOR = Color.DARKGRAY;

    public static final byte TOP_WALL = 0;
    public static final byte BOTTOM_WALL = 1;
    public static final byte LEFT_WALL = 2;
    public static final byte RIGHT_WALL = 3;

    private int x, y, w, h;
    private int i, j, id;
    private boolean selected, visited, inpath, rootTarger;
    private Wall[] walls;
    private int cost;
    private boolean linked = false;
    private ArrayList<ArrayList<Integer>> links;

    public Cell(int x, int y, int w, int h, int i, int j, int id, boolean selected, boolean visited, boolean inpath, boolean rootTarget, Wall[] walls, int cost, boolean linked, ArrayList<ArrayList<Integer>> links) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.i = i;
        this.j = j;
        this.id = id;
        this.selected = selected;
        this.visited = visited;
        this.inpath = inpath;
        this.rootTarger = rootTarget;
        this.walls = walls;
        this.cost = cost;
        this.linked = linked;
        this.links = links;
    }

    public Cell(int x, int y, int w, int h, int i, int j, int id) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.i = i;
        this.j = j;
        this.id = id;
        this.walls = new Wall[4];
        this.walls[0] = new Wall(false);
        this.walls[1] = new Wall(false);
        this.walls[2] = new Wall(false);
        this.walls[3] = new Wall(false);
        this.links = new ArrayList<>();
        this.rootTarger = false;
    }

    public boolean isRootTarger() {
        return rootTarger;
    }

    public void setRootTarger(boolean rootTarger) {
        this.rootTarger = rootTarger;
    }

    public ArrayList<ArrayList<Integer>> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<ArrayList<Integer>> links) {
        this.links = links;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void draw(GraphicsContext gc) {
        // show cell
        if (inpath) {
            gc.setFill(PATH_COLOR);
        } else if (rootTarger) {
            gc.setFill(ROOT_TARGET_COLOR);
        } else if (visited && !selected) {
            gc.setFill(VISITED_COLOR);
        } else if (selected) {
            gc.setFill(SELECTED_COLOR);
        } else {
            gc.setFill(UNVISITED_COLOR);
        }
        gc.fillRect(x, y, w, h);
        // show walls
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        for (int k = 0; k < this.walls.length; k++) {
            if (!this.walls[k].isBroken()) {
                switch (k) {
                    case TOP_WALL -> {
                        x1 = x;
                        y1 = y;
                        x2 = x + w;
                        y2 = y;
                    }
                    case BOTTOM_WALL -> {
                        x1 = x;
                        y1 = y + h;
                        x2 = x + w;
                        y2 = y + h;
                    }
                    case LEFT_WALL -> {
                        x1 = x;
                        y1 = y;
                        x2 = x;
                        y2 = y + h;
                    }
                    case RIGHT_WALL -> {
                        x1 = x + w;
                        y1 = y;
                        x2 = x + w;
                        y2 = y + h;
                    }
                    default -> {
                    }
                }
                gc.strokeLine(x1, y1, x2, y2);
            }
        }
    }

    public void link(Cell to) {
        if (to.getJ() == this.getJ() - 1) {
            // its a left neighbour
            this.breakWall(LEFT_WALL);
            to.breakWall(RIGHT_WALL);
        } else if (to.getJ() == this.getJ() + 1) {
            // its a right neighbour
            this.breakWall(RIGHT_WALL);
            to.breakWall(LEFT_WALL);
        } else if (to.getI() == this.getI() - 1) {
            // has to be top neighbour
            this.breakWall(TOP_WALL);
            to.breakWall(BOTTOM_WALL);
        } else if (to.getI() == this.getI() + 1) {
            // has to be bottom neighbour
            this.breakWall(BOTTOM_WALL);
            to.breakWall(TOP_WALL);
        }
        this.linked = true;
        to.linked = true;
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(to.getI());
        arr.add(to.getJ());
        this.links.add(arr);
        arr = new ArrayList<>();
        arr.add(i);
        arr.add(j);
        to.links.add(arr);
    }

    public void leave() {
        this.selected = false;
    }

    private void breakWall(byte id) {
        this.walls[id].setBroken(true);
    }

    public void visit() {
        this.visited = true;
        this.selected = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setWalls(Wall[] walls) {
        this.walls = walls;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setInpath(boolean inpath) {
        this.inpath = inpath;
    }

    public boolean isInpath() {
        return inpath;
    }

}
