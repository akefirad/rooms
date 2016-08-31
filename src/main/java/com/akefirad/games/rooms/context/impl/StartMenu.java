package com.akefirad.games.rooms.context.impl;

import com.akefirad.games.rooms.context.Context;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class StartMenu implements Context {
    private final List<Decision> decisions;

    public StartMenu () {
        decisions = new ArrayList<>(asList(
                new ExitGame(),
                new NewGame(),
                new LoadGame()
        ));
    }

    @Override
    public boolean hasDecisions () {
        return true;
    }

    @Override
    public List<Decision> listDecisions () {
        return unmodifiableList(decisions);
    }
}
