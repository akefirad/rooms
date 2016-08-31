package com.akefirad.games.rooms.interaction;

import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

/**
 * Base interface for interactable objects.
 * An interactable object has its own list of
 * decisions which is added to the list of
 * available decision of the context.
 */
public interface Interactable {
    /**
     * Returns a list of decision, which a player can make
     *
     * @param player player
     * @return list of decisions
     */
    List<Decision> listDecisions (Player player);
}
