package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BadgeTest {

    @DisplayName("총 할인 금액에 따라 배지를 반환한다.")
    @ParameterizedTest
    @MethodSource("badgeList")
    public void testFrom(Badge badge, int discount) {
        Badge testBadge = Badge.from(discount);
        assertThat(badge).isEqualTo(testBadge);
    }

    private static Stream<Arguments> badgeList() {
        return Stream.of(
                Arguments.of(Badge.SANTA, 20000),
                Arguments.of(Badge.TREE, 10000),
                Arguments.of(Badge.STAR, 5000),
                Arguments.of(Badge.NOT_APPLICABLE, 3000)
        );
    }

    @DisplayName("배지에 따라 배지 이름을 반환한다.")
    @ParameterizedTest
    @MethodSource("badgeNameList")
    public void testGetBadgeName(Badge badge, String createdBadge) {
        assertThat(badge.getBadgeName()).isEqualTo(createdBadge);
    }

    private static Stream<Arguments> badgeNameList() {
        return Stream.of(
                Arguments.of(Badge.SANTA, "산타"),
                Arguments.of(Badge.TREE, "트리"),
                Arguments.of(Badge.STAR, "별"),
                Arguments.of(Badge.NOT_APPLICABLE, "없음")
        );
    }
}
