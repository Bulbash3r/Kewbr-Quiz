package App.Controllers;

import App.Main;
import App.Models.Client;
import App.Models.Pack;
import App.Models.Server;
import App.Models.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Класс контроллера игровой сцены
 * @author NikiTer
 */
public class GameScene implements Initializable {

    private @FXML
    BorderPane brdPaneClient;
    private @FXML
    BorderPane brdPaneHost;

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
    Label lblAnswer;
    private @FXML
    Label lblScore;

    private @FXML
    VBox vboxChatClient;
    private @FXML
    VBox vboxChatHost;
    private @FXML
    VBox vboxQuestionsClient;
    private @FXML
    VBox vboxQuestionsHost;
    private @FXML
    VBox vboxUsers;
    private @FXML
    VBox vboxUsersAndQuestions;

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
    private boolean isPaused = false;

    private String nickname;
    private int questionsCounter = 0;
    private int timeForAnswer = 60;
    private int currentTime;
    private Timeline timeline;
    private int score = 0;

    private Pack currPack = null;
    private Map<String, User> users;

    /**
     * Конструктор для хоста
     * @param nickname -- никнейм игрока
     * @param manager -- ссылка на центральный контроллер
     * @see Server#run()
     */
    GameScene(String nickname, Pack pack, ControllersManager manager) {
        this.nickname = nickname;
        this.manager = manager;
        this.currPack = pack;
        users = new HashMap<>();
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
     * @see Client#run(String)
     */
    GameScene(String nickname, String host, int port, ControllersManager manager) {
        this.nickname = nickname;
        this.manager = manager;
        users = new HashMap<>();
        client = new Client(port, host, this);
        isServer = false;
        client.run(nickname);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNickname.setText(nickname);

        if (isServer)
            serverInit();
        else
            clientInit();
    }

    private void serverInit() {
        brdPaneHost.setVisible(true);
        brdPaneClient.setVisible(false);

        //Выставляем отображение ip адреса и номера порта
        try {
            lblIP.setText(InetAddress.getLocalHost().toString().split("/")[1]);
            lblPort.setText("8000");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /*
         * Установка события, которе инициирует отправку сообщения
         * при нажатии на Enter
         */
        txtFieldChatHost.setOnKeyReleased(new EventHandler<KeyEvent>() {
            /**
             * @see Server#writeMessage(String)
             */
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER)
                    return;
                if (!txtFieldChatHost.getText().isEmpty() && txtFieldChatHost.getText().length() <= 20) {
                    server.writeMessage(txtFieldChatHost.getText());
                    txtFieldChatHost.clear();
                }
            }
        });

        //Выход из игры в главное меню
        btnExitHost.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @see Server#shutdown()
             * @see ControllersManager#backToMenu()
             */
            @Override
            public void handle(ActionEvent event) {
                if (!isPaused && timeline != null)
                    stopTime();
                server.shutdown();
                manager.backToMenu();
            }
        });

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isPaused) {
                    server.writeHost("Start");
                    continueTime();
                } else if (timeline == null) {
                    server.writeHost("Start");
                    doTime();
                }
            }
        });

        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (timeline != null && !isPaused) {
                    server.writeHost("Stop");
                    stopTime();
                }
            }
        });
    }

    private void clientInit() {
        brdPaneHost.setVisible(false);
        brdPaneClient.setVisible(true);

        /*
         * Установка события, которе инициирует отправку сообщения
         * при нажатии на Enter
         */
        txtFieldChatClient.setOnKeyReleased(new EventHandler<KeyEvent>() {
            /**
             * @see Client#writeMessage(String)
             */
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER)
                    return;
                if (!txtFieldChatClient.getText().isEmpty() && txtFieldChatClient.getText().length() <= 20) {
                    client.writeMessage(txtFieldChatClient.getText());
                    txtFieldChatClient.clear();
                }
            }
        });

        //Выход из игры в главное меню
        btnExitClient.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @see Client#shutdown()
             * @see ControllersManager#backToMenu()
             */
            @Override
            public void handle(ActionEvent event) {
                client.outMessage(lblNickname.getText());
                client.shutdown();
                manager.backToMenu();
            }
        });
    }

    /**
     * Метод для печати сообщения на экране
     * @param message -- сообщение, которое нужно напечатать
     */
    public void print(String message) {
        if (isServer) {
            vboxChatHost.getChildren().add(new Label(message));
            scrollPaneChatHost.setVvalue(1.0);
        } else {
            vboxChatClient.getChildren().add(new Label(message));
            scrollPaneChatClient.setVvalue(1.0);
        }
    }

    public void doTime() {
        currentTime = timeForAnswer;

        if (isServer)
            lblTimerHost.setText(String.valueOf(currentTime));
        else
            lblTimerClient.setText(String.valueOf(currentTime));

        if (timeline == null) {
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);

            timeline.stop();

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    currentTime--;

                    if (isServer)
                        lblTimerHost.setText(String.valueOf(currentTime));
                    else
                        lblTimerClient.setText(String.valueOf(currentTime));

                    if (currentTime <= 0)
                        timeline.stop();
                }
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.playFromStart();
    }

    public void stopTime() {
        isPaused = true;
        timeline.stop();
    }

    public void continueTime() {
        isPaused = false;
        timeline.play();
    }

    public void addUser(String nickname) {

        User user;
        if (isServer)
            user = new User(new Image(Main.class.getResourceAsStream("../Images/User.png")), nickname);
        else
            user = new User(new Image(Main.class.getResourceAsStream("../Images/User.png")), nickname);

        user.getBtnRight().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                user.getBtnRight().setStyle("-fx-background-color: #0ce752; -fx-background-radius: 0 0 0 0;");
                user.getBtnWrong().setStyle("-fx-background-color: #FB8122; -fx-background-radius: 0 0 0 0;");
                server.writeHost("Right", user.getNickname());
            }
        });

        user.getBtnWrong().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                user.getBtnWrong().setStyle("-fx-background-color: #ee3d3d; -fx-background-radius: 0 0 0 0;");
                user.getBtnRight().setStyle("-fx-background-color: #FB8122; -fx-background-radius: 0 0 0 0;");
                server.writeHost("Wrong", user.getNickname());
            }
        });

        users.put(nickname, user);

        if (isServer) {
            vboxUsers.getChildren().add(user.gethBox());
        } else {
            vboxUsersAndQuestions.getChildren().add(user.gethBox());
        }
    }

    public void removeUser(String nickname) {

        if (isServer) {
            vboxUsers.getChildren().remove(users.remove(nickname).gethBox());
        } else {
            vboxUsersAndQuestions.getChildren().remove(users.remove(nickname).gethBox());
        }
    }

    public Set<Map.Entry<String, User>> getUsersSet() {
        return users.entrySet();
    }

    public void addQuestion(String question) {
        questionsCounter++;

        vboxQuestionsClient.getChildren().add(new Label(questionsCounter + ": " + question));
    }

    public void addQuestion(String question, String answer) {
        questionsCounter++;

        vboxQuestionsHost.getChildren().add(new Label(questionsCounter + ": " + question));
        lblAnswer.setText(answer);
    }

    public void checkAnswer(String nickname, String answer) {

    }

    public void increaseScore() {
        score++;
        lblScore.setText("" + score);
    }

    /**
     * Метод, возвращающий никнейм игрока
     * @return -- никнейм игрока
     */
    public String getNickname() {
        return lblNickname.getText();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
