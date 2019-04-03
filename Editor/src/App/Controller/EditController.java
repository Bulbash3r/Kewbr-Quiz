package App.Controller;

import App.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML
    private TextArea questionField;

    @FXML
    private TextField answerField;

    @FXML
    private Button confirmButton;

    private Stage stage;
    private Manager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add();
                close();
            }
        });
    }

    public EditController(Manager manager) {
        this.manager = manager;
        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/edit.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    private void add() {
        manager.setQuestion(questionField.getText(), answerField.getText());
    }

    private void close() {
        stage.close();
    }
}
