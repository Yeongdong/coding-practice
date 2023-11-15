package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.*;

public class OrderedMenus {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String MENU_AMOUNT_SPLITTER = "-";
    private final List<OrderedMenu> orderedMenus;

    private OrderedMenus(List<OrderedMenu> orderedMenus) {
        this.orderedMenus = orderedMenus;
    }

    public static OrderedMenus from(List<String> orderInputs) {
        List<OrderedMenu> orderedMenus = createOrder(orderInputs);
        validateOrder(orderedMenus);
        return new OrderedMenus(orderedMenus);
    }

    private static List<OrderedMenu> createOrder(List<String> orderInputs) {
        try {
            List<OrderedMenu> menuList = new ArrayList<>();
            for (String order : orderInputs) {
                String[] menus = order.split(MENU_AMOUNT_SPLITTER);
                String menuName = menus[PromotionRules.MENU_ORDER.getValue()];
                int orderCount = Integer.parseInt(menus[PromotionRules.COUNT_ORDER.getValue()]);
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
//        validateInputForm(orderedMenuList);
        validateDuplicates(orderedMenuList);
        validateTotalOrderCount(orderedMenuList);
//        validateInMenu(orderedMenuList);
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

    private static void validateMenusNotOnlyBeverage(List<OrderedMenu> orderedMenuList, MenuCategory menuCategory) {
        if (orderedMenuList.stream()
                .allMatch(menuName -> isInMenuCategory(Menu.findByName(menuName.getMenuName()), menuCategory))) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }
}
