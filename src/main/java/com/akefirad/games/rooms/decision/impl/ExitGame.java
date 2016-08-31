package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.context.Context;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;

import java.util.List;

import static com.akefirad.games.rooms.util.Constants.GOODBYE_MSG;
import static java.util.Collections.emptyList;

public class ExitGame implements Decision {
    public static final String NAME = "Exit Game";

    @Override
    public String getName () {
        return NAME;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        return new Consequence(new Context() {
            @Override
            public boolean hasDecisions () {
                return false;
            }

            @Override
            public List<Decision> listDecisions () {
                return emptyList();
            }
        }, GOODBYE_MSG);
    }
}
