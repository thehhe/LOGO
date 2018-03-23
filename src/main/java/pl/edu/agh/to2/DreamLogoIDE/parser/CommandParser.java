package pl.edu.agh.to2.DreamLogoIDE.parser;

import pl.edu.agh.to2.DreamLogoIDE.command.Command;

import java.text.ParseException;

public interface CommandParser {
    Command getCommand(String command) throws IllegalArgumentException, ParseException;
}
