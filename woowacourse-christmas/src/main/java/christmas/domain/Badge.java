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

    public static Badge from(int totalDiscount) {
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_20000.getValue())
            return SANTA;
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_10000.getValue())
            return TREE;
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_5000.getValue())
            return STAR;
        return NOT_APPLICABLE;
    }

    public String getBadgeName() {
        return badgeName;
    }
}
