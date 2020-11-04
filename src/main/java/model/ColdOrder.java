package model;

public class ColdOrder extends Order{

    public ColdOrder(){
        super(OrderType.ORANGE_JUICE);
        this.sugarQuantity = SugarQuantity.ZERO;
    }
}
