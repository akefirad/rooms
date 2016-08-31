package com.akefirad.games.rooms;

import com.akefirad.games.rooms.context.Context;
import com.akefirad.games.rooms.context.impl.StartMenu;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.Argument;
import com.akefirad.games.rooms.exception.EOFConsoleException;
import com.akefirad.games.rooms.ui.Console;
import com.akefirad.games.rooms.ui.impl.SimpleConsole;

import java.util.List;

import static com.akefirad.games.rooms.util.Constants.WELCOME_MSG;

public class Application {
    private static final String MESSAGE = "Let's start!";

    private Console console;
    private Context context;

    public Application (Console console, Context context) {
        this.console = console;
        this.context = context;
    }

    public static void main (String[] args) {
        Application application = new Application(new SimpleConsole(), new StartMenu());
        application.run();
    }

    public void run () {
        console.showMessage(WELCOME_MSG);
        String message = MESSAGE;
        while (context.hasDecisions()) {
            try {
                List<Decision> decisions = context.listDecisions();

                console.showDecisions(decisions);
                console.showMessage(message);

                Decision decision = selectDecision(decisions);
                List<Argument<?>> arguments = readArguments(decision);

                Consequence consequence = decision.make(arguments);
                message = consequence.getMessage();
                context = consequence.getContext();
            }
            catch (EOFConsoleException e) {
                break; // No input!
            }
            catch (NumberFormatException e) {
                console.showError("Invalid number: " + e.getMessage(), e);
            }
            catch (Exception e) {
                console.showError(e.getMessage(), e);
            }
        }
    }

    private List<Argument<?>> readArguments (Decision decision) {
        List<Argument<?>> arguments = decision.getArguments();
        console.readArguments(arguments);
        return arguments;
    }

    private Decision selectDecision (List<Decision> decisions) {
        Integer index = console.selectDecision(decisions.size());
        if (index == null)
            throw new EOFConsoleException();

        return decisions.get(index);
    }
}
