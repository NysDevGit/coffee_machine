package business;

import exception.LackOfMoneyException;
import helper.OrderSenderTestHelper;
import model.Order;
import model.OrderType;
import model.SugarQuantity;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class OrderSenderTest {

    @Mock
    private DrinkMaker drinkMaker;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    class sendOrderWithoutSugarAndWithoutStick{

        @Test
        public void shouldSendACoffeeWithoutSugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.6);
            verify(drinkMaker, times(1)).process("C::");
        }

        @Test
        public void shouldSendAChocolateWithoutSugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("H::");
        }

        @Test
        public void shouldSendATeaWithoutSugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA,SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.4);
            verify(drinkMaker, times(1)).process("T::");
        }
    }

    @Nested
    class sendOrderWithOneSugarAndStick{

        @Test
        public void shouldSendACoffeeWith1Sugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.ONE);
            new OrderSender(drinkMaker).send(order,0.46);
            verify(drinkMaker, times(1)).process("C:1:0");
        }

        @Test
        public void shouldSendAChocolateWith1Sugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.ONE);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("H:1:0");
        }

        @Test
        public void shouldSendATeaWith1Sugar(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA,SugarQuantity.ONE);
            new OrderSender(drinkMaker).send(order,0.4);
            verify(drinkMaker, times(1)).process("T:1:0");
        }
    }

    @Nested
    class sendOrderWithTwoSugarAndStick{

        @Test
        public void shouldSendACoffeeWith2Sugars(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.TWO);
            new OrderSender(drinkMaker).send(order, 0.6);
            verify(drinkMaker, times(1)).process("C:2:0");
        }

        @Test
        public void shouldSendAChocolateWith2Sugars(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.TWO);
            new OrderSender(drinkMaker).send(order, 0.5);
            verify(drinkMaker, times(1)).process("H:2:0");
        }

        @Test
        public void shouldSendATeaWith2Sugars(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA,SugarQuantity.TWO);
            new OrderSender(drinkMaker).send(order, 0.4);
            verify(drinkMaker, times(1)).process("T:2:0");
        }
    }

    @Nested
    class sendOrderExactMoney {
        @Test
        public void shouldSendAChocolateWhenGivingTheExactMoney(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE, SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("H::");
        }

        @Test
        public void shouldSendACoffeeWhenGivingTheExactMoney(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE, SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.6);
            verify(drinkMaker, times(1)).process("C::");
        }

        @Test
        public void shouldSendATeaWhenGivingTheExactMoney(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA, SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.4);
            verify(drinkMaker, times(1)).process("T::");
        }
    }

    @Nested
    class sendOrderExtraMoney {

        @Test
        public void shouldSendAChocolateWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE, SugarQuantity.ZERO);
            new OrderSender(drinkMaker).send(order,0.8);
            verify(drinkMaker, times(1)).process("H::");
        }

        @Test
        public void shouldSendACoffeeWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE, SugarQuantity.ONE);
            new OrderSender(drinkMaker).send(order,0.9);
            verify(drinkMaker, times(1)).process("C:1:0");
        }

        @Test
        public void shouldSendATeaWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA, SugarQuantity.ONE);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("T:1:0");
        }

    }

    @Nested
    class sendMessageLackOfMoney {

        @Test
        public void shouldThrowsLackOfMoneyExceptionForChocolate(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE, SugarQuantity.ZERO);
            Assertions.assertThrows(LackOfMoneyException.class, () -> new OrderSender(drinkMaker).send(order,0.2));
            verify(drinkMaker, times(1)).process("M:Missing 0.3 euro");
        }

        @Test()
        public void shouldThrowsLackOfMoneyExceptionForCoffee(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE, SugarQuantity.ZERO);
            Assertions.assertThrows(LackOfMoneyException.class, () -> new OrderSender(drinkMaker).send(order,0.4));
            verify(drinkMaker, times(1)).process("M:Missing 0.2 euro");
        }

        @Test()
        public void shouldThrowsLackOfMoneyExceptionForTea(){
            Order order = OrderSenderTestHelper.createOrder(OrderType.TEA, SugarQuantity.ZERO);
            Assertions.assertThrows(LackOfMoneyException.class, () -> new OrderSender(drinkMaker).send(order,0.3));
            verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
        }
    }

}
