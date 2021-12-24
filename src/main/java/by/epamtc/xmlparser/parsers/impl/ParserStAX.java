package by.epamtc.xmlparser.parsers.impl;

import by.epamtc.xmlparser.bean.Device;
import by.epamtc.xmlparser.parsers.Parser;
import by.epamtc.xmlparser.parsers.ParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserStAX implements Parser {
    private final List<Device> devices = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(ParserStAX.class);

    @Override
    public List<Device> parse(Part part) throws ParserException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        Device currentDevice = null;

        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(part.getInputStream());
            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if ("device".equals(startElement.getName().getLocalPart())) {
                        currentDevice = new Device();
                        Attribute attr = startElement.getAttributeByName(new QName("id"));
                        if (attr != null) {
                            currentDevice.setId(Integer.parseInt(attr.getValue().replaceAll("_", "")));
                        }
                        attr = startElement.getAttributeByName(new QName("stock"));
                        if (attr != null) {
                            currentDevice.setId(Integer.parseInt(attr.getValue()));
                        }
                    } else if ("name".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setName(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("origin")) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setOrigin(xmlEvent.asCharacters().getData());
                    } else if ("price".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setPrice(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if ("ports".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        if (!xmlEvent.isEndElement()) {
                            currentDevice.setPorts(parsePorts(xmlEvent.asCharacters().getData()));
                        } else {
                            currentDevice.setPorts(new ArrayList<>());
                        }
                    } else if ("group".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setGroup(xmlEvent.asCharacters().getData());
                    } else if ("energyConsumption".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setEnergyConsumption(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if ("cooler".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setHasCooler("true".equals(xmlEvent.asCharacters().getData()));
                    } else if ("peripheral".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setPeripheral("true".equals(xmlEvent.asCharacters().getData()));
                    } else if ("critical".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setCritical("true".equals(xmlEvent.asCharacters().getData()));
                    } else if ("arrivalDate".equals(startElement.getName().getLocalPart())) {
                        xmlEvent = reader.nextEvent();
                        currentDevice.setArrivalDate(parseDate(xmlEvent.asCharacters().getData()));
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("device")) {
                        devices.add(currentDevice);
                    }
                }
            }

        } catch (IOException | XMLStreamException e) {
            logger.error(e.getClass().getSimpleName() + " while parsing xml");
            throw new ParserException(e.getClass().getSimpleName() + " while parsing xml", e);
        }

        return devices;
    }

    private List<String> parsePorts(String info) {
        Pattern p = Pattern.compile("\\s*[A-Za-z-0-9.]+\\s*");
        Matcher m = p.matcher(info);

        List<String> ports = new ArrayList<>();

        while (m.find()) {
            String port = m.group();
            port = port.replaceAll(" ", "");
            ports.add(port);
        }

        return ports;
    }

    private Date parseDate(String info) throws ParserException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(info);
        } catch (ParseException e) {
            logger.error(e.getClass().getSimpleName() + " while parsing Arrival Date");
            throw new ParserException(e.getClass().getSimpleName() + " while parsing Arrival Date", e);
        }
    }
}
