package com.akefirad.games.rooms.player.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.player.Guide;
import com.akefirad.games.rooms.player.Player;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;

public class SimplePlayer implements Player {
    private final Guide guide;

    private String name;
    private int experience;
    private Room room;

    public SimplePlayer (String name, int experience, Guide guide) {
        assertNotNull(name, "Player name");
        assertNotNull(guide, "Player guide");

        this.name = name;
        this.experience = experience;

        this.guide = guide;
    }

    @Override
    public String getName () {
        return name;
    }

    @Override
    public int getExperience () {
        return experience;
    }

    @Override
    public void setExperience (int experience) {
        this.experience = experience;
    }

    @Override
    public Room getRoom () {
        return room;
    }

    @Override
    public void setRoom (Room room) {
        assertNotNull(room, "Room");

        removePlayerFromRoom();
        this.room = room;
        setPlayerRoom(room);
    }

    @Override
    public Guide getGuide () {
        return guide;
    }

    private void setPlayerRoom (Room room) {
        if (room.getPlayer() != this)
            room.setPlayer(this);
    }

    private void removePlayerFromRoom () {
        if (this.room != null)
            this.room.setPlayer(null);
    }
}
