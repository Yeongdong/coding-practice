package menu.domain;

import java.util.Collections;
import java.util.List;

public class SelectCategory {
    private final List<String> categories;

    private SelectCategory(List<String> categories) {
        this.categories = categories;
    }

    public static SelectCategory from(List<String> categories) {
        if (isOverThreeFrequency(categories)) MenuInfo.recommendCategory();
        return new SelectCategory(categories);
    }

    private static boolean isOverThreeFrequency(List<String> categories) {
        return Collections.frequency(categories, MenuInfo.values()) > 2;
    }

    public List<String> getCategories() {
        return categories;
    }
}
