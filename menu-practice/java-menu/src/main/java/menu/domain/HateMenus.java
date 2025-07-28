package menu.domain;

import java.util.*;
import java.util.stream.Collectors;

public class HateMenus {
    private final List<HateMenu> menuNames;

    private HateMenus(List<HateMenu> menuNames) {
        this.menuNames = menuNames;
    }

    public static HateMenus from(String menuName) {
        List<HateMenu> menuNames = Arrays.stream(menuName.split(","))
                .map(HateMenu::from)
                .collect(Collectors.toList());
        validateHateMenusSize(menuNames);
        return new HateMenus(menuNames);
    }

    private static void validateHateMenusSize(List<HateMenu> menuNames) {
        if (menuNames.size() > 2) {
            throw new IllegalArgumentException("[ERROR] 먹지 못하는 음식은 0개 이상 2개 이하 입력 가능합니다.");
        }
    }

    public List<HateMenu> getMenuNames() {
        return Collections.unmodifiableList(menuNames);
    }

    public boolean isExist(List<String> menus) {
        HashSet<HateMenu> inputHateMenus = menus.stream()
                .map(HateMenu::from)
                .collect(Collectors.toCollection(HashSet::new));

        HashSet<HateMenu> existingHateMenus = new HashSet<>(menuNames);

        return existingHateMenus.equals(inputHateMenus);
    }
}
