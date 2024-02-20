package de.nkilders.compiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var machine = new TestMachine();

        machine.processText("a12b");
        LOGGER.info("Machine in final state: {}", machine.isInFinalState());
        LOGGER.info("Machine in error state: {}", machine.isInErrorState());

        machine.reset();
        machine.processText("a=12b");
        LOGGER.info("Machine in final state: {}", machine.isInFinalState());
        LOGGER.info("Machine in error state: {}", machine.isInErrorState());
    }
}
