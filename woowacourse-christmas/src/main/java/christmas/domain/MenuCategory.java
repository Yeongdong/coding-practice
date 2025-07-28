package christmas.domain;

import java.util.Arrays;
import java.util.List;

public enum MenuCategory {
    APPETIZER(Arrays.asList(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CAESAR_SALAD)),
    MAIN(Arrays.asList(Menu.T_BONE_STEAK, Menu.BBQ_RIB, Menu.SEAFOOD_PASTA, Menu.CHRISTMAS_PASTA)),
    DESSERT(Arrays.asList(Menu.CHOCOLATE_CAKE, Menu.ICECREAM)),
    BEVERAGE(Arrays.asList(Menu.COKE_ZERO, Menu.RED_WINE, Menu.CHAMPAIGN));

    private final List<Menu> menus;

    MenuCategory(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }
}
