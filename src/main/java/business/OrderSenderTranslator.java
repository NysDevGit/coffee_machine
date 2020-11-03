package business;

import model.Order;

import java.util.StringJoiner;

public class OrderSenderTranslator {

    public static String translateOrder(Order drinkOrder){
        StringJoiner stringOrder = new StringJoiner(":");

        stringOrder.add(drinkOrder.getTypeCode());
        stringOrder.add(drinkOrder.getSugarQuantity());

        return  stringOrder.toString();
    }
}
