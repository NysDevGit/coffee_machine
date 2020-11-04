package model.order.type;

public class OrderType {
    protected final String code;
    protected final double price;

    OrderType(String code, double price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }
}