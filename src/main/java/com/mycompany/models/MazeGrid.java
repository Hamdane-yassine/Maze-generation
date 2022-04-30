/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
    private final AnimationTimer timer;

    public MazeGrid(int rows, int columns) {
        double w = CalcWidth();
        double h = CalcHeight();
        this.rows = rows;
        this.columns = columns;
        this.canvas = new Canvas(w, h);
        this.setCenter(this.canvas);
        this.InitialCells();
        this.autosize();
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(canvas.getGraphicsContext2D());
            }
        };
        Platform.runLater(timer::start);
        timer.stop();

    }

    public double CalcWidth() {
        double initialWidth = ((Screen.getPrimary().getBounds().getWidth() * 0.80) - ((Screen.getPrimary().getBounds().getWidth() * 0.80) * 0.25)) - 45.0;
        return twoDegit(initialWidth);
    }

    public double CalcHeight() {
        return twoDegit(((Screen.getPrimary().getBounds().getHeight() * 0.80)) - 15.0);
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
        double w = CalcWidth();
        double h = CalcHeight();
        this.canvas.setWidth(w);
        this.canvas.setHeight(h);
        this.InitialCells();
        Platform.runLater(timer::start);
        timer.stop();

    }

    public void Redraw() {
        double w = CalcWidth();
        double h = CalcHeight();
        this.canvas.setWidth(w);
        this.canvas.setHeight(h);
        Platform.runLater(timer::start);
        timer.stop();

    }

    private void InitialCells() {
        double w = this.canvas.getWidth();
        double h = this.canvas.getHeight();
        double cw = w / this.columns;
        double ch = h / this.rows;
        int id = 0;
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double x = j * cw;
                double y = i * ch;
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

    public double twoDegit(double a) {
        return Math.round(a / 100.0) * 100.0;
    }

}
