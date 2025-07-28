package christmas.domain;

import christmas.utils.Promotions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("할인 전 총주문 금액이 10_000원 이상이고, 음료 외 다른 메뉴가 주문에 포함될시 True를 반환하며 테스트를 통과한다.")
    @Test
    void canParticipateInPromotion() {
        String menuList = "양송이수프-2,타파스-1,시저샐러드-3";
        OrderedMenus orderedMenuList = OrderedMenus.from(menuList);
        Customer customer = Customer.reserve(EventDate.from("25"), orderedMenuList);
        assertThat(customer.canGetBenefit()).isTrue();
    }

    @DisplayName("음료만 주문한 경우 주문 실패를 반환한다.")
    @Test
    void hasOnlyBeverageFail() {
        String menuList = "레드와인-2,제로콜라-1";

        assertThatThrownBy(() -> OrderedMenus.from(menuList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다.");
    }

    @DisplayName("크리스마스 디데이 이벤트 기간에 참여할 경우 할인 금액을 반환한다.")
    @Test
    void calculateAccumulateDiscountPrice() {
        String menuList = "양송이수프-2,타파스-1,시저샐러드-3";
        OrderedMenus orderedMenuList = OrderedMenus.from(menuList);
        Customer customer = Customer.reserve(EventDate.from("15"), orderedMenuList);

        int accumulateDiscountPrice = customer.getChristmasDiscountPrice();

        assertThat(accumulateDiscountPrice).isEqualTo(2400);
    }

    @DisplayName("크리스마스 디데이 이벤트 기간이 아닌 경우 크리스마스 디데이 할인 금액은 0을 반환한다.")
    @Test
    void calculateAccumulateDiscountPriceFail() {
        String menuList = "양송이수프-2,타파스-1,시저샐러드-3";
        OrderedMenus orderedMenuList = OrderedMenus.from(menuList);
        Customer customer = Customer.reserve(EventDate.from("26"), orderedMenuList);

        int accumulateDiscountPrice = customer.getChristmasDiscountPrice();

        assertThat(accumulateDiscountPrice).isEqualTo(0);
    }

    @DisplayName("이벤트 기간에 해당하는 이벤트를 반환한다.")
    @Test
    void eventPeriod() {
        String menuList = "양송이수프-2,타파스-1,시저샐러드-3";
        OrderedMenus orderedMenus = OrderedMenus.from(menuList);
        Customer customer = Customer.reserve(EventDate.from("24"), orderedMenus);

        List<String> events = customer.getEventPeriod();

        assertThat(events).containsExactly(Promotions.CHRISTMAS_DDAY.getPromotionName(), Promotions.WEEKDAY.getPromotionName(), Promotions.SPECIAL.getPromotionName());
    }
}