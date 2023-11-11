package christmas.application;

import christmas.domain.EventDate;
import christmas.domain.OrderedMenus;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PromotionOrder {
    public static void order() {
        EventDate eventDate = InputView.inputEstimateDate();
        OrderedMenus orderedMenus = InputView.inputMenuAndAmount();
        OutputView.printPreviewBenefit(eventDate.getVisitDate());
        OutputView.printOrderedMenus(orderedMenus);     // <주문 메뉴>
        OutputView.printTotalPriceBeforeDiscount(orderedMenus); // <할인 전 총주문 금액>
        OutputView.printGiveawayMenu(orderedMenus); // <증정 메뉴>
        OutputView.printBenefits(eventDate, orderedMenus); // <혜택 내역>
        OutputView.printTotalBenefitPrice();    // <총혜택 금액>
        OutputView.printEstimatePrice(orderedMenus);    // <할인 후 예상 결제 금액>
        OutputView.printEventBadge();       // <12월 이벤트 배지>
    }
}
