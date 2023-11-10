package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.OrderedMenus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputView {
    private static final String MENU_AND_AMOUNT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDER_SPLITTER = ",";
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";

    public static OrderedMenus inputMenuAndAmount() {
        try {
            System.out.println(MENU_AND_AMOUNT);
            String menuAndAmount = Console.readLine();
            List<String> orderInputs = Arrays.stream(menuAndAmount.split(ORDER_SPLITTER))
                    .toList();
            return makeOrder(orderInputs);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputMenuAndAmount();
        }
    }

    private static OrderedMenus makeOrder(List<String> orderInputs) {
        try {
            List<String> menuNames = new ArrayList<>();
            List<Integer> amounts = new ArrayList<>();

            for (String order : orderInputs) {
                String[] menus = order.split("-");
                validateInputForm(menus);
                menuNames.add(menus[0]);
                amounts.add(Integer.parseInt(menus[1]));
            }
            return OrderedMenus.from(menuNames, amounts);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateInputForm(String[] menus) {
        if (menus.length != 2) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }
}
