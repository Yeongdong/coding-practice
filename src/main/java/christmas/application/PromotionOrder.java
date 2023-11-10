package christmas.application;

import christmas.domain.OrderedMenus;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PromotionOrder {
    public static void order() {
        OrderedMenus orderedMenus = InputView.inputMenuAndAmount();
        OutputView.printOrderedMenus(orderedMenus);
    }
}
