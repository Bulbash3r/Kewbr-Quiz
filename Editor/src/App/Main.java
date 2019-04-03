package App;

import App.Controller.Manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Manager manager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        manager = new Manager(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/main.fxml"));
        loader.setController(manager.getMainController());
        primaryStage.setTitle("Kewbr Quiz Editor");
        primaryStage.setScene(new Scene(loader.load(), 600, 400));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
