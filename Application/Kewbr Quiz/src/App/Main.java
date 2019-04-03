package App;

import App.Controllers.ControllersManager;
import App.Models.Pack;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
            manager.getMainMenu().setStage(prStage);
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
        Pack pack = new Pack("Pack #1", 3, new Date());
        pack.addNewQuestion("Who?", "Me");
        pack.addNewQuestion("What?", "That");
        pack.addNewQuestion("Why?", "because");
        Gson gson = new Gson();
        String temp = gson.toJson(pack);
        try {
            FileWriter fileWriter = new FileWriter(new File("../Test.kwq"), false);
            fileWriter.write(temp);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
