package App.Controllers;

import App.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAvatar implements Initializable {

    private @FXML Button btnOK;

    private @FXML ImageView imgvAvatar;
    private @FXML TextField txtfieldNickname;
    private @FXML Label lblTooLarge;

    private ControllersManager manager;
    private Stage stage;

    public UserAvatar(ControllersManager manager){
        this.manager = manager;

        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/UserAvatar.fxml"));
            loader.setController(this);
            VBox rootPane = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(rootPane));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        stage.show();
        System.out.println("Is works!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtfieldNickname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 20) {
                    if (!lblTooLarge.isVisible()) {
                        lblTooLarge.setVisible(true);
                        btnOK.setDisable(true);
                    }
                } else if (newValue.length() == 0) {
                    btnOK.setDisable(true);
                } else if (lblTooLarge.isVisible() || btnOK.isDisable()) {
                    lblTooLarge.setVisible(false);
                    btnOK.setDisable(false);
                }
            }
        });

        imgvAvatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                avatarChoose();
            }
        });

        txtfieldNickname.setText(manager.getMainMenu().getNickname());
        imgvAvatar.setImage(manager.getMainMenu().getAvatarPic());
        imgvAvatar.setSmooth(true);

        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.getMainMenu().setNickname(txtfieldNickname.getText());
                manager.getMainMenu().setAvatarPic(imgvAvatar.getImage());
                stage.close();
            }
        });
    }

    private void avatarChoose() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your avatar picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File pic = fileChooser.showOpenDialog(stage);
        imgvAvatar.setImage(new Image("file:" + pic.getPath()));
    }
}
