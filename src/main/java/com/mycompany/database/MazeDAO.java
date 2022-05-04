/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.database;

import com.mycompany.models.MazeModel;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author HAMDANE
 */
public class MazeDAO {

    public static Connection connect() {
            try {
                URL u = null;
                try {
                    u = new URL(com.mycompany.main.App.class.getResource("/DB/sqlite-jdbc-3.30.1.jar").toString());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MazeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                URLClassLoader classLoader = new URLClassLoader(new URL[]{u});
                Driver driver = (Driver) Class.forName("org.sqlite.JDBC", true, classLoader).newInstance();
                try {
                    DriverManager.registerDriver(new DelegatingDriver(driver));
                } catch (SQLException ex) {
                    Logger.getLogger(MazeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            }
            String url = "jdbc:sqlite:mazes.db";
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(MazeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void SaveMaze(String Name, String algo, String solAlgo, int rows, int columns, String data) {
        try (
            Connection con = connect();
            PreparedStatement p = con.prepareStatement("Insert Into maze(Name,Rows,Columns,Date,Algo,SolAlgo,Data) values (?,?,?,?,?,?,?)");) {
            p.setString(1, Name);
            p.setInt(2, rows);
            p.setInt(3, columns);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            p.setString(4, dtf.format(LocalDateTime.now()));
            p.setString(5, algo);
            p.setString(6, solAlgo);
            p.setString(7, data);
            p.execute();
        } catch (SQLException sq) {
            Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            ms1.setTitle("Connection failed");
            ms1.setHeaderText("Operation Failed !");
            ms1.setContentText(sq.getMessage());
            ms1.showAndWait();
        }
    }

    public static ObservableList<MazeModel> GetMazes() {
        ObservableList<MazeModel> list = FXCollections.observableArrayList();
        try (
            Connection con = MazeDAO.connect();
            PreparedStatement p = con.prepareStatement("Select * from maze");)
        {
            ResultSet r = p.executeQuery();
            while (r.next()) {
                list.add(new MazeModel(r.getInt("ID"), r.getString("Name"), r.getInt("Rows"), r.getInt("Columns"), r.getString("Date"), r.getString("Algo"), r.getString("SolAlgo"), r.getString("Data")));
            }
        } catch (SQLException sq) {
            Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            ms1.setTitle("Connection failed");
            ms1.setHeaderText("Operation Failed !");
            ms1.setContentText(sq.getMessage());
            ms1.showAndWait();
        }
        return list;
    }

    public static void DeleteMaze(int ID) {
        try (
            Connection con = MazeDAO.connect();
            PreparedStatement p = con.prepareStatement("Delete from maze where ID=?");) {
            p.setInt(1, ID);
            p.execute();
        } catch (SQLException sq) {
            Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            ms1.setTitle("Connection failed");
            ms1.setHeaderText("Operation Failed !");
            ms1.setContentText(sq.getMessage());
            ms1.showAndWait();
        }
    }

}
