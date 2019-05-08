package App.Controllers;

import App.Models.Client;
import App.Models.Server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Класс контроллера игровой сцены
 * @author NikiTer
 */
public class GameScene implements Initializable {

    private @FXML
    BorderPane btdPaneClient;
    private @FXML
    BorderPane btdPaneHost;

    private @FXML
    Button btnExitClient;
    private @FXML
    Button btnExitHost;
    private @FXML
    Button btnConfirm;
    private @FXML
    Button btnStart;
    private @FXML
    Button btnStop;

    private @FXML
    ImageView imgvAvatar;
    private @FXML
    ImageView imgvResult;

    private @FXML
    Label lblNickname;
    private @FXML
    Label lblTimerClient;
    private @FXML
    Label lblTimerHost;
    private @FXML
    Label lblIP;
    private @FXML
    Label lblPort;

    private @FXML
    VBox vboxChatClient;
    private @FXML
    VBox vboxChatHost;

    private @FXML
    TextField txtFieldChatClient;
    private @FXML
    TextField txtFieldChatHost;

    private @FXML
    ScrollPane scrollPaneChatHost;
    private @FXML
    ScrollPane scrollPaneChatClient;


    private ControllersManager manager;

    private Server server;
    private Client client;
    private boolean isServer;

    private String nickname;

    /**
     * Конструктор для хоста
     * @param nickname -- никнейм игрока
     * @param manager -- ссылка на центральный контроллер
     * @see Server#run()
     */
    GameScene(String nickname, ControllersManager manager) {
        this.nickname = nickname;
        this.manager = manager;
        server = new Server(8000, this);
        isServer = true;
        server.run();
    }

    /**
     * Конструктор для клиента
     * @param nickname -- никнейм игрока
     * @param host -- ip адрес хоста
     * @param port -- номер порта хоста
     * @param manager -- ссылка на центральный контроллер
     * @see Client#run()
     */
    GameScene(String nickname, String host, int port, ControllersManager manager) {
        this.nickname = nickname;
        this.manager = manager;
        client = new Client(port, host, this);
        isServer = false;
        client.run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNickname.setText(nickname);

        //Если это сервер, выставляем отображение ip адреса и номера порта
        if (isServer) {
            try {
                lblIP.setText(InetAddress.getLocalHost().toString().split("/")[1]);
                lblPort.setText("8000");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        /*
         * Установка события, которе инициирует отправку сообщения
         * при нажатии на Enter
         */
        if (isServer) {
            txtFieldChatHost.setOnKeyReleased(new EventHandler<KeyEvent>() {
                /**
                 * @see Server#write(String)
                 * @see Client#write(String)
                 */
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() != KeyCode.ENTER)
                        return;
                    if (!txtFieldChatHost.getText().isEmpty() && txtFieldChatHost.getText().length() <= 20) {
                        server.write(txtFieldChatHost.getText());
                        txtFieldChatHost.clear();
                    }
                }
            });
        } else {
            txtFieldChatClient.setOnKeyReleased(new EventHandler<KeyEvent>() {
                /**
                 * @see Server#write(String)
                 * @see Client#write(String)
                 */
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() != KeyCode.ENTER)
                        return;
                    if (!txtFieldChatClient.getText().isEmpty() && txtFieldChatClient.getText().length() <= 20) {
                        client.write(txtFieldChatClient.getText());
                        txtFieldChatClient.clear();
                    }
                }
            });
        }

        //Выход из игры в главное меню
        if (isServer) {
            btnExitHost.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * @see Server#shutdown()
                 * @see ControllersManager#backToMenu()
                 */
                @Override
                public void handle(ActionEvent event) {
                    server.shutdown();
                    manager.backToMenu();
                }
            });
        } else {
            btnExitClient.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * @see Client#shutdown()
                 * @see ControllersManager#backToMenu()
                 */
                @Override
                public void handle(ActionEvent event) {
                    client.shutdown();
                    manager.backToMenu();
                }
            });
        }
    }

    /**
     * Метод для печати сообщения на экране
     * @param message -- сообщение, которое нужно напечатать
     */
    public void print(String message) {
        if (isServer) {
            vboxChatHost.getChildren().add(new Label(message));
            scrollPaneChatHost.setVvalue(0.0);
        } else {
            vboxChatClient.getChildren().add(new Label(message));
            scrollPaneChatClient.setVvalue(0.0);
        }
    }

    /**
     * Метод, возвращающий никнейм игрока
     * @return -- никнейм игрока
     */
    public String getNickname() {
        return lblNickname.getText();
    }
}
