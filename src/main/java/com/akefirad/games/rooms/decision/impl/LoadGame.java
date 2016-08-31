package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;

import java.util.List;

import static com.akefirad.games.rooms.context.Games.loadGame;
import static com.akefirad.games.rooms.decision.Arguments.expandToMap;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

public class LoadGame implements Decision {
    public static final String NAME = "Load Game";

    private static final String FILE_PATH = "file.path";
    private static final String FILE_PATH_MSG = "Enter the path of the file: ";
    private static final String MESSAGE = "Game is loaded! Ready to start?";

    @Override
    public String getName () {
        return NAME;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        String path = (String) ofNullable(expandToMap(arguments)
                .get(FILE_PATH))
                .orElseThrow(() -> new IllegalArgumentException("File path is null or not found!"));
        Room room = loadGame(path);
        return new Consequence(new GameMenu(room.getPlayer(), true), MESSAGE);
    }

    @Override
    public List<Argument<?>> getArguments () {
        return singletonList(new Argument<>(FILE_PATH, FILE_PATH_MSG, string -> string));
    }
}

