package christmas.domain;

import christmas.utils.PromotionRules;
import christmas.utils.Promotions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BenefitsTest {
    @DisplayName("혜택이 있을시 이벤트 혜택을 반환한다.")
    @Test
    void calculateBenefits_WithSpecialEvent() {
        List<String> menuList = Arrays.asList("해산물파스타-2", "레드와인-1", "초코케이크-1");
        OrderedMenus orderedMenuList = OrderedMenus.from(menuList);
        Customer customer = Customer.reserve(EventDate.from(26), orderedMenuList);
        Benefits benefits = Benefits.from(customer);

        assertThat(benefits.isEmpty()).isFalse();
        assertThat(benefits.getTotalDiscount()).isEqualTo(Menu.CHAMPAIGN.getPrice() + PromotionRules.EVENT_PERIOD_DISCOUNT.getValue());
        assertThat(benefits.getBenefits()).containsKey(Promotions.WEEKDAY.getPromotionName());
    }
}