package pl.edu.agh.to2.DreamLogoIDE.parser;

import pl.edu.agh.to2.DreamLogoIDE.command.Command;
import pl.edu.agh.to2.DreamLogoIDE.command.ProcedureCommand;
import pl.edu.agh.to2.DreamLogoIDE.command.ProcedureDefinitionCommand;
import pl.edu.agh.to2.DreamLogoIDE.command.RepeatCommand;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class CommandParserImp implements CommandParser {
    private CommandProvider commandProvider;
    private Environment environment = new Environment();
    private ProcedureDefinitionCommand notEndedDefinition = null;

    public CommandParserImp(CommandProvider commandProvider) throws IOException {
        this.commandProvider = commandProvider;
    }

    @Override
    public Command getCommand(String textCommand) throws IllegalArgumentException, ParseException {
        textCommand = textCommand.toLowerCase().trim();

        if (notEndedDefinition != null)
            return continueDefinition(textCommand);

        Queue<String> args = new LinkedList<>(Arrays.asList(textCommand.split("\\s+")));

        return getCommand(args);
    }

    private Command getCommand(Queue<String> args) throws ParseException {
        Command command;
        if (commandProvider.isSupported(args.peek())) {
            command = getBasicCommand(args);
        } else if (environment.isDefined(args.peek())) {
            command = getUserCommand(args);
        } else
            throw new ParseException("Command not supprted", 0);

        return command;
    }

    private Command getBasicCommand(Queue<String> args) throws ParseException {
        Command command = commandProvider.getCommand(getCommandArgs(args));

        if (command instanceof RepeatCommand)
            command = getRepeatCommand((RepeatCommand) command, args);
        else if (command instanceof ProcedureDefinitionCommand) {
            if (!args.isEmpty())
                ((ProcedureDefinitionCommand) command).setCommandArgs(getProcedureDefinitionArgs(args));
            notEndedDefinition = (ProcedureDefinitionCommand) command;
            return null;
        }
        return command;
    }

    private Command getUserCommand(Queue<String> args) throws ParseException {
        ProcedureDefinitionCommand definition = environment.getDefinition(args.peek());

        String[] commandArgs = getCommandArgs(args, definition.getCommandArgumentsNumber());
        ProcedureCommand procedureCommand = new ProcedureCommand(commandArgs);

        Iterable<String> lines = definition.getProcedure(Arrays.copyOfRange(commandArgs, 1, commandArgs.length));
        for (String line : lines)
            procedureCommand.addCommand(getCommand(line));

        return procedureCommand;
    }

    private String[] getCommandArgs(Queue<String> args) throws ParseException {
        String keyword = args.peek();
        if (!commandProvider.isSupported(keyword))
            throw new ParseException("Illegal command", 0);

        int argsNumber = commandProvider.getCommandArgumentsNumber(keyword);
        String[] commandArgs = new String[argsNumber + 1];
        for (int i = 0; i < argsNumber + 1; i++) {
            if (args.isEmpty())
                throw new ParseException("Illegal arguments number", 0);
            commandArgs[i] = args.remove();
        }

        return commandArgs;
    }

    private String[] getCommandArgs(Queue<String> args, int argsNumber) throws ParseException {
        String[] commandArgs = new String[argsNumber + 1];
        for (int i = 0; i < argsNumber + 1; i++)
            commandArgs[i] = args.remove();

        return commandArgs;
    }

    private String[] getProcedureDefinitionArgs(Queue<String> args) {
        List<String> arguments = new LinkedList<>();
        while (args.peek().startsWith(":")) {
            arguments.add(args.poll());
            if (args.isEmpty())
                break;
        }

        String[] arr = new String[arguments.size()];
        return arguments.toArray(arr);
    }

    private Command getRepeatCommand(RepeatCommand command, Queue<String> args) throws ParseException {
        if (!args.remove().equals("["))
            throw new ParseException("Not found '['", 0);

        while (!args.peek().equals("]"))
            command.addCommand(getCommand(args));

        args.remove();
        return command;
    }

    private ProcedureDefinitionCommand continueDefinition(String procedureLine) throws ParseException {
        if (procedureLine.equals("end") || procedureLine.equals("już")) {
            ProcedureDefinitionCommand def = notEndedDefinition;
            notEndedDefinition = null;
            environment.addDefinition(def);
            return def;
        }

        notEndedDefinition.addTextLine(procedureLine);
        return null;
    }
}
