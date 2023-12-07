package menu.domain;

public class HateMenu extends Menu {
    private HateMenu(String menuName) {
        super(menuName);
    }

    public static HateMenu from(String menuName) {
        return new HateMenu(menuName);
    }
}
