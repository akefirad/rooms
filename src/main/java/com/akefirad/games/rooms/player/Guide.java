package com.akefirad.games.rooms.player;

import com.akefirad.games.rooms.context.Room;

/**
 * A guild object helps player with some useful information.
 */
public interface Guide {
    /**
     * Returns the main room of the game (where the game is started)
     *
     * @return main room
     */
    Room mainRoom ();
}
