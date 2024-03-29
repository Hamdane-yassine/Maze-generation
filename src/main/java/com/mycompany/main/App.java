package com.mycompany.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.stage.Screen;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Maze.fxml"));
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth() * 0.95, Screen.getPrimary().getBounds().getHeight() * 0.90);
        stage.setTitle("Génération des labyrinthes");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("maze.png")));
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
