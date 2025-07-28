package menu.domain;

public class SelectMenu extends Menu {
    private SelectMenu(String menuName) {
        super(menuName);
    }

    public static SelectMenu from(String menuName) {
        return new SelectMenu(menuName);
    }
}
