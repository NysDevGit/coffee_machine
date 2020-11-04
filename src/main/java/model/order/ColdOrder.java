package model.order;

import model.order.type.name.OrangeJuice;

public class ColdOrder extends Order{

    public ColdOrder(){
        super(new OrangeJuice());
        this.sugarQuantity = SugarQuantity.ZERO;
    }
}
