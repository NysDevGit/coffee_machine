package business;

import helper.OrderSenderTestHelper;
import model.Order;
import model.OrderType;
import model.SugarQuantity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class OrderSenderTest {

    @Mock
    private DrinkMaker drinkMaker;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSendATeaWith1Sugar(){
        Order order = OrderSenderTestHelper.createOrder(OrderType.TEA,SugarQuantity.ONE);
        new OrderSender(drinkMaker).send(order);
        verify(drinkMaker, times(1)).prepare("T:1:0");
    }

    @Test
    public void shouldSendACoffeeWith2Sugars(){
        Order order = OrderSenderTestHelper.createOrder(OrderType.COFFEE,SugarQuantity.TWO);
        new OrderSender(drinkMaker).send(order);
        verify(drinkMaker, times(1)).prepare("C:2:0");
    }

    @Test
    public void shouldSendAChocolateWithoutSugar(){
        Order order = OrderSenderTestHelper.createOrder(OrderType.CHOCOLATE,SugarQuantity.ZERO);
        new OrderSender(drinkMaker).send(order);
        verify(drinkMaker, times(1)).prepare("H::");
    }


}
