package BlinovMS.ConCash.service.impl;

import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.entity.DateCourse;
import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;
import BlinovMS.ConCash.repository.CurrencyRepository;
import BlinovMS.ConCash.repository.DateCourseRepository;
import BlinovMS.ConCash.repository.HistoryRepository;
import BlinovMS.ConCash.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private DateCourseRepository dateCourseRepository;
    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void parseAndSaveCourse() {
        DateCourse dateCourse = new DateCourse();
        dateCourse.setDate(LocalDate.now());
        dateCourseRepository.save(dateCourse);

        Currency currency = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader =
                    xmlInputFactory.createXMLEventReader(new BufferedInputStream(
                            new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream()));

            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "Valute":
                            currency = new Currency();
                            currency.setDate(dateCourse);
                            break;
                        case "NumCode":
                            xmlEvent = reader.nextEvent();
                            currency.setNumCode(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        case "CharCode":
                            xmlEvent = reader.nextEvent();
                            currency.setCharCode(xmlEvent
                                    .asCharacters()
                                    .getData());
                            break;
                        case "Nominal":
                            xmlEvent = reader.nextEvent();
                            currency.setNominal(Integer
                                    .parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        case "Name":
                            xmlEvent = reader.nextEvent();
                            currency.setName(xmlEvent
                                    .asCharacters()
                                    .getData());
                            break;
                        case "Value":
                            xmlEvent = reader.nextEvent();
                            currency.setValue(
                                    new BigDecimal(xmlEvent
                                            .asCharacters()
                                            .getData()
                                            .replace(",", ".")));

                            break;
                    }
                }

                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Valute")) {
                        currencyRepository.save(currency);
                    }
                }
            }

        } catch (XMLStreamException | IOException exc) {
            exc.printStackTrace();
        }
    }

    @PostConstruct
    @Override
    public void checkCourse() {
        try {
            LocalDate localDate = LocalDate.now();
            DateCourse dateCourse = dateCourseRepository.findByDate(localDate);
            System.out.println(dateCourse.getDate());
        } catch (Exception e) {
            parseAndSaveCourse();
        }
    }

    @Override
    public void convert(
            Currency fromCurrency, Currency toCurrency, Integer inputNum, User user, DateCourse dateCourse) {

        BigDecimal val = fromCurrency.getValue();
        BigDecimal nom = BigDecimal.valueOf(fromCurrency.getNominal());
        BigDecimal input = BigDecimal.valueOf(inputNum);

        History history = new History();
        history.setFromCurrency(fromCurrency.getName());
        history.setToCurrency(toCurrency.getName());
        history.setOriginalSum(input);
        history.setDate(dateCourse);
        history.setUser(user);
        BigDecimal result = val.divide(nom).multiply(input);
        if (toCurrency.getCharCode() == "RUB") {
            history.setResultSum(result);
        } else {
            history.setResultSum(result.divide(
                    toCurrency.getValue()).multiply(BigDecimal.valueOf(toCurrency.getNominal())));
        }
        historyRepository.save(history);
    }
}
