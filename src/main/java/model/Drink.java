package model;

public class Drink {

    private final DrinkType type;
    private final SugarNumber sugarNumber;
    private Boolean extraHot = null;

    public Drink(DrinkType type, SugarNumber sugarNumber, Boolean extraHot){
        this.type = type;
        this.sugarNumber = sugarNumber;
        this.extraHot = extraHot;
    }

    public Drink(DrinkType type, SugarNumber sugarNumber){
        this.type = type;
        this.sugarNumber = sugarNumber;
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

    public Boolean isExtraHot(){
        return extraHot;
    }
}
