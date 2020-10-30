package business;

import model.Drink;
import org.apache.commons.collections4.CollectionUtils;
import repository.AccountReportRepository;

import java.util.HashMap;

public class ReportWriter {

    private final AccountReportRepository accountReportRepository;

    public ReportWriter(AccountReportRepository accountReportRepository) {
        this.accountReportRepository = accountReportRepository;
    }

    public void display(){
        HashMap<Integer, Drink> drinks =  accountReportRepository.findAll();
        if(CollectionUtils.isNotEmpty(drinks.values())) {
            drinks.values().stream().distinct()
                    .forEach(drink -> {
                        long sold = accountReportRepository.countByCode(drink.getType().getCode());
                        System.out.println(drink.getType().getCode() + ": " + sold + " sold");
                    });
            System.out.print("Total: " + accountReportRepository.sumAll() + " euro(s)");
        }
    }
}
