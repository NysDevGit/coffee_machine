package model;

public class HotDrink extends Drink{

    private final boolean extraHot;

    public HotDrink(DrinkType type, SugarNumber sugarNumber, boolean isExtraHot){
        super(type, sugarNumber);
        this.extraHot = isExtraHot;
    }

    public boolean isExtraHot(){
        return extraHot;
    }
}
