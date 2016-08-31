package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.impl.*;
import com.akefirad.games.rooms.interaction.impl.Creature;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.akefirad.games.rooms.context.Rooms.newRoom;
import static com.akefirad.games.rooms.interaction.Creatures.newCreature;
import static com.akefirad.games.rooms.player.Players.newPlayer;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FightCreatureTest {
    private Decision decision;
    private Room another;
    private Creature creature;

    @Before
    public void setUp () throws IOException {
        Room room = newRoom("Main");
        another = newRoom("AnotherRoom");
        creature = newCreature("TestCreature");

        room.setPlayer(newPlayer("TestPlayer", 0, room));
        room.addCreature(creature);
        room.connect(another);
        decision = new FightCreature(room.getPlayer(), creature);
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(FightCreature.NAME + creature.getName()));
    }

    @Test
    public void testMake () throws Exception {
        assertThat(decision.getArguments(), empty());
        Room room = (Room) decision.make(emptyList()).getContext();
        assertTrue(room.hasDecisions());
        assertThat(room.listDecisions(), hasSize(2));
        assertThat(room.listDecisions().get(0).getName(), equalTo(PauseGame.NAME));
        assertThat(room.listDecisions().get(1).getName(), equalTo(OpenDoor.NAME + another.getName()));
    }
}
