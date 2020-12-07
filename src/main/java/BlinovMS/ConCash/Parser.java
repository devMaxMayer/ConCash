package BlinovMS.ConCash;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public void parseCash() {

        String daily = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + daily;
        List<Moneta> monetaList = parseXMLfile(url, daily);
        for (Moneta moneta : monetaList) {
            System.out.println(
                    moneta.toString());
        }
    }

    private static List<Moneta> parseXMLfile(String url, String daily) {
        List<Moneta> monetasList = new ArrayList<>();
        Moneta moneta = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader =
                    xmlInputFactory.createXMLEventReader(new BufferedInputStream(new URL(url).openStream()));

            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "Valute":
                            moneta = new Moneta();
                            moneta.setDate(daily);
                            Attribute idAttr = startElement.getAttributeByName(new QName("ID"));
                            if (idAttr != null) {
                                moneta.setId(idAttr.getValue());
                            }
                            break;
                        case "NumCode":
                            xmlEvent = reader.nextEvent();
                            moneta.setNumCode(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        case "CharCode":
                            xmlEvent = reader.nextEvent();
                            moneta.setCharCode(xmlEvent.asCharacters().getData());
                            break;
                        case "Nominal":
                            xmlEvent = reader.nextEvent();
                            moneta.setNominal(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        case "Name":
                            xmlEvent = reader.nextEvent();
                            moneta.setName(xmlEvent.asCharacters().getData());
                            break;
                        case "Value":
                            xmlEvent = reader.nextEvent();
                            moneta.setValue(
                                    new BigDecimal(xmlEvent.asCharacters().getData().replace(",", ".")));

                            break;
                    }
                }

                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Valute")) {
                        monetasList.add(moneta);
                    }
                }
            }

        } catch (XMLStreamException | IOException exc) {
            exc.printStackTrace();
        }
        return monetasList;
    }
}
