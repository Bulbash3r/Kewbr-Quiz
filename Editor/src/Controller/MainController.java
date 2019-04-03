package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Package, String> answerColumn;

    @FXML
    private TableColumn<Package, String> questionColumn;

    @FXML
    private TableView<Package> questionsTable;

    @FXML
    private Button createPackageButton;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private TextField packageNameField;

    @FXML
    void initialize() {
        setDifficultyChoiceBox(getDifficultys());
        ObservableList<Package> question = FXCollections.observableArrayList();

        questionColumn.setCellValueFactory(new PropertyValueFactory<Package, String>("questions"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<Package, String>("answers"));

        questionsTable.setItems(question);
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
