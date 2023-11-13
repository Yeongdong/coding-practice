package christmas.domain;

import christmas.utils.PromotionRules;

public class OrderedMenu {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final Menu menu;
    private final int orderCount;

    private OrderedMenu(Menu menuName, int orderCount) {
        this.menu = menuName;
        this.orderCount = orderCount;
    }

    public static OrderedMenu from(Menu menuName, int orderCount) {
        validateOrderCount(orderCount);
        return new OrderedMenu(menuName, orderCount);
    }

    private static void validateOrderCount(int orderCount) {
        if (orderCount < PromotionRules.MINIMUM_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    public int getPrice() {
        return getMenu().getPrice() * getOrderCount();
    }

    public String getMenuName() {
        return getMenu().getName();
    }

    public int getOrderCount() {
        return orderCount;
    }

    public Menu getMenu() {
        return menu;
    }
}
