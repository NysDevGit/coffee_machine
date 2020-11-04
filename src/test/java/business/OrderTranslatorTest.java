package business;

import helper.OrderSenderTestHelper;
import model.order.Order;
import model.order.type.name.Chocolate;
import model.order.type.name.Coffee;
import model.order.SugarQuantity;
import model.order.type.name.Tea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OrderTranslatorTest {

    @Nested
    class translateOrderWithoutSugar {

        @Test
        public void shouldTranslateACoffeeWithoutSugar(){
            Order order = OrderSenderTestHelper.createHotOrder(new Coffee(),SugarQuantity.ZERO,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("C::", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWithoutSugar(){
            Order order = OrderSenderTestHelper.createHotOrder(new Chocolate(),SugarQuantity.ZERO,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("H::", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWithoutSugar(){
            Order order = OrderSenderTestHelper.createHotOrder(new Tea(),SugarQuantity.ZERO,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("T::", translatedOrder);
        }
    }

    @Nested
    class translateOrderWithOneSugar {

        @Test
        public void shouldTranslateACoffeeWith1Sugar(){
            Order order = OrderSenderTestHelper.createHotOrder(new Coffee(), SugarQuantity.ONE,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("C:1:0", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWith1Sugar(){
            Order order = OrderSenderTestHelper.createHotOrder(new Chocolate(), SugarQuantity.ONE,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("H:1:0", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWith1Sugar(){
            Order order = OrderSenderTestHelper.createHotOrder(new Tea(), SugarQuantity.ONE,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("T:1:0", translatedOrder);
        }
    }

    @Nested
    class translateOrderWithTwoSugar{

        @Test
        public void shouldTranslateACoffeeWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(new Coffee(),SugarQuantity.TWO,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("C:2:0", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(new Chocolate(),SugarQuantity.TWO,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("H:2:0", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(new Tea(),SugarQuantity.TWO,false);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("T:2:0", translatedOrder);
        }
    }

    @Nested
    class translateExtraHotOrder{

        @Test
        public void shouldTranslateACoffeeWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(new Coffee(),SugarQuantity.TWO,true);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("Ch:2:0", translatedOrder);
        }

        @Test
        public void shouldTranslateAChocolateWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(new Chocolate(),SugarQuantity.TWO,true);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("Hh:2:0", translatedOrder);
        }

        @Test
        public void shouldTranslateATeaWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(new Tea(),SugarQuantity.TWO,true);
            String translatedOrder = OrderTranslator.translateOrder(order);
            Assertions.assertEquals("Th:2:0", translatedOrder);
        }
    }

}
