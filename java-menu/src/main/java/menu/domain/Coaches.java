package menu.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Coaches {
    private final List<Coach> coachList;
    private SelectCategory selectCategory;

    private Coaches(List<Coach> coachList) {
        this.coachList = coachList;
    }

    public static Coaches from(String coachName) {
        List<Coach> coachList = Arrays.stream(coachName.split(","))
                .map(Coach::from)
                .collect(Collectors.toList());
        validateCoachesSize(coachList);
        return new Coaches(coachList);
    }

    public void addSelectCategory(List<String> categories) {
        this.selectCategory = SelectCategory.from(categories);
    }

    private static void validateCoachesSize(List<Coach> coachList) {
        if (coachList.size() < 2 || coachList.size() > 5) {
            throw new IllegalArgumentException("[ERROR] 코치는 최소 2명 이상, 5명 이하 입력해야 합니다.");
        }
    }

    public void getRecommendation() {
        boolean successAdding = true;
        while(successAdding) {
            for (Coach coach : coachList) {
                List<String> recommendedMenus = MenuInfo.getRecommendation(selectCategory);
                successAdding = coach.addSelectMenus(recommendedMenus);
            }
        }
    }

    public List<Coach> getCoachList() {
        return Collections.unmodifiableList(coachList);
    }

    private SelectCategory getSelectCategory() {
        return selectCategory;
    }

    public List<String> getCoachesSelectCategory() {
        return getSelectCategory().getCategories();
    }
}
