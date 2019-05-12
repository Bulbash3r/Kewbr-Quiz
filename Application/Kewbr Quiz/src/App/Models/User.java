package App.Models;

import App.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class User {

    private HBox hBox;
    private HBox hBoxButtons;
    private ImageView imgvAvatar;
    private Label lblNickname;
    private Label lblAnswer;
    private Label lblScore;
    private Button btnRight;
    private Button btnWrong;

    private int score = 0;

    public User(Image avatar, String nickname) {

        imgvAvatar = new ImageView(avatar);
        imgvAvatar.setSmooth(true);
        imgvAvatar.setFitWidth(70);
        imgvAvatar.setFitHeight(70);

        lblNickname = new Label(nickname);
        lblNickname.setFont(Font.font("System Bold"));
        lblNickname.setTextFill(Paint.valueOf("#e1e2e2"));

        lblAnswer = new Label();
        lblAnswer.setFont(Font.font("System Bold"));
        lblAnswer.setTextFill(Paint.valueOf("#e1e2e2"));

        lblScore = new Label();
        lblScore.setFont(Font.font("System Bold"));
        lblScore.setTextFill(Paint.valueOf("#e1e2e2"));

        btnRight = new Button("V");
        btnRight.prefHeight(25);
        btnRight.prefWidth(25);
        btnRight.setStyle("-fx-background-color: #FB8122; -fx-background-radius: 0 0 0 0;");
        btnRight.setFont(Font.font("System Bold"));
        btnRight.setTextFill(Paint.valueOf("#e1e2e2"));

        btnWrong = new Button("X");
        btnWrong.prefHeight(25);
        btnWrong.prefWidth(25);
        btnWrong.setStyle("-fx-background-color: #FB8122; -fx-background-radius: 0 0 0 0;");
        btnWrong.setFont(Font.font("System Bold"));
        btnWrong.setTextFill(Paint.valueOf("#e1e2e2"));

        hBoxButtons = new HBox(5);
        hBoxButtons.getChildren().addAll(btnWrong, btnRight);
        hBoxButtons.setAlignment(Pos.CENTER);

        hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(imgvAvatar, lblNickname, hBoxButtons, lblAnswer);
        hBoxButtons.setAlignment(Pos.CENTER_LEFT);
    }

    public HBox gethBox() {
        return hBox;
    }

    public void setAnswer(String answer) {
        lblAnswer.setText(answer);
    }

    public Button getBtnRight() {
        return btnRight;
    }

    public Button getBtnWrong() {
        return btnWrong;
    }

    public String getNickname() {
        return lblNickname.getText();
    }

    public void increaseScore() {
        score++;
        lblScore.setText("" + score);
    }
}