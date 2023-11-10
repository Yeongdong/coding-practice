package christmas.domain;

public class EventDate {
    private static final String INVALID_DATE_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private final int visitDate;

    private EventDate(int visitDate) {
        this.visitDate = visitDate;
    }

    public static EventDate from(int visitDate) {
        validateDate(visitDate);
        return new EventDate(visitDate);
    }

    private static void validateDate(int visitDate) {
        if (visitDate < 1 || visitDate > 31) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }
}
