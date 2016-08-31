package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;

public class PauseGame implements Decision {
    public static final String NAME = "Pause Game";

    private static final String MESSAGE = "The game is paused!";

    private final Player player;

    public PauseGame (Player player) {
        assertNotNull(player, "Room");

        this.player = player;
    }

    @Override
    public String getName () {
        return NAME;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        return new Consequence(new GameMenu(player, true), MESSAGE);
    }
}
