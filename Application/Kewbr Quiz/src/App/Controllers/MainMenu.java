package App.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    private @FXML Button btnJoinGame;
    private @FXML Button btnCreateGame;
    private @FXML Button btnSettings;
    private @FXML Button btnExit;

    private @FXML HBox hboxUser;
    private @FXML ImageView imgvAvatar;
    private @FXML Label lblNickname;

    private ControllersManager manager;

    public MainMenu(ControllersManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.close();
            }
        });

        hboxUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                manager.createUserAvaterMenu();
            }
        });
    }

    public String getNickname() {
        return lblNickname.getText();
    }

    public void setNickname(String nickname) {
        lblNickname.setText(nickname);
    }
}
