package pl.edu.agh.to2.DreamLogoIDE.parser;

import pl.edu.agh.to2.DreamLogoIDE.command.Command;

import java.text.ParseException;

public interface CommandProvider {
    boolean isSupported(String keyword);

    int getCommandArgumentsNumber(String keyword);

    Command getCommand(String[] arguments) throws ParseException;
}
