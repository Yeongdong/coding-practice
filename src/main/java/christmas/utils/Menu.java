package christmas.utils;

public enum Menu {
    양송이수프(6_000, MenuCategory.APPETIZER),
    타파스(5_500, MenuCategory.APPETIZER),
    시저샐러드(8_000, MenuCategory.APPETIZER),
    티본스테이크(55_000, MenuCategory.MAIN),
    바비큐립(54_000, MenuCategory.MAIN),
    해산물파스타(35_000, MenuCategory.MAIN),
    크리스마스파스타(25_000, MenuCategory.MAIN),
    초코케이크(15_000, MenuCategory.DESSERT),
    아이스크림(5_000, MenuCategory.DESSERT),
    제로콜라(3_000, MenuCategory.BEVERAGE),
    레드와인(60_000, MenuCategory.BEVERAGE),
    샴페인(25_000, MenuCategory.BEVERAGE);

    private final int price;
    private final MenuCategory menuCategory;

    Menu(int price, MenuCategory menuCategory) {
        this.price = price;
        this.menuCategory = menuCategory;
    }

    public int getPrice() {
        return price;
    }

    public MenuCategory getMenuCategory() {
        return menuCategory;
    }
}
