package Controller;

import Model.Question;
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
    private TableColumn<Question, Integer> scoreColumn;

    @FXML
    private TableColumn<Question, Integer> timeColumn;

    @FXML
    private TableColumn<Question, String> answerColumn;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableView<Question> questionsTable;

    @FXML
    private Button createPackageButton;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private TextField packageNameField;

    @FXML
    void initialize() {
        setDifficultyChoiceBox(getDifficultys());
        ObservableList<Question> question = FXCollections.observableArrayList(
                new Question("Lol", "Kek", 100, 15),
                new Question("Kek", "Cheburek", 100, 15),
                new Question("dmitry", "savra", 500, 60)
        );

        questionColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("answer"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<Question, Integer>("score"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Question, Integer>("time"));

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
