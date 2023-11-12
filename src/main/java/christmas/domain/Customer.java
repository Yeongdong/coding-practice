package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.Arrays;
import java.util.List;

public class Customer {
    private final EventDate estimateVisitDate;
    private final OrderedMenus orderedMenus;
    private Badge badge;

    private Customer(EventDate visitDate, OrderedMenus orderedMenus) {
        this.estimateVisitDate = visitDate;
        this.orderedMenus = orderedMenus;
    }

    public static Customer reserve(EventDate visitDate, OrderedMenus orderedMenus) {
        return new Customer(visitDate, orderedMenus);
    }

    public EventDate getEstimateVisitDate() {
        return estimateVisitDate;
    }

    public int getVisitDate() {
        return estimateVisitDate.getVisitDate();
    }

    public List<OrderedMenu> getOrderedMenusList() {
        return orderedMenus.getOrderedMenus();
    }

    public String getBadgeStatus(Benefits benefits) {
        this.badge = Badge.from(benefits.getTotalDiscount());
        return badge.getBadgeName();
    }

    public int getTotalPriceBeforeDiscount() {
        return getOrderedMenusList().stream()
                .mapToInt(orderedMenu -> orderedMenu.getPrice() * orderedMenu.getOrderCount())
                .sum();
    }

    public boolean canGetBenefit() {
        return getTotalPriceBeforeDiscount() >= PromotionRules.MINIMUM_PRICE.getValue();
    }

    public boolean hasOnlyBeverage() {
        return orderedMenus.getOrderedMenus().stream()
                .allMatch(orderedMenu -> orderedMenuBelongsToCategory(orderedMenu, MenuCategory.BEVERAGE));
    }

    public boolean orderedMenuBelongsToCategory(OrderedMenu orderedMenu, MenuCategory category) {
        String menuName = orderedMenu.getMenu().getName();
        return Arrays.stream(MenuCategory.values())
                .anyMatch(menuCategory -> menuCategory.getMenus().stream()
                        .anyMatch(menu -> menu.getName().equals(menuName) && menuCategory == category));
    }
}