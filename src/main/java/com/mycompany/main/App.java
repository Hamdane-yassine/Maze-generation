package com.mycompany.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.Screen;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../mazegeneration/Maze.fxml"));
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth() * 0.80, Screen.getPrimary().getBounds().getHeight() * 0.80);
        stage.setTitle("Generation Des labyrinth");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

}
