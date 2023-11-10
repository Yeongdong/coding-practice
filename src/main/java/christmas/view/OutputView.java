package christmas.view;

import christmas.domain.OrderedMenu;
import christmas.domain.OrderedMenus;

public class OutputView {
    public static void printOrderedMenus(OrderedMenus orderedMenus) {
        System.out.println("<주문메뉴>");
        for (int i = 0; i < orderedMenus.getOrderedMenus().size(); i++) {
            OrderedMenu orderedMenu = orderedMenus.getOrderedMenus().get(i);
            System.out.println(orderedMenu.getMenuName() + " " + orderedMenu.getAmount() + "개");
        }
    }
}
