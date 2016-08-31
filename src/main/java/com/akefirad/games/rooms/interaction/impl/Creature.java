package com.akefirad.games.rooms.interaction.impl;

import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.FightCreature;
import com.akefirad.games.rooms.interaction.Interactable;
import com.akefirad.games.rooms.player.Player;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

import static com.akefirad.games.rooms.interaction.impl.CreatureType.*;
import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static com.akefirad.games.rooms.util.Constants.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

public class Creature implements Interactable {
    private static final Map<CreatureType, String[]> ICONS = of(
            new SimpleEntry<>(Wolf, WOLF_ICON),
            new SimpleEntry<>(Scorpion, SCORPION_ICON),
            new SimpleEntry<>(Man, MAN_ICON),
            new SimpleEntry<>(Unknown, UNKNOWN_ICON))
            .collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));

    private String name;
    private String type;
    private int experience;
    private boolean alive;

    public Creature (String name) {
        assertNotNull(name, "Creature name");

        this.name = name;
        this.type = Unknown.name();
        this.experience = 0;
        this.alive = true;
    }

    public String getName () {
        return name;
    }

    public int getExperience () {
        return experience;
    }

    public void setExperience (int experience) {
        this.experience = experience;
    }

    public boolean isAlive () {
        return alive;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        assertNotNull(type, "Creature type");

        this.type = type;
    }

    public void setAlive (boolean alive) {
        this.alive = alive;
    }

    @Override
    public boolean equals (Object that) {
        return this == that || that != null && getClass() == that.getClass() &&
                Objects.equals(name, ((Creature) that).name);
    }

    @Override
    public int hashCode () {
        return name.hashCode();
    }

    /**
     * Draw an icon for the decision.
     * Default implementation returns a question mark.
     *
     * @return decision's icon
     */
    public String[] getIcon () {
        return ICONS.get(CreatureType.of(type));
    }

    @Override
    public List<Decision> listDecisions (Player player) {
        return isAlive() ? singletonList(new FightCreature(player, this)) : emptyList();
    }
}
