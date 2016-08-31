package com.akefirad.games.rooms.context;

import com.akefirad.games.rooms.context.impl.SimpleRoom;
import com.akefirad.games.rooms.exception.RoomNotFoundException;
import com.akefirad.games.rooms.interaction.impl.Creature;
import com.akefirad.games.rooms.player.Players;

import java.util.*;
import java.util.regex.Pattern;

import static com.akefirad.games.rooms.player.Players.loadPlayer;
import static com.akefirad.games.rooms.util.Asserts.assertMatch;
import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static com.akefirad.games.rooms.util.StringUtils.trimToEmpty;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.compile;
import static java.util.stream.IntStream.range;

/**
 * A special class for working with Room objects.
 * Though it's implemented as a simple factory with
 * static methods, it can easily be converted to an
 * injectable bean.
 */
public final class Rooms {
    public static final String MAIN_ROOM_NAME = "Main";

    private static final Pattern NAME_PATTERN = compile("^\\w+$");
    private static final String ROOM = "room.";
    private static final String ROOM_MAIN = "room.main";
    private static final String ROOM_DOOR = ".door.";
    private static final String ROOM_CREATURE = ".creature.";
    private static final String CREATURE_NAME = ".creature.name.";
    private static final String CREATURE_TYPE = ".type";
    private static final String CREATURE_EXPERIENCE = ".experience";
    private static final String CREATURE_ALIVE = ".alive";

    private Rooms () {
    }

    /**
     * Instantiate a new Room object
     *
     * @param name name of room
     * @return a Room object
     */
    public static Room newRoom (String name) {
        name = trimToEmpty(name);
        assertMatch(NAME_PATTERN, name, "Room name");

        return new SimpleRoom(name);
    }

    /**
     * Loads the game (rooms and other entities) and returns a room
     *
     * @param data  data containing the game information
     * @param saved true if it's going to load a saved game (which has player)
     * @return main room or the room previously saved
     */
    public static Room loadRooms (Map<String, String> data, boolean saved) {
        Room main = loadMainRoom(data);
        Map<String, Room> rooms = new HashMap<>();
        rooms.put(main.getName(), main);
        loadConnectedRooms(data, rooms);
        loadCreatures(data, rooms);
        return saved ? loadPlayer(data, rooms).getRoom() : rooms.get(MAIN_ROOM_NAME);
    }

    /**
     * Saves the game (rooms and other entities) into a map
     *
     * @param main main room
     * @return a map containing the game data
     */
    public static Map<String, String> saveRoom (Room main) {
        assertNotNull(main, "Room");

        Map<String, String> data = new HashMap<>();
        data.put(ROOM_MAIN, main.getName());
        saveRooms(main, new HashSet<>(), data);
        return data;
    }

    private static Room loadMainRoom (Map<String, String> game) {
        return newRoom(ofNullable(game.get(ROOM_MAIN))
                .orElseThrow(() -> new RoomNotFoundException("Main room not found!")));
    }

    private static void loadConnectedRooms (Map<String, String> game, Map<String, Room> rooms) {
        game.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().startsWith(ROOM))
                .filter(entry -> entry.getKey().toLowerCase().contains(ROOM_DOOR))
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(entry -> {
                    // Pattern: room.<some-room>.connect
                    String[] parts = entry.getKey().split("\\.");
                    if (parts.length == 4) {
                        String room = parts[1];
                        if (!rooms.containsKey(room))
                            rooms.put(room, newRoom(room));
                        if (!rooms.containsKey(entry.getValue()))
                            rooms.put(entry.getValue(), newRoom(entry.getValue()));
                        rooms.get(room).connect(rooms.get(entry.getValue()));
                    }
                });
    }

    private static void loadCreatures (Map<String, String> data, Map<String, Room> rooms) {
        rooms.entrySet()
                .forEach(room -> {
                    String roomPrefix = ROOM + room.getKey() + CREATURE_NAME;
                    data.entrySet().stream()
                            .filter(entry -> entry.getKey().startsWith(roomPrefix))
                            .forEach(entry ->
                                    room.getValue().addCreature(new Creature(entry.getValue())));
                });
        loadCreaturesProperties(data, rooms);
    }

    private static void loadCreaturesProperties (Map<String, String> data, Map<String, Room> rooms) {
        rooms.entrySet().forEach(room -> {
            String roomPrefix = ROOM + room.getKey() + ROOM_CREATURE;
            room.getValue().getCreatures()
                    .forEach(creature -> {
                        String creaturePrefix = roomPrefix + creature.getName();
                        loadCreatureType(data, creature, creaturePrefix);
                        loadCreatureExperience(data, creature, creaturePrefix);
                        loadCreatureAlive(data, creature, creaturePrefix);
                    });
        });
    }

    private static void loadCreatureAlive (Map<String, String> data, Creature creature, String creaturePrefix) {
        String creatureAlive = creaturePrefix + CREATURE_ALIVE;
        if (data.containsKey(creatureAlive))
            creature.setAlive(parseBoolean(data.get(creatureAlive)));
    }

    private static void loadCreatureExperience (Map<String, String> data, Creature creature, String creaturePrefix) {
        String creatureExperience = creaturePrefix + CREATURE_EXPERIENCE;
        if (data.containsKey(creatureExperience))
            creature.setExperience(parseInt(data.get(creatureExperience)));
    }

    private static void loadCreatureType (Map<String, String> data, Creature creature, String creaturePrefix) {
        String creatureType = creaturePrefix + CREATURE_TYPE;
        if (data.containsKey(creatureType))
            creature.setType(data.get(creatureType));
    }

    private static void saveRooms (Room room, Set<Room> visited, Map<String, String> data) {
        assertNotNull(room, "Room");
        assertNotNull(data, "Rooms");

        visited.add(room);
        savePlayer(room, data);
        saveCreatures(room, data);
        saveConnectedRooms(room, visited, data);
    }

    private static void savePlayer (Room room, Map<String, String> data) {
        if (room.getPlayer() != null)
            data.putAll(Players.savePlayer(room.getPlayer()));
    }

    private static void saveCreatures (Room room, Map<String, String> data) {
        List<Creature> creatures = room.getCreatures();
        String roomPrefix = ROOM + room.getName();
        int size = creatures.size();
        range(0, size)
                .forEach(idx -> {
                    Creature creature = creatures.get(idx);
                    int creatureId = size - idx - 1;
                    data.put(roomPrefix + CREATURE_NAME + creatureId, creature.getName());
                    String creaturePrefix = roomPrefix + ROOM_CREATURE + creature.getName();
                    data.put(creaturePrefix + CREATURE_TYPE, creature.getType());
                    data.put(creaturePrefix + CREATURE_EXPERIENCE, valueOf(creature.getExperience()));
                    data.put(creaturePrefix + CREATURE_ALIVE, valueOf(creature.isAlive()));
                });
    }

    private static void saveConnectedRooms (Room room, Set<Room> visited, Map<String, String> data) {
        List<Room> others = room.getRooms();
        range(0, others.size())
                .forEach(idx -> {
                    Room other = others.get(idx);
                    String label = ROOM + room.getName() + ROOM_DOOR + idx;
                    data.put(label, other.getName());
                    if (!visited.contains(other))
                        saveRooms(other, visited, data);
                });
    }
}
