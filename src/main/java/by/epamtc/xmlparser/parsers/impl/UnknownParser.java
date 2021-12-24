package by.epamtc.xmlparser.parsers.impl;

import by.epamtc.xmlparser.bean.Device;
import by.epamtc.xmlparser.parsers.Parser;
import by.epamtc.xmlparser.parsers.ParserException;

import javax.servlet.http.Part;
import java.util.List;

public class UnknownParser implements Parser {
    @Override
    public List<Device> parse(Part part) throws ParserException {
        throw new ParserException("Выбранный парсер не зарегистрирован");
    }
}
