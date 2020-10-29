package model;

import model.type.DrinkType;

public class Drink {
    private final DrinkType type;
    private final int sugarNumber;

    public Drink(DrinkType type, int sugarNumber){
        this.type = type;
        this.sugarNumber = sugarNumber;
    }

    public DrinkType getType() {
        return type;
    }

    public int getSugarNumber() {
        return sugarNumber;
    }

}
