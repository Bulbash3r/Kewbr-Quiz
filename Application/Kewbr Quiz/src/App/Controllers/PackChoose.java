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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PackChoose implements Initializable {

    private @FXML
    Button btnOk;
    private @FXML
    Button btnChoose;

    private @FXML
    TextField txtFieldPath;

    private ControllersManager manager;
    private Stage stage;
    private File dir = null;

    private Gson GSON = new Gson();

    public PackChoose(ControllersManager manager) {
        this.manager = manager;

        stage = new Stage();
        stage.setTitle("Pack Chooser");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(manager.getMainMenu().getStage().getScene().getWindow());
        stage.getIcons().add(new Image("Images/icon.png"));
        stage.setResizable(false);

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/PackChoose.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File("Directory.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                dir = new File(scanner.nextLine());
                scanner.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnChoose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                choosePath();
            }
        });

        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                choosePack();
            }
        });
    }

    private void choosePack() {

        try {
            Scanner scanner = new Scanner(new File(txtFieldPath.getText()));
            manager.createGame(GSON.fromJson(scanner.nextLine(), Pack.class));
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        stage.close();
    }

    private void choosePath() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your pack");

        if (dir != null) {
            if (dir.exists())
                fileChooser.setInitialDirectory(dir);
        }

        /*
        Добавление фильтров, которые позволят выбирать
        только .kwq файлы
         */
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("KWQ", "*.kwq")
        );

            File pack = fileChooser.showOpenDialog(stage);

            if (pack != null)
                txtFieldPath.setText(pack.getAbsolutePath());
    }
}
