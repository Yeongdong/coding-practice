package christmas.domain;

import christmas.utils.PromotionRules;

public class Badge {
    private static final String SANTA = "산타";
    private static final String TREE = "트리";
    private static final String STAR = "별";
    private static final String NOT_APPLICABLE = "없음";
    private String badgeName;

    private Badge(String badgeName) {
        this.badgeName = badgeName;
    }

    public static Badge from(int totalDiscount) {
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_20000.getValue())
            return new Badge(SANTA);
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_10000.getValue())
            return new Badge(TREE);
        if (totalDiscount >= PromotionRules.TOTAL_BENEFIT_5000.getValue())
            return new Badge(STAR);
        return new Badge(NOT_APPLICABLE);
    }

    public String getBadgeName() {
        return badgeName;
    }
}
