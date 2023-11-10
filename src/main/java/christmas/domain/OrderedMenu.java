package christmas.domain;

import christmas.utils.Menu;

public class OrderedMenu {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final Menu menuName;
    private final int amount;

    private OrderedMenu(Menu menuName, int amount) {
        this.menuName = menuName;
        this.amount = amount;
    }

    public static OrderedMenu from(String menuName, int amount) {
        validateInMenu(menuName);
        validateAmount(amount);
        return new OrderedMenu(Menu.valueOf(menuName), amount);
    }

    private static void validateInMenu(String menuName) {
        try {
            Menu.valueOf(menuName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateAmount(int amounts) {
        if (amounts < 1) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    public Menu getMenuName() {
        return menuName;
    }

    public int getAmount() {
        return amount;
    }
}
