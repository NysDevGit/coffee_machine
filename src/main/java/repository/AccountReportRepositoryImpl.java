package repository;

import model.Drink;

import java.util.HashMap;

public class AccountReportRepositoryImpl implements AccountReportRepository{

    private final HashMap<Integer,Drink> drinkList = new HashMap<>();

    @Override
    public HashMap<Integer, Drink> findAll() {
        return drinkList;
    }

    @Override
    public void save(Drink drink) {
        drinkList.put(drinkList.size(),drink);
    }

    @Override
    public long countByCode(String code) {
        return drinkList.values().stream().filter(drink -> drink.getType().getCode().equals(code)).count();
    }

    @Override
    public double sumAll() {
        return drinkList.values().stream().mapToDouble(drink -> drink.getType().getPrice()).sum();
    }
}
