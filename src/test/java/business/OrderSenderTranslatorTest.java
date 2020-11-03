package business;

import helper.OrderSenderTestHelper;
import model.Order;
import model.OrderType;
import model.SugarQuantity;
import org.junit.Assert;
import org.junit.Test;

public class OrderSenderTranslatorTest {

    @Test
    public void shouldTranslateATeaWith1Sugar(){
        Order order = OrderSenderTestHelper.createOrder(OrderType.TEA, SugarQuantity.ONE);
        String translatedOrder = OrderSenderTranslator.translateOrder(order);
        Assert.assertEquals("T:1:0", translatedOrder);
    }

    @Test
    public void shouldTranslateACoffeeWith2Sugars(){
        Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.TWO);
        String translatedOrder = OrderSenderTranslator.translateOrder(order);
        Assert.assertEquals("C:2:0", translatedOrder);
    }

    @Test
    public void shouldTranslateAChocolateWithoutSugar(){
        Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.ZERO);
        String translatedOrder = OrderSenderTranslator.translateOrder(order);
        Assert.assertEquals("H::", translatedOrder);
    }

}
