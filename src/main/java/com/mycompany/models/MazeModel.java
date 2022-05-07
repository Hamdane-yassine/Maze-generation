package com.mycompany.models;

public class MazeModel {

    private int ID;
    private String name;
    private int rows;
    private int columns;
    private String Date;
    private String Algo;
    private String solAlgo;
    private String Data;

    public MazeModel(int ID, String name, int rows, int columns, String Date, String Algo, String solAlgo, String Data) {
        this.ID = ID;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.Date = Date;
        this.Algo = Algo;
        this.solAlgo = solAlgo;
        this.Data = Data;
    }

    public String getSolAlgo() {
        return solAlgo;
    }

    public void setSolAlgo(String solAlgo) {
        this.solAlgo = solAlgo;
    }

    public String getAlgo() {
        return Algo;
    }

    public void setAlgo(String Algo) {
        this.Algo = Algo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

}
