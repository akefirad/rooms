package com.akefirad.games.rooms.decision.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.Consequence;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

import static com.akefirad.games.rooms.context.Games.newGame;
import static com.akefirad.games.rooms.decision.Arguments.expandToMap;
import static com.akefirad.games.rooms.player.Players.newPlayer;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

public class NewGame implements Decision {
    public static final String NAME = "New Game";

    private static final String PLAYER_NAME = "player.name";
    private static final String PLAYER_NAME_MSG =
            "Enter the name of player (only alphanumeric and underscore): ";
    private static final String MESSAGE = "OK, everything is ready. Good luck, %s!";

    @Override
    public String getName () {
        return NAME;
    }

    @Override
    public Consequence make (List<Argument<?>> arguments) {
        Room room = newGame();
        String name = (String) ofNullable(expandToMap(arguments)
                .get(PLAYER_NAME))
                .orElseThrow(() -> new IllegalArgumentException("Player name is null or not found!"));
        Player player = newPlayer(name, 0, room);
        room.setPlayer(player);
        player.setRoom(room);
        return new Consequence(new GameMenu(player), format(MESSAGE, player.getName()));
    }

    @Override
    public List<Argument<?>> getArguments () {
        return singletonList(new Argument<>(PLAYER_NAME, PLAYER_NAME_MSG, name -> name));
    }
}

