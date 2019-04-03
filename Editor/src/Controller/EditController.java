package Controller;

import Model.Question;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class EditController {

    @FXML
    private TextArea questionField;

    @FXML
    private TextField answerField;

    @FXML
    private Button confirmButton;

    private Question question;

    public void setQuestion(Question question) {
        this.question = question;
        questionField.setText(question.getQuestions());
        answerField.setText(question.getAnswers());
    }

    public void actionClose (ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave (ActionEvent actionEvent) {
        question.setQuestions(questionField.getText());
        question.setAnswers(answerField.getText());
        actionClose(actionEvent);
    }
}
