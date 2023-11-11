package christmas.utils;

public enum Promotions {
    CHRISTMAS_DDAY("크리스마스 디데이"),
    WEEKDAY("평일"),
    WEEKEND("주말"),
    SPECIAL("특별"),
    NOTHING("없음");
    private final String promotionName;
    Promotions(String promotionName) {
        this.promotionName = promotionName;
    }
    public String getPromotionName() {
        return promotionName;
    }
}
