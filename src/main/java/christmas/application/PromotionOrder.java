package christmas.application;

import christmas.domain.Benefits;
import christmas.domain.EventDate;
import christmas.domain.OrderedMenus;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PromotionOrder {
    public static void order() {
        EventDate eventDate = InputView.inputEstimateDate();
        OrderedMenus orderedMenus = InputView.inputMenuAndAmount();
        OutputView.printPreviewBenefit(eventDate);
        OutputView.printOrderedMenus(orderedMenus);     // <주문 메뉴>
        OutputView.printTotalPriceBeforeDiscount(orderedMenus); // <할인 전 총주문 금액>
        Benefits benefits = Benefits.from(eventDate, orderedMenus);
        OutputView.printGiveawayMenu(benefits); // <증정 메뉴>
        OutputView.printBenefits(benefits); // <혜택 내역>
        OutputView.printTotalBenefit(benefits);    // <총혜택 금액>
        OutputView.printEstimatePrice(benefits, orderedMenus);    // <할인 후 예상 결제 금액>
        OutputView.printEventBadge(benefits);       // <12월 이벤트 배지>
    }
}
