package business;

import model.ColdDrink;
import model.HotDrink;
import model.exception.NotEnoughMoneyException;
import model.DrinkType;
import model.SugarNumber;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.AccountReportRepository;

import static org.mockito.Mockito.*;

public class OrderSenderTest {

    @Mock
    private DrinkMaker drinkMaker;

    @Mock
    private AccountReportRepository accountReportRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMakeATeaWith1Sugar(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ONE,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("T:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeACoffeeWith2Sugars(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.TWO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,1.0);
        verify(drinkMaker, times(1)).process("C:2:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAChocolateWithoutSugar(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,1.0);
        verify(drinkMaker, times(1)).process("H::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAChocolateIfThereIsEnoughMoney(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.5);
        verify(drinkMaker, times(1)).process("H::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeACoffeeIfThereIsEnoughMoney(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.6);
        verify(drinkMaker, times(1)).process("C::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeATeaIfThereIsEnoughMoney(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.4);
        verify(drinkMaker, times(1)).process("T::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAChocolateIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.8);
        verify(drinkMaker, times(1)).process("H::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeACoffeeIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ONE,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.9);
        verify(drinkMaker, times(1)).process("C:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeATeaIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ONE,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.5);
        verify(drinkMaker, times(1)).process("T:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }


    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForChocolate(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.2);
        verify(drinkMaker, times(1)).process("M:Missing 0.3 euro");
        verify(accountReportRepository, never()).save(hotDrink);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForCoffee(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.4);
        verify(drinkMaker, times(1)).process("M:Missing 0.2 euro");
        verify(accountReportRepository, never()).save(hotDrink);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForTea(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink,0.3);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
        verify(accountReportRepository, never()).save(hotDrink);
    }

    @Test
    public void shouldMakeAnOrangeJuice(){
        ColdDrink coldDrink = new ColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker, accountReportRepository).send(coldDrink,0.6);
        verify(drinkMaker, times(1)).process("O::");
        verify(accountReportRepository, times(1)).save(coldDrink);
    }

    @Test
    public void shouldMakeAnOrangeJuiceIfThereIsMoreMoneyThanExpected(){
        ColdDrink coldDrink = new ColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker, accountReportRepository).send(coldDrink,0.9);
        verify(drinkMaker, times(1)).process("O::");
        verify(accountReportRepository, times(1)).save(coldDrink);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForOrangeJuice(){
        ColdDrink coldDrink = new ColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker, accountReportRepository).send(coldDrink,0.5);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
        verify(accountReportRepository, never()).save(coldDrink);
    }

    @Test
    public void shouldMakeAnExtraHotCoffee(){
        HotDrink hotDrink = createDrink(DrinkType.COFFEE, SugarNumber.ONE,true);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Ch:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAnExtraHotChocolate(){
        HotDrink hotDrink = createDrink(DrinkType.CHOCOLATE, SugarNumber.ONE,true);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Hh:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAnExtraHotTea(){
        HotDrink hotDrink = createDrink(DrinkType.TEA, SugarNumber.ONE,true);
        new OrderSender(drinkMaker, accountReportRepository).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Th:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }
    private HotDrink createDrink(DrinkType type, SugarNumber sugarNumber, boolean isExtraHot){
        return  new HotDrink(type,sugarNumber, isExtraHot);
    }

}
