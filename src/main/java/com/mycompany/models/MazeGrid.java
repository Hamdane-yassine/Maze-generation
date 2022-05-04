/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 *
 * @author HAMDANE
 */
public final class MazeGrid extends BorderPane {

    private final Canvas canvas;
    private int rows;
    private int columns;
    private Cell cells[][];
    private boolean affected = false;

    public MazeGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        int w = CalcWidth();
        int h = CalcHeight();
        this.canvas = new Canvas(w, h);
        this.canvas.setCache(true);
        this.canvas.setCacheHint(CacheHint.SPEED);
        this.setCenter(this.canvas);
        this.InitialCells();
        this.autosize();
        canvas.getGraphicsContext2D().setLineWidth(1.2);
        draw(canvas.getGraphicsContext2D());
    }

    public int CalcWidth() {
        int initialWidth = (int) (((Screen.getPrimary().getBounds().getWidth() * 0.90) - ((Screen.getPrimary().getBounds().getWidth() * 0.90) * 0.25)) - 45.0);
        int val = initialWidth % this.columns;
        if (val != 0) {
            initialWidth += (this.columns - val);
        }
        return initialWidth;
    }

    public int CalcHeight() {
        int initialHeight = (int) (((Screen.getPrimary().getBounds().getHeight() * 0.81)));
        int val = initialHeight % this.rows;
        if (val != 0) {
            initialHeight += (this.rows - val);
        }
        return initialHeight;
    }

    public void draw(GraphicsContext gc) {
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.cells[i][j].draw(gc);
            }
        }
    }

    public void Repaint(boolean charged) {
        int w = CalcWidth();
        int h = CalcHeight();
        this.canvas.setWidth(w);
        this.canvas.setHeight(h);
        if (!charged) {
            this.InitialCells();
        }
        draw(canvas.getGraphicsContext2D());
    }

    private void InitialCells() {
        int w = (int) this.canvas.getWidth();
        int h = (int) this.canvas.getHeight();
        int cw = w / this.columns;
        int ch = h / this.rows;
        int id = 0;
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int x = j * cw;
                int y = i * ch;
                cells[i][j] = new Cell(x, y, cw, ch, i, j, id);
                id++;
            }
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isAffected() {
        return affected;
    }

    public void setAffected(boolean affected) {
        this.affected = affected;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

}
