package model;

import model.exception.TooMuchSugarException;
import model.type.DrinkType;

public class Drink {
    private final DrinkType type;
    private int sugarNumber;
    private boolean withStick;

    public Drink(DrinkType type, int sugarNumber){
        this.type = type;
        this.sugarNumber = sugarNumber;
        if(sugarNumber >  2)
            throw new TooMuchSugarException("Too much sugar!");
        this.withStick = sugarNumber > 0 && sugarNumber <= 2;
    }

    public DrinkType getType() {
        return type;
    }

    public int getSugarNumber() {
        return sugarNumber;
    }

    public boolean isWithStick() {
        return withStick;
    }
}
