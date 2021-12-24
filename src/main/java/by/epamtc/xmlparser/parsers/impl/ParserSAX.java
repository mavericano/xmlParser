package by.epamtc.xmlparser.parsers.impl;

import by.epamtc.xmlparser.bean.Device;

import by.epamtc.xmlparser.parsers.Parser;
import by.epamtc.xmlparser.parsers.ParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserSAX implements Parser {
    private List<Device> devices;
    private static final Logger logger = LogManager.getLogger(ParserSAX.class);

    public List<Device> parse(Part part) throws ParserException {
        try {
            devices = new ArrayList<>();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            EventHandler handler = new EventHandler();
            parser.parse(part.getInputStream(), handler);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            devices = new ArrayList<>();
            logger.error(e.getClass().getSimpleName() + " while parsing xml");
            throw new ParserException(e.getClass().getSimpleName() + " while parsing xml", e);
        }

        return devices;
    }

    private class EventHandler extends DefaultHandler {
        private Device currentDevice;
        private String currentElement;

        @Override
        public void startDocument() {
            currentDevice = new Device();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            currentElement = qName;
            if ("device".equals(currentElement)) {
               currentDevice.setId(Integer.parseInt(attributes.getValue("id").replaceAll("_", "")));
               String stock = attributes.getValue("stock");
               currentDevice.setStock(
                       stock == null ? 0 : Integer.parseInt(stock)
               );
           }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ("device".equals(qName)) {
                devices.add(currentDevice);
                currentDevice = new Device();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String info = new String(ch, start, length);
            info = info.replace("\n", "").trim();

            if (!info.isEmpty()) {
                switch (currentElement) {
                    case "name":
                        currentDevice.setName(info);
                        break;
                    case "origin":
                        currentDevice.setOrigin(info);
                        break;
                    case "price":
                        currentDevice.setPrice(Integer.parseInt(info));
                        break;
                    case "ports":
                        currentDevice.setPorts(parsePorts(info));
                        break;
                    case "group":
                        currentDevice.setGroup(info);
                        break;
                    case "energyConsumption":
                        currentDevice.setEnergyConsumption(Integer.parseInt(info));
                        break;
                    case "cooler":
                        currentDevice.setHasCooler("true".equals(info));
                        break;
                    case "peripheral":
                        currentDevice.setPeripheral("true".equals(info));
                        break;
                    case "critical":
                        currentDevice.setCritical("true".equals(info));
                        break;
                    case "arrivalDate":
                        currentDevice.setArrivalDate(parseDate(info));
                        break;
                }
            }
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

        private Date parseDate(String info) throws SAXException {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                return formatter.parse(info);
            } catch (ParseException e) {
                logger.error(e.getClass().getSimpleName() + " while parsing Arrival Date");
                throw new SAXException(e.getClass().getSimpleName() + " while parsing Arrival Date", e);
            }
        }
    }
}
