package App.Controllers;

import App.Main;
import App.Models.Pack;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PackEditor implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgvDots;
    @FXML
    private HBox hboxAdd;
    @FXML
    private TableView<Pack> tbvPacks;

    private ControllersManager manager;

    private Stage stage;
    private File dir = null;
    private static final Gson GSON = new Gson();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Pack, String> nameColumn = new TableColumn<Pack, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Pack, String>("name"));


        TableColumn<Pack, Integer> difficultyColumn = new TableColumn<Pack, Integer>("Difficulty");
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<Pack, Integer>("difficulty"));

        TableColumn<Pack, Date> dateColumn = new TableColumn<Pack, Date>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Pack, Date>("date"));

        tbvPacks.getColumns().addAll(nameColumn, difficultyColumn, dateColumn);

        File file = new File("../Directory.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                dir = new File(scanner.nextLine());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            update();
        }
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                savePath();
                stage.close();
            }
        });

        hboxAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openEditor();
            }
        });

        imgvDots.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                choose();
            }
        });
    }

    public PackEditor(ControllersManager manager) {
        this.manager = manager;
        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/PackEditor.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        stage.show();
    }

    private void choose() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Choose path to the folder with your packs");
        if (dir != null)
            dirChooser.setInitialDirectory(dir);
        dir = dirChooser.showDialog(stage);
        update();
    }

    private void savePath() {
        if (dir == null)
            return;
        try {
            FileWriter fileWriter = new FileWriter(new File("../Directory.txt"), false);
            fileWriter.write(dir.getAbsolutePath());
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void update() {
        if (dir.listFiles(new Pack.Filter()) != null)
            if (dir.listFiles(new Pack.Filter()).length != 0) {
                tbvPacks.getItems().clear();
                try {
                    for (File file : dir.listFiles(new Pack.Filter()))
                        tbvPacks.getItems().add(GSON.fromJson(new Scanner(file).useDelimiter("\\A").next(), Pack.class));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    private void openEditor() {

    }

}