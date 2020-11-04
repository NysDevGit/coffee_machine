package model.order;

import model.order.type.HotOrderType;

public class HotOrder extends Order{

    private final boolean extraHot;

    public HotOrder(HotOrderType type, SugarQuantity sugarQuantity, boolean extraHot){
        super(type);
        this.sugarQuantity = sugarQuantity;
        this.extraHot = extraHot;
    }

    public String getCode() {
        return extraHot ? this.getType().getCode()+"h" : this.getType().getCode();
    }

}
