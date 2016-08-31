package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Context;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;

/**
 * A consequence is a result of making a decision
 * which holds the result context and a message to
 * give some information about what happened.
 */
public class Consequence {
    private final Context context;
    private final String message;

    public Consequence (Context context, String message) {
        assertNotNull(context, "Context");
        assertNotNull(message, "Message");

        this.context = context;
        this.message = message;
    }

    /**
     * Returns the context as the result of making the decision
     *
     * @return context
     */
    public Context getContext () {
        return context;
    }

    /**
     * A message giving some information about what happened
     *
     * @return a message
     */
    public String getMessage () {
        return message;
    }
}
