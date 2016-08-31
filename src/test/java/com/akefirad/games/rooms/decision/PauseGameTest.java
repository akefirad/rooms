package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.impl.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.akefirad.games.rooms.util.TestUtils.PAUSED_GAME_PATH;
import static com.akefirad.games.rooms.context.Games.loadGame;
import static com.akefirad.games.rooms.decision.impl.PauseGame.NAME;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PauseGameTest {
    private Decision decision;

    @Before
    public void setUp () throws IOException {
        Room room = loadGame(getClass().getResource(PAUSED_GAME_PATH).getFile());
        decision = new PauseGame(room.getPlayer());
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(NAME));
    }

    @Test
    public void testMake () throws Exception {
        assertThat(decision.getArguments(), empty());
        GameMenu game = (GameMenu) decision.make(emptyList()).getContext();
        assertTrue(game.hasDecisions());
        assertThat(game.listDecisions(), hasSize(3));
        assertThat(game.listDecisions().get(0).getName(), equalTo(ExitGame.NAME));
        assertThat(game.listDecisions().get(1).getName(), equalTo(StartGame.NAME_RESUME));
        assertThat(game.listDecisions().get(2).getName(), equalTo(SaveGame.NAME));
    }
}
