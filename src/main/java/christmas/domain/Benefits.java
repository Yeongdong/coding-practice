package christmas.domain;

import christmas.utils.Menu;
import christmas.utils.MenuCategory;

import java.util.HashMap;
import java.util.Map;

public class Benefits {
    private static Map<String, Integer> benefits;

    private Benefits(Map<String, Integer> benefits) {
        this.benefits = benefits;
    }

    public static Benefits from(EventDate eventDate, OrderedMenus orderedMenus) {
        Map<String, Integer> benefits = new HashMap<>();
        starDiscount(eventDate, benefits);
        weekendDiscount(eventDate, benefits, orderedMenus);
        weekdayDiscount(eventDate, benefits, orderedMenus);
        dDayDiscount(eventDate, benefits);
        return new Benefits(benefits);
    }

    public static String giveaway(OrderedMenus orderedMenus) {
        if (orderedMenus.getTotalPriceBeforeDiscount() >= 120_000) {
            return "샴페인 1개";
        }
        return null;
    }

    private static void dDayDiscount(EventDate eventDate, Map<String, Integer> benefits) {
        if (eventDate.isChristmasPeriod(eventDate.getVisitDate())) {
            benefits.put("크리스마스 디데이", 1000 + (eventDate.getVisitDate() - 1) * 100);
        }
    }

    private static void weekdayDiscount(EventDate eventDate, Map<String, Integer> benefits, OrderedMenus orderedMenus) {
        if (!eventDate.isWeekend(eventDate.getVisitDate())) {
            benefits.put("평일", calculateWeekDiscount(MenuCategory.DESSERT, orderedMenus));
        }
    }

    private static void weekendDiscount(EventDate eventDate, Map<String, Integer> benefits, OrderedMenus orderedMenus) {
        if (eventDate.isWeekend(eventDate.getVisitDate())) {
            benefits.put("주말", calculateWeekDiscount(MenuCategory.MAIN, orderedMenus));
        }
    }

    private static void starDiscount(EventDate eventDate, Map<String, Integer> benefits) {
        if (eventDate.isHasStar()) {
            benefits.put("특별", calculateStarDiscount());
        }
    }

    private static int calculateWeekDiscount(MenuCategory menuCategory, OrderedMenus orderedMenus) {
        final int discount = 2023;
        int menuCount = 0;
        for (OrderedMenu orderedMenu : orderedMenus.getOrderedMenus()) {
            Menu menu = orderedMenu.getMenuName();
            if (menu.getMenuCategory() == menuCategory) {
                menuCount += orderedMenu.getAmount();
            }
        }
        return discount * menuCount;
    }

    private static int calculateStarDiscount() {
        return 1000;
    }

    public boolean isEmpty() {
        return benefits.isEmpty();
    }

    public Map<String, Integer> getBenefits() {
        return benefits;
    }
}
