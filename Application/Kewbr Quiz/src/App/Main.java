package App;

import App.Controllers.ControllersManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Главный класс Main
 * @author NikiTer
 */
public class Main extends Application {

    private Stage prStage;
    private ControllersManager manager;

    @Override
    public void start(Stage prStage) throws Exception {
        this.prStage = prStage;
        prStage.setTitle("KEWBR Quiz");

        manager = new ControllersManager(this);

        //Ставим свой контроллер и загружаем fxml разметку на сцену
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/MainMenu.fxml"));
            loader.setController(manager.getMainMenu());
            AnchorPane rootPane = loader.load();
            prStage.setScene(new Scene(rootPane));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        prStage.show();
    }

    public Stage getPrStage() {
        return prStage;
    }

    /**
     * Метод, использующийся для закрытия приложения
     */
    public final void close() {
        prStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
