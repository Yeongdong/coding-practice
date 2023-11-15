package christmas.domain;

import christmas.utils.PromotionRules;
import christmas.utils.Promotions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDate {
    private static final String INVALID_DATE_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private final int visitDate;

    private EventDate(int visitDate) {
        this.visitDate = visitDate;
    }

    public static EventDate from(String estimatedDate) {
        int visitDate = toInt(estimatedDate);
        validateDate(visitDate);
        return new EventDate(visitDate);
    }

    public List<String> eventPeriod() {
        LocalDate localDate = getLocalDate(visitDate);
        List<String> availableEvents = new ArrayList<>();
        if (isChristmasPeriod(localDate)) {
            availableEvents.add(Promotions.CHRISTMAS_DDAY.getPromotionName());
        }
        if (isWeekend(localDate)) {
            availableEvents.add(Promotions.WEEKEND.getPromotionName());
        }
        if (!isWeekend(localDate)) {
            availableEvents.add(Promotions.WEEKDAY.getPromotionName());
        }
        if (hasStarMark(localDate)) {
            availableEvents.add(Promotions.SPECIAL.getPromotionName());
        }
        if (availableEvents.isEmpty()) {
            availableEvents.add(Promotions.NOTHING.getPromotionName());
        }
        return availableEvents;
    }

    public int calculateAccumulateDiscountPrice() {
        if (getVisitDate() >= 1 && getVisitDate() <= 25) {
            return PromotionRules.DISCOUNT_START_PRICE.getValue() + (getVisitDate() - 1) * PromotionRules.ACCUMULATE_DISCOUNT_PRICE.getValue();
        }
        return 0;
    }

    public int getVisitDate() {
        return visitDate;
    }

    private boolean isChristmasPeriod(LocalDate localDate) {
        return (localDate.getMonth() == Month.DECEMBER) && (localDate.getDayOfMonth() <= PromotionRules.CHRISTMAS_DATE.getValue());
    }

    private boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    private boolean hasStarMark(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY || localDate.getDayOfMonth() == PromotionRules.CHRISTMAS_DATE.getValue();
    }

    private static void validateDate(int visitDate) {
        if (visitDate < PromotionRules.START_DATE.getValue() || visitDate > PromotionRules.END_DATE.getValue()) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    private static LocalDate getLocalDate(int visitDate) {
        try {
            String dateString = String.format("202312%02d", visitDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(dateString);
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }
}
