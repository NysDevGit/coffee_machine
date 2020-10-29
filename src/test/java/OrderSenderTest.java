import business.DrinkMaker;
import business.OrderSender;
import model.Drink;
import model.exception.NotEnoughMoneyException;
import model.DrinkType;
import model.SugarNumber;
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
    public void shouldMakeATeaWith1Sugar(){
        Drink drink = createDrink(DrinkType.TEA, SugarNumber.ONE);
        new OrderSender(drinkMaker).send(drink, 1.0);
        verify(drinkMaker, times(1)).process("T:1:0");
    }

    @Test
    public void shouldMakeACoffeeWith2Sugars(){
        Drink drink = createDrink(DrinkType.COFFEE, SugarNumber.TWO);
        new OrderSender(drinkMaker).send(drink,1.0);
        verify(drinkMaker, times(1)).process("C:2:0");
    }

    @Test
    public void shouldMakeAChocolateWithoutSugar(){
        Drink drink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,1.0);
        verify(drinkMaker, times(1)).process("H::");
    }

    @Test
    public void shouldMakeAChocolateIfThereIsEnoughMoney(){
        Drink drink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.5);
        verify(drinkMaker, times(1)).process("H::");
    }

    @Test
    public void shouldMakeACoffeeIfThereIsEnoughMoney(){
        Drink drink = createDrink(DrinkType.COFFEE, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.6);
        verify(drinkMaker, times(1)).process("C::");
    }

    @Test
    public void shouldMakeATeaIfThereIsEnoughMoney(){
        Drink drink = createDrink(DrinkType.TEA, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.4);
        verify(drinkMaker, times(1)).process("T::");
    }

    @Test
    public void shouldMakeAChocolateIfThereIsMoreMoneyThanExpected(){
        Drink drink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.8);
        verify(drinkMaker, times(1)).process("H::");
    }

    @Test
    public void shouldMakeACoffeeIfThereIsMoreMoneyThanExpected(){
        Drink drink = createDrink(DrinkType.COFFEE, SugarNumber.ONE);
        new OrderSender(drinkMaker).send(drink,0.9);
        verify(drinkMaker, times(1)).process("C:1:0");
    }

    @Test
    public void shouldMakeATeaIfThereIsMoreMoneyThanExpected(){
        Drink drink = createDrink(DrinkType.TEA, SugarNumber.ONE);
        new OrderSender(drinkMaker).send(drink,0.5);
        verify(drinkMaker, times(1)).process("T:1:0");
    }


    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForChocolate(){
        Drink drink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.2);
        verify(drinkMaker, times(1)).process("M:Missing 0.3 euro");
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForCoffee(){
        Drink drink = createDrink(DrinkType.COFFEE, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.4);
        verify(drinkMaker, times(1)).process("M:Missing 0.2 euro");
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForTea(){
        Drink drink = createDrink(DrinkType.TEA, SugarNumber.ZERO);
        new OrderSender(drinkMaker).send(drink,0.3);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
    }

    private Drink createDrink(DrinkType type, SugarNumber sugarNumber){
        return  new Drink(type,sugarNumber);
    }

}
