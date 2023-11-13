package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.*;
import java.util.stream.Collectors;

public class OrderedMenus {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final List<OrderedMenu> orderedMenus;

    private OrderedMenus(List<OrderedMenu> orderedMenus) {
        this.orderedMenus = orderedMenus;
    }

    public static OrderedMenus from(List<String> orderedMenuNames, List<Integer> orderCounts) {
        validateOrder(orderedMenuNames, orderCounts);
        List<OrderedMenu> orderedMenus = createOrder(orderedMenuNames, orderCounts);
        return new OrderedMenus(orderedMenus);
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

    private static List<OrderedMenu> createOrder(List<String> orderedMenuNames, List<Integer> orderCounts) {
        return orderedMenuNames.stream()
                .map(menuName -> OrderedMenu.from(Menu.findByName(menuName), orderCounts.get(orderedMenuNames.indexOf(menuName))))
                .collect(Collectors.toList());
    }

    private static void validateOrder(List<String> orderedMenuNames, List<Integer> orderCounts) {
        validateDuplicates(orderedMenuNames);
        validateTotalOrderCount(orderCounts);
        validateInMenu(orderedMenuNames);
        validateMenusNotOnlyBeverage(orderedMenuNames, MenuCategory.BEVERAGE);
    }

    private static void validateTotalOrderCount(List<Integer> orderCounts) {
        int totalOrderCount = orderCounts.stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (totalOrderCount > PromotionRules.MAXIMUM_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateDuplicates(List<String> orderedMenuNames) {
        Set<String> uniqueMenuNames = new HashSet<>(orderedMenuNames);
        if (uniqueMenuNames.size() != orderedMenuNames.size()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateInMenu(List<String> orderedMenuNames) {
        for (String menuName : orderedMenuNames) {
            if (Menu.findByName(menuName) == null) {
                throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
            }
        }
    }

    private static void validateMenusNotOnlyBeverage(List<String> orderedMenuNames, MenuCategory menuCategory) {
        if (orderedMenuNames.stream()
                .allMatch(menuName -> isInMenuCategory(Menu.findByName(menuName), menuCategory))) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }
}
