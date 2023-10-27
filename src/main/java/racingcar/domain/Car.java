package racingcar.domain;

public class Car {
    private final String name;
    private int position;

    private Car(String name) {
        this.name = name;
        this.position = 0;
    }

    public static Car createCar(String name) {
        return new Car(name);
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
