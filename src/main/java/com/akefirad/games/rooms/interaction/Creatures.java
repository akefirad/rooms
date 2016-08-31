package com.akefirad.games.rooms.interaction;

import com.akefirad.games.rooms.interaction.impl.Creature;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;

public final class Creatures {
    private Creatures () {
    }

    public static Creature newCreature (String name) {
        assertNotNull(name, "Creature name");
        return new Creature(name);
    }
}
