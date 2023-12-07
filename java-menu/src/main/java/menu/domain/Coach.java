package menu.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Coach {
    private final String coachName;
    private HateMenus hateMenus;
    private SelectMenus selectMenus;

    private Coach(String coachName) {
        this.coachName = coachName;
    }

    public static Coach from(String coachName) {
        validateNameLength(coachName);
        return new Coach(coachName);
    }

    private static void validateNameLength(String coachName) {
        if (coachName.length() < 2 || coachName.length() > 4)
            throw new IllegalArgumentException("[ERROR] 코치 이름은 2글자 이상 4글자 이하입니다.");
    }

    public String getCoachName() {
        return coachName;
    }

    public HateMenus getHateMenus() {
        return hateMenus;
    }

    public SelectMenus getSelectMenus() {
        return selectMenus;
    }

    public List<String> getSelectMenuNames() {
        return getSelectMenus().getSelectMenus();
    }

    public void addHateMenus(String hateMenu) {
        this.hateMenus = HateMenus.from(hateMenu);
    }

    private boolean isHateMenu(List<String> menu) {
        return hateMenus.isExist(menu);
    }

    private boolean isDuplicateMenu(List<String> selectMenus) {
        Set<String> menu = new HashSet<>(selectMenus);
        return menu.size() != selectMenus.size();
    }

    public boolean addSelectMenus(List<String> menus) {
        if (isHateMenu(menus) || isDuplicateMenu(menus)) {
            this.selectMenus = SelectMenus.from(menus);
            return false;
        } else {
            return true;
        }
    }
}
