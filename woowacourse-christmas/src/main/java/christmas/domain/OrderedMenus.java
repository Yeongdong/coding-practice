package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.*;

public class OrderedMenus {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String MENU_AMOUNT_SPLITTER = "-";
    private static final String ORDER_SPLITTER = ",";
    private final List<OrderedMenu> orderedMenus;

    private OrderedMenus(List<OrderedMenu> orderedMenus) {
        this.orderedMenus = orderedMenus;
    }

    public static OrderedMenus from(String orderInputs) {
        List<OrderedMenu> orderedMenus = createOrder(toList(orderInputs));
        validateOrder(orderedMenus);
        return new OrderedMenus(orderedMenus);
    }

    private static List<OrderedMenu> createOrder(List<String> orderInputs) {
        try {
            List<OrderedMenu> menuList = new ArrayList<>();
            for (String order : orderInputs) {
                String[] menus = order.split(MENU_AMOUNT_SPLITTER);
                validateInputForm(menus);
                String menuName = menus[PromotionRules.MENU_ORDER.getValue()];
                int orderCount = toInt(menus[PromotionRules.COUNT_ORDER.getValue()]);
                menuList.add(OrderedMenu.from(menuName, orderCount));
            }
            return menuList;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    public static boolean isInMenuCategory(Menu orderedMenu, MenuCategory category) {
        String menuName = orderedMenu.getName();
        return Arrays.stream(MenuCategory.values())
                .anyMatch(menuCategory -> menuCategory.getMenus().stream()
                        .anyMatch(menu -> menu.getName().equals(menuName) && menuCategory == category));
    }

    public List<OrderedMenu> getOrderedMenus() {
        return orderedMenus;
    }

    private static void validateOrder(List<OrderedMenu> orderedMenuList) {
        validateDuplicates(orderedMenuList);
        validateTotalOrderCount(orderedMenuList);
        validateMenusNotOnlyBeverage(orderedMenuList, MenuCategory.BEVERAGE);
    }

    private static void validateTotalOrderCount(List<OrderedMenu> orderedMenuList) {
        int totalOrderCount = orderedMenuList.stream()
                .mapToInt(OrderedMenu::getOrderCount)
                .sum();
        if (totalOrderCount > PromotionRules.MAXIMUM_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateDuplicates(List<OrderedMenu> orderedMenuList) {
        Set<String> uniqueMenuNames = new HashSet<>();
        boolean hasDuplicates = orderedMenuList.stream()
                .map(OrderedMenu::getMenuName)
                .anyMatch(menuName -> !uniqueMenuNames.add(menuName));

        if (hasDuplicates) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateMenusNotOnlyBeverage(List<OrderedMenu> orderedMenuList, MenuCategory menuCategory) {
        if (orderedMenuList.stream()
                .allMatch(menuName -> isInMenuCategory(Menu.findByName(menuName.getMenuName()), menuCategory))) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static List<String> toList(String menuAndOrderCount) {
        return Arrays.stream(menuAndOrderCount.split(ORDER_SPLITTER))
                .toList();
    }

    private static void validateInputForm(String[] menus) {
        if (menus.length != PromotionRules.VALID_MENU_LENGTH.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }
}
