package App.Controllers;

import App.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Класс центрального контроллера
 * @author NikiTer
 */
public class ControllersManager {

    private MainMenu mainMenu = null;
    private UserAvatar userAvatar = null;
    private ConnectWindow connectWindow = null;
    private GameScene gameScene = null;
    private Scene mainMenuScene = null;
    private Main main;

    /**
     * Конструктор
     * @param main -- ссылка на main
     */
    public ControllersManager(Main main) {
        this.main = main;
    }

    /**
     * Метод, использующийся для закрытия приложения
     * @see Main#close()
     */
    public final void close() {
        main.close();
    }

    /**
     * Метод получения контроллера главного меню {@link ControllersManager#mainMenu}
     * @return возвращает текущий контроллер главного меню
     */
    public MainMenu getMainMenu() {

        //Если контроллера нет, он создается
        if (mainMenu == null)
            mainMenu = new MainMenu(this);
        return mainMenu;
    }

    /**
     * Метод вызова меню выбора аватара и ника
     * @see UserAvatar#UserAvatar(ControllersManager)
     */
    void createUserAvatarMenu() {
        userAvatar = new UserAvatar(this);
    }

    void createConnectWindow() {
        connectWindow = new ConnectWindow(this);
    }

    void backToMenu() {
        main.getPrStage().setScene(mainMenuScene);
    }

    void joinGame(final String host, final int port) {
        if (mainMenuScene == null)
            mainMenuScene = main.getPrStage().getScene();
        gameScene = new GameScene(mainMenu.getNickname(), host, port, this);

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/GameScene.fxml"));
            loader.setController(gameScene);
            main.getPrStage().setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createGame() {
        mainMenuScene = main.getPrStage().getScene();
        gameScene = new GameScene(mainMenu.getNickname(), this);

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/GameScene.fxml"));
            loader.setController(gameScene);
            main.getPrStage().setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
