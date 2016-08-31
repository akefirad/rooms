package com.akefirad.games.rooms.context.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.PauseGame;
import com.akefirad.games.rooms.interaction.impl.Creature;
import com.akefirad.games.rooms.interaction.impl.Door;
import com.akefirad.games.rooms.player.Player;

import java.util.*;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class SimpleRoom implements Room {
    private final String name;
    private final List<Door> doors;
    private final List<Creature> creatures;
    private Player player;

    public SimpleRoom (String name) {
        assertNotNull(name, "Room name");
        this.name = name;
        this.doors = new ArrayList<>();
        this.creatures = new ArrayList<>();
    }

    @Override
    public String getName () {
        return name;
    }

    @Override
    public void connect (Room room) {
        assertNotNull(room, "Room");
        doors.add(new Door(room));
    }

    @Override
    public Player getPlayer () {
        return player;
    }

    @Override
    public void setPlayer (Player player) {
        this.player = player;
        if (player != null && player.getRoom() != this)
            player.setRoom(this);
    }

    @Override
    public List<Room> getRooms () {
        return unmodifiableList(doors.stream()
                .map(Door::getTo)
                .collect(toList()));
    }

    @Override
    public void addCreature (Creature creature) {
        assertNotNull(creature, "Creature");

        creatures.add(creature);
    }

    public List<Creature> getCreatures () {
        return unmodifiableList(creatures);
    }

    @Override
    public boolean hasDecisions () {
        return true;
    }

    @Override
    public List<Decision> listDecisions () {
        List<Decision> decisions = new ArrayList<>();
        decisions.add(new PauseGame(player));

        addFights(decisions);
        if (decisions.size() == 1) // You can do nothing if there's an enemy in the room;
            addDoors(decisions);

        return unmodifiableList(decisions);
    }

    @Override
    public boolean equals (Object that) {
        return this == that || that != null && getClass() == that.getClass() &&
                Objects.equals(name, ((SimpleRoom) that).name);
    }

    @Override
    public int hashCode () {
        return name.hashCode();
    }

    private void addDoors (List<Decision> decisions) {
        doors.forEach(door -> decisions.addAll(door.listDecisions(player)));
    }

    private void addFights (List<Decision> decisions) {
        creatures.forEach(creature -> decisions.addAll(creature.listDecisions(player)));
    }
}
