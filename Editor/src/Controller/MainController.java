package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private Button openPackageButton;

    @FXML
    private Button savePackageButton;

    @FXML
    private Button createPackageButton;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private TextField packageNameField;

    @FXML
    void initialize() {
        assert openPackageButton != null : "fx:id=\"openPackageButton\" was not injected: check your FXML file 'main.fxml'.";
        assert savePackageButton != null : "fx:id=\"savePackageButton\" was not injected: check your FXML file 'main.fxml'.";
        assert createPackageButton != null : "fx:id=\"createPackageButton\" was not injected: check your FXML file 'main.fxml'.";
        assert difficultyChoiceBox != null : "fx:id=\"difficultyChoiceBox\" was not injected: check your FXML file 'main.fxml'.";
        assert packageNameField != null : "fx:id=\"packageNameField\" was not injected: check your FXML file 'main.fxml'.";
        setDifficultyChoiceBox(getDifficultys());
    }

    public ObservableList<String> getDifficultys() {
        ObservableList<String> difficultys = FXCollections.observableArrayList("1 - физ-ра", "2 - лекции Титовой", "3 - лабы по ЦОСиИ", "4 - курсач по СиФО", "5 - зачёт Глеца");
        return difficultys;
    }

    public void setDifficultyChoiceBox(ObservableList<String> list) {
        difficultyChoiceBox.setItems (list);
    }
}
