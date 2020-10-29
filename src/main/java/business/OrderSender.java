package business;

import model.Drink;
import model.exception.NotEnoughMoneyException;
import org.apache.commons.lang3.BooleanUtils;

import java.util.StringJoiner;

public class OrderSender {

    private final DrinkMaker drinkMaker;

    public OrderSender(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
    }

    public void send(Drink order, double money){
        checkIfThereIsEnoughMoney(order, money);
        String stringOrder = translateOrder(order);
        drinkMaker.process(stringOrder);
    }

    private void checkIfThereIsEnoughMoney(Drink order, double money) {
        if(money < order.getType().getPrice()){
            String message = translateMessage("Missing "+(order.getType().getPrice()-money)+" euro");
            drinkMaker.process(message);
            throw new NotEnoughMoneyException();
        }
    }

    private String translateOrder(Drink drink){
        StringJoiner stringOrder = new StringJoiner(":");
        String code = drink.getType().getCode();

        if(BooleanUtils.isTrue(drink.isExtraHot()))
            code += "h";

        stringOrder.add(code);
        stringOrder.add(drink.getSugarNumber());

        return  stringOrder.toString();
    }

    private String translateMessage(String message){
        return "M:"+ message ;
    }

}
