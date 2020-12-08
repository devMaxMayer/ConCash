package BlinovMS.ConCash.service;

import BlinovMS.ConCash.entity.ConversionResult;
import BlinovMS.ConCash.entity.Currency;

import java.util.Date;

public interface CurrencyService {

    void parseAndSaveCourse ();

    void checkCourse(Date date);

    ConversionResult convert(Currency fromCurrency,Currency toCurrency);



}
