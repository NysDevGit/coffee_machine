package business;

import model.Drink;
import model.exception.NotEnoughMoneyException;
import repository.AccountReportRepository;

import java.util.StringJoiner;

public class OrderSender {

    private final DrinkMaker drinkMaker;

    private final AccountReportRepository accountReportRepository;

    public OrderSender(DrinkMaker drinkMaker, AccountReportRepository accountReportRepository) {
        this.drinkMaker = drinkMaker;
        this.accountReportRepository = accountReportRepository;
    }

    public void send(Drink order, double money){
        checkIfThereIsEnoughMoney(order, money);
        String stringOrder = translateOrder(order);
        accountReportRepository.save(order);
        drinkMaker.process(stringOrder);
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
