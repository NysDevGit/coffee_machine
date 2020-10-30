package business;

import business.notifier.BeverageQuantityChecker;
import business.notifier.EmailNotifier;
import exception.ShortageException;
import model.*;
import exception.NotEnoughMoneyException;
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

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private BeverageQuantityChecker beverageQuantityChecker;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(false);
    }

    @Test
    public void shouldMakeATeaWith1Sugar(){
        HotDrink hotDrink = createHotDrink(DrinkType.TEA, SugarNumber.ONE,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("T:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeACoffeeWith2Sugars(){
        HotDrink hotDrink = createHotDrink(DrinkType.COFFEE, SugarNumber.TWO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,1.0);
        verify(drinkMaker, times(1)).process("C:2:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAChocolateWithoutSugar(){
        HotDrink hotDrink = createHotDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,1.0);
        verify(drinkMaker, times(1)).process("H::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAChocolateIfThereIsEnoughMoney(){
        HotDrink hotDrink = createHotDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.5);
        verify(drinkMaker, times(1)).process("H::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeACoffeeIfThereIsEnoughMoney(){
        HotDrink hotDrink = createHotDrink(DrinkType.COFFEE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.6);
        verify(drinkMaker, times(1)).process("C::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeATeaIfThereIsEnoughMoney(){
        HotDrink hotDrink = createHotDrink(DrinkType.TEA, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.4);
        verify(drinkMaker, times(1)).process("T::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAChocolateIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createHotDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.8);
        verify(drinkMaker, times(1)).process("H::");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeACoffeeIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createHotDrink(DrinkType.COFFEE, SugarNumber.ONE,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.9);
        verify(drinkMaker, times(1)).process("C:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeATeaIfThereIsMoreMoneyThanExpected(){
        HotDrink hotDrink = createHotDrink(DrinkType.TEA, SugarNumber.ONE,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.5);
        verify(drinkMaker, times(1)).process("T:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }


    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForChocolate(){
        HotDrink hotDrink = createHotDrink(DrinkType.CHOCOLATE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.2);
        verify(drinkMaker, times(1)).process("M:Missing 0.3 euro");
        verify(accountReportRepository, never()).save(hotDrink);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForCoffee(){
        HotDrink hotDrink = createHotDrink(DrinkType.COFFEE, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.4);
        verify(drinkMaker, times(1)).process("M:Missing 0.2 euro");
        verify(accountReportRepository, never()).save(hotDrink);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForTea(){
        HotDrink hotDrink = createHotDrink(DrinkType.TEA, SugarNumber.ZERO,false);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink,0.3);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
        verify(accountReportRepository, never()).save(hotDrink);
    }

    @Test
    public void shouldMakeAnOrangeJuice(){
        ColdDrink coldDrink = createColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(coldDrink,0.6);
        verify(drinkMaker, times(1)).process("O::");
        verify(accountReportRepository, times(1)).save(coldDrink);
    }

    @Test
    public void shouldMakeAnOrangeJuiceIfThereIsMoreMoneyThanExpected(){
        ColdDrink coldDrink = createColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(coldDrink,0.9);
        verify(drinkMaker, times(1)).process("O::");
        verify(accountReportRepository, times(1)).save(coldDrink);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyExceptionIfThereIsNotEnoughMoneyForOrangeJuice(){
        ColdDrink coldDrink = createColdDrink(DrinkType.ORANGE_JUICE);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(coldDrink,0.5);
        verify(drinkMaker, times(1)).process("M:Missing 0.1 euro");
        verify(accountReportRepository, never()).save(coldDrink);
    }

    @Test
    public void shouldMakeAnExtraHotCoffee(){
        HotDrink hotDrink = createHotDrink(DrinkType.COFFEE, SugarNumber.ONE,true);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Ch:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAnExtraHotChocolate(){
        HotDrink hotDrink = createHotDrink(DrinkType.CHOCOLATE, SugarNumber.ONE,true);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Hh:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test
    public void shouldMakeAnExtraHotTea(){
        HotDrink hotDrink = createHotDrink(DrinkType.TEA, SugarNumber.ONE,true);
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(hotDrink, 1.0);
        verify(drinkMaker, times(1)).process("Th:1:0");
        verify(accountReportRepository, times(1)).save(hotDrink);
    }

    @Test(expected = ShortageException.class)
    public void shouldThrowShortageExceptionCoffeeCase(){
        when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(true);
        HotDrink hotDrink = createHotDrink(DrinkType.COFFEE, SugarNumber.ONE,false);
        verifyShortageExceptionOnDrinks(hotDrink, 0.8);
    }

    @Test(expected = ShortageException.class)
    public void shouldThrowShortageExceptionChocolateCase(){
        when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(true);
        HotDrink hotDrink = createHotDrink(DrinkType.CHOCOLATE, SugarNumber.ONE,false);
        verifyShortageExceptionOnDrinks(hotDrink, 1.0);
    }

    @Test(expected = ShortageException.class)
    public void shouldThrowShortageExceptionTeaCase(){
        when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(true);
        HotDrink hotDrink = createHotDrink(DrinkType.TEA, SugarNumber.ONE,false);
        verifyShortageExceptionOnDrinks(hotDrink, 0.7);
    }

    @Test(expected = ShortageException.class)
    public void shouldThrowShortageExceptionOrangeJuiceCase(){
        when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(true);
        ColdDrink coldDrink = createColdDrink(DrinkType.ORANGE_JUICE);
        verifyShortageExceptionOnDrinks(coldDrink, 0.7);
    }

    private HotDrink createHotDrink(DrinkType type, SugarNumber sugarNumber, boolean isExtraHot){
        return new HotDrink(type,sugarNumber, isExtraHot);
    }

    private ColdDrink createColdDrink(DrinkType type){
        return new ColdDrink(type);
    }

    private void verifyShortageExceptionOnDrinks(Drink drink, double money){
        new OrderSender(drinkMaker, accountReportRepository, emailNotifier, beverageQuantityChecker).send(drink,money);
        verify(emailNotifier, times(1)).notifyMissingDrink(drink.getType().getCode());
        verify(drinkMaker, times(1)).process("M:Shortage on :"+drink.getType().getCode());
    }

}
