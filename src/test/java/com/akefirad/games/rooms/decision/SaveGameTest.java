package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.impl.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static com.akefirad.games.rooms.util.TestUtils.PAUSED_GAME_PATH;
import static com.akefirad.games.rooms.context.Games.loadGame;
import static com.akefirad.games.rooms.context.Games.loadGameData;
import static com.akefirad.games.rooms.decision.impl.SaveGame.NAME;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SaveGameTest {
    private Decision decision;

    @Before
    public void setUp () throws IOException {
        Room room = loadGame(getClass().getResource(PAUSED_GAME_PATH).getFile());
        decision = new SaveGame(room.getPlayer());
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(NAME));
    }

    @Test
    public void testMake () throws Exception {
        String file = createTempFile("rad", "game").toAbsolutePath().toString();
        List<Argument<?>> arguments = decision.getArguments();
        arguments.iterator().next().setValue(file);
        GameMenu game = (GameMenu) decision.make(arguments).getContext();
        assertTrue(game.hasDecisions());
        assertThat(game.listDecisions(), hasSize(3));

        Map<String, String> expected = loadGameData(new ByteArrayInputStream(readAllBytes(
                get(getClass().getResource(PAUSED_GAME_PATH).getFile()))));
        Map<String, String> actual = loadGameData(new ByteArrayInputStream(readAllBytes(get(file))));
        assertThat(actual, equalTo(expected));
    }
}
