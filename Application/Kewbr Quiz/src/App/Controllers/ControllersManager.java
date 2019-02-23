package App.Controllers;

import App.Main;

public class ControllersManager {

    private MainMenu mainMenu = null;
    private UserAvatar userAvatar = null;
    private Main main;

    public ControllersManager(Main main) {
        this.main = main;
    }

    public final void close() {
        main.close();
    }

    public MainMenu getMainMenu() {
        if (mainMenu == null)
            mainMenu = new MainMenu(this);
        return mainMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void createUserAvaterMenu() {
        userAvatar = new UserAvatar(this);
    }
}
