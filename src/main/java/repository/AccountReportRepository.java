package repository;

import model.Drink;

import java.util.HashMap;

public interface AccountReportRepository {

    HashMap<Integer,Drink> findAll();

    void save(Drink drink);

    long countByCode(String code);

    double sumAll();
}
