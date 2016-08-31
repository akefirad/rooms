package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

import static com.akefirad.games.rooms.context.Games.saveGame;
import static com.akefirad.games.rooms.context.Rooms.saveRoom;
import static com.akefirad.games.rooms.decision.Arguments.expandToMap;
import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

public class SaveGame implements Decision {
    public static final String NAME = "Save Game";

    private static final String FILE_PATH = "file.path";
    private static final String FILE_PATH_MSG = "Enter the path of the file: ";
    private static final String MESSAGE = "Game is saved.";

    private final Player player;

    public SaveGame (Player player) {
        assertNotNull(player, "Player");

        this.player = player;
    }

    @Override
    public String getName () {
        return NAME;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        String path = (String) ofNullable(expandToMap(arguments)
                .get(FILE_PATH))
                .orElseThrow(() -> new IllegalArgumentException("File path is null or not found!"));
        saveGame(saveRoom(player.getGuide().mainRoom()), path);
        return new Consequence(new GameMenu(player, true), MESSAGE);
    }

    @Override
    public List<Argument<?>> getArguments () {
        return singletonList(new Argument<>(FILE_PATH, FILE_PATH_MSG, string -> string));
    }
}
