package by.epamtc.xmlparser.parsers;

import by.epamtc.xmlparser.bean.Device;
import org.xml.sax.SAXException;

import javax.servlet.http.Part;
import java.util.List;

public interface Parser {
    List<Device> parse(Part part) throws ParserException;
}
