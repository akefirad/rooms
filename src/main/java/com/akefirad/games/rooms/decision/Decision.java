package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.decision.impl.Argument;

import java.util.List;

import static com.akefirad.games.rooms.util.Constants.UNKNOWN_ICON;
import static java.util.Collections.emptyList;

public interface Decision {
    int DRAWING_HEIGHT = 13;

    /**
     * Returns the name of the decision, e.g. Open Door, FightCreature, etc
     *
     * @return decision getName
     */
    String getName ();

    /**
     * Makes this decision and returns the (new) context
     *
     * @param arguments decision's arguments
     * @return consequence of the decision
     */
    Consequence make (List<Argument<?>> arguments);

    /**
     * Returns the set of arguments needed to make this decisions
     *
     * @return a set of arguments
     */
    default List<Argument<?>> getArguments () {
        return emptyList();
    }

    /**
     * Draw an icon for the decision.
     * Default implementation returns a question mark.
     *
     * @return decision's icon
     */
    default String[] getIcon () {
        return UNKNOWN_ICON;
    }
}
