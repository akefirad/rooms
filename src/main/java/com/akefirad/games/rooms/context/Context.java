package com.akefirad.games.rooms.context;

import com.akefirad.games.rooms.decision.Decision;

import java.util.List;

/**
 * Base interface for context. Each context has its own list of
 * available decisions. The user needs to select from this list.
 */
public interface Context {
    /**
     * Returns whether there is any decision in the context to be made.
     *
     * @return true if there is any decision, otherwise false
     */
    boolean hasDecisions ();

    /**
     * Shows the available decisions to the user vis console
     *
     * @return list of decisions, not-null
     */
    List<Decision> listDecisions ();
}
