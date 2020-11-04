package business;

import exception.IncompatibleDrinkTypeOrderException;
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
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE,SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.6);
            verify(drinkMaker, times(1)).process("C::");
        }

        @Test
        public void shouldSendAChocolateWithoutSugar(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE,SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("H::");
        }

        @Test
        public void shouldSendATeaWithoutSugar(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA,SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.4);
            verify(drinkMaker, times(1)).process("T::");
        }
    }

    @Nested
    class sendOrderWithOneSugarAndStick{

        @Test
        public void shouldSendACoffeeWith1Sugar(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE,SugarQuantity.ONE, false);
            new OrderSender(drinkMaker).send(order,0.46);
            verify(drinkMaker, times(1)).process("C:1:0");
        }

        @Test
        public void shouldSendAChocolateWith1Sugar(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE,SugarQuantity.ONE, false);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("H:1:0");
        }

        @Test
        public void shouldSendATeaWith1Sugar(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA,SugarQuantity.ONE, false);
            new OrderSender(drinkMaker).send(order,0.4);
            verify(drinkMaker, times(1)).process("T:1:0");
        }
    }

    @Nested
    class sendOrderWithTwoSugarAndStick{

        @Test
        public void shouldSendACoffeeWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE,SugarQuantity.TWO, false);
            new OrderSender(drinkMaker).send(order, 0.6);
            verify(drinkMaker, times(1)).process("C:2:0");
        }

        @Test
        public void shouldSendAChocolateWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE,SugarQuantity.TWO, false);
            new OrderSender(drinkMaker).send(order, 0.5);
            verify(drinkMaker, times(1)).process("H:2:0");
        }

        @Test
        public void shouldSendATeaWith2Sugars(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA,SugarQuantity.TWO, false);
            new OrderSender(drinkMaker).send(order, 0.4);
            verify(drinkMaker, times(1)).process("T:2:0");
        }
    }

    @Nested
    class sendOrderExactMoney {
        @Test
        public void shouldSendAChocolateWhenGivingTheExactMoney(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE, SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("H::");
        }

        @Test
        public void shouldSendACoffeeWhenGivingTheExactMoney(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE, SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.6);
            verify(drinkMaker, times(1)).process("C::");
        }

        @Test
        public void shouldSendATeaWhenGivingTheExactMoney(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA, SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.4);
            verify(drinkMaker, times(1)).process("T::");
        }

        @Test
        public void shouldSendAnOrangeJuiceWhenGivingExactMoney(){
            Order order = OrderSenderTestHelper.createColdOrder();
            new OrderSender(drinkMaker).send(order,0.6);
            verify(drinkMaker, times(1)).process("O::");
        }

    }

    @Nested
    class sendOrderExtraMoney {

        @Test
        public void shouldSendAChocolateWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE, SugarQuantity.ZERO, false);
            new OrderSender(drinkMaker).send(order,0.8);
            verify(drinkMaker, times(1)).process("H::");
        }

        @Test
        public void shouldSendACoffeeWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE, SugarQuantity.ONE, false);
            new OrderSender(drinkMaker).send(order,0.9);
            verify(drinkMaker, times(1)).process("C:1:0");
        }

        @Test
        public void shouldSendATeaWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA, SugarQuantity.ONE, false);
            new OrderSender(drinkMaker).send(order,0.5);
            verify(drinkMaker, times(1)).process("T:1:0");
        }

        @Test
        public void shouldSendAnOrangeJuiceWhenGivingExtraMoney(){
            Order order = OrderSenderTestHelper.createColdOrder();
            new OrderSender(drinkMaker).send(order,0.8);
            verify(drinkMaker, times(1)).process("O::");
        }

    }

    @Nested
    class sendMessageLackOfMoney {

        @Test
        public void shouldThrowsLackOfMoneyExceptionForChocolate(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE, SugarQuantity.ZERO, false);
            Assertions.assertThrows(LackOfMoneyException.class, () -> new OrderSender(drinkMaker).send(order,0.2));
            verify(drinkMaker, times(1)).process("M:Missing 0.3 euro");
        }

        @Test()
        public void shouldThrowsLackOfMoneyExceptionForCoffee(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE, SugarQuantity.ZERO, false);
            Assertions.assertThrows(LackOfMoneyException.class, () -> new OrderSender(drinkMaker).send(order,0.4));
            verify(drinkMaker, times(1)).process("M:Missing 0.2 euro");
        }

        @Test()
        public void shouldThrowsLackOfMoneyExceptionForTea(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA, SugarQuantity.ZERO, false);
            Assertions.assertThrows(LackOfMoneyException.class, () -> new OrderSender(drinkMaker).send(order,0.3));
            verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
        }
    }

    @Nested
    class sendColdOrderOrangeJuice {

        @Test
        public void shouldSendAnOrangeJuice() {
            Order order = OrderSenderTestHelper.createColdOrder();
            new OrderSender(drinkMaker).send(order, 0.5);
            verify(drinkMaker, times(1)).process("O::");
        }

        @Test
        public void shouldThrowsIncompatibleDrinkTypeOrderExceptionWhenOrangeJuiceInstantiateAsHotDrink() {
            Assertions.assertThrows(IncompatibleDrinkTypeOrderException.class,
                    () -> OrderSenderTestHelper.createHotOrder(OrderType.ORANGE_JUICE,SugarQuantity.ZERO,false),
                    "You can't instantiate a cold drink as hot drink");
        }

    }

    @Nested
    class sendExtraHotOrder {

        @Test
        public void shouldSendAnExtraHotCoffee(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.COFFEE,SugarQuantity.TWO, true);
            new OrderSender(drinkMaker).send(order, 0.6);
            verify(drinkMaker, times(1)).process("Ch:2:0");
        }

        @Test
        public void shouldSendAnExtraHotChocolate(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.CHOCOLATE,SugarQuantity.TWO, true);
            new OrderSender(drinkMaker).send(order, 0.5);
            verify(drinkMaker, times(1)).process("Hh:2:0");
        }

        @Test
        public void shouldSendAnExtraHotTea(){
            Order order = OrderSenderTestHelper.createHotOrder(OrderType.TEA,SugarQuantity.TWO, true);
            new OrderSender(drinkMaker).send(order, 0.4);
            verify(drinkMaker, times(1)).process("Th:2:0");
        }
    }

}
