package model;

public enum SugarNumber {
    ZERO(":"),
    ONE("1:0"),
    TWO("2:0");

    private final String value;

    SugarNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
