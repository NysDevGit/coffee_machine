package business;

import model.exception.TooMuchSugarException;
import model.Drink;
import model.exception.NotEnoughMoneyException;

import java.util.StringJoiner;

public class OrderSender {

    private final DrinkMaker drinkMaker;

    public OrderSender(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
    }

    public void send(Drink order, double money){
        checkIfThereIsTooMuchSugar(order);
        checkIfThereIsEnoughMoney(order, money);
        String stringOrder = translateOrder(order);
        drinkMaker.process(stringOrder);
    }

    private void checkIfThereIsTooMuchSugar(Drink order) {
        if(order.getSugarNumber() >  2) {
            String message = translateMessage("Too much sugar");
            drinkMaker.process(message);
            throw new TooMuchSugarException();
        }
    }

    private void checkIfThereIsEnoughMoney(Drink order, double money) {
        if(money < order.getType().getPrice()){
            String message = translateMessage("Missing "+(order.getType().getPrice()-money)+" euro");
            drinkMaker.process(message);
            throw new NotEnoughMoneyException();
        }
    }

    private String translateOrder(Drink drinkOrder){
        StringJoiner stringOrder = new StringJoiner(":");

        stringOrder.add(drinkOrder.getType().getCode());
        stringOrder.add(drinkOrder.getSugarNumber() > 0 ? String.valueOf(drinkOrder.getSugarNumber()) : "");
        stringOrder.add(drinkOrder.getSugarNumber() > 0  ? "0" : "");

        return  stringOrder.toString();
    }

    private String translateMessage(String message){
        return "M:"+ message ;
    }

}
