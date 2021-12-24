package by.epamtc.xmlparser.parsers;

import by.epamtc.xmlparser.bean.Device;
import org.xml.sax.SAXException;

import java.util.List;

public interface Parser {
    List<Device> parse(String path) throws ParserException;
}
