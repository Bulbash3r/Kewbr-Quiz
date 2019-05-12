package App.Controllers;

import App.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс контроллера главного меню
 * @author NikiTer
 */
public class MainMenu implements Initializable {

    private @FXML Button btnJoinGame;
    private @FXML Button btnCreateGame;
    private @FXML Button btnSettings;
    private @FXML Button btnExit;
    private @FXML Button btnPackEditor;

    private @FXML HBox hboxUser;
    private @FXML ImageView imgvAvatar;
    private @FXML Label lblNickname;

    private ControllersManager manager;

    private Stage stage = null;

    /**
     * Конструктор
     * @param manager -- ссылка на центральный контроллер
     */
    MainMenu(ControllersManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        Установка обработчика событий на кнопку выхода,
        который инициирует закрытие приложения при нажатии на кнопку
         */
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @see ControllersManager#close()
             */
            @Override
            public void handle(ActionEvent event) {
                manager.close();
            }
        });

        btnCreateGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.createPackChooseWindow();
            }
        });

        btnJoinGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.createConnectWindow();
            }
        });

        /*
        Установка обработчика событий на худ пользователя,
        который инициирует вызов меню выбора аватара и ника
         */
        hboxUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * @see ControllersManager#createUserAvatarMenu()
             */
            @Override
            public void handle(MouseEvent event) {
                manager.createUserAvatarMenu();
            }
        });

        btnPackEditor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.createPackEditor();
            }
        });

        imgvAvatar.setImage(new Image("Images/User.png"));
        imgvAvatar.setSmooth(true);
    }

    /**
     * Метод получения текущего никнейма пользователя
     * @return возвращает никнейм
     */
    public String getNickname() {
        return lblNickname.getText();
    }

    /**
     * Метод установки нового никнейма пользователя
     * @param nickname -- новый никнейм
     */
    public void setNickname(String nickname) {
        lblNickname.setText(nickname);
    }

    /**
     * Метод получения изображения из текущего аватара пользователя
     * @return возвращает изображение
     */
    public Image getAvatarPic() {
        return imgvAvatar.getImage();
    }

    /**
     * Метод установки нового изображения аватара пользователя
     * @param image -- новое изображение
     */
    public void setAvatarPic(Image image) {
        imgvAvatar.setImage(image);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}