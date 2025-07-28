package christmas.domain;

import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000),
    T_BONE_STEAK("티본스테이크", 55_000),
    BBQ_RIB("바비큐립", 54_000),
    SEAFOOD_PASTA("해산물파스타", 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000),
    CHOCOLATE_CAKE("초코케이크", 15_000),
    ICECREAM("아이스크림", 5_000),
    COKE_ZERO("제로콜라", 3_000),
    RED_WINE("레드와인", 60_000),
    CHAMPAIGN("샴페인", 25_000);

    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final String name;
    private final int price;

    Menu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static Menu findByName(String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_MESSAGE));
    }
}
