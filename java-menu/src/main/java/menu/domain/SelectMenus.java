package menu.domain;

import java.util.List;

public class SelectMenus {
    private List<String> selectMenus;

    private SelectMenus(List<String> selectMenus) {
        this.selectMenus = selectMenus;
    }

    public static SelectMenus from(List<String> selectMenus) {
        return new SelectMenus(selectMenus);
    }

    public List<String> getSelectMenus() {
        return selectMenus;
    }
}
