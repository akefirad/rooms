package com.akefirad.games.rooms.player;

import com.akefirad.games.rooms.context.Room;

public interface Player {
    /**
     * Returns the name of the player
     *
     * @return player's name
     */
    String getName ();

    /**
     * Returns the experience of the player
     *
     * @return player's experience
     */
    int getExperience ();

    /**
     * Sets experience of the player
     *
     * @param experience player experience
     */
    void setExperience (int experience);

    /**
     * Returns the room in which the player currently is
     *
     * @return player's room
     */
    Room getRoom ();

    /**
     * Enter the player to the room
     *
     * @param room room to which the player enters
     */
    void setRoom (Room room);

    /**
     * Returns the guide of the player
     *
     * @return guide
     */
    Guide getGuide ();
}
