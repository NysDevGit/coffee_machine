package business;

import model.Drink;

import java.util.StringJoiner;

public class OrderSender {

    private final DrinkMaker drinkMaker;

    public OrderSender(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
    }

    public void send(Drink order){
        String stringOrder = translateOrder(order);
        drinkMaker.prepare(stringOrder);
    }

    private String translateOrder(Drink drinkOrder){
        StringJoiner stringOrder = new StringJoiner(":");

        stringOrder.add(drinkOrder.getType().getCode());
        stringOrder.add(drinkOrder.getSugarNumber() > 0 ? String.valueOf(drinkOrder.getSugarNumber()) : "");

        return  stringOrder.toString();
    }

}
