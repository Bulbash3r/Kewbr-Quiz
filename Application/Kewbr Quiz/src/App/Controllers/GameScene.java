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
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class GameScene implements Initializable {

    private @FXML Button btnSend;
    private @FXML Button btnExit;
    private @FXML Label lblNickname;
    private @FXML Label lblUsers;
    private @FXML ScrollPane scrollPane;
    private @FXML VBox vboxChat;
    private @FXML TextField txtfieldInput;
    private @FXML VBox vboxServerInfo;
    private @FXML Label lblIP;
    private @FXML Label lblPort;

    private ControllersManager manager;

    private Server server;
    private Client client;
    private boolean isServer;

    private String nickname;

    GameScene(String nickname, ControllersManager manager) {
        this.nickname = nickname;
        this.manager = manager;
        server = new Server(8000, this);
        isServer = true;
        server.run();
    }

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

        if (isServer) {
            try {
                lblIP.setText(InetAddress.getLocalHost().toString().split("/")[1]);
                lblPort.setText("8000");
                vboxServerInfo.setVisible(true);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        txtfieldInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER)
                    return;
                if (!txtfieldInput.getText().isEmpty() && txtfieldInput.getText().length() <= 20) {
                    if (isServer)
                        server.write(txtfieldInput.getText());
                    else
                        client.write(txtfieldInput.getText());
                    txtfieldInput.clear();
                }
            }
        });

        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!txtfieldInput.getText().isEmpty() && txtfieldInput.getText().length() <= 20) {
                    if (isServer)
                        server.write(txtfieldInput.getText());
                    else
                        client.write(txtfieldInput.getText());
                    txtfieldInput.clear();
                }
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isServer)
                    server.shutdown();
                else
                    client.shutdown();
                manager.backToMenu();
            }
        });
    }

    public void print(String message) {
        vboxChat.getChildren().add(new Label(message));
        scrollPane.setVvalue(1.0);
    }

    public String getNickname() {
        return lblNickname.getText();
    }
}
