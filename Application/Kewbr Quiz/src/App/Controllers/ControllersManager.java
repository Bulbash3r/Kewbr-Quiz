package App.Controllers;

import App.Main;

/**
 * Класс центрального контроллера
 * @author NikiTer
 */
public class ControllersManager {

    private MainMenu mainMenu = null;
    private UserAvatar userAvatar = null;
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

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * Метод вызова меню выбора аватара и ника
     * @see UserAvatar#UserAvatar(ControllersManager)
     */
    public void createUserAvaterMenu() {
        userAvatar = new UserAvatar(this);
    }
}
