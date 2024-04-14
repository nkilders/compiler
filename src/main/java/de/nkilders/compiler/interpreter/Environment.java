package de.nkilders.compiler.interpreter;

import java.util.HashMap;
import java.util.Map;

import de.nkilders.compiler.interpreter.values.BooleanValue;
import de.nkilders.compiler.interpreter.values.NullValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class Environment {
    private final Environment parent;
    private final Map<String, Variable> variables;

    public Environment(Environment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Environment() {
        this(null);

        this.declareGlobalVariables();
    }

    /**
     * @throws VarException if the variable does already exist
     */
    public void declareVariable(String name, boolean isConst, @SuppressWarnings("rawtypes") RuntimeValue value) throws VarException {
        if(variables.containsKey(name)) {
            String message = String.format("Cannot declare variable %s since it does already exist", name);
            throw new VarException(message);
        }

        Variable variable = new Variable(name, isConst, value);
        variables.put(name, variable);
    }

    /**
     * @throws VarException if the variable does already exist
     */
    public void declareVariable(String name, boolean isConst) throws VarException {
        declareVariable(name, isConst, null);
    }

    /**
     * @throws VarException if the variable does not exist or is const
     */
    public void assignVariable(String name, @SuppressWarnings("rawtypes") RuntimeValue value) throws VarException {
        if(variables.containsKey(name)) {
            variables.get(name).setValue(value);
            return;
        }

        if(parent != null) {
            parent.assignVariable(name, value);
            return;
        }

        String message = String.format("Cannot assign variable %s since it does not exist", name);
        throw new VarException(message);
    }

    /**
     * @throws VarException if the variable does not exist
     */
    @SuppressWarnings("rawtypes")
    public RuntimeValue readVariable(String name) throws VarException {
        if(variables.containsKey(name)) {
            return variables.get(name).getValue();
        }

        if(parent != null) {
            return parent.readVariable(name);
        }

        String message = String.format("Cannot read variable %s since it does not exist", name);
        throw new VarException(message);
    }

    private void declareGlobalVariables() {
        declareVariable("true", true, new BooleanValue(true));
        declareVariable("false", true, new BooleanValue(false));
        declareVariable("null", true, new NullValue());
    }

    @Override
    public String toString() {
        return "Environment [parent=" + parent + ", variables=" + variables + "]";
    }
}
