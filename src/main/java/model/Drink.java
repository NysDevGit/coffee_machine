package model;

public class Drink {

    private final DrinkType type;
    private final SugarNumber sugarNumber;
    private boolean extraHot = false;

    public Drink(DrinkType type, SugarNumber sugarNumber, boolean extraHot){
        this.type = type;
        this.sugarNumber = sugarNumber;
        this.extraHot = extraHot;
    }

    public Drink(DrinkType type){
        this.type = type;
        this.sugarNumber = SugarNumber.ZERO;
    }

    public DrinkType getType() {
        return type;
    }

    public String getSugarNumber() {
        return sugarNumber.getValue();
    }

    public boolean isExtraHot(){
        return extraHot;
    }
}
