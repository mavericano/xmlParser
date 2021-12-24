package by.epamtc.xmlparser.parsers;

import by.epamtc.xmlparser.controller.commandImpl.ViewTable;
import by.epamtc.xmlparser.controller.commandImpl.WrongCommand;
import by.epamtc.xmlparser.parsers.impl.ParserDOM;
import by.epamtc.xmlparser.parsers.impl.ParserSAX;
import by.epamtc.xmlparser.parsers.impl.ParserStAX;

import java.util.HashMap;

public class ParserProvider {
    private HashMap<String, Parser> parsers = new HashMap<>();
    private final static ParserProvider instance = new ParserProvider();

    private ParserProvider(){
        parsers.put("SAX", new ParserSAX());
        parsers.put("StAX", new ParserStAX());
        parsers.put("DOM", new ParserDOM());
        parsers.put("Unknown", new ParserDOM());
//        commands.put("VIEW_TABLE", new ViewTable());
//        commands.put("WRONG_COMMAND", new WrongCommand());
    }

    public static ParserProvider getInstance() {
        return instance;
    }

    public Parser provideParser(String name) {
        Parser toProvide = parsers.get(name);
        return toProvide == null ? parsers.get("Unknown") : toProvide;
    }

    public void registerParser(String name, Parser toRegister) {
        parsers.put(name, toRegister);
    }
}
