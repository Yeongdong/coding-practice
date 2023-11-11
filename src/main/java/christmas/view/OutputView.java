package christmas.view;

import christmas.domain.*;

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
        System.out.println(benefits.checkGiveaway());
    }

    public static void printBenefits(Benefits benefits) {
        System.out.println(BENEFITS_MESSAGE);
        System.out.println(benefits.checkBenefits());
    }

    public static void printTotalBenefit() {
        System.out.println(TOTAL_BENEFITS_MESSAGE);
        System.out.printf(DISCOUNT_FORMAT, Benefits.getTotalDiscount());
    }

    public static void printEstimatePrice(OrderedMenus orderedMenus) {
        System.out.println(ESTIMATED_PRICE_AFTER_DISCOUNT_MESSAGE);
        System.out.printf(PRICE_FORMAT, Benefits.calculateEstimatePrice(orderedMenus));
    }

    public static void printEventBadge() {
        System.out.println(DECEMBER_EVENT_BADGE_MESSAGE);
        Badge badge = Badge.from(Benefits.getTotalDiscount());
        System.out.println(badge.getBadgeName());
    }
}
