import business.DrinkMaker;
import business.OrderSender;
import model.Drink;
import model.exception.TooMuchSugarException;
import model.type.DrinkType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Or;

import static org.junit.Assert.*;
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
    public void shouldMakeATeaWith1Sugar(){
        Drink drink = createDrink(DrinkType.TEA,1);
        new OrderSender(drinkMaker).send(drink);
        verify(drinkMaker, times(1)).prepare("T:1:0");
    }

    @Test
    public void shouldMakeACoffeeWith2Sugars(){
        Drink drink = createDrink(DrinkType.COFFEE,2);
        new OrderSender(drinkMaker).send(drink);
        verify(drinkMaker, times(1)).prepare("C:2:0");
    }

    @Test
    public void shouldMakeAChocolateWithoutSugar(){
        Drink drink = createDrink(DrinkType.CHOCOLATE,0);
        new OrderSender(drinkMaker).send(drink);
        verify(drinkMaker, times(1)).prepare("H::");
    }

    @Test(expected = TooMuchSugarException.class)
    public void shouldThrowTooMuchSugarExceptionWhenThereAreMoreThanTwoSugar(){
        createDrink(DrinkType.CHOCOLATE,3);
    }


    private Drink createDrink(DrinkType type, int sugarNumber){
        return  new Drink(type,sugarNumber);
    }


}
