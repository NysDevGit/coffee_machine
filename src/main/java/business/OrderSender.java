package business;

import model.Order;

public class OrderSender {

    private final DrinkMaker drinkMaker;

    public OrderSender(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
    }

    public void send(Order order){
        String translatedOrder = OrderSenderTranslator.translateOrder(order);
        drinkMaker.prepare(translatedOrder);
    }
}
