package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    
    @FXML
    private Button addQuestionButton;

    @FXML
    private Button deleteQuestionButton;

    @FXML
    private Button changeQuestionButton;

    @FXML
    private Button openPackageButton;

    @FXML
    private Button savePackageButton;

    @FXML
    private TableView<?> questionsTable;

    @FXML
    private Button createPackageButton;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private TextField packageNameField;

    private ObservableList<?> table = FXCollections.observableArrayList("1 - физ-ра", "2 - лекции Титовой", "3 - лабы по ЦОСиИ", "4 - курсач по СиФО", "5 - зачёт Глеца");


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

    public void openPackageButtonMethod() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл пакета");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы пакетов", ".kwq"));
        File file = fileChooser.showOpenDialog(new Stage());
        setAllEnable();
    }

    public void setAllEnable() {
        packageNameField.setDisable(false);
        difficultyChoiceBox.setDisable(false);
        questionsTable.setDisable(false);
    }

    public void createPackageButtonMethod() {
        setAllEnable();
    }

    public void savePackageButtonMethod() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить пакет");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы пакетов", ".kwq"));
        File file = fileChooser.showSaveDialog(new Stage());
    }

}
