package model.order;

import model.order.type.OrderType;

public class Order {
    protected final OrderType type;
    protected SugarQuantity sugarQuantity;

    protected Order(OrderType type){
        this.type = type;
    }

    public OrderType getType() {
        return type;
    }

    public String getSugarQuantity() {
        return sugarQuantity.getQuantity();
    }

    public String getCode() {
        return this.getType().getCode();
    }

    public double getPrice() {
        return this.getType().getPrice();
    }
}
