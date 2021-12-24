package by.epamtc.xmlparser.controller;

import by.epamtc.xmlparser.controller.commandImpl.ReturnToMenu;
import by.epamtc.xmlparser.controller.commandImpl.ViewTable;
import by.epamtc.xmlparser.controller.commandImpl.WrongCommand;

import java.util.HashMap;

public class CommandProvider {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final static CommandProvider instance = new CommandProvider();

    private CommandProvider(){
        commands.put("VIEW_TABLE", new ViewTable());
        commands.put("RETURN_TO_MENU", new ReturnToMenu());
        commands.put("WRONG_COMMAND", new WrongCommand());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command provideCommand(String name) {
        Command toProvide = commands.get(name);
        return toProvide == null ? commands.get("WRONG_COMMAND") : toProvide;
    }

    public void registerCommand(String name, Command toRegister) {
        commands.put(name, toRegister);
    }
}
