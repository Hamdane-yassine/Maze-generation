/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 *
 * @author HAMDANE
 */
public final class MazeGrid extends BorderPane {

    private Canvas canvas;
    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Color FOREGROUND_COLOR = Color.WHITE;
    private int rows;
    private int columns;
    private Cell cells[][];
    private boolean affected = false;

    public MazeGrid(int rows, int columns) {
        int w = CalcWidth(columns);
        int h = CalcHeight(rows);
        this.rows = rows;
        this.columns = columns;
        this.canvas = new Canvas(w, h);
        this.setCenter(this.canvas);
        this.autosize();
        this.InitialCells();
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(canvas.getGraphicsContext2D());
            }
        };

        new Thread(() -> {
            Platform.runLater(timer::start);
        }).start();
    }

    public int CalcWidth(int columns) {
        int initialWidth = ((int) (Screen.getPrimary().getBounds().getWidth() * 0.80) - (int) ((Screen.getPrimary().getBounds().getWidth() * 0.80) * 0.225)) - 50;
        return ((int) (initialWidth / columns)) * columns;
    }

    public int CalcHeight(int rows) {
        return ((int) (((int) (Screen.getPrimary().getBounds().getHeight() * 0.80)) - 50) / rows) * rows;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        gc.setFill(FOREGROUND_COLOR);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.cells[i][j].draw(gc);
            }
        }
    }

    public void Repaint() {
        int w = CalcWidth(columns);
        int h = CalcHeight(rows);
        this.canvas.setWidth(w);
        this.canvas.setHeight(h);
        this.InitialCells();
        final AnimationTimer timer;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(canvas.getGraphicsContext2D());
            }
        };

        new Thread(() -> {
            Platform.runLater(timer::start);
        }).start();
    }

    public void Redraw() {
        int w = CalcWidth(columns);
        int h = CalcHeight(rows);
        this.canvas.setWidth(w);
        this.canvas.setHeight(h);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(canvas.getGraphicsContext2D());
            }
        };

        new Thread(() -> {
            Platform.runLater(timer::start);
        }).start();
    }

    private void InitialCells() {
        int w = CalcWidth(this.columns);
        int h = CalcHeight(this.rows);
        int cw = w / this.columns;
        int ch = h / this.rows;
        int id=0;
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int x = j * cw;
                int y = i * ch;
                cells[i][j] = new Cell(x, y, cw, ch, i, j,id);
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
