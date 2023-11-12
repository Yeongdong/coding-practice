package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.*;

public class OrderedMenus {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final List<OrderedMenu> orderedMenus;

    private OrderedMenus(List<OrderedMenu> orderedMenus) {
        this.orderedMenus = orderedMenus;
    }

    public static OrderedMenus from(List<Menu> orderedMenuNames, List<Integer> orderCounts) {
        validateDuplicates(orderedMenuNames);
        validateTotalOrderCount(getTotalOrderCount(orderCounts));
        List<OrderedMenu> orderedMenus = createOrder(orderedMenuNames, orderCounts);
        return new OrderedMenus(orderedMenus);
    }

    public List<OrderedMenu> getOrderedMenus() {
        return orderedMenus;
    }

    private static List<OrderedMenu> createOrder(List<Menu> orderedMenuNames, List<Integer> orderCounts) {
        List<OrderedMenu> orderedMenus = new ArrayList<>();
        for (int i = 0; i < orderedMenuNames.size(); i++) {
            orderedMenus.add(OrderedMenu.from(orderedMenuNames.get(i), orderCounts.get(i)));
        }
        return orderedMenus;
    }

    private static int getTotalOrderCount(List<Integer> orderCounts) {
        return orderCounts.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static void validateTotalOrderCount(int totalOrderCount) {
        if (totalOrderCount > PromotionRules.MAXIMUM_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateDuplicates(List<Menu> orderedMenuNames) {
        Set<Menu> menuNames = new HashSet<>(orderedMenuNames);
        if (menuNames.size() != orderedMenuNames.size()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }
}
