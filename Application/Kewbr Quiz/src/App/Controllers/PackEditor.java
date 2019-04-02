package App.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PackEditor implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgvDots;
    @FXML
    private HBox hboxAdd;
    @FXML
    private TableView tbvPacks;

    private ControllersManager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public PackEditor(ControllersManager manager) {
        this.manager = manager;
    }
}
