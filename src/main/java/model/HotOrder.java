package model;

import exception.IncompatibleDrinkTypeOrderException;

public class HotOrder extends Order{

    private final boolean extraHot;

    public HotOrder(OrderType type, SugarQuantity sugarQuantity, boolean extraHot){
        super(type);
        this.sugarQuantity = sugarQuantity;
        this.extraHot = extraHot;
        if(OrderType.ORANGE_JUICE.equals(type))
            throw new IncompatibleDrinkTypeOrderException();
    }

    public String getCode() {
       return extraHot ? this.getType().getCode()+"h" : this.getType().getCode();
    }

}
