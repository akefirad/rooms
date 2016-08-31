package com.akefirad.games.rooms.util;

import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.Argument;
import com.akefirad.games.rooms.ui.Console;
import com.akefirad.games.rooms.ui.impl.SimpleConsole;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class TestConsole implements Console {
    private final StringReader reader;
    private final StringWriter writer;
    private final Console console;

    public TestConsole (String input) {
        this.reader = new StringReader(input);
        this.writer = new StringWriter();
        this.console = new SimpleConsole(writer, reader);
    }

    public StringReader getReader () {
        return reader;
    }

    @Override
    public void showDecisions (List<Decision> decisions) {
        console.showDecisions(decisions);
    }

    @Override
    public Integer selectDecision (int size) {
        return console.selectDecision(size);
    }

    @Override
    public void readArguments (List<Argument<?>> arguments) {
        console.readArguments(arguments);
    }

    @Override
    public void showError (String message, Exception e) {
        console.showError(message, e);
    }

    @Override
    public void showMessage (String message) {
        console.showMessage(message);
    }
}
