package com.akefirad.games.rooms.interaction;

import com.akefirad.games.rooms.interaction.impl.Creature;
import com.akefirad.games.rooms.util.Constants;
import org.junit.Test;

import static com.akefirad.games.rooms.interaction.Creatures.newCreature;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CreatureTest {
    @Test
    public void testUnknownType () {
        Creature creature = newCreature("TestCreature");
        creature.setType("foo-bar");

        assertThat(creature.getIcon(), is(Constants.UNKNOWN_ICON));
    }
}
