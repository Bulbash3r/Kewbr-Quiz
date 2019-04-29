package App.Controller;

import App.Model.JsonConverter;
import App.Model.Package;
import App.Model.Question;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Package quizPackage;

    @FXML
    private Button openPackageButton;

    @FXML
    private Button savePackageButton;

    @FXML
    private Button createPackageButton;

    @FXML
    private Button addQuestionButton;

    @FXML
    private Button deleteQuestionButton;

    @FXML
    private Button changeQuestionButton;

    @FXML
    private TableColumn<Question, String> answerColumn;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableView<Question> questionsTable;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private TextField packageNameField;

    private Manager manager;

    MainController(Manager manager) {
        this.manager = manager;
    }

    private ObservableList<String> getDifficultys() {
        ObservableList<String> difficultys = FXCollections.observableArrayList("1 - физ-ра", "2 - лекции Титовой", "3 - лабы по ЦОСиИ", "4 - курсач по СиФО", "5 - зачёт Глеца");
        return difficultys;
    }

    private void setDifficultyChoiceBox(ObservableList<String> list) {
        difficultyChoiceBox.setItems (list);
    }

    private void openPackageButtonMethod() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл пакета");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы пакетов", "*.kwq"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {

                InputStream is = new FileInputStream(file);
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));

                String line = buf.readLine();
                StringBuilder sb = new StringBuilder();

                while(line != null){
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }

                String json = sb.toString();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(json);
                quizPackage = new JsonConverter().deserialize(jsonElement,null, null);
                setAllEnable();

                packageNameField.setText(quizPackage.getPackageName());
                difficultyChoiceBox.getSelectionModel().select(quizPackage.getDifficulty()-1);

                ArrayList<Question> question = new ArrayList<Question>();
                for (int i = 0; i < quizPackage.getQuestions().size(); i++) {
                    question.add(new Question(quizPackage.getQuestions().get(i), quizPackage.getAnswers().get(i)));
                }
                questionsTable.getItems().clear();
                questionsTable.setItems(FXCollections.observableArrayList(question));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createPackageButtonMethod() {
        setAllEnable();
        difficultyChoiceBox.getSelectionModel().select(0);
        questionsTable.setItems(FXCollections.observableArrayList());
        quizPackage = new Package();
    }

    private void setAllEnable() {
        packageNameField.setDisable(false);
        difficultyChoiceBox.setDisable(false);
        questionsTable.setDisable(false);
        addQuestionButton.setDisable(false);
        deleteQuestionButton.setDisable(false);
        changeQuestionButton.setDisable(false);
    }

    private void setAllDisable() {
        packageNameField.setText("");
        difficultyChoiceBox.getSelectionModel().select(0);
        packageNameField.setDisable(true);
        difficultyChoiceBox.setDisable(true);
        questionsTable.setDisable(true);
        addQuestionButton.setDisable(true);
        deleteQuestionButton.setDisable(true);
        changeQuestionButton.setDisable(true);
        questionsTable.setItems(FXCollections.observableArrayList());
    }

    private void savePackageButtonMethod() {
        quizPackage.setDate(new Date());
        quizPackage.setDifficulty(difficultyChoiceBox.getSelectionModel().getSelectedIndex()+1);
        quizPackage.setPackageName(packageNameField.getText());
        List<Question> arrayList = questionsTable.getItems();

        ArrayList<String> questions = new ArrayList<String>();
        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0; i < arrayList.size(); i++) {
            questions.add(arrayList.get(i).getQuestions());
            answers.add(arrayList.get(i).getAnswers());
        }

        quizPackage.setQuestions(questions);
        quizPackage.setAnswers(answers);

        Gson gson = new Gson();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить пакет");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы пакетов", "*.kwq"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {

            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.print(gson.toJson(quizPackage).replace("9,","9"));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setAllDisable();
        }
    }

    public void addQuestion(String question, String answer) {
        Question question1 = new Question(question, answer);
        questionsTable.getItems().add(question1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDifficultyChoiceBox(getDifficultys());

        changeQuestionButton.setVisible(false);
        addQuestionButton.setDisable(true);
        deleteQuestionButton.setDisable(true);
        changeQuestionButton.setDisable(true);

        //observableList = questionsTable.getItems();
        questionColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questions"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("answers"));
        //questionsTable.setItems (observableList);

        addQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.createEditWindow();
            }
        });

        createPackageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createPackageButtonMethod();
            }
        });

        openPackageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openPackageButtonMethod();
            }
        });

        savePackageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                savePackageButtonMethod();
            }
        });

        deleteQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Question selectedItem = questionsTable.getSelectionModel().getSelectedItem();
                questionsTable.getItems().remove(selectedItem);
            }
        });
    }
}
