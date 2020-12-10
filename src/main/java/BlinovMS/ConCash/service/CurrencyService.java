package BlinovMS.ConCash.service;

import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.entity.DateCourse;
import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;

import java.util.List;

public interface CurrencyService {

    void parseAndSaveCourse ();

    void checkCourse();

    History convert(History history, User user);

    Currency findByName(String name);

    List<Currency> getList ();



}
