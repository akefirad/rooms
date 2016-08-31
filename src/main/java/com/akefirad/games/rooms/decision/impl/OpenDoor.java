package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.player.Player;
import com.akefirad.games.rooms.util.Constants;

import java.util.List;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.lang.String.format;

public class OpenDoor implements Decision {
    public static final String NAME = "Enter ";

    private static final String MESSAGE = "You are in %s room.";

    private final String name;
    private final Player player;
    private final Room to;

    public OpenDoor (Player player, Room to) {
        assertNotNull(player, "Player");
        assertNotNull(to, "Room");

        this.name = NAME + to.getName();
        this.player = player;
        this.to = to;
    }

    @Override
    public String getName () {
        return name;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        player.setRoom(to);
        return new Consequence(to, format(MESSAGE, to.getName()));
    }

    @Override
    public String[] getIcon () {
        return Constants.DOOR_ICON;
    }
}
