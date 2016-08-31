package com.akefirad.games.rooms.context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import static com.akefirad.games.rooms.context.Rooms.loadRooms;
import static com.akefirad.games.rooms.util.ExceptionHandler.handleIOException;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toMap;

/**
 * A special class for loading and saving game as a whole.
 * Though it's implemented as a simple helper class with
 * static methods, it can easily be converted to an
 * injectable bean.
 */
public final class Games {
    private static final String DIVINE_COMEDY = "/divine-comedy.rooms";

    private Games () {
    }

    /**
     * Instantiate a new default game
     *
     * @return main room
     */
    public static Room newGame () {
        return handleIOException(() -> {
            Class<? extends Class> clazz = Games.class.getClass();
            try (InputStream stream = clazz.getResourceAsStream(DIVINE_COMEDY)) {
                return loadRooms(loadGameData(stream), false);
            }
        });
    }

    /**
     * Loads a previously saved game from a file
     *
     * @param file game file
     * @return current room
     */
    public static Room loadGame (String file) {
        return handleIOException(() ->
                loadRooms(loadGameData(file), true));
    }

    /**
     * Loads game data from a file into a map
     *
     * @param file game file
     * @return map containing the game data
     */
    public static Map<String, String> loadGameData (String file) {
        return handleIOException(() ->
                loadGameData(new ByteArrayInputStream(Files.readAllBytes(Paths.get(file)))));
    }

    /**
     * Loads game data from a stream into a map
     *
     * @param stream game stream
     * @return map containing the game data
     */
    public static Map<String, String> loadGameData (InputStream stream) {
        return handleIOException(() -> {
            Properties properties = new Properties();
            properties.load(stream);
            return toMapOfStrings(properties);
        });
    }

    /**
     * Saves a game into a file
     *
     * @param game game data
     * @param file game file
     */
    public static void saveGame (Map<String, String> game, String file) {
        handleIOException(() -> {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            saveGame(game, bytes);
            Files.write(Paths.get(file), bytes.toByteArray());
        });
    }

    /**
     * Saves a game into a stream
     *
     * @param game   game data
     * @param stream game stream
     */
    public static void saveGame (Map<String, String> game, OutputStream stream) {
        handleIOException(() -> {
            Properties properties = new Properties();
            properties.putAll(game);
            properties.store(stream, null);
        });
    }

    private static Map<String, String> toMapOfStrings (Properties properties) {
        return (properties == null) ? null : properties.entrySet().stream()
                .collect(toMap(e -> valueOf(e.getKey()), e -> valueOf(e.getValue())));
    }
}
