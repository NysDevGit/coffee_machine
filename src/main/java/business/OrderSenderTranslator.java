package business;

import model.Order;

import java.util.StringJoiner;

public class OrderSenderTranslator {

    public static String translateOrder(Order order){
        StringJoiner stringOrder = new StringJoiner(":");

        stringOrder.add(order.getCode());
        stringOrder.add(order.getSugarQuantity());

        return  stringOrder.toString();
    }

    public static String translateMessage(String message){
        return "M:"+ message ;
    }
}
