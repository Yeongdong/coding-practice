package christmas.domain;

import christmas.utils.Promotions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class EventDateTest {

    @DisplayName("날짜에 따라 이벤트를 반환한다.")
    @ParameterizedTest
    @MethodSource("dateAndEvent")
    void eventPeriod(String visitDate, List<String> expectedEvents) {
        EventDate eventDate = EventDate.from(visitDate);
        List<String> events = eventDate.eventPeriod();

        assertThat(events).containsExactlyInAnyOrderElementsOf(expectedEvents);
    }

    private static Stream<Arguments> dateAndEvent() {
        return Stream.of(
                Arguments.of("5", List.of(Promotions.CHRISTMAS_DDAY.getPromotionName(), Promotions.WEEKDAY.getPromotionName())),
                Arguments.of("16", List.of(Promotions.CHRISTMAS_DDAY.getPromotionName(), Promotions.WEEKEND.getPromotionName())),
                Arguments.of("31", List.of(Promotions.SPECIAL.getPromotionName(), Promotions.WEEKDAY.getPromotionName()))
        );
    }

    @DisplayName("크리스마스 디데이 이벤트 기간에는 누적 할인 금액을 반환한다.")
    @Test
    void calculateAccumulateDiscountPriceDuringChristmas() {
        EventDate eventDate = EventDate.from("20");
        int discountPrice = eventDate.calculateAccumulateDiscountPrice();

        assertThat(discountPrice).isEqualTo(29 * 100);
    }

    @DisplayName("크리스마스 디데이 이벤트 기간이 아닐때는 누적 할인 금액을 반환하지 않는다.")
    @Test
    void calculateAccumulateDiscountPriceNotDuringChristmas() {
        EventDate eventDate = EventDate.from("30");
        int discountPrice = eventDate.calculateAccumulateDiscountPrice();

        assertThat(discountPrice).isEqualTo(0);
    }
}