package by.epamtc.xmlparser.parsers.impl;

import by.epamtc.xmlparser.parsers.Parser;
import by.epamtc.xmlparser.parsers.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epamtc.xmlparser.bean.Device;

public class ParserDOM implements Parser {
    private List<Device> devices = new ArrayList<>();

    public List<Device> parse(Part part) throws ParserException {
        //File inputFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(part.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("device");
            Device currentDevice;

            for (int i = 0; i < nodes.getLength(); i++) {
                currentDevice = new Device();
                parseNode(nodes.item(i), currentDevice);
                devices.add(currentDevice);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParserException(e.getMessage(), e);
        }

        return devices;
    }

    private void parseNode(Node currentNode, Device currentDevice) throws ParserException {
        Element device = (Element) currentNode;

        String id = device.getAttribute("id");
        id = id.replaceAll("_", "");
        currentDevice.setId(Integer.parseInt(id));

        String stock = device.getAttribute("stock");
        if ("".equals(stock)) {
            stock = "0";
        }
        currentDevice.setStock(Integer.parseInt(stock));
        currentDevice.setName(device.getElementsByTagName("name").item(0).getTextContent());
        currentDevice.setOrigin(device.getElementsByTagName("origin").item(0).getTextContent());
        currentDevice.setPrice(Integer.parseInt(device.getElementsByTagName("price").item(0).getTextContent()));

        Element type = (Element) device.getElementsByTagName("type").item(0);
        currentDevice.setPorts(parsePorts(type.getElementsByTagName("ports").item(0).getTextContent()));
        currentDevice.setGroup(type.getElementsByTagName("group").item(0).getTextContent());
        currentDevice.setEnergyConsumption(Integer.parseInt(type.getElementsByTagName("energyConsumption").item(0).getTextContent()));
        currentDevice.setHasCooler("true".equals(type.getElementsByTagName("cooler").item(0).getTextContent()));
        currentDevice.setPeripheral("true".equals(type.getElementsByTagName("peripheral").item(0).getTextContent()));

        currentDevice.setCritical("true".equals(device.getElementsByTagName("critical").item(0).getTextContent()));
        currentDevice.setArrivalDate(parseDate(device.getElementsByTagName("arrivalDate").item(0).getTextContent()));
    }

    private Date parseDate(String info) throws ParserException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(info);
        } catch (ParseException e) {
            throw new ParserException("Ошибка при чтении даты", e);
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
}
