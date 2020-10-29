package model;

public enum SugarNumber {
    ZERO(0),
    ONE(1),
    TWO(2);

    private final int value;

    SugarNumber(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
