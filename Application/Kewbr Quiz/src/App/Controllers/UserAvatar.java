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

/**
 * Класс контроллера меню выбора аватара и ника
 * @author NikiTer
 */
public class UserAvatar implements Initializable {

    private @FXML Button btnOK;

    private @FXML ImageView imgvAvatar;
    private @FXML TextField txtfieldNickname;
    private @FXML Label lblTooLarge;

    private ControllersManager manager;
    private Stage stage;

    /**
     * Конструктор
     * @param manager -- ссылка на центральный контроллер
     */
    public UserAvatar(ControllersManager manager){
        this.manager = manager;
        stage = new Stage();

        //Ставим сами себя в качестве контроллера и загружаем fxml разметку на сцену
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        Установка слушателя на поле для ввода никнейма,
        который будет запрещать вводить никнейм длинее 20 символов
        или пустой
         */
        txtfieldNickname.textProperty().addListener(new ChangeListener<String>() {
            /**
             * Выключает кнопку "OK" при пустом поле, или если
             * никнейм длинее 20 символов. Также, при 20 символах
             * показывает предупреждение "Too large"
             * @param newValue -- текущее значение поля (никнейм)
             */
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

        /*
        Установка обработчика событий на аватар, который,
        при нажатии на аватар, будет вызывать проводник
        для выбора нового аватара
         */
        imgvAvatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * @see UserAvatar#avatarChoose()
             */
            @Override
            public void handle(MouseEvent event) {
                avatarChoose();
            }
        });

        //Чтобы при запуске окна, там уже были текущий ник и аватар
        txtfieldNickname.setText(manager.getMainMenu().getNickname());
        imgvAvatar.setImage(manager.getMainMenu().getAvatarPic());
        imgvAvatar.setSmooth(true);

        /*
        Установка обработчика событий на кнопку "OK", который,
        при нажатии на кнопку, будет закрывать приложение и
        менять старые ник и аватар на новые
         */
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @see ControllersManager#getMainMenu()
             * @see MainMenu#setAvatarPic(Image)
             * @see MainMenu#setNickname(String)
             */
            @Override
            public void handle(ActionEvent event) {
                manager.getMainMenu().setNickname(txtfieldNickname.getText());
                manager.getMainMenu().setAvatarPic(imgvAvatar.getImage());
                stage.close();
            }
        });
    }

    /**
     * Метод вызова проводника для выбора нового аватара
     */
    private void avatarChoose() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your avatar picture");

        /*
        Добавление фильтров, которые позволят выбирать
        только .png и .jpg файлы
         */
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File pic = fileChooser.showOpenDialog(stage);
        imgvAvatar.setImage(new Image("file:" + pic.getPath()));
    }
}
