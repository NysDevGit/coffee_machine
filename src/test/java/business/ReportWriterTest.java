package business;

import business.report.ReportWriter;
import model.Drink;
import model.DrinkType;
import model.SugarNumber;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import repository.AccountReportRepository;
import repository.AccountReportRepositoryImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReportWriterTest {

    @Spy
    private final AccountReportRepository accountReportRepository = spy(new AccountReportRepositoryImpl());

    @Spy
    private final ReportWriter reportWriter = new ReportWriter(accountReportRepository);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
        MockitoAnnotations.initMocks(this);
        doCallRealMethod().when(accountReportRepository).save(any(Drink.class));
        doCallRealMethod().when(accountReportRepository).findAll();
        doCallRealMethod().when(accountReportRepository).countByCode(anyString());
        doCallRealMethod().when(accountReportRepository).sumAll();
        doCallRealMethod().when(reportWriter).display();
    }

    @Test
    public void shouldBeEmptyReport(){
        reportWriter.display();
        verify(accountReportRepository, never()).countByCode(anyString());
        verify(accountReportRepository, times(1)).findAll();
        verify(accountReportRepository, never()).sumAll();
        String[] lines = extractAllLinesFromTheReport(outContent);
        assertEquals("", lines[0]);
    }

    @Test
    public void shouldReportOneChocolateSold(){
        fillMachineHistory(Collections.singletonList(new Drink(DrinkType.CHOCOLATE, SugarNumber.ONE, false)));
        reportWriter.display();
        verify(accountReportRepository, times(1)).countByCode(anyString());
        verify(accountReportRepository, times(1)).findAll();
        verify(accountReportRepository, times(1)).sumAll();
        String[] lines = extractAllLinesFromTheReport(outContent);
        assertEquals("H: 1 sold",lines[0]);
        assertEquals("Total: 0.5 euro(s)",lines[1]);
    }

    @Test
    public void shouldReportEachDrinkSoldAndTotal(){
        fillMachineHistory(getAllDrinksInAList());
        reportWriter.display();
        verify(accountReportRepository, times(4)).countByCode(anyString());
        verify(accountReportRepository, times(1)).findAll();
        verify(accountReportRepository, times(1)).sumAll();
        String[] lines = extractAllLinesFromTheReport(outContent);
        assertEquals("H: 2 sold",lines[0]);
        assertEquals("C: 1 sold",lines[1]);
        assertEquals("T: 2 sold",lines[2]);
        assertEquals("O: 1 sold",lines[3]);
        assertEquals("Total: 3.0 euro(s)",lines[4]);
    }

    private void fillMachineHistory(List<Drink> drinks){
        drinks.forEach(accountReportRepository::save);
    }

    private String[] extractAllLinesFromTheReport(ByteArrayOutputStream outContent){
        return outContent.toString().split("\r\n");
    }

    private List<Drink> getAllDrinksInAList(){
        return Arrays.asList(new Drink(DrinkType.CHOCOLATE, SugarNumber.ONE, false),
                new Drink(DrinkType.COFFEE, SugarNumber.ONE, false),
                new Drink(DrinkType.TEA, SugarNumber.ONE, false),
                new Drink(DrinkType.ORANGE_JUICE),
                new Drink(DrinkType.CHOCOLATE, SugarNumber.ONE, false),
                new Drink(DrinkType.TEA, SugarNumber.ONE, false));
    }

}
