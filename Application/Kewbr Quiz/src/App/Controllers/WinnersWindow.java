package App.Controllers;

import App.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
        stage.setTitle("Game Over");
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

            if (nickname.equals("W"))
                continue;

            ImageView icon_left = new ImageView(new Image("Images/winner_winner.png"));
            icon_left.setFitHeight(32.0);
            icon_left.setFitWidth(32.0);
            icon_left.setSmooth(true);

            ImageView icon_right = new ImageView(new Image("Images/winner_winner.png"));
            icon_right.setFitHeight(32.0);
            icon_right.setFitWidth(32.0);
            icon_right.setSmooth(true);

            Label lblNickname = new Label(nickname);
            lblNickname.setFont(Font.font("Berlin Sans FB Regular", 18.0));
            lblNickname.setTextFill(Paint.valueOf("#e1e2e2"));

            HBox hBox = new HBox(10.0, icon_left, lblNickname, icon_right);
            hBox.setAlignment(Pos.CENTER);

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
