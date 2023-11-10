package christmas.view;

import christmas.domain.Benefits;
import christmas.domain.EventDate;
import christmas.domain.OrderedMenu;
import christmas.domain.OrderedMenus;

public class OutputView {
    private static final String PREVIEW_BENEFIT_MESSAGE = "12월 %d일에 우테코 식당에서  받을 이벤트 혜택 미리 보기!\n";
    private static final String ORDERED_MENU_MESSAGE = "\n<주문메뉴>";
    private static final String TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE = "\n<할인 전 총주문 금액>";
    private static final String GIVEAWAY_MENU_MESSAGE = "\n<증정 메뉴>";
    private static final String BENEFITS_MESSAGE = "\n<혜택 내역>";
    private static final String DISCOUNTS = "%s 할인: -%,d원\n";
    private static final String GIVEAWAY_EVENT = "증정 이벤트: -%,d\n";
    private static final String TOTAL_BENEFITS_MESSAGE = "\n<총혜택 내역>";
    private static final String ESTIMATED_PRICE_AFTER_DISCOUNT_MESSAGE = "\n<할인 후 예상 결제 금액>";
    private static final String DECEMBER_EVENT_BADGE_MESSAGE = "\n<12월 이벤트 배지>";

    public static void printPreviewBenefit(int date) {
        System.out.printf(PREVIEW_BENEFIT_MESSAGE, date);
    }

    public static void printOrderedMenus(OrderedMenus orderedMenus) {
        System.out.println(ORDERED_MENU_MESSAGE);
        for (int i = 0; i < orderedMenus.getOrderedMenus().size(); i++) {
            OrderedMenu orderedMenu = orderedMenus.getOrderedMenus().get(i);
            System.out.println(orderedMenu.getMenuName() + " " + orderedMenu.getAmount() + "개");
        }
    }

    public static void printTotalPriceBeforeDiscount(OrderedMenus orderedMenus) {
        System.out.println(TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE);
        int totalPriceBeforeDiscount = orderedMenus.getTotalPriceBeforeDiscount();
        System.out.printf(String.format("%,d", totalPriceBeforeDiscount) + "원");
        System.out.println();
    }

    public static void printGiveawayMenu(OrderedMenus orderedMenus) {
        System.out.println(GIVEAWAY_MENU_MESSAGE);
        System.out.println(Benefits.giveaway(orderedMenus));
    }

    public static void printBenefits(EventDate eventDate, OrderedMenus orderedMenus) {
        System.out.println(BENEFITS_MESSAGE);
        Benefits benefits = Benefits.from(eventDate, orderedMenus);
        if (benefits.isEmpty()) {
            System.out.println("없음");
        }
        benefits.getBenefits().forEach((name, discount) -> {
            System.out.printf(DISCOUNTS, name, discount);
        });
        if (Benefits.giveaway(orderedMenus) != null) {
            System.out.printf(GIVEAWAY_EVENT, 25000);
        }

    }

}
