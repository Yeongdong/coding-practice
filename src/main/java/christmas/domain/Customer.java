package christmas.domain;

public class Customer {
    private final EventDate estimateVisitDate;
    private final OrderedMenus orderedMenus;
    private Badge badge;

    private Customer(EventDate visitDate, OrderedMenus orderedMenus) {
        this.estimateVisitDate = visitDate;
        this.orderedMenus = orderedMenus;
    }

    public static Customer from(EventDate visitDate, OrderedMenus orderedMenus) {
        return new Customer(visitDate, orderedMenus);
    }

    public EventDate getEstimateVisitDate() {
        return estimateVisitDate;
    }

    public OrderedMenus getOrderedMenus() {
        return orderedMenus;
    }

    public String getBadgeStatus(Benefits benefits) {
        this.badge = Badge.from(benefits.getTotalDiscount());
        return badge.getBadgeName();
    }
}