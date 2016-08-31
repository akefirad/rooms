package com.akefirad.games.rooms.ui;

import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.Argument;

import java.util.List;

/**
 * Base interface for the console
 */
public interface Console {
    /**
     * Shows the decisions list to the user
     *
     * @param decisions decisions list
     */
    void showDecisions (List<Decision> decisions);

    /**
     * Selects a decision from the list, by asking from the user
     *
     * @param size size of the decisions list
     * @return decision's index
     */
    Integer selectDecision (int size);

    /**
     * Reads arguments, by asking from the user
     *
     * @param arguments arguments list
     */
    void readArguments (List<Argument<?>> arguments);

    /**
     * Shows the message and exception to the user
     *
     * @param message message
     * @param e       exception
     */
    void showError (String message, Exception e);

    /**
     * Shows the message to the user
     *
     * @param message message
     */
    void showMessage (String message);
}
