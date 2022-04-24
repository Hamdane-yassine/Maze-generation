package com.mycompany.mazegeneration;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {

    public static Color VISITED_COLOR = Color.WHITE;
    public static Color UNVISITED_COLOR = Color.WHITE;
    public static Color SELECTED_COLOR = Color.BISQUE;
    public static Color WALL_COLOR = Color.BLACK;

    public static final byte TOP_WALL = 0;
    public static final byte BOTTOM_WALL = 1;
    public static final byte LEFT_WALL = 2;
    public static final byte RIGHT_WALL = 3;

    private int x, y, w, h, i, j;
    private boolean selected, visited;

    private Wall[] walls;

    public Cell(int x, int y, int w, int h, int i, int j, boolean selected, boolean visited, Wall[] walls) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.i = i;
        this.j = j;
        this.selected = selected;
        this.visited = visited;
        this.walls = walls;
    }

    public Cell(int x, int y, int w, int h, int i, int j) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.i = i;
        this.j = j;
        this.walls = new Wall[4];
        this.walls[0] = new Wall(false);
        this.walls[1] = new Wall(false);
        this.walls[2] = new Wall(false);
        this.walls[3] = new Wall(false);
    }

    public void draw(GraphicsContext gc) {
        // show cell
        if (visited && !selected) {
            gc.setFill(VISITED_COLOR);
        } else if (selected) {
            gc.setFill(SELECTED_COLOR);
        } else {
            gc.setFill(UNVISITED_COLOR);
        }
        gc.fillRect(x, y, w, h);

        // show walls
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        gc.setStroke(WALL_COLOR);
        for (int k = 0; k < this.walls.length; k++) {
            if (!this.walls[k].isBroken()) {
                switch (k) {
                    case TOP_WALL:
                        x1 = x;
                        y1 = y;
                        x2 = x + w;
                        y2 = y;
                        break;
                    case BOTTOM_WALL:
                        x1 = x;
                        y1 = y + h;
                        x2 = x + w;
                        y2 = y + h;
                        break;
                    case LEFT_WALL:
                        x1 = x;
                        y1 = y;
                        x2 = x;
                        y2 = y + h;
                        break;
                    case RIGHT_WALL:
                        x1 = x + w;
                        y1 = y;
                        x2 = x + w;
                        y2 = y + h;
                        break;
                    default:
                        break;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
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

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
