package model;

public class Drink {
    private final DrinkType type;
    private final SugarNumber sugarNumber;

    public Drink(DrinkType type, SugarNumber sugarNumber){
        this.type = type;
        this.sugarNumber = sugarNumber;
    }

    public DrinkType getType() {
        return type;
    }

    public int getSugarNumber() {
        return sugarNumber.getValue();
    }

}
