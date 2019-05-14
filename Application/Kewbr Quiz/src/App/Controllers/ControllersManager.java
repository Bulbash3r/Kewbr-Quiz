package App.Controllers;

import App.Main;
import App.Models.Pack;
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
    private PackEditor packEditor = null;
    private GameScene gameScene = null;
    private PackChoose packChoose = null;
    private WinnersWindow winnersWindow = null;

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

    /**
     * Метод вызова окна для ввода данных для подключения к игре
     * @see ConnectWindow#ConnectWindow(ControllersManager)
     */
    void createConnectWindow() {
        connectWindow = new ConnectWindow(this);
    }

    /**
     * Метод вызова редактора пакетов
     * @see PackEditor#PackEditor(ControllersManager)
     */
    void createPackEditor() {
        packEditor = new PackEditor(this);
    }

    /**
     * Метод, вызывающийся для возврата в главное меню
     */
    void backToMenu() {

        gameScene = null;
        winnersWindow = null;
        main.getPrStage().setScene(mainMenuScene);
    }

    /**
     * Метод подключения к готовой игре
     * @param host -- ip адрес хоста
     * @param port -- номер порта хоста
     * @see GameScene#GameScene(String, String, int, ControllersManager)
     */
    void joinGame(final String host, final int port) {
        //Сохраняем сцену главного меню, чтобы потом не создавать её снова
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

    public void createPackChooseWindow() {
        packChoose = new PackChoose(this);
    }

    /**
     * Метод создания комнаты для игры
     * @see GameScene#GameScene(String, Pack, ControllersManager)
     */
    public void createGame(Pack pack) {
        //Сохраняем сцену главного меню, чтобы потом не создавать её снова
        if (mainMenuScene == null)
            mainMenuScene = main.getPrStage().getScene();
        gameScene = new GameScene(mainMenu.getNickname(), pack, this);

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/GameScene.fxml"));
            loader.setController(gameScene);
            main.getPrStage().setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createWinnersWindow(String[] winners) {
        winnersWindow = new WinnersWindow(winners, this);
    }
}
