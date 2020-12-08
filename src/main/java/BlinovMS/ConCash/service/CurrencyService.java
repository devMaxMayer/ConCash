package BlinovMS.ConCash.service;

import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.entity.DateCourse;
import BlinovMS.ConCash.entity.User;

public interface CurrencyService {

    void parseAndSaveCourse ();

    void checkCourse();

    void convert(Currency fromCurrency, Currency toCurrency, Integer inputNum, User user, DateCourse dateCourse);



}
