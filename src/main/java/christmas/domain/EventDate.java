package christmas.domain;

import christmas.utils.PromotionRules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class EventDate {
    private static final String INVALID_DATE_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private final int visitDate;
    private final boolean hasStar;

    public EventDate(int visitDate, boolean hasStar) {
        this.visitDate = visitDate;
        this.hasStar = hasStar;
    }

    public static EventDate from(int visitDate) {
        validateDate(visitDate);
        boolean hasStar = hasStar(visitDate);
        return new EventDate(visitDate, hasStar);
    }

    private static void validateDate(int visitDate) {
        if (visitDate < PromotionRules.START_DATE.getValue() || visitDate > PromotionRules.END_DATE.getValue()) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    private static Date toDate(int visitDate) {
        try {
            String dateString = String.format("202312%02d", visitDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    private static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    public boolean isChristmasPeriod(int visitDate) {
        Date date = toDate(visitDate);
        LocalDate localDate = getLocalDate(date);
        return localDate.getMonth() == Month.DECEMBER && localDate.getDayOfMonth() <= PromotionRules.CHRISTMAS_DATE.getValue();
    }

    public boolean isWeekend(int visitDate) {
        Date date = toDate(visitDate);
        LocalDate localDate = getLocalDate(date);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    public static boolean hasStar(int visitDate) {
        Date date = toDate(visitDate);
        LocalDate localDate = getLocalDate(date);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY || localDate.getDayOfMonth() == PromotionRules.CHRISTMAS_DATE.getValue();
    }

    public int getVisitDate() {
        return visitDate;
    }

    public boolean isHasStar() {
        return hasStar;
    }
}
