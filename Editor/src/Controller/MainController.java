package Controller;

import Model.JsonConverter;
import Model.Package;
import Model.Question;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainController {

    private Package quizPackage;

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

                ObservableList<Question> observableList = FXCollections.observableArrayList(question);
                questionColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questions"));
                answerColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("answers"));
                questionsTable.setItems(observableList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createPackageButtonMethod() {
        setAllEnable();
    }

    public void setAllEnable() {
        packageNameField.setDisable(false);
        difficultyChoiceBox.setDisable(false);
        questionsTable.setDisable(false);
    }

    public void savePackageButtonMethod() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        quizPackage.setDate(dateFormat.format(new Date()));
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
                writer.print(gson.toJson(quizPackage));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
