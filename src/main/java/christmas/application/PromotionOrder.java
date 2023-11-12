package christmas.application;

import christmas.domain.Benefits;
import christmas.domain.Customer;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PromotionOrder {
    public static void order() {
        Customer customer = Customer.from(InputView.inputVisitDate(), InputView.inputMenuAndOrderCount());
        OutputView.printPreviewBenefit(customer);
        OutputView.printOrderedMenus(customer);
        OutputView.printTotalPriceBeforeDiscount(customer);
        Benefits benefits = Benefits.from(customer.getEstimateVisitDate(), customer.getOrderedMenus());
        OutputView.printGiveawayMenu(benefits);
        OutputView.printBenefits(benefits);
        OutputView.printTotalBenefit(benefits);
        OutputView.printEstimatePrice(benefits, customer.getOrderedMenus());
        OutputView.printEventBadge(customer, benefits);
    }
}
