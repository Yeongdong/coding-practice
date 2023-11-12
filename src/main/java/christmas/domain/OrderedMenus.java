package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.*;

public class OrderedMenus {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final List<OrderedMenu> orderedMenus;

    private OrderedMenus(List<OrderedMenu> orderedMenus) {
        this.orderedMenus = orderedMenus;
    }

    public static OrderedMenus from(List<String> orderedMenuNames, List<Integer> amounts) {
        validateDuplicates(orderedMenuNames);
        validateTotalAmount(getTotalAmount(amounts));
        List<OrderedMenu> orderedMenus = createOrder(orderedMenuNames, amounts);
        return new OrderedMenus(orderedMenus);
    }

    public int getTotalPriceBeforeDiscount() {
        int totalPriceBeforeDiscount = 0;
        for (int i = 0; i < getOrderedMenus().size(); i++) {
            OrderedMenu orderedMenu = getOrderedMenus().get(i);
            totalPriceBeforeDiscount += orderedMenu.getMenuName().getPrice() * orderedMenu.getAmount();
        }
        return totalPriceBeforeDiscount;
    }

    public static boolean canGetBenefit(OrderedMenus orderedMenus) {
        return orderedMenus.getTotalPriceBeforeDiscount() >= PromotionRules.MINIMUN_PRICE.getValue();
    }

    public static boolean hasOnlyBeverage(OrderedMenus orderedMenus) {
        return orderedMenus.getOrderedMenus().stream()
                .allMatch(orderedMenu -> orderedMenuBelongsToCategory(orderedMenu, MenuCategory.BEVERAGE));
    }

    public List<OrderedMenu> getOrderedMenus() {
        return orderedMenus;
    }

    private static List<OrderedMenu> createOrder(List<String> orderedMenuNames, List<Integer> amounts) {
        List<OrderedMenu> orderedMenus = new ArrayList<>();
        for (int i = 0; i < orderedMenuNames.size(); i++) {
            orderedMenus.add(OrderedMenu.from(orderedMenuNames.get(i), amounts.get(i)));
        }
        return orderedMenus;
    }

    private static int getTotalAmount(List<Integer> amounts) {
        return amounts.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static void validateTotalAmount(int totalAmount) {
        if (totalAmount > PromotionRules.MAXIMUM_AMOUNT.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateDuplicates(List<String> orderedMenuNames) {
        Set<String> menuNames = new HashSet<>(orderedMenuNames);
        if (menuNames.size() != orderedMenuNames.size()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    public static boolean orderedMenuBelongsToCategory(OrderedMenu orderedMenu, MenuCategory category) {
        String menuName = orderedMenu.getMenuName().getName();
        return Arrays.stream(MenuCategory.values())
                .anyMatch(menuCategory -> menuCategory.getMenus().stream()
                        .anyMatch(menu -> menu.getName().equals(menuName) && menuCategory == category));
    }
}
