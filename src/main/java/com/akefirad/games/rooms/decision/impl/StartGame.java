package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.lang.String.format;

public class StartGame implements Decision {
    public static final String NAME_START = "Start Game";
    public static final String NAME_RESUME = "Resume Game";

    private static final String MESSAGE = "Welcome to %s";

    private final boolean paused;
    private final Player player;

    public StartGame (Player player, boolean paused) {
        assertNotNull(player, "Player");
        assertNotNull(player.getRoom(), "Room");

        this.player = player;
        this.paused = paused;
    }

    @Override
    public String getName () {
        return paused ? NAME_RESUME : NAME_START;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        return new Consequence(player.getRoom(), format(MESSAGE, player.getRoom().getName()));
    }
}
