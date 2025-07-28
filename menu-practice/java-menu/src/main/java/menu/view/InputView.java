package menu.view;

import java.util.Scanner;

public class InputView {
    private final static String INPUT_COACH_NAME_MESSAGE = "점심 메뉴 추천을 시작합니다.\n" + "\n" + "코치의 이름을 입력해 주세요. (, 로 구분)";
    private final static String INPUT_HATE_MENU_MESSAGE = "%s(이)가 못 먹는 메뉴를 입력해 주세요.\n";

    private static final Scanner scanner = new Scanner(System.in);

    public static String inputCoachName() {
        try {
            System.out.println(INPUT_COACH_NAME_MESSAGE);
            return scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputCoachName();
        }
    }

    public static String inputHateMenu(String coachName) {
        try {
            System.out.printf(INPUT_HATE_MENU_MESSAGE, coachName);
            return scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputHateMenu(coachName);
        }
    }
}
