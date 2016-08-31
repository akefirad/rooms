package com.akefirad.games.rooms.context;

import com.akefirad.games.rooms.interaction.impl.Creature;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

/**
 * Room a special context in which interactable objects can exist.
 */
public interface Room extends Context {
    /**
     * Returns the name of the room
     *
     * @return room's name
     */
    String getName ();

    /**
     * Connects the given room to this room
     *
     * @param room connecting room
     */
    void connect (Room room);

    /**
     * Returns the current player in this room
     *
     * @return player in the room
     */
    Player getPlayer ();

    /**
     * Set the player in the room
     *
     * @param player player in the room
     */
    void setPlayer (Player player);

    /**
     * Returns the list of connected rooms to this room
     *
     * @return list of rooms, not-null
     */
    List<Room> getRooms ();

    /**
     * Add a creature to this room
     *
     * @param creature creature to be added to this room
     */
    void addCreature (Creature creature);

    /**
     * Returns creatures in the room
     *
     * @return list of creatures, not-null
     */
    List<Creature> getCreatures ();
}
