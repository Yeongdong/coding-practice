package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.EventDate;
import christmas.domain.OrderedMenus;

public class InputView {
    private static final String GREETING_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String ESTIMATE_DATE_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_AND_ORDER_COUNT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    public static EventDate inputVisitDate() {
        try {
            System.out.println(GREETING_MESSAGE);
            System.out.println(ESTIMATE_DATE_MESSAGE);
            return EventDate.from(Console.readLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputVisitDate();
        }
    }

    public static OrderedMenus inputMenuAndOrderCount() {
        try {
            System.out.println(MENU_AND_ORDER_COUNT);
            return OrderedMenus.from(Console.readLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputMenuAndOrderCount();
        }
    }
}
