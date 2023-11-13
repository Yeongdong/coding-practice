package christmas.domain;

import christmas.utils.PromotionRules;

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

    public int getTotalPriceBeforeDiscount() {
        return getOrderedMenusList().stream()
                .mapToInt(orderedMenu -> orderedMenu.getPrice() * orderedMenu.getOrderCount())
                .sum();
    }

    public String getBadgeStatus(Benefits benefits) {
        this.badge = Badge.from(benefits.getTotalDiscount());
        return badge.getBadgeName();
    }

    public boolean isParticipateInPromotion() {
        return canGetBenefit() && !getOrderedMenus().hasOnlyBeverage();
    }

    public List<String> getEventPeriod() {
        return getEstimateVisitDate().eventPeriod();
    }

    public int getChristmasDiscountPrice() {
        return getEstimateVisitDate().calculateAccumulateDiscountPrice();
    }

    public List<OrderedMenu> getOrderedMenusList() {
        return getOrderedMenus().getOrderedMenus();
    }

    public int getVisitDate() {
        return getEstimateVisitDate().getVisitDate();
    }

    private boolean canGetBenefit() {
        return getTotalPriceBeforeDiscount() >= PromotionRules.MINIMUM_PRICE.getValue();
    }

    private OrderedMenus getOrderedMenus() {
        return orderedMenus;
    }

    private EventDate getEstimateVisitDate() {
        return estimateVisitDate;
    }
}
