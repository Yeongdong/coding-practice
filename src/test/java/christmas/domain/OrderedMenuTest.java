package christmas.domain;

import christmas.utils.PromotionRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderedMenuTest {

    @DisplayName("OrderedMenu 객체 생성이 정상적으로 동작할시 테스트를 통과한다.")
    @Test
    void orderedMenuCreationTest() {
        String menu = "양송이수프";
        int orderCount = 3;
        OrderedMenu orderedMenu = OrderedMenu.from(menu, orderCount);

        assertThat(orderedMenu).isNotNull();
        assertThat(orderedMenu.getMenuName()).isEqualTo(menu);
        assertThat(orderedMenu.getOrderCount()).isEqualTo(orderCount);
    }

    @DisplayName("메뉴의 갯수가 최소 단위 이하일때, IllegalArgumentException이 발생하며 '[ERROR] 유효하지 않은 주문입니다.' 메시지를 출력한다.")
    @Test
    void orderedMenuCreationWithInvalidOrderCountTest() {
        String menu = "타파스";
        int invalidOrderCount = PromotionRules.MINIMUM_ORDER_COUNT.getValue() - 1;

        assertThatThrownBy(() -> OrderedMenu.from(menu, invalidOrderCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다.");
    }

    @DisplayName("메뉴의 가격을 정상적으로 가져올시 테스트를 통과한다.")
    @Test
    void getPriceTest() {
        String menu = "시저샐러드";
        int orderCount = 2;
        OrderedMenu orderedMenu = OrderedMenu.from(menu, orderCount);
        int totalPrice = orderedMenu.getPrice();

        assertThat(totalPrice).isEqualTo(orderedMenu.getPrice());
    }

    @DisplayName("메뉴의 이름을 정상적으로 가져올시 테스트를 통과한다.")
    @Test
    void getMenuNameTest() {
        String menu = "티본스테이크";
        int orderCount = 1;
        OrderedMenu orderedMenu = OrderedMenu.from(menu, orderCount);
        String menuName = orderedMenu.getMenuName();

        assertThat(menuName).isEqualTo(orderedMenu.getMenuName());
    }
}