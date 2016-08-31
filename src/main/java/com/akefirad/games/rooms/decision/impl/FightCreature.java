package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.interaction.impl.Creature;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.lang.String.format;

public class FightCreature implements Decision {
    public static final String NAME = "Fight ";

    private static final String MESSAGE =
            "Great! your experience is increased to %s. BTW you're in %s room.";

    private final Player player;
    private final Creature creature;

    public FightCreature (Player player, Creature creature) {
        assertNotNull(player, "Player");
        assertNotNull(creature, "Creature");

        this.player = player;
        this.creature = creature;
    }

    @Override
    public String getName () {
        return NAME + creature.getName();
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        player.setExperience(player.getExperience() + creature.getExperience());
        creature.setAlive(false);
        return new Consequence(player.getRoom(),
                format(MESSAGE, player.getExperience(), player.getRoom().getName()));
    }

    @Override
    public String[] getIcon () {
        return creature.getIcon();
    }
}
