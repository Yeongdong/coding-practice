package christmas.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderedMenus {
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final List<OrderedMenu> orderedMenus;
    private final int totalAmount;

    private OrderedMenus(List<OrderedMenu> orderedMenus, int totalAmount) {
        this.orderedMenus = orderedMenus;
        this.totalAmount = totalAmount;
    }

    public static OrderedMenus from(List<String> orderedMenuNames, List<Integer> amounts) {
        validateDuplicates(orderedMenuNames);
        validateTotalAmount(getTotalAmount(amounts));
        List<OrderedMenu> orderedMenus = createOrder(orderedMenuNames, amounts);
        return new OrderedMenus(orderedMenus, getTotalAmount(amounts));
    }

    private static List<OrderedMenu> createOrder(List<String> orderedMenuNames, List<Integer> amounts) {
        List<OrderedMenu> orderedMenus = new ArrayList<>();
        for (int i = 0; i < orderedMenuNames.size(); i++) {
            orderedMenus.add(OrderedMenu.from(orderedMenuNames.get(i), amounts.get(i)));
        }
        return orderedMenus;
    }

    private static int getTotalAmount(List<Integer> amounts) {
        return amounts.stream().mapToInt(Integer::intValue).sum();
    }

    private static void validateTotalAmount(int totalAmount) {
        if (totalAmount > 20) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateDuplicates(List<String> orderedMenuNames) {
        Set<String> menuNames = new HashSet<>(orderedMenuNames);
        if (menuNames.size() != orderedMenuNames.size()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    public List<OrderedMenu> getOrderedMenus() {
        return orderedMenus;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
