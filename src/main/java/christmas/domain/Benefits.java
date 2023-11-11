package christmas.domain;

import christmas.utils.PromotionRules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Benefits {
    private static Map<String, Integer> benefits;
    private static int totalDiscount;
    private static final String CHRISTMAS_DDAY = "크리스마스 디데이";
    private static final String WEEKDAY = "평일";
    private static final String WEEKEND = "주말";
    private static final String SPECIAL = "특별";
    private static final String NOT_APPLICABLE = "없음";
    private static final String GIVEAWAY = "샴페인 1개";

    private Benefits(Map<String, Integer> benefits) {
        this.benefits = benefits;
    }

    public static Benefits from(EventDate eventDate, OrderedMenus orderedMenus) {
        if (OrderedMenus.canGetBenefit(orderedMenus)) {
            Map<String, Integer> benefits = new HashMap<>();
            starDiscount(eventDate, benefits);
            weekendDiscount(eventDate, benefits, orderedMenus);
            weekdayDiscount(eventDate, benefits, orderedMenus);
            dDayDiscount(eventDate, benefits);
            return new Benefits(benefits);
        }
        return new Benefits(Collections.emptyMap());
    }

    public static String giveaway(OrderedMenus orderedMenus) {
        if (orderedMenus.getTotalPriceBeforeDiscount() >= PromotionRules.GIVEAWAY_CONDITION.getValue()) {
            return GIVEAWAY;
        }
        return NOT_APPLICABLE;
    }

    private static void dDayDiscount(EventDate eventDate, Map<String, Integer> benefits) {
        if (eventDate.isChristmasPeriod(eventDate.getVisitDate())) {
            benefits.put(CHRISTMAS_DDAY, PromotionRules.DISCOUNT_START_PRICE.getValue() + (eventDate.getVisitDate() - 1) * PromotionRules.ACCUMULATE_DISCOUNT_PRICE.getValue());
        }
    }

    private static void weekdayDiscount(EventDate eventDate, Map<String, Integer> benefits, OrderedMenus orderedMenus) {
        if (!eventDate.isWeekend(eventDate.getVisitDate())) {
            benefits.put(WEEKDAY, calculateWeekDiscount(MenuCategory.DESSERT, orderedMenus));
        }
    }

    private static void weekendDiscount(EventDate eventDate, Map<String, Integer> benefits, OrderedMenus orderedMenus) {
        if (eventDate.isWeekend(eventDate.getVisitDate())) {
            benefits.put(WEEKEND, calculateWeekDiscount(MenuCategory.MAIN, orderedMenus));
        }
    }

    private static void starDiscount(EventDate eventDate, Map<String, Integer> benefits) {
        if (eventDate.isHasStar()) {
            benefits.put(SPECIAL, calculateStarDiscount());
        }
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
        totalDiscount = benefits.values().stream().mapToInt(Integer::intValue).sum();
        return totalDiscount;
    }

    public static int calculateEstimatePrice(OrderedMenus orderedMenus) {
        return orderedMenus.getTotalPriceBeforeDiscount() - totalDiscount;
    }

    private static int calculateStarDiscount() {
        return PromotionRules.STAR_DISCOUNT.getValue();
    }

    public boolean isEmpty() {
        return benefits.isEmpty();
    }

    public Map<String, Integer> getBenefits() {
        return benefits;
    }

}
