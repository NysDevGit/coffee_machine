package model;

import model.exception.TooMuchSugarException;
import model.type.DrinkType;

public class Drink {
    private final DrinkType type;
    private int sugarNumber;

    public Drink(DrinkType type, int sugarNumber){
        this.type = type;
        this.sugarNumber = sugarNumber;
        if(sugarNumber >  2)
            throw new TooMuchSugarException("Too much sugar!");
    }

    public DrinkType getType() {
        return type;
    }

    public int getSugarNumber() {
        return sugarNumber;
    }

}
