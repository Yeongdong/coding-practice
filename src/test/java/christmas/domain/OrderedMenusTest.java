package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderedMenusTest {

    @DisplayName("주문 메뉴가 정상적으로 생성될시 테스트를 통과한다.")
    @Test
    void orderedMenusCreationTest() {
        String menuList = "양송이수프-2,타파스-1,시저샐러드-3";
        OrderedMenus orderedMenus = OrderedMenus.from(menuList);

        assertThat(orderedMenus).isNotNull();
        assertThat(orderedMenus.getOrderedMenus()).hasSize(menuList.length());
    }

    @DisplayName("주문 메뉴가 중복될시 IllegalArgumentException이 발생하며 '[ERROR] 유효하지 않은 주문입니다.' 메시지를 출력한다.")
    @Test
    void orderedMenusCreationWithDuplicateMenuTest() {
        String menuList = "타파스-2,타파스-1,시저샐러드-3";

        assertThatThrownBy(() -> OrderedMenus.from(menuList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다.");
    }

    @DisplayName("총 주문 수량이 제한을 초과할시 IllegalArgumentException이 발생하며, '[ERROR] 유효하지 않은 주문입니다.' 메시지를 출력한다.")
    @Test
    void orderedMenusCreationWithTotalOrderCountExceedLimitTest() {
        String menuList = "양송이수프-15,타파스-12,시저샐러드-3";

        assertThatThrownBy(() -> OrderedMenus.from(menuList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다.");
    }

    @Test
    @DisplayName("주문이 메뉴에 없으면 IllegalArgumentException이 발생하며, '[ERROR] 유효하지 않은 주문입니다.' 메시지를 출력한다.")
    void menuNotFoundThrowsException() {
        String menuList = "양송이수프-5,타파스-2,블랙커피-3";

        assertThatThrownBy(() -> OrderedMenus.from(menuList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다.");
    }
}