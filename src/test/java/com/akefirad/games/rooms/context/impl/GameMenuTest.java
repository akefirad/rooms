package com.akefirad.games.rooms.context.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.impl.*;
import org.junit.*;

import static com.akefirad.games.rooms.context.Rooms.newRoom;
import static com.akefirad.games.rooms.player.Players.newPlayer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GameMenuTest {
    private Room room;


    @Before
    public void setup () {
        room = newRoom("Test");
        room.setPlayer(newPlayer("Test", 0, room));
    }

    @Test
    public void testHasDecisions () throws Exception {
        GameMenu game = new GameMenu(room.getPlayer());
        assertTrue(game.hasDecisions());
    }

    @Test
    public void testListDecisionsOfNewGame () throws Exception {
        GameMenu game = new GameMenu(room.getPlayer());
        assertThat(game.listDecisions(), hasSize(3));
        assertThat(game.listDecisions().get(0).getName(), equalTo(ExitGame.NAME));
        assertThat(game.listDecisions().get(1).getName(), equalTo(StartGame.NAME_START));
        assertThat(game.listDecisions().get(2).getName(), equalTo(SaveGame.NAME));
    }

    @Test
    public void testListDecisionsOfPausedGame () throws Exception {
        GameMenu game = new GameMenu(room.getPlayer(), true);
        assertThat(game.listDecisions(), hasSize(3));
        assertThat(game.listDecisions().get(0).getName(), equalTo(ExitGame.NAME));
        assertThat(game.listDecisions().get(1).getName(), equalTo(StartGame.NAME_RESUME));
        assertThat(game.listDecisions().get(2).getName(), equalTo(SaveGame.NAME));
    }
}