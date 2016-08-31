package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.impl.*;
import com.akefirad.games.rooms.player.Player;
import org.junit.Before;
import org.junit.Test;

import static com.akefirad.games.rooms.context.Rooms.newRoom;
import static com.akefirad.games.rooms.player.Players.newPlayer;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OpenDoorTest {
    private Room room;
    private Room another;
    private Decision decision;

    @Before
    public void setUp () {
        room = newRoom("Main");
        another = newRoom("AnotherRoom");
        Player player = newPlayer("TestPlayer", 0, room);

        room.setPlayer(player);
        room.connect(another);
        another.connect(room);
        decision = new OpenDoor(player, another);
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(OpenDoor.NAME + another.getName()));
    }

    @Test
    public void testMake () throws Exception {
        assertThat(decision.getArguments(), empty());
        Room room = (Room) decision.make(emptyList()).getContext();
        assertThat(room, is(another));
        assertTrue(room.hasDecisions());
        assertThat(room.listDecisions(), hasSize(2));
        assertThat(room.listDecisions().get(0).getName(), equalTo(PauseGame.NAME));
        assertThat(room.listDecisions().get(1).getName(), equalTo(OpenDoor.NAME + this.room.getName()));
    }
}
