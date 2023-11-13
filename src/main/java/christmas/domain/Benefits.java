package christmas.domain;

import christmas.utils.PromotionRules;
import christmas.utils.Promotions;

import java.util.*;

public class Benefits {
    private final Map<String, Integer> benefits;

    private Benefits(Map<String, Integer> benefits) {
        this.benefits = benefits;
    }

    public static Benefits from(Customer customer) {
        if (customer.isParticipateInPromotion()) {
            Map<String, Integer> benefits = checkEvent(customer);
            return new Benefits(benefits);
        }
        return new Benefits(Collections.emptyMap());
    }

    private static Map<String, Integer> checkEvent(Customer customer) {
        Map<String, Integer> benefits = new HashMap<>();
        List<String> eventPeriod = customer.getEventPeriod();
        for (String event : eventPeriod) {
            int discountAmount = getDiscountAmountForEvent(customer, event);
            benefits.put(event, discountAmount);
        }
        if (customer.getTotalPriceBeforeDiscount() >= PromotionRules.GIVEAWAY_CONDITION.getValue()) {
            benefits.put(Menu.CHAMPAIGN.getName(), Menu.CHAMPAIGN.getPrice());
        }
        return benefits;
    }

    public int getTotalDiscount() {
        return benefits.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int calculateEstimatePrice(Customer customer) {
        int discount = getBenefits().entrySet().stream()
                .filter(entry -> !Menu.CHAMPAIGN.getName().equals(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
        return customer.getTotalPriceBeforeDiscount() - discount;
    }

    public boolean isEmpty() {
        return benefits.isEmpty();
    }

    public boolean containsKey(String benefit) {
        return benefits.containsKey(benefit);
    }

    public Map<String, Integer> getBenefits() {
        return benefits;
    }

    private static int getDiscountAmountForEvent(Customer customer, String event) {
        if (Promotions.CHRISTMAS_DDAY.getPromotionName().equals(event)) {
            return customer.getChristmasDiscountPrice();
        }
        if (Promotions.WEEKDAY.getPromotionName().equals(event)) {
            return calculateWeekDiscount(MenuCategory.DESSERT, customer);
        }
        if (Promotions.WEEKEND.getPromotionName().equals(event)) {
            return calculateWeekDiscount(MenuCategory.MAIN, customer);
        }
        if (Promotions.SPECIAL.getPromotionName().equals(event)) {
            return PromotionRules.STAR_DISCOUNT.getValue();
        }
        return 0;
    }

    private static int calculateWeekDiscount(MenuCategory menuCategory, Customer customer) {
        final int discount = PromotionRules.EVENT_PERIOD_DISCOUNT.getValue();
        return customer.getOrderedMenusList().stream()
                .filter(orderedMenu -> OrderedMenus.orderedMenuBelongsToCategory(orderedMenu, menuCategory))
                .mapToInt(OrderedMenu::getOrderCount)
                .sum() * discount;
    }
}
