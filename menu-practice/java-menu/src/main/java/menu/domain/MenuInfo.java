package menu.domain;

import java.util.*;

public enum MenuInfo {
    JAPANESE(1, "일식", Arrays.asList("규동", "우동", "미소시루", "스시", "가츠동", "오니기리", "하이라이스", "라멘", "오코노미야끼")),
    KOREAN(2, "한식", Arrays.asList("김밥", "김치찌개", "쌈밥", "된장찌개", "비빔밥", "칼국수", "불고기", "떡볶이", "제육볶음")),
    CHINESE(3, "중식", Arrays.asList("깐풍기", "볶음면", "동파육", "짜장면", "짬뽕", "마파두부", "탕수육", "토마토 달걀볶음", "고추잡채")),
    ASIAN(4, "아시안", Arrays.asList("팟타이", "카오 팟", "나시고렝", "파인애플 볶음밥", "쌀국수", "똠얌꿍", "반미", "월남쌈", "분짜")),
    WESTERN(5, "양식", Arrays.asList("라자냐", "그라탱", "뇨끼", "끼슈", "프렌치 토스트", "바게트", "스파게티", "피자", "파니니"));

    private final int value;
    private final String category;
    private final List<String> menuNames;

    MenuInfo(int value, String category, List<String> menuNames) {
        this.value = value;
        this.category = category;
        this.menuNames = menuNames;
    }

    private int getValue() {
        return value;
    }

    private String getCategory() {
        return category;
    }

    private static String getCategoryByNumber(int number) {
        return Arrays.stream(MenuInfo.values())
                .filter(menuInfo -> menuInfo.getValue() == number)
                .findFirst()
                .map(MenuInfo::getCategory)
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<String> getMenuNames() {
        return menuNames;
    }

    public static List<String> recommendCategory() {
        List<String> categories = new ArrayList<>();
        while (categories.size() < DaysOfWeek.values().length) {
            int randomIndex = new Random().nextInt(MenuInfo.values().length) + 1;
            categories.add(getCategoryByNumber(randomIndex));
        }
        return categories;
    }

    public static List<String> getMenuNamesByCategory(String category) {
        return Arrays.stream(MenuInfo.values())
                .filter(menuInfo -> menuInfo.getCategory() == category)
                .findFirst()
                .map(MenuInfo::getMenuNames)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getRecommendation(SelectCategory selectCategory) {
        List<String> recommendedMenus = new ArrayList<>();
        for (String category : selectCategory.getCategories()) {
            List<String> menuNames = getMenuNamesByCategory(category);
            Collections.shuffle(menuNames);
            String recommendedMenu = menuNames.get(0);
            recommendedMenus.add(recommendedMenu);
        }
        return recommendedMenus;
    }
}
