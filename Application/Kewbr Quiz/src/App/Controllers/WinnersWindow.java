package App.Controllers;

import App.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WinnersWindow implements Initializable {

    private @FXML
    Button btnOk;
    private @FXML
    VBox vBoxWinners;

    private ControllersManager manager;

    private Stage stage;
    private String[] winners;

    public WinnersWindow(String[] winners, ControllersManager manager) {

        this.winners = winners;
        this.manager = manager;

        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(manager.getMainMenu().getStage().getScene().getWindow());
        stage.getIcons().add(new Image("Images/icon.png"));
        stage.setResizable(false);

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/WinnersWindow.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (String nickname : winners) {

            ImageView icon = new ImageView(new Image("Images/winner_winner.png"));
            icon.setFitHeight(32.0);
            icon.setFitWidth(32.0);
            icon.setSmooth(true);

            Label lblNickname = new Label(nickname);
            lblNickname.setFont(Font.font("Berlin Sans FB Regular", 18.0));
            lblNickname.setTextFill(Paint.valueOf("#e1e2e2"));

            HBox hBox = new HBox(10.0, icon, lblNickname, icon);

            vBoxWinners.getChildren().add(hBox);
        }

        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                manager.backToMenu();

                stage.close();
            }
        });
    }
}
