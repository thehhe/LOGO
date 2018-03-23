package pl.edu.agh.to2.DreamLogoIDE.parser;

import pl.edu.agh.to2.DreamLogoIDE.command.ProcedureDefinitionCommand;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, ProcedureDefinitionCommand> definitions = new HashMap<>();

    public boolean isDefined(String name) {
        return definitions.containsKey(name);
    }

    public ProcedureDefinitionCommand getDefinition(String name) {
        return definitions.get(name);
    }

    public void addDefinition(ProcedureDefinitionCommand definition) {
        definitions.put(definition.getName(), definition);
    }
}
