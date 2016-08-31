package com.akefirad.games.rooms.context.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.impl.*;
import com.akefirad.games.rooms.interaction.impl.Creature;
import com.akefirad.games.rooms.player.Player;
import org.junit.Before;
import org.junit.Test;

import static com.akefirad.games.rooms.context.Rooms.newRoom;
import static com.akefirad.games.rooms.interaction.Creatures.newCreature;
import static com.akefirad.games.rooms.player.Players.newPlayer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SimpleRoomTest {
    private Room room;
    private Room another;
    private Creature creature;
    private Player player;

    @Before
    public void setUp () {
        room = newRoom("Main");
        another = newRoom("AnotherRoom");
        creature = newCreature("TestCreature");
        player = newPlayer("TestPlayer", 0, room);

        room.setPlayer(player);
        room.addCreature(creature);
        room.connect(another);
    }

    @Test
    public void testSetPlayer () {
        assertThat(room.getPlayer(), notNullValue());
        assertThat(player.getRoom(), is(room));
    }

    @Test
    public void testAddCreature () {
        assertThat(room.getCreatures(), hasSize(1));
        assertThat(room.getCreatures().get(0), is(creature));
    }

    @Test
    public void testConnect () {
        assertThat(room.getRooms(), hasSize(1));
        assertThat(room.getRooms().get(0), is(another));
    }

    @Test
    public void testDecisions () {
        assertThat(room.listDecisions(), hasSize(2));
        assertThat(room.listDecisions().get(0).getName(), equalTo(PauseGame.NAME));
        assertThat(room.listDecisions().get(1).getName(), equalTo(FightCreature.NAME + creature.getName()));

        creature.setAlive(false);

        assertThat(room.listDecisions(), hasSize(2));
        assertThat(room.listDecisions().get(0).getName(), equalTo(PauseGame.NAME));
        assertThat(room.listDecisions().get(1).getName(), equalTo(OpenDoor.NAME + another.getName()));
    }
}
