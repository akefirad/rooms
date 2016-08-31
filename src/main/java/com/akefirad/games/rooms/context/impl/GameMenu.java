package com.akefirad.games.rooms.context.impl;

import com.akefirad.games.rooms.context.Context;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.*;
import com.akefirad.games.rooms.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class GameMenu implements Context {
    private final List<Decision> decisions;

    public GameMenu (Player player) {
        this(player, false);
    }

    public GameMenu (Player player, boolean paused) {
        assertNotNull(player, "Player");
        decisions = new ArrayList<>(asList(
                new ExitGame(),
                new StartGame(player, paused),
                new SaveGame(player)
        ));
    }

    @Override
    public boolean hasDecisions () {
        return decisions.size() > 0;
    }

    @Override
    public List<Decision> listDecisions () {
        return unmodifiableList(decisions);
    }

}
