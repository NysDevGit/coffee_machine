package business;

import business.notifier.BeverageQuantityChecker;
import business.notifier.EmailNotifier;
import model.Drink;
import exception.NotEnoughMoneyException;
import exception.ShortageException;
import repository.AccountReportRepository;

import java.util.StringJoiner;

public class OrderSender {

    private final DrinkMaker drinkMaker;
    private final AccountReportRepository accountReportRepository;
    private final EmailNotifier emailNotifier;
    private final BeverageQuantityChecker beverageQuantityChecker;

    public OrderSender(DrinkMaker drinkMaker, AccountReportRepository accountReportRepository, EmailNotifier emailNotifier, BeverageQuantityChecker beverageQuantityChecker) {
        this.drinkMaker = drinkMaker;
        this.accountReportRepository = accountReportRepository;
        this.emailNotifier = emailNotifier;
        this.beverageQuantityChecker = beverageQuantityChecker;
    }

    public void send(Drink order, double money){
        checkIfThereIsEnoughMoney(order, money);
        String stringOrder = translateOrder(order);
        checkBeverageQuantity(order);
        accountReportRepository.save(order);
        drinkMaker.process(stringOrder);
    }

    private void checkBeverageQuantity(Drink drink) {
        String typeCode = drink.getType().getCode();
        if(beverageQuantityChecker.isEmpty(typeCode)){
            emailNotifier.notifyMissingDrink(typeCode);
            String message = translateMessage("Shortage on "+typeCode);
            drinkMaker.process(message);
            throw new ShortageException();

        }
    }

    private void checkIfThereIsEnoughMoney(Drink order, double money) {
        if(money < order.getType().getPrice()){
            String message = translateMessage("Missing "+(order.getType().getPrice()-money)+" euro");
            drinkMaker.process(message);
            throw new NotEnoughMoneyException();
        }
    }

    private String translateOrder(Drink drink){
        StringJoiner stringOrder = new StringJoiner(":");
        String code = drink.getType().getCode();

        if(drink.isExtraHot())
            code += "h";

        stringOrder.add(code);
        stringOrder.add(drink.getSugarNumber());

        return  stringOrder.toString();
    }

    private String translateMessage(String message){
        return "M:"+ message ;
    }

}
