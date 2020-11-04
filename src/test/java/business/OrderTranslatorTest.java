package business;

import helper.OrderSenderTestHelper;
import model.Order;
import model.OrderType;
import model.SugarQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OrderTranslatorTest {

    @Nested
    class translateOrderWithoutSugar {

        @Test
        public void shouldTranslateACoffeeWithoutSugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.ZERO);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("C::", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWithoutSugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.ZERO);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("H::", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWithoutSugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA,SugarQuantity.ZERO);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("T::", translatedOrder);
        }
    }

    @Nested
    class translateOrderWithOneSugar {

        @Test
        public void shouldTranslateACoffeeWith1Sugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE, SugarQuantity.ONE);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("C:1:0", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWith1Sugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE, SugarQuantity.ONE);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("H:1:0", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWith1Sugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA, SugarQuantity.ONE);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("T:1:0", translatedOrder);
        }
    }

    @Nested
    class translateOrderWithTwoSugar{

        @Test
        public void shouldTranslateACoffeeWith2Sugars(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.TWO);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("C:2:0", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWith2Sugars(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.TWO);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("H:2:0", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWith2Sugars(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA,SugarQuantity.TWO);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("T:2:0", translatedOrder);
        }
    }

}
