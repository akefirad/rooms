package com.akefirad.games.rooms.player;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.exception.PlayerNotFoundException;
import com.akefirad.games.rooms.exception.PlayerRoomNotFoundException;
import com.akefirad.games.rooms.player.impl.SimpleGuide;
import com.akefirad.games.rooms.player.impl.SimplePlayer;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.akefirad.games.rooms.context.Rooms.MAIN_ROOM_NAME;
import static com.akefirad.games.rooms.util.Asserts.assertMatch;
import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static com.akefirad.games.rooms.util.StringUtils.trimToEmpty;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.regex.Pattern.compile;

/**
 * A special class for working with Player objects.
 * Though it's implemented as a simple factory with
 * static methods, it can easily be converted to an
 * injectable bean.
 */
public final class Players {
    private static final Pattern NAME_PATTERN = compile("^\\w+$");
    private static final String PLAYER_NAME = "player.name";
    private static final String PLAYER_ROOM = "player.room";
    private static final String PLAYER_EXPERIENCE = "player.experience";

    private Players () {
    }

    /**
     * Instantiates a new Player object
     *
     * @param name       player name
     * @param experience player experience
     * @param main       main room
     * @return player object
     */
    public static Player newPlayer (String name, int experience, Room main) {
        name = trimToEmpty(name);
        assertMatch(NAME_PATTERN, name, "Player name");
        assertNotNull(main, "Main room");
        return new SimplePlayer(name, experience, new SimpleGuide(main));
    }

    /**
     * Loads a player form the give game data
     *
     * @param data  game data
     * @param rooms room objects
     * @return player object
     */
    public static Player loadPlayer (Map<String, String> data, Map<String, Room> rooms) {
        Room main = rooms.get(MAIN_ROOM_NAME);
        String room = loadPlayerRoom(data);
        String name = loadPlayerName(data);
        int experience = loadPlayerExperience(data);
        Player player = newPlayer(name, experience, main);
        player.setRoom(rooms.get(room));
        return player;
    }

    /**
     * Saves a player object into a map
     *
     * @param player player object
     * @return map containing player data
     */
    public static Map<String, String> savePlayer (Player player) {
        assertNotNull(player, "Player");
        Map<String, String> map = new HashMap<>();
        map.put(PLAYER_NAME, player.getName());
        map.put(PLAYER_ROOM, player.getRoom().getName());
        map.put(PLAYER_EXPERIENCE, valueOf(player.getExperience()));
        return map;
    }

    private static String loadPlayerRoom (Map<String, String> data) {
        return data.entrySet().stream()
                .filter(e -> e.getKey().equals(PLAYER_ROOM))
                .map(e -> new SimpleEntry<>(e.getKey(), e.getValue()))
                .findAny()
                .orElseThrow(() -> new PlayerRoomNotFoundException("No room found for player!"))
                .getValue();
    }

    private static int loadPlayerExperience (Map<String, String> game) {
        return game.entrySet().stream()
                .filter(e -> e.getKey().equals(PLAYER_EXPERIENCE))
                .mapToInt(integer -> parseInt(integer.getValue()))
                .findAny()
                .orElse(0);
    }

    private static String loadPlayerName (Map<String, String> game) {
        return game.entrySet().stream()
                .filter(e -> e.getKey().equals(PLAYER_NAME))
                .map(e -> new SimpleEntry<>(e.getKey(), e.getValue()))
                .findAny()
                .orElseThrow(() -> new PlayerNotFoundException("No player found!"))
                .getValue();
    }
}
