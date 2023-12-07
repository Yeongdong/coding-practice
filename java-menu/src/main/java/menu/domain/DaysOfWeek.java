package menu.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DaysOfWeek {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일");
    private final String dayInWeek;

    DaysOfWeek(String dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    private String getDayInWeek() {
        return dayInWeek;
    }

    public static List<String> getDayOfWeek() {
        return Arrays.stream(DaysOfWeek.values())
                .map(DaysOfWeek::getDayInWeek)
                .collect(Collectors.toList());
    }
}
