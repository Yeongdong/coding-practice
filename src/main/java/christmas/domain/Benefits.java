package christmas.domain;

import christmas.utils.PromotionRules;
import christmas.utils.Promotions;

import java.util.*;

public class Benefits {
    private final Map<String, Integer> benefits;
    private static final String GIVEAWAY = "샴페인";

    private Benefits(Map<String, Integer> benefits) {
        this.benefits = benefits;
    }

    public static Benefits from(EventDate eventDate, OrderedMenus orderedMenus) {
        if (OrderedMenus.canGetBenefit(orderedMenus) && !OrderedMenus.hasOnlyBeverage(orderedMenus)) {
            Map<String, Integer> benefits = checkEvent(eventDate, orderedMenus);
            return new Benefits(benefits);
        }
        return new Benefits(Collections.emptyMap());
    }

    private static Map<String, Integer> checkEvent(EventDate eventDate, OrderedMenus orderedMenus) {
        Map<String, Integer> benefits = new HashMap<>();
        List<String> eventPeriod = eventDate.eventPeriod();
        for (String event : eventPeriod) {
            int discountAmount = getDiscountAmountForEvent(eventDate, event, orderedMenus);
            benefits.put(event, discountAmount);
        }
        if (orderedMenus.getTotalPriceBeforeDiscount() >= PromotionRules.GIVEAWAY_CONDITION.getValue()) {
            benefits.put(Menu.valueOf(GIVEAWAY).name(), Menu.valueOf(GIVEAWAY).getPrice());
        }
        return benefits;
    }

    private static int getDiscountAmountForEvent(EventDate eventDate, String event, OrderedMenus orderedMenus) {
        if (Promotions.CHRISTMAS_DDAY.getPromotionName().equals(event)) {
            return eventDate.calculateAccumulateDiscountPrice();
        }
        if (Promotions.WEEKDAY.getPromotionName().equals(event)) {
            return calculateWeekDiscount(MenuCategory.DESSERT, orderedMenus);
        }
        if (Promotions.WEEKEND.getPromotionName().equals(event)) {
            return calculateWeekDiscount(MenuCategory.MAIN, orderedMenus);
        }
        if (Promotions.SPECIAL.getPromotionName().equals(event)) {
            return PromotionRules.STAR_DISCOUNT.getValue();
        }
        return 0;
    }

    private static int calculateWeekDiscount(MenuCategory menuCategory, OrderedMenus orderedMenus) {
        final int discount = PromotionRules.EVENT_PERIOD_DISCOUNT.getValue();

        return orderedMenus.getOrderedMenus().stream()
                .filter(orderedMenu -> OrderedMenus.orderedMenuBelongsToCategory(orderedMenu, menuCategory))
                .mapToInt(OrderedMenu::getAmount)
                .sum() * discount;
    }


    public int getTotalDiscount() {
        return benefits.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int calculateEstimatePrice(Benefits benefits, OrderedMenus orderedMenus) {
        int discount = getBenefits().entrySet().stream()
                .filter(entry -> !GIVEAWAY.equals(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
        return orderedMenus.getTotalPriceBeforeDiscount() - discount;
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
}
