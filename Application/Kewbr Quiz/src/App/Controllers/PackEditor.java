package App.Controllers;

import App.Main;
import App.Models.Pack;
import App.Models.Question;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class PackEditor implements Initializable {
    @FXML
    private Button btnBackLauncher;
    @FXML
    private ImageView imgvDots;
    @FXML
    private HBox hboxAdd;
    @FXML
    private TableView<Pack> tbvPacks;
    @FXML
    private VBox vboxLauncher;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBackEditor;
    @FXML
    private ComboBox<String> cmbBoxDifficulty;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TableView<Question> tbvQuestions;
    @FXML
    private VBox vboxEditor;
    @FXML
    private Label lblWriteName;

    private ControllersManager manager;

    private Stage stage;
    private File dir = null;
    private Pack curPack = null;
    private Gson GSON = new Gson();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        launcherInit();
        editorInit();
    }

    private void launcherInit() {
        TableColumn<Pack, String> nameColumn = new TableColumn<Pack, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Pack, String>("name"));


        TableColumn<Pack, Integer> difficultyColumn = new TableColumn<Pack, Integer>("Difficulty");
            difficultyColumn.setCellValueFactory(new PropertyValueFactory<Pack, Integer>("difficulty"));

        TableColumn<Pack, Date> dateColumn = new TableColumn<Pack, Date>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Pack, Date>("date"));

        tbvPacks.getColumns().addAll(nameColumn, difficultyColumn, dateColumn);
        tbvPacks.setRowFactory(new Callback<TableView<Pack>, TableRow<Pack>>() {
            @Override
            public TableRow<Pack> call(TableView<Pack> param) {
                TableRow<Pack> row = new TableRow<>();

                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2 && !row.isEmpty())
                            openEditor(row.getItem());
                    }
                });

                return row;
            }
        });

        tbvPacks.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DELETE && tbvPacks.getSelectionModel().getSelectedIndex() >= 0) {
                    File file = new File(dir.getPath() + "\\" + tbvPacks.getItems().get(tbvPacks.getSelectionModel().getSelectedIndex()).getName() + ".kwq");
                    if (file.exists()) {
                        try {
                            Files.delete(file.toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        update();
                    }
                }
            }
        });

        File file = new File("../Directory.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                dir = new File(scanner.nextLine());
                scanner.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            update();
        }
        btnBackLauncher.setOnAction(new EventHandler<ActionEvent>() {
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

    private void editorInit() {
        TableColumn<Question, String> questionColumn = new TableColumn<Question, String>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));


        TableColumn<Question, String> answerColumn = new TableColumn<Question, String>("Answer");
        answerColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("answer"));

        tbvQuestions.getColumns().addAll(questionColumn, answerColumn);

        cmbBoxDifficulty.getItems().addAll("1 - Novice", "2 - Apprentice", "3 - Adept", "4 - Expert", "5 - Master");
<<<<<<< HEAD
        cmbBoxDifficulty.setValue("1 -Novice");
=======
        cmbBoxDifficulty.setValue("1 - Novice");
>>>>>>> 294f87f085c3ba3d02e43d77f20d648e1f84493c

        btnBackEditor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openLauncher();
            }
        });

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertQuestionWindow(tbvQuestions.getItems().size());
            }
        });

        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertQuestionWindow(tbvQuestions.getSelectionModel().getSelectedIndex());
            }
        });

        btnRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeQuestion(tbvQuestions.getSelectionModel().getSelectedIndex());
            }
        });

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveQuestion();
            }
        });
    }

    PackEditor(ControllersManager manager) {
        this.manager = manager;

        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(manager.getMainMenu().getStage().getScene().getWindow());

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
        if (dir != null) {
            if (dir.exists())
                dirChooser.setInitialDirectory(dir);
        }

        File temp = dirChooser.showDialog(stage);

        if (temp != null) {
            dir = temp;
            update();
        }
    }

    private void savePath() {
        if (dir == null || !dir.exists())
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
        if (dir == null || !dir.exists())
            return;
        File[] files = dir.listFiles(new Pack.Filter());
        tbvPacks.getItems().clear();

        try {
            if (files != null && files.length != 0) {
                Scanner scanner;
                for (File file : files) {
                    scanner = new Scanner(file);
                    tbvPacks.getItems().add(GSON.fromJson(scanner.nextLine(), Pack.class));
                    scanner.close();
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void openEditor() {
        if (dir == null || !dir.exists())
            return;
        vboxEditor.setVisible(true);
        vboxLauncher.setVisible(false);
    }

    private void openEditor(Pack pack) {
        if (dir == null || !dir.exists())
            return;
        curPack = pack;
        txtFieldName.setText(pack.getName());
        tbvQuestions.getItems().addAll(pack.getQuestions());
        cmbBoxDifficulty.setValue(cmbBoxDifficulty.getItems().get(pack.getDifficulty() - 1));
        vboxEditor.setVisible(true);
        vboxLauncher.setVisible(false);
    }

    private void openLauncher() {
        txtFieldName.clear();
        tbvQuestions.getItems().clear();
        cmbBoxDifficulty.setValue(cmbBoxDifficulty.getItems().get(0));
        vboxEditor.setVisible(false);
        vboxLauncher.setVisible(true);
        update();
    }

    private void insertQuestionWindow(int index) {
        if (index >= 0) {
            QuestionWindow questionWindow;
            if (index == tbvQuestions.getItems().size())
                questionWindow = new QuestionWindow(this, index);
            else
                questionWindow = new QuestionWindow(this, tbvQuestions.getItems().get(index), index);
        }
    }

    private void removeQuestion(int index) {
        tbvQuestions.getItems().remove(index);
    }

    private void saveQuestion() {
        int difficulty = Character.getNumericValue(cmbBoxDifficulty.getValue().charAt(0));

        if (curPack == null)
            curPack = new Pack(txtFieldName.getText(), difficulty, new Date());
        else {
            File file = new File(dir.getPath() + "\\" + curPack.getName() + ".kwq");
            if (file.exists())
                if (file.delete())
                    curPack = new Pack(txtFieldName.getText(), difficulty, new Date());
        }

        curPack.setQuestions(tbvQuestions.getItems());

        try {
            FileWriter fileWriter = new FileWriter(new File(dir.getPath() + "\\" + curPack.getName() + ".kwq"), false);
            fileWriter.write(GSON.toJson(curPack));
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        curPack = null;
    }

    public void insertQuestion(Question question, int index) {
        if (index >= tbvQuestions.getItems().size())
            tbvQuestions.getItems().add(question);
        else
            tbvQuestions.getItems().set(index, question);

    }

    public Stage getStage() {
        return stage;
    }
}