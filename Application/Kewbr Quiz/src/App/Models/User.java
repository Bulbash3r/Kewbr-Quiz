package App.Models;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class User {

    private HBox hBox;
    private HBox hBoxButtons;
    private ImageView imgvAvatar;
    private Label lblNickname;
    private Label lblAnswer;
    private Button btnRight;
    private Button btnWrong;

    public User(Image avatar, String nickname) {
        imgvAvatar = new ImageView(avatar);
        imgvAvatar.setSmooth(true);
        imgvAvatar.prefWidth(70);
        imgvAvatar.prefHeight(70);

        lblNickname = new Label(nickname);
        lblAnswer = new Label();

        btnRight = new Button("V");
        btnRight.prefHeight(25);
        btnRight.prefWidth(25);
        btnWrong = new Button("X");
        btnWrong.prefHeight(25);
        btnWrong.prefWidth(25);

        hBoxButtons = new HBox(0);
        hBoxButtons.getChildren().addAll(btnWrong, btnRight);
        hBoxButtons.setAlignment(Pos.CENTER);

        hBox = new HBox(10);
        hBox.getChildren().addAll(imgvAvatar, lblNickname, hBoxButtons, lblAnswer);
        hBoxButtons.setAlignment(Pos.CENTER_LEFT);
    }
}
