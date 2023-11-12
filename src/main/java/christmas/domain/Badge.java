package christmas.domain;

import christmas.utils.PromotionRules;

public enum Badge {
    SANTA("산타"),
    TREE("트리"),
    STAR("별"),
    NOT_APPLICABLE("없음");
    private final String badgeName;

    Badge(String badgeName) {
        this.badgeName = badgeName;
    }

    public static String createBadge(int totalDiscount) {
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_20000.getValue())
            return SANTA.getBadgeName();
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_10000.getValue())
            return TREE.getBadgeName();
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_5000.getValue())
            return STAR.getBadgeName();
        return NOT_APPLICABLE.getBadgeName();
    }

    private String getBadgeName() {
        return badgeName;
    }
}
