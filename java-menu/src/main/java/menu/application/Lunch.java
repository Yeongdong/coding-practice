package menu.application;

import menu.domain.Coach;
import menu.domain.Coaches;
import menu.domain.MenuInfo;
import menu.view.InputView;
import menu.view.ResultView;

public class Lunch {
    public void recommend() {
        Coaches coaches = Coaches.from(InputView.inputCoachName());
        for (Coach coach : coaches.getCoachList()) {
            coach.addHateMenus(InputView.inputHateMenu(coach.getCoachName()));
        }
        coaches.addSelectCategory(MenuInfo.recommendCategory());
        coaches.getRecommendation();
        ResultView.printResultMessage();
        ResultView.printCategory(coaches);
        ResultView.printRecomResult(coaches);
        ResultView.printCompleteMessage();
    }
}
