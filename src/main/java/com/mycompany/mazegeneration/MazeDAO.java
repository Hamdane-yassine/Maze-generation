/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mazegeneration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author HAMDANE
 */
public class MazeDAO {

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:mazes.db");
    }

    public static void SaveMaze(String Name, int rows, int columns, String data) {
        try (
            Connection con = connect();
            PreparedStatement p = con.prepareStatement("Insert Into maze(Name,Rows,Columns,Date,Data) values (?,?,?,?,?)");) {
            p.setString(1, Name);
            p.setInt(2, rows);
            p.setInt(3, columns);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            p.setString(4, dtf.format(LocalDateTime.now()));
            p.setString(5, data);
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
            PreparedStatement p = con.prepareStatement("Select * from maze");) {
            ResultSet r = p.executeQuery();
            while (r.next()) {
                list.add(new MazeModel(r.getInt("ID"), r.getString("Name"), r.getInt("Rows"), r.getInt("Columns"), r.getString("Date"), r.getString("Data")));
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
    
     public static void DeleteMaze(int ID)
   {
       try
        (
          Connection con = MazeDAO.connect();
          PreparedStatement p = con.prepareStatement("Delete from maze where ID=?");
        )
        {
         p.setInt(1, ID);
         p.execute();
        }catch(SQLException sq)
        {
           Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
           ms1.setTitle("Connection failed");
           ms1.setHeaderText("Operation Failed !");
           ms1.setContentText(sq.getMessage());
           ms1.showAndWait(); 
        }
   }
}
