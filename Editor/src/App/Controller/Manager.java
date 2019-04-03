package App.Controller;

import App.Main;

public class Manager {

    private MainController mainController = null;
    private EditController editController = null;
    private Main main;

    public Manager(Main main) {
        this.main = main;
    }

    public MainController getMainController() {
        if (mainController == null)
            mainController = new MainController(this);
        return mainController;
    }

    public void createEditWindow() {
        editController = new EditController(this);
    }

    public void setQuestion(String question, String answer) {
        mainController.addQuestion(question, answer);
    }
}
