package model.order;

public enum SugarQuantity {
    ZERO(":"),
    ONE("1:0"),
    TWO("2:0");

    private final String quantity;

    SugarQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }
}
