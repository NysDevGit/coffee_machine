package model;

public enum SugarQuantity {
    ZERO(":"),
    ONE("1:0"),
    TWO("2:0");

    private final String quantity;

    SugarQuantity(String value) {
        this.quantity = value;
    }

    public String getQuantity() {
        return quantity;
    }
}
