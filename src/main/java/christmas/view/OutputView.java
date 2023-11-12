package christmas.view;

import christmas.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OutputView {
    private static final String PREVIEW_BENEFIT_MESSAGE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";
    private static final String ORDERED_MENU_MESSAGE = "\n<주문 메뉴>";
    private static final String ORDERED_MENU_AND_AMOUNT = "%s %d개\n";
    private static final String TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE = "\n<할인 전 총주문 금액>";
    private static final String PRICE_FORMAT = "%,d원\n";
    private static final String DISCOUNT_FORMAT = "-%,d원\n";
    private static final String GIVEAWAY_MENU_MESSAGE = "\n<증정 메뉴>";
    private static final String BENEFITS_MESSAGE = "\n<혜택 내역>";
    private static final String TOTAL_BENEFITS_MESSAGE = "\n<총혜택 금액>";
    private static final String ESTIMATED_PRICE_AFTER_DISCOUNT_MESSAGE = "\n<할인 후 예상 결제 금액>";
    private static final String DECEMBER_EVENT_BADGE_MESSAGE = "\n<12월 이벤트 배지>";
    private static final String NOT_APPLICABLE = "없음";
    private static final String GIVEAWAY_FORMAT = "%s %d개";
    private static final String BENEFIT_FORMAT = "%s 할인: -%,d원";
    private static final String GIVEAWAY_EVENT = "증정 이벤트: -%,d원";
    private static final String GIVEAWAY = "샴페인";

    public static void printPreviewBenefit(EventDate eventDate) {
        System.out.printf(PREVIEW_BENEFIT_MESSAGE, eventDate.getVisitDate());
    }

    public static void printOrderedMenus(OrderedMenus orderedMenus) {
        System.out.println(ORDERED_MENU_MESSAGE);
        for (int i = 0; i < orderedMenus.getOrderedMenus().size(); i++) {
            OrderedMenu orderedMenu = orderedMenus.getOrderedMenus().get(i);
            System.out.printf(ORDERED_MENU_AND_AMOUNT, orderedMenu.getMenuName(), orderedMenu.getAmount());
        }
    }

    public static void printTotalPriceBeforeDiscount(OrderedMenus orderedMenus) {
        System.out.println(TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE);
        int totalPriceBeforeDiscount = orderedMenus.getTotalPriceBeforeDiscount();
        System.out.printf(PRICE_FORMAT, totalPriceBeforeDiscount);
    }

    public static void printGiveawayMenu(Benefits benefits) {
        System.out.println(GIVEAWAY_MENU_MESSAGE);
        System.out.println(checkGiveaway(benefits));
    }

    public static void printBenefits(Benefits benefits) {
        System.out.println(BENEFITS_MESSAGE);
        System.out.println(checkBenefits(benefits));
    }

    public static void printTotalBenefit(Benefits benefits) {
        System.out.println(TOTAL_BENEFITS_MESSAGE);
        System.out.printf(DISCOUNT_FORMAT, benefits.getTotalDiscount());
    }

    public static void printEstimatePrice(Benefits benefits, OrderedMenus orderedMenus) {
        System.out.println(ESTIMATED_PRICE_AFTER_DISCOUNT_MESSAGE);
        System.out.printf(PRICE_FORMAT, benefits.calculateEstimatePrice(benefits, orderedMenus));
    }

    public static void printEventBadge(Benefits benefits) {
        System.out.println(DECEMBER_EVENT_BADGE_MESSAGE);
        System.out.println(Badge.createBadge(benefits.getTotalDiscount()));
    }

    private static String checkGiveaway(Benefits benefits) {
        if (benefits.containsKey(GIVEAWAY)) {
            int giveawayCount = (int) benefits.getBenefits().keySet().stream()
                    .filter(key -> key.equals(GIVEAWAY))
                    .count();
            return String.format(GIVEAWAY_FORMAT, GIVEAWAY, giveawayCount);
        }
        return NOT_APPLICABLE;
    }

    private static String checkBenefits(Benefits benefits) {
        List<String> result = new ArrayList<>();
        if (benefits.isEmpty()) {
            return NOT_APPLICABLE;
        }
        Set<String> filteredKeys = filterGiveaway(benefits);
        addFilteredBenefitsToResult(benefits, filteredKeys, result);
        if (benefits.containsKey(GIVEAWAY)) {
            result.add(String.format(GIVEAWAY_EVENT, Menu.valueOf(GIVEAWAY).getPrice()));
        }
        return String.join("\n", result);
    }

    private static Set<String> filterGiveaway(Benefits benefits) {
        return benefits.getBenefits().keySet().stream()
                .filter(key -> !GIVEAWAY.equals(key))
                .collect(Collectors.toSet());
    }

    private static void addFilteredBenefitsToResult(Benefits benefits, Set<String> filteredKeys, List<String> result) {
        filteredKeys.forEach(key -> {
            int discount = benefits.getBenefits().get(key);
            result.add(String.format(BENEFIT_FORMAT, key, discount));
        });
    }
}
