package menu.domain;

public abstract class Menu {
    protected final String menuName;

    protected Menu(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }
}
