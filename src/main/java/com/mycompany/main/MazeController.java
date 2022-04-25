/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package com.mycompany.main;

import com.mycompany.database.MazeDAO;
import com.mycompany.models.Cell;
import com.mycompany.models.MazeModel;
import com.mycompany.models.MazeGrid;
import com.mycompany.generation.RecursiveBacktracker;
import com.mycompany.generation.Kruskals;
import com.google.gson.Gson;
import com.mycompany.generation.GenerationAlgorithm;
import com.mycompany.generation.SimplifiedPrims;
import com.mycompany.generation.TruePrims;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author HAMDANE
 */
public class MazeController implements Initializable {

    private int rows = 10;
    private int columns = 10;
    private MazeGrid grid;
    private GenerationAlgorithm GenAlgo;
    private Timer myRepeatingTimer;
    private int period = 500;
    private boolean hasSteped = false;
    private int MazeId = -1;
    private ObservableList<MazeModel> MazeList;
    @FXML
    VBox leftPane;
    @FXML
    SplitPane splitPane;
    @FXML
    ScrollPane scrollmaze;
    @FXML
    ScrollPane scrollpane;
    @FXML
    ComboBox gridsize;
    @FXML
    ComboBox genAlgo;
    @FXML
    ComboBox savebox;
    @FXML
    Slider genslider;

    @Override
    public void initialize(URL url, ResourceBundle reb) {

        this.genslider.valueProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
            int value = (int) MazeController.this.genslider.getValue();
            int actualvalue = 1;
            if (value < 100) {
                actualvalue = 100 - value;
            }
            MazeController.this.period = actualvalue * 10;
            MazeController.this.Vitesse((long) MazeController.this.period);
        });
        this.LoadMazes();
        this.gridsize.setItems(FXCollections.observableArrayList(new String[]{"10x10", "15x15", "25x25", "50x50", "100x100"}));
        this.gridsize.getSelectionModel().selectFirst();
        this.genAlgo.setItems(FXCollections.observableArrayList(new String[]{"Recursive Backtracker", "Kruskal’s", "Simplified Prim’s", "True Prim’s"}));
        this.genAlgo.getSelectionModel().selectFirst();
        this.grid = new MazeGrid(rows, columns);
        this.grid.setPadding(new Insets(10, 10, 10, 10));
        this.scrollmaze.setContent(grid);
        this.leftPane.setPadding(new Insets(2, 2, 2, 2));
        this.leftPane.setSpacing(10);
        this.scrollpane.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.225));
        this.scrollpane.minWidthProperty().bind(splitPane.widthProperty().multiply(0.225));
    }

    public void UpdateGrid() {
        this.MazeId = -1;
        this.resetTimer();
        String selectedSize = this.gridsize.getSelectionModel().getSelectedItem().toString();
        switch (selectedSize) {
            case "10x10":
                this.resetGrid(10, 10);
                break;
            case "15x15":
                this.resetGrid(15, 15);
                break;
            case "25x25":
                this.resetGrid(25, 25);
                break;
            case "50x50":
                this.resetGrid(50, 50);
                break;
            case "100x100":
                this.resetGrid(100, 100);
                break;
        }
    }

    public void MazeGeneration() {
        String selectedSize = this.genAlgo.getSelectionModel().getSelectedItem().toString();
        switch (selectedSize) {
            case "Recursive Backtracker": {
                if (this.GenAlgo == null) {
                    this.GenAlgo = new RecursiveBacktracker(this.grid.getCells(), rows, columns);
                }
                this.generate();

            }
            break;
            case "Kruskal’s": {
                if (this.GenAlgo == null) {
                    this.GenAlgo = new Kruskals(this.grid.getCells(), this.rows, this.columns);
                }
                this.generate();
            }
            break;
            case "Simplified Prim’s": {
                if (this.GenAlgo == null) {
                    this.GenAlgo = new SimplifiedPrims(this.grid.getCells(), this.rows, this.columns);
                }
                this.generate();
            }
            break;
            case "True Prim’s": {
                if (this.GenAlgo == null) {
                    this.GenAlgo = new TruePrims(this.grid.getCells(), this.rows, this.columns);
                }
                this.generate();
            }
            break;

        }
    }

    public void Step() {
        if (this.GenAlgo == null) {
            String selectedSize = this.genAlgo.getSelectionModel().getSelectedItem().toString();
            switch (selectedSize) {
                case "Recursive Backtracker":
                    this.GenAlgo = new RecursiveBacktracker(this.grid.getCells(), rows, columns);
                    break;
                case "Kruskal’s":
                    this.GenAlgo = new Kruskals(this.grid.getCells(), this.rows, this.columns);
                    break;
                case "Simplified Prim’s":
                    this.GenAlgo = new SimplifiedPrims(this.grid.getCells(), this.rows, this.columns);
                    break;
                case "True Prim’s":
                    this.GenAlgo = new TruePrims(this.grid.getCells(), this.rows, this.columns);
                    break;

            }
        }
        if (!this.GenAlgo.isFinished()) {
            this.resetTimer();
            this.grid.setAffected(true);
            this.GenAlgo.update();
            this.hasSteped = true;
        }
    }

    public void ExportImage() {
        WritableImage wi = new WritableImage((int) this.grid.getCanvas().getWidth(), (int) this.grid.getCanvas().getHeight());
        WritableImage snapshot = this.grid.getCanvas().snapshot(new SnapshotParameters(), wi);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File output = fileChooser.showSaveDialog((Stage) this.splitPane.getScene().getWindow());
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
        } catch (IOException E) {
        }
    }

    public void ResetAlgorithms() {
        this.GenAlgo = null;
    }

    public void Vitesse(long period) {
        if (this.GenAlgo != null && !this.GenAlgo.isFinished() && !this.hasSteped) {
            this.myRepeatingTimer.cancel();
            this.myRepeatingTimer = new Timer();
            this.myRepeatingTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        GenAlgo.update();
                        if (GenAlgo.isFinished()) {
                            myRepeatingTimer.cancel();
                        }
                    });
                }
            }, 0, period);
        }
    }

    public void Save() {
        if (this.grid.isAffected()) {
            String defaultName;
            if (!this.MazeList.isEmpty()) {
                defaultName = "" + (this.MazeList.get(this.MazeList.size() - 1).getID() + 1);
            } else {
                defaultName = "Labyrinth 1";
            }
            TextInputDialog inputdialog = new TextInputDialog(defaultName);
            inputdialog.setContentText("Nom: ");
            inputdialog.setHeaderText("Entrer le nom de labyrinth");
            Optional<String> result = inputdialog.showAndWait();
            if (result.isPresent()) {
                final String name;
                if (inputdialog.getEditor().getText().isEmpty()) {
                    name = defaultName;
                } else {
                    name = inputdialog.getEditor().getText();
                }
                Gson gson = new Gson();
                new Thread(() -> {
                    Platform.runLater(() -> {
                        String json = gson.toJson(grid.getCells());
                        MazeDAO.SaveMaze(name, rows, columns, json);
                        LoadMazes();
                    });
                }).start();

            }

        } else {
            Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            ms1.setTitle("Message");
            ms1.setHeaderText("Impossible d'enregistrer labyrinth");
            ms1.setContentText("Aucune labyrinth a été créer");
            ms1.show();
        }

    }

    public void MazeLoading() {
        this.resetTimer();
        int index = this.savebox.getSelectionModel().getSelectedIndex();
        if (index != this.MazeId && !this.MazeList.isEmpty()) {
            this.MazeId = index;
            Gson gson = new Gson();
            MazeModel maze = this.MazeList.get(index);
            this.rows = maze.getRows();
            this.columns = maze.getColumns();
            this.grid.setRows(this.rows);
            this.grid.setColumns(this.columns);
            this.grid.setAffected(true);
            Cell[][] cells = gson.fromJson(maze.getData(), Cell[][].class);
            this.grid.setCells(cells);
            this.grid.Redraw();
        }
    }

    public void MazeSettings() throws IOException {
        Stage popupwindow = new Stage();
        BorderPane Bp = new BorderPane();
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        Button b1 = new Button("Supprimer");
        Button b2 = new Button("Annuler");
        TableView tv = this.MazeTable();
        tv.setPadding(new Insets(10, 10, 10, 10));
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Labyrinths");
        b2.setOnAction(e -> popupwindow.close());
        b1.setOnAction((event) -> {
            if (tv.getSelectionModel().getSelectedItem() != null) {
                MazeModel m = (MazeModel) tv.getSelectionModel().getSelectedItem();
                MazeDAO.DeleteMaze(m.getID());
                LoadMazes();
                tv.setItems(MazeList);
            } else {
                Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                ms1.setTitle("INFORMATION");
                ms1.setHeaderText("SÉLECTIONNER UNE LABYRINTH");
                ms1.setContentText("Vous devez sélectionner une labyrinth !");
                ms1.showAndWait();
            }
        });
        hb.getChildren().addAll(b1, b2);
        hb.setSpacing(5);
        Bp.setCenter(tv);
        Bp.setBottom(hb);
        hb.setAlignment(Pos.CENTER);
        hb.setPrefHeight(46);
        Scene scene1 = new Scene(Bp, 610, 400);
        popupwindow.setResizable(false);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public void LoadMazes() {
        this.MazeList = MazeDAO.GetMazes();
        String[] mazes = new String[this.MazeList.size()];
        for (int i = 0; i < this.MazeList.size(); i++) {
            mazes[i] = this.MazeList.get(i).getID() + "-" + this.MazeList.get(i).getName();
        }
        this.savebox.setItems(FXCollections.observableArrayList(mazes));
        this.savebox.getSelectionModel().selectFirst();
    }

    public void resetTimer() {
        if (this.myRepeatingTimer != null) {
            this.myRepeatingTimer.cancel();
            this.myRepeatingTimer = null;
        }
    }

    public TableView MazeTable() {
        TableView<MazeModel> tableView = new TableView();
        TableColumn<MazeModel, Integer> Id = new TableColumn<>("ID");
        Id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Id.setPrefWidth(46);
        Id.setResizable(false);
        Id.setStyle("-fx-alignment: CENTER;");
        TableColumn<MazeModel, String> name = new TableColumn<>("Nom");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(157.60);
        name.setResizable(false);
        name.setStyle("-fx-alignment: CENTER;");
        TableColumn<MazeModel, Integer> rowscol = new TableColumn<>("Nb lignes");
        rowscol.setCellValueFactory(new PropertyValueFactory<>("rows"));
        rowscol.setPrefWidth(102.40);
        rowscol.setResizable(false);
        rowscol.setStyle("-fx-alignment: CENTER;");
        TableColumn<MazeModel, Integer> columnscol = new TableColumn<>("Nb colonnes");
        columnscol.setCellValueFactory(new PropertyValueFactory<>("columns"));
        columnscol.setPrefWidth(102.40);
        columnscol.setResizable(false);
        columnscol.setStyle("-fx-alignment: CENTER;");
        TableColumn<MazeModel, Integer> Date = new TableColumn<>("Date d'enregistrement");
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Date.setPrefWidth(192);
        Date.setResizable(false);
        Date.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(Id);
        tableView.getColumns().add(name);
        tableView.getColumns().add(rowscol);
        tableView.getColumns().add(columnscol);
        tableView.getColumns().add(Date);
        tableView.setItems(this.MazeList);
        return tableView;
    }

    public void generate() {
        if (!this.grid.isAffected() || this.hasSteped) {
            this.hasSteped = false;
            if (!this.grid.isAffected()) {
                this.grid.setAffected(true);
            }
            if (!this.GenAlgo.isFinished()) {
                this.myRepeatingTimer = new Timer();
                myRepeatingTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (GenAlgo.isFinished()) {
                                myRepeatingTimer.cancel();
                            } else {
                                GenAlgo.update();
                            }
                        });
                    }
                }, 0, period);
            }
        }
    }

    public void resetGrid(int rows, int columns) {
        if (this.rows != rows && this.columns != columns || this.grid.isAffected()) {
            this.grid.setAffected(false);
            this.rows = rows;
            this.columns = columns;
            this.grid.setRows(rows);
            this.grid.setColumns(columns);
            this.grid.Repaint();
            this.ResetAlgorithms();
        }
    }
}
