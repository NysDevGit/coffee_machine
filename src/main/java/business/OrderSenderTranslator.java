package business;

import model.Order;

import java.util.StringJoiner;

public class OrderSenderTranslator {

    public static String translateOrder(Order order){
        StringJoiner stringOrder = new StringJoiner(":");

        stringOrder.add(order.getTypeCode());
        stringOrder.add(order.getSugarQuantity());

        return  stringOrder.toString();
    }
}
