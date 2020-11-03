package model;

public class Order {
    private final OrderType type;
    private final SugarQuantity sugarQuantity;

    public Order(OrderType type, SugarQuantity sugarQuantity){
        this.type = type;
        this.sugarQuantity = sugarQuantity;
    }

    public String getTypeCode() {
        return type.getCode();
    }

    public String getSugarQuantity() {
        return sugarQuantity.getQuantity();
    }

}
