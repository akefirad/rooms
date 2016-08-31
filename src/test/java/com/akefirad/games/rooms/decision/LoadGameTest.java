package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.impl.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.akefirad.games.rooms.util.TestUtils.PAUSED_GAME_PATH;
import static com.akefirad.games.rooms.decision.impl.LoadGame.NAME;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class LoadGameTest {
    private Decision decision;

    @Before
    public void setUp () {
        decision = new LoadGame();
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(NAME));
    }

    @Test
    public void testMake () throws Exception {
        List<Argument<?>> arguments = decision.getArguments();
        arguments.iterator().next().setValue(getClass().getResource(PAUSED_GAME_PATH).getFile());
        GameMenu game = (GameMenu) decision.make(arguments).getContext();
        assertThat(game.listDecisions().get(0).getName(), equalTo(ExitGame.NAME));
        assertThat(game.listDecisions().get(1).getName(), equalTo(StartGame.NAME_RESUME));
        assertThat(game.listDecisions().get(2).getName(), equalTo(SaveGame.NAME));
    }
}
