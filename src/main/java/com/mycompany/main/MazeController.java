package com.mycompany.main;

import com.mycompany.database.MazeDAO;
import com.mycompany.models.Cell;
import com.mycompany.models.MazeModel;
import com.mycompany.models.MazeGrid;
import com.mycompany.generation.RecursiveBacktracker;
import com.mycompany.generation.Kruskals;
import com.google.gson.Gson;
import com.mycompany.generation.AldousBroder;
import com.mycompany.generation.GenerationAlgorithm;
import com.mycompany.generation.SimplifiedPrims;
import com.mycompany.generation.TruePrims;
import com.mycompany.generation.Wilsons;
import com.mycompany.solving.AStar;
import com.mycompany.solving.BreadthFirstSearch;
import com.mycompany.solving.DepthFirstSearch;
import com.mycompany.solving.Dijkstra;
import com.mycompany.solving.SolvingAlgorithm;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

public class MazeController implements Initializable {

    private int rows = 10;
    private int columns = 10;
    private MazeGrid grid;
    private GenerationAlgorithm genAlgo;
    private SolvingAlgorithm solAlgo;
    private double genperiod = 50 * (1000 / 60);
    private double solperiod = 50 * (1000 / 60);
    private boolean hasStepedGen = false;
    private boolean hasStepedSol = false;
    private boolean startSolving = false;
    private boolean mazecharged = false;
    private int MazeId = -1;
    private ObservableList<MazeModel> MazeList;
    private String genalgoselected;
    private String solalgoselected;
    private String lastFileChooserPath;
    private Timeline timeline;
    private KeyFrame generationKey;
    private KeyFrame solvingKey;
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
    ComboBox selectedgenAlgo;
    @FXML
    ComboBox solselect;
    @FXML
    ComboBox savebox;
    @FXML
    Slider genslider;
    @FXML
    Slider solslider;

    @Override
    public void initialize(URL url, ResourceBundle reb) {

        this.splitPane.setDividerPosition(0, 0.25);
        this.genslider.valueProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
            MazeController.this.GenVitesse();
        });
        this.solslider.valueProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
            MazeController.this.SolVitesse();
        });
        this.LoadMazes();
        this.gridsize.setItems(FXCollections.observableArrayList(new String[]{"10x10", "15x15", "25x25", "50x50", "100x100"}));
        this.gridsize.getSelectionModel().selectFirst();
        this.selectedgenAlgo.setItems(FXCollections.observableArrayList(new String[]{"Recursive Backtracker", "Kruskal", "Simplified Prim", "True Prim", "Aldous-Broder", "Wilson"}));
        this.selectedgenAlgo.getSelectionModel().selectFirst();
        this.solselect.setItems(FXCollections.observableArrayList(new String[]{"Dijkstra", "Depth First Search", "Breadth First Search", "A*"}));
        this.solselect.getSelectionModel().selectFirst();
        this.grid = new MazeGrid(rows, columns);
        this.grid.setPadding(new Insets(10, 10, 10, 10));
        this.scrollmaze.setContent(grid);
        this.leftPane.setPadding(new Insets(2, 2, 2, 2));
        this.leftPane.setSpacing(10);
        this.timeline = new Timeline();
    }

    //Grid management
    public void UpdateGrid() {
        this.MazeId = -1;
        this.resetTimer();
        String selectedSize = this.gridsize.getSelectionModel().getSelectedItem().toString();
        switch (selectedSize) {
            case "10x10" ->
                this.resetGrid(10, 10);
            case "15x15" ->
                this.resetGrid(15, 15);
            case "25x25" ->
                this.resetGrid(25, 25);
            case "50x50" ->
                this.resetGrid(50, 50);
            case "100x100" ->
                this.resetGrid(100, 100);
        }
    }

    public void resetGrid(int rows, int columns) {
        if (this.rows != rows && this.columns != columns || this.grid.isAffected()) {
            this.grid.setAffected(false);
            this.rows = rows;
            this.columns = columns;
            this.grid.setRows(rows);
            this.grid.setColumns(columns);
            this.grid.Repaint(false);
            this.ResetAlgorithms();
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
            this.grid.Repaint(true);
            this.mazecharged = true;
            this.genalgoselected = maze.getAlgo();
            this.solalgoselected = maze.getSolAlgo();
        }
    }

    //Generation section
    public void MazeGeneration() {
        if (!this.grid.isAffected() || this.hasStepedGen) {
            this.hasStepedGen = false;
            if (this.genAlgo == null) {
                this.genalgoselected = this.selectedgenAlgo.getSelectionModel().getSelectedItem().toString();
                this.grid.setAffected(true);
                switch (genalgoselected) {
                    case "Recursive Backtracker" ->
                        this.genAlgo = new RecursiveBacktracker(this.grid.getCells(), rows, columns);
                    case "Kruskal" ->
                        this.genAlgo = new Kruskals(this.grid.getCells(), this.rows, this.columns);
                    case "Simplified Prim" ->
                        this.genAlgo = new SimplifiedPrims(this.grid.getCells(), this.rows, this.columns);
                    case "True Prim" ->
                        this.genAlgo = new TruePrims(this.grid.getCells(), this.rows, this.columns);
                    case "Aldous-Broder" ->
                        this.genAlgo = new AldousBroder(this.grid.getCells(), this.rows, this.columns);
                    case "Wilson" ->
                        this.genAlgo = new Wilsons(this.grid.getCells(), this.rows, this.columns);
                }
            }
            this.generate();
        }
    }

    public void GenStep() {
        if (this.genAlgo == null) {
            this.genalgoselected = this.selectedgenAlgo.getSelectionModel().getSelectedItem().toString();
            switch (this.genalgoselected) {
                case "Recursive Backtracker" ->
                    this.genAlgo = new RecursiveBacktracker(this.grid.getCells(), rows, columns);
                case "Kruskal" ->
                    this.genAlgo = new Kruskals(this.grid.getCells(), this.rows, this.columns);
                case "Simplified Prim" ->
                    this.genAlgo = new SimplifiedPrims(this.grid.getCells(), this.rows, this.columns);
                case "True Prim" ->
                    this.genAlgo = new TruePrims(this.grid.getCells(), this.rows, this.columns);
                case "Aldous-Broder" ->
                    this.genAlgo = new AldousBroder(this.grid.getCells(), this.rows, this.columns);
                case "Wilson" ->
                    this.genAlgo = new Wilsons(this.grid.getCells(), this.rows, this.columns);
            }
        }
        if (!this.genAlgo.isFinished()) {
            if (this.timeline.getStatus() == Animation.Status.RUNNING) {
                this.timeline.stop();
            }
            this.grid.setAffected(true);
            this.genAlgo.update(this.grid.getCanvas().getGraphicsContext2D());
            this.hasStepedGen = true;
        }
    }

    public void GenVitesse() {
        double value = this.genslider.getValue();
        double actualvalue = 1;
        if (value < 100) {
            actualvalue = 100.0 - value;
        }
        this.genperiod = actualvalue * (1000 / 60);
        if (this.genAlgo != null && !this.genAlgo.isFinished() && !this.hasStepedGen) {
            this.generationKey = new KeyFrame(Duration.millis(this.genperiod), event -> {
                if (!this.genAlgo.isFinished()) {
                    this.genAlgo.update(this.grid.getCanvas().getGraphicsContext2D());
                } else {
                    this.timeline.stop();
                }
            });
            timeline.stop();
            timeline.getKeyFrames().setAll(this.generationKey);
            timeline.play();
        }
    }

    public void generate() {

        if (!this.genAlgo.isFinished()) {
            this.generationKey = new KeyFrame(Duration.millis(this.genperiod), event -> {
                if (!this.genAlgo.isFinished()) {
                    this.genAlgo.update(this.grid.getCanvas().getGraphicsContext2D());

                } else {
                    this.timeline.stop();
                }
            });
            timeline.getKeyFrames().setAll(this.generationKey);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public void GenHelp() {
        String algo = this.selectedgenAlgo.getSelectionModel().getSelectedItem().toString();
        switch (algo) {
            case "Recursive Backtracker" ->
                this.Help(algo, GenerationAlgorithm.RECURSIVE);
            case "Kruskal" ->
                this.Help(algo, GenerationAlgorithm.KRUSKAL);
            case "Simplified Prim" ->
                this.Help(algo, GenerationAlgorithm.SIMPLEPRIM);
            case "True Prim" ->
                this.Help(algo, GenerationAlgorithm.TRUEPRIM);
            case "Aldous-Broder" ->
                this.Help(algo, GenerationAlgorithm.ALDOUS);
            case "Wilson" ->
                this.Help(algo, GenerationAlgorithm.WILSON);
        }
    }

    //solve section
    public void MazeSolving() {
        if (this.genAlgo != null && this.genAlgo.isFinished() || mazecharged) {
            this.SolAlgoChanged();
            if (!this.startSolving || this.hasStepedSol) {
                this.hasStepedSol = false;
                if (this.solAlgo == null) {
                    this.startSolving = true;
                    this.solalgoselected = this.solselect.getSelectionModel().getSelectedItem().toString();
                    switch (solalgoselected) {
                        case "Dijkstra" ->
                            this.solAlgo = new Dijkstra(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                        case "Depth First Search" ->
                            this.solAlgo = new DepthFirstSearch(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                        case "Breadth First Search" ->
                            this.solAlgo = new BreadthFirstSearch(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                        case "A*" ->
                            this.solAlgo = new AStar(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                    }
                }
                this.MazeId = -1;
                this.resolve();
            }
        }

    }

    public void resolve() {
        if (!this.solAlgo.isFinished()) {
            this.solvingKey = new KeyFrame(Duration.millis(this.solperiod), event -> {
                if (!this.solAlgo.isFinished()) {
                    this.solAlgo.update(this.grid.getCanvas().getGraphicsContext2D());

                } else {
                    this.timeline.stop();
                }
            });
            timeline.getKeyFrames().setAll(this.solvingKey);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public boolean SolAlgoChanged() {
        if (this.solalgoselected == null) {
            return false;
        }
        String sel = this.solselect.getSelectionModel().getSelectedItem().toString();
        if (sel.equals(solalgoselected)) {
            return false;
        }

        this.resetMazeSol();
        return true;
    }

    public void SolStep() {
        if (this.genAlgo != null && this.genAlgo.isFinished() || mazecharged) {
            this.SolAlgoChanged();
            if (this.solAlgo == null) {
                this.startSolving = true;
                this.solalgoselected = this.solselect.getSelectionModel().getSelectedItem().toString();
                switch (solalgoselected) {
                    case "Dijkstra" ->
                        this.solAlgo = new Dijkstra(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                    case "Depth First Search" ->
                        this.solAlgo = new DepthFirstSearch(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                    case "Breadth First Search" ->
                        this.solAlgo = new BreadthFirstSearch(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                    case "A*" ->
                        this.solAlgo = new AStar(this.grid.getCells()[0][0], this.grid.getCells()[this.rows - 1][this.columns - 1], this.grid.getCells(), this.grid.getCanvas().getGraphicsContext2D());
                }
            }
            if (!this.solAlgo.isFinished()) {
                if (this.timeline.getStatus() == Animation.Status.RUNNING) {
                    this.timeline.stop();
                }
                this.MazeId = -1;
                this.solAlgo.update(this.grid.getCanvas().getGraphicsContext2D());

                this.hasStepedSol = true;
            }
        }

    }

    public void SolVitesse() {
        double value = this.solslider.getValue();
        double actualvalue = 1;
        if (value < 100) {
            actualvalue = 100.0 - value;
        }
        this.solperiod = actualvalue * (1000 / 60);
        if (this.solAlgo != null && !this.solAlgo.isFinished() && !this.hasStepedSol) {
            this.solvingKey = new KeyFrame(Duration.millis(this.solperiod), event -> {
                if (!this.solAlgo.isFinished()) {
                    this.solAlgo.update(this.grid.getCanvas().getGraphicsContext2D());
                } else {
                    this.timeline.stop();
                }
            });
            timeline.stop();
            timeline.getKeyFrames().setAll(this.solvingKey);
            timeline.play();
        }
    }

    public void SolHelp() {
        String algo = this.solselect.getSelectionModel().getSelectedItem().toString();
        switch (algo) {
            case "Dijkstra" ->
                this.Help(algo, SolvingAlgorithm.DIJKSTRA);
            case "Depth First Search" ->
                this.Help(algo, SolvingAlgorithm.DFS);
            case "Breadth First Search" ->
                this.Help(algo, SolvingAlgorithm.BFS);
            case "A*" ->
                this.Help(algo, SolvingAlgorithm.ASTAR);
        }
    }

    //Save and exportation section
    public void ExportImage() {
        WritableImage wi = new WritableImage((int) this.grid.getCanvas().getWidth(), (int) this.grid.getCanvas().getHeight());
        WritableImage snapshot = this.grid.getCanvas().snapshot(new SnapshotParameters(), wi);
        FileChooser fileChooser = new FileChooser();
        if (this.lastFileChooserPath == null) {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Pictures"));
        } else {
            fileChooser.setInitialDirectory(new File(this.lastFileChooserPath));
        }
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File output = fileChooser.showSaveDialog((Stage) this.splitPane.getScene().getWindow());
        try {
            if (output != null) {
                this.lastFileChooserPath = output.getParent();
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
            }
        } catch (IOException E) {
        }
    }

    public void Save() {
        if (this.grid.isAffected() && this.genAlgo != null && this.genAlgo.isFinished() && !mazecharged || mazecharged && this.solAlgo != null && this.solAlgo.isFinished()) {
            String defaultName;
            if (!this.MazeList.isEmpty()) {
                defaultName = "Labyrinthe " + (this.MazeList.get(this.MazeList.size() - 1).getID() + 1);
            } else {
                defaultName = "Labyrinthe 1";
            }
            TextInputDialog inputdialog = new TextInputDialog(defaultName);
            inputdialog.setContentText("Nom: ");
            inputdialog.setHeaderText("Entrer le nom du labyrinthe");
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
                        if (this.solalgoselected == null) {
                            this.solalgoselected = "Non résolu";
                        }
                        MazeDAO.SaveMaze(name, this.genalgoselected, this.solalgoselected, rows, columns, json);
                        LoadMazes();
                    });
                }).start();

            }

        } else {
            Alert ms1 = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            ms1.setTitle("Message");
            ms1.setHeaderText("Impossible d'enregistrer un labyrinthe");
            ms1.show();
        }

    }
    //Maze table 

    public void MazeSettings() {
        Stage popupwindow = new Stage();
        BorderPane Bp = new BorderPane();
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        Button b1 = new Button("Supprimer");
        Button b2 = new Button("Annuler");
        TableView tv = this.MazeTable();
        tv.setPadding(new Insets(10, 10, 10, 10));
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Labyrinthes");
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
                ms1.setHeaderText("SÉLECTIONNER UN LABYRINTHE");
                ms1.setContentText("Veuillez sélectionner un labyrinthe !");
                ms1.showAndWait();
            }
        });
        hb.getChildren().addAll(b1, b2);
        hb.setSpacing(5);
        Bp.setCenter(tv);
        Bp.setBottom(hb);
        hb.setPrefHeight(46);
        Scene scene1 = new Scene(Bp, 860, 400);
        popupwindow.setResizable(false);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
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
        name.setPrefWidth(147.60);
        name.setResizable(false);
        name.setStyle("-fx-alignment: CENTER;");
        TableColumn<MazeModel, String> algo = new TableColumn<>("Génération Algo");
        algo.setCellValueFactory(new PropertyValueFactory<>("Algo"));
        algo.setPrefWidth(140);
        algo.setResizable(false);
        algo.setStyle("-fx-alignment: CENTER;");
        TableColumn<MazeModel, String> solalgo = new TableColumn<>("Résolution Algo");
        solalgo.setCellValueFactory(new PropertyValueFactory<>("solAlgo"));
        solalgo.setPrefWidth(140);
        solalgo.setResizable(false);
        solalgo.setStyle("-fx-alignment: CENTER;");
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
        Date.setPrefWidth(172);
        Date.setResizable(false);
        Date.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(Id);
        tableView.getColumns().add(name);
        tableView.getColumns().add(algo);
        tableView.getColumns().add(solalgo);
        tableView.getColumns().add(rowscol);
        tableView.getColumns().add(columnscol);
        tableView.getColumns().add(Date);
        tableView.setItems(this.MazeList);
        return tableView;
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

    //Reset section
    public void ResetAlgorithms() {
        this.genAlgo = null;
        this.solAlgo = null;
        this.startSolving = false;
        this.hasStepedSol = false;
        this.hasStepedGen = false;
        this.mazecharged = false;
    }

    public void resetTimer() {
        if (this.timeline.getStatus() == Animation.Status.RUNNING) {
            this.timeline.stop();
        }
    }

    public void resetMazeSol() {
        if (this.solAlgo != null || mazecharged) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    this.grid.getCells()[i][j].setSelected(false);
                    this.grid.getCells()[i][j].setInpath(false);
                    if (this.grid.getCells()[i][j].isRootTarger()) {
                        this.grid.getCells()[i][j].setRootTarger(false);
                    }
                }
            }
            resetTimer();
            this.grid.draw(this.grid.getCanvas().getGraphicsContext2D());
            this.solAlgo = null;
            this.solalgoselected = null;
            this.startSolving = false;
        }
    }

    public void Help(String title, String info) {
        Stage popupwindow = new Stage();
        BorderPane Bp = new BorderPane();
        TextArea ta = new TextArea();
        ta.setText(info);
        ta.setWrapText(true);
        ta.setEditable(false);
        ta.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color:transparent;-fx-background-color:black");
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle(title);
        Bp.setPadding(new Insets(10, 10, 10, 10));
        Bp.setCenter(ta);
        Scene scene1 = new Scene(Bp, 500, 350);
        popupwindow.setResizable(false);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public MazeGrid getGrid() {
        return grid;
    }

}
