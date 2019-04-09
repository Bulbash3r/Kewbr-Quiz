package App.Controllers;

import App.Main;
import App.Models.Question;
import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TabPaneSkin;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionWindow implements Initializable {

    private PackEditor packEditor;
    private Stage stage;
    private int index;
    private Question question = null;


    @FXML
    private Button btnOk;
    @FXML
    private TextArea txtAreaQuestion;
    @FXML
    private TextField txtFieldAnswer;

    public QuestionWindow(PackEditor packEditor, int index) {
        this.packEditor = packEditor;
        this.index = index;

        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(packEditor.getStage().getScene().getWindow());

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/QuestionWindow.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        stage.show();
    }

    public QuestionWindow(PackEditor packEditor, Question question, int index) {
        this.packEditor = packEditor;
        this.question = question;
        this.index = index;

        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(packEditor.getStage().getScene().getWindow());

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/QuestionWindow.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (question != null) {
            txtAreaQuestion.setText(question.getQuestion());
            txtFieldAnswer.setText(question.getAnswer());
        }

        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnQuestion();
            }
        });

        txtAreaQuestion.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.TAB) {
                    TextAreaSkin skin = (TextAreaSkin) txtAreaQuestion.getSkin();
                    if (skin.getBehavior()  instanceof TextAreaBehavior) {
                        TextAreaBehavior behavior = skin.getBehavior();
                        if (event.isControlDown()) {
                            behavior.callAction("InsertTab");
                        } else {
                            behavior.callAction("TraverseNext");
                        }
                        event.consume();
                    }

                }
            }
        });
    }

    private void returnQuestion() {
        if (txtAreaQuestion.getText().isEmpty() || txtFieldAnswer.getText().isEmpty())
            return;
        if (question == null)
            question = new Question(txtAreaQuestion.getText(), txtFieldAnswer.getText());
        else {
            question.setQuestion(txtAreaQuestion.getText());
            question.setAnswer(txtFieldAnswer.getText());
        }
        packEditor.insertQuestion(question, index);
        stage.close();
    }
}
