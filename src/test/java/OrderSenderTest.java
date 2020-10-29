import business.DrinkMaker;
import business.OrderSender;
import model.ColdDrink;
import model.HotDrink;
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
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ONE,false);
        new OrderSender(drinkMaker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("T:1:0");
    }

    @Test
    public void shouldMakeACoffeeWith2Sugars(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.TWO,false);
        new OrderSender(drinkMaker).send(hotDrink,1.0);
        verify(drinkMaker, times(1)).process("C:2:0");
    }

    @Test
    public void shouldMakeAChocolateWithoutSugar(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,1.0);
        verify(drinkMaker, times(1)).process("H::");
    }

    @Test
    public void shouldMakeAChocolateIfThereIsEnoughMoney(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.5);
        verify(drinkMaker, times(1)).process("H::");
    }

    @Test
    public void shouldMakeACoffeeIfThereIsEnoughMoney(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.6);
        verify(drinkMaker, times(1)).process("C::");
    }

    @Test
    public void shouldMakeATeaIfThereIsEnoughMoney(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.4);
        verify(drinkMaker, times(1)).process("T::");
    }

    @Test
    public void shouldMakeAChocolateIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.8);
        verify(drinkMaker, times(1)).process("H::");
    }

    @Test
    public void shouldMakeACoffeeIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ONE,false);
        new OrderSender(drinkMaker).send(hotDrink,0.9);
        verify(drinkMaker, times(1)).process("C:1:0");
    }

    @Test
    public void shouldMakeATeaIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ONE,false);
        new OrderSender(drinkMaker).send(hotDrink,0.5);
        verify(drinkMaker, times(1)).process("T:1:0");
    }


    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForChocolate(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.2);
        verify(drinkMaker, times(1)).process("M:Missing 0.3 euro");
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForCoffee(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.4);
        verify(drinkMaker, times(1)).process("M:Missing 0.2 euro");
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForTea(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker).send(hotDrink,0.3);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
    }

    @Test
    public void shouldMakeAnOrangeJuice(){
        ColdDrink coldDrink = new ColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker).send(coldDrink,0.6);
        verify(drinkMaker, times(1)).process("O::");
    }

    @Test
    public void shouldMakeAnOrangeJuiceIfThereIsMoreMoneyThanExpected(){
        ColdDrink coldDrink = new ColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker).send(coldDrink,0.9);
        verify(drinkMaker, times(1)).process("O::");
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForOrangeJuice(){
        ColdDrink coldDrink = new ColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker).send(coldDrink,0.5);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
    }

    @Test
    public void shouldMakeAnExtraHotCoffee(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ONE,true);
        new OrderSender(drinkMaker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Ch:1:0");
    }

    @Test
    public void shouldMakeAnExtraHotChocolate(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ONE,true);
        new OrderSender(drinkMaker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Hh:1:0");
    }

    @Test
    public void shouldMakeAnExtraHotTea(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ONE,true);
        new OrderSender(drinkMaker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Th:1:0");
    }
    private HotDrink createDrink(DrinkType type, SugarNumber sugarNumber, boolean isExtraHot){
        return  new HotDrink(type,sugarNumber, isExtraHot);
    }

}
