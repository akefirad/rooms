package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.impl.*;
import org.junit.Test;

import java.io.IOException;

import static com.akefirad.games.rooms.context.Games.loadGame;
import static com.akefirad.games.rooms.util.TestUtils.NEW_GAME_PATH;
import static com.akefirad.games.rooms.util.TestUtils.PAUSED_GAME_PATH;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StartGameTest {
    @Test
    public void testNameWithNewGame () throws IOException {
        Room room = loadGame(getClass().getResource(NEW_GAME_PATH).getFile());
        Decision decision = new StartGame(room.getPlayer(), false);
        assertThat(decision.getName(), equalTo(StartGame.NAME_START));
    }

    @Test
    public void testNameWithPausedGame () throws IOException {
        Room room = loadGame(getClass().getResource(PAUSED_GAME_PATH).getFile());
        Decision decision = new StartGame(room.getPlayer(), true);
        assertThat(decision.getName(), equalTo(StartGame.NAME_RESUME));
    }

    @Test
    public void testMakeWithNew () throws Exception {
        Room main = loadGame(getClass().getResource(NEW_GAME_PATH).getFile());
        assertThat(main.getRooms(), hasSize(1));

        Decision decision = new StartGame(main.getPlayer(), false);

        assertThat(decision.getArguments(), empty());

        Room room = (Room) decision.make(emptyList()).getContext();

        assertThat(room, is(main));
        assertTrue(room.hasDecisions());
        assertThat(room.listDecisions(), hasSize(2));
        assertThat(room.listDecisions().get(0).getName(), equalTo(PauseGame.NAME));
        assertThat(room.listDecisions().get(1).getName(), equalTo(OpenDoor.NAME + main.getRooms().get(0).getName()));
    }

    @Test
    public void testMakeWithPausedGame () throws Exception {
        Room main = loadGame(getClass().getResource(PAUSED_GAME_PATH).getFile());
        assertThat(main.getRooms(), hasSize(2));

        Decision decision = new StartGame(main.getPlayer(), true);

        assertThat(decision.getArguments(), empty());

        Room room = (Room) decision.make(emptyList()).getContext();

        assertThat(room, is(main));
        assertTrue(room.hasDecisions());
        assertThat(room.listDecisions(), hasSize(2));
        assertThat(room.listDecisions().get(0).getName(), equalTo(PauseGame.NAME));
        assertThat(room.listDecisions().get(1).getName(), startsWith(FightCreature.NAME));
    }
}
