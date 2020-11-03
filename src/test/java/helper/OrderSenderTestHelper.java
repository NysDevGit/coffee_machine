package helper;

import model.Order;
import model.OrderType;
import model.SugarQuantity;

public class OrderSenderTestHelper {

    public static Order createOrder(OrderType type, SugarQuantity sugarQuantity){
        return  new Order(type,sugarQuantity);
    }

}
