package menu.view;

import menu.domain.Coach;
import menu.domain.Coaches;
import menu.domain.DaysOfWeek;

import java.util.List;

public class ResultView {
    private final static String RESULT_MESSAGE = "메뉴 추천 결과입니다.";
    private final static String START_RESULT_MARK = "[ ";
    private final static String SEPARATOR = " | ";
    private final static String END_RESULT_MARK = " ]";
    private final static String ASSORTION_MESSAGE = "구분";
    private final static String CATEGORY_MESSAGE = "카테고리";
    private final static String COMPLETE_RECOMMEND_MESSAGE = "\n추천을 완료했습니다.";

    public static void printResultMessage() {
        System.out.println(RESULT_MESSAGE);
        List<String> weekResult = DaysOfWeek.getDayOfWeek();
        weekResult.add(0, ASSORTION_MESSAGE);
        System.out.println(inFormat(weekResult));
    }

    public static void printCategory(Coaches coaches) {
        List<String> categoryResult = coaches.getCoachesSelectCategory();
        categoryResult.add(0, CATEGORY_MESSAGE);
        System.out.println(inFormat(categoryResult));
    }

    public static void printRecomResult(Coaches coaches) {
        for (Coach coach : coaches.getCoachList()) {
            List<String> recomMenus = coach.getSelectMenuNames();
            recomMenus.add(0, coach.getCoachName());
            System.out.println(inFormat(recomMenus));
        }
    }

    public static void printCompleteMessage() {
        System.out.println(COMPLETE_RECOMMEND_MESSAGE);
    }

    private static String inFormat(List<String> values) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START_RESULT_MARK);
        stringBuilder.append(String.join(SEPARATOR, values));
        stringBuilder.append(END_RESULT_MARK);
        return stringBuilder.toString();
    }

}
