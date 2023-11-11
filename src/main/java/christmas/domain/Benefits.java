package christmas.domain;

import christmas.utils.PromotionRules;
import christmas.utils.Promotions;

import java.util.*;
import java.util.stream.Collectors;

public class Benefits {
    private static Map<String, Integer> benefits;
    private static final String GIVEAWAY = "샴페인";
    private static final String NOT_APPLICABLE = "없음";
    private static final String GIVEAWAY_FORMAT = "%s %d개";
    private static final String BENEFIT_FORMAT = "%s 할인: -%,d원";
    private static final String GIVEAWAY_EVENT = "증정 이벤트: -%,d원";


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
        int menuCount = 0;
        for (OrderedMenu orderedMenu : orderedMenus.getOrderedMenus()) {
            Menu menu = orderedMenu.getMenuName();
            if (menu.getMenuCategory() == menuCategory) {
                menuCount += orderedMenu.getAmount();
            }
        }
        return discount * menuCount;
    }

    public static int getTotalDiscount() {
        return benefits.values().stream().mapToInt(Integer::intValue).sum();
    }

    public static int calculateEstimatePrice(OrderedMenus orderedMenus) {
        int discount = benefits.entrySet().stream()
                .filter(entry -> !GIVEAWAY.equals(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
        return orderedMenus.getTotalPriceBeforeDiscount() - discount;
    }

    public String checkGiveaway() {
        if (benefits.containsKey(GIVEAWAY)) {
            int giveawayCount = (int) benefits.keySet().stream()
                    .filter(key -> key.equals(GIVEAWAY))
                    .count();
            return String.format(GIVEAWAY_FORMAT, GIVEAWAY, giveawayCount);
        }
        return NOT_APPLICABLE;
    }

    public String checkBenefits() {
        List<String> result = new ArrayList<>();
        if (benefits.isEmpty()) {
            return NOT_APPLICABLE;
        }
        Set<String> filteredKeys = filterGiveaway(benefits);
        addFilteredBenefitsToResult(filteredKeys, result);
        if (benefits.containsKey(GIVEAWAY)) {
            result.add(String.format(GIVEAWAY_EVENT, Menu.valueOf(GIVEAWAY).getPrice()));
        }
        return String.join("\n", result);
    }

    private Set<String> filterGiveaway(Map<String, Integer> benefits) {
        return benefits.keySet().stream()
                .filter(key -> !GIVEAWAY.equals(key))
                .collect(Collectors.toSet());
    }

    private void addFilteredBenefitsToResult(Set<String> filteredKeys, List<String> result) {
        filteredKeys.forEach(key -> {
            int discount = benefits.get(key);
            result.add(String.format(BENEFIT_FORMAT, key, discount));
        });
    }
}
