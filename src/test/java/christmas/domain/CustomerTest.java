package christmas.domain;

import christmas.utils.Promotions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @DisplayName("할인 전 총주문 금액이 10_000원 이상이고, 음료 외 다른 메뉴가 주문에 포함될시 True를 반환하며 테스트를 통과한다.")
    @Test
    void canParticipateInPromotion() {
        List<String> orderedMenus = Arrays.asList(Menu.CAESAR_SALAD.getName(), Menu.RED_WINE.getName());
        List<Integer> orderCounts = Arrays.asList(2, 3);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(25), orderedMenuList);
        assertThat(customer.isParticipateInPromotion()).isTrue();
    }

    @DisplayName("음료만 주문한 경우 참여 실패를 반환한다.")
    @Test
    void hasOnlyBeverageFail() {
        List<String> orderedMenus = Arrays.asList(Menu.COKE_ZERO.getName(), Menu.RED_WINE.getName());
        List<Integer> orderCounts = Arrays.asList(2, 3);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(25), orderedMenuList);
        assertThat(customer.isParticipateInPromotion()).isFalse();
    }

    @DisplayName("크리스마스 디데이 이벤트 기간에 참여할 경우 할인 금액을 반환한다.")
    @Test
    void calculateAccumulateDiscountPrice() {
        List<String> orderedMenus = Arrays.asList(Menu.T_BONE_STEAK.getName(), Menu.RED_WINE.getName());
        List<Integer> orderCounts = Arrays.asList(2, 3);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(15), orderedMenuList);

        int accumulateDiscountPrice = customer.getChristmasDiscountPrice();

        assertThat(accumulateDiscountPrice).isEqualTo(2400);
    }

    @DisplayName("크리스마스 디데이 이벤트 기간이 아닌 경우 크리스마스 디데이 할인 금액은 0을 반환한다.")
    @Test
    void calculateAccumulateDiscountPriceFail() {
        List<String> orderedMenus = Arrays.asList(Menu.T_BONE_STEAK.getName(), Menu.RED_WINE.getName());
        List<Integer> orderCounts = Arrays.asList(2, 3);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(26), orderedMenuList);

        int accumulateDiscountPrice = customer.getChristmasDiscountPrice();

        assertThat(accumulateDiscountPrice).isEqualTo(0);
    }

    @DisplayName("이벤트 기간에 이벤트 확인")
    @Test
    void eventPeriod() {
        List<String> orderedMenus = Arrays.asList(Menu.T_BONE_STEAK.getName(), Menu.RED_WINE.getName());
        List<Integer> orderCounts = Arrays.asList(2, 3);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(24), orderedMenuList);

        List<String> events = customer.getEventPeriod();

        assertThat(events).containsExactly(Promotions.CHRISTMAS_DDAY.getPromotionName(), Promotions.WEEKDAY.getPromotionName(), Promotions.SPECIAL.getPromotionName());
    }
}