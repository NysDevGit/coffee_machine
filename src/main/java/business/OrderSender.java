package business;

import exception.LackOfMoneyException;
import model.order.Order;

public class OrderSender {

    private final DrinkMaker drinkMaker;

    public OrderSender(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
    }

    public void send(Order order, double money){
        this.checkIfMoneyAmountIsSufficient(order,money);
        String translatedOrder = OrderTranslator.translateOrder(order);
        drinkMaker.process(translatedOrder);
    }

    private void checkIfMoneyAmountIsSufficient(Order order, double money){
        if(money < order.getPrice()){
            String message = OrderTranslator.translateMessage("Missing "+(order.getPrice() - money)+" euro");
            drinkMaker.process(message);
            throw new LackOfMoneyException();
        }
    }
}
