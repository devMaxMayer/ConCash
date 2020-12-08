package BlinovMS.ConCash.service.impl;

import BlinovMS.ConCash.entity.ConversionResult;
import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.repository.CurrencyRepository;
import BlinovMS.ConCash.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @PostConstruct
    @Override
    public void parseAndSaveCourse() {
        //List<Currency> currentCourse = new ArrayList<>();
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
                            currency.setDate(LocalDate.now());
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
                       // currentCourse.add(currency);
                        currencyRepository.save(currency);
                    }
                }
            }

        } catch (XMLStreamException | IOException exc) {
            exc.printStackTrace();
        }
    }


    @Override
    public void checkCourse(Date date) {

    }

    @Override
    public ConversionResult convert(Currency fromCurrency, Currency toCurrency) {
        return null;
    }
}
