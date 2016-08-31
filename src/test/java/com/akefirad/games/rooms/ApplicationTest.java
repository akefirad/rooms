package com.akefirad.games.rooms;

import com.akefirad.games.rooms.context.impl.StartMenu;
import com.akefirad.games.rooms.util.TestConsole;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;

import static com.akefirad.games.rooms.util.TestUtils.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationTest {
    @Test
    public void testExitGame () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("0\n"); // Select ExitGame

        TestConsole console = new TestConsole(sb.toString());
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }

    @Test
    public void testNewGame () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n"); // Select NewGame
        sb.append("Rad\n"); // Enter player name
        sb.append("0\n"); // Select ExitGame

        TestConsole console = new TestConsole(sb.toString());
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }

    @Test
    public void testLoadGame () throws IOException {
        loadGameFile(PAUSED_GAME_PATH);
    }

    @Test
    public void testSaveGame () throws IOException {
        String load = getClass().getResource(PAUSED_GAME_PATH).getFile();
        String save = Files.createTempFile("rad", "game").toAbsolutePath().toString();

        StringBuilder sb = new StringBuilder();
        sb.append("2\n"); // Select LoadGame
        sb.append(load).append("\n"); // Enter file name
        sb.append("2\n"); // Select SaveGame
        sb.append(save).append("\n"); // Enter file name
        sb.append("0\n"); // Select ExitGame

        TestConsole console = new TestConsole(sb.toString());
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }

    @Test
    public void EOFConsoleException () throws IOException {
        TestConsole console = new TestConsole("");
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }

    @Test
    public void testNumberFormatException () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("foo\n"); // Select ExitGame
        sb.append("0\n"); // Select ExitGame

        TestConsole console = new TestConsole(sb.toString());
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }

    @Test
    public void testIllegalArgumentException () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n"); // Select NewGame
        sb.append("foo-bar\n"); // Enter player name
        sb.append("0\n"); // Select ExitGame

        TestConsole console = new TestConsole(sb.toString());
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }

    @Test
    public void testRoomNotFoundException () throws IOException {
        loadGameFile(NO_MAIN_GAME_PATH);
    }

    @Test
    public void testPlayerRoomNotFoundException () throws IOException {
        loadGameFile(NO_PLAYER_ROOM_PATH);
    }

    @Test
    public void testPlayerPlayerNotFoundException () throws IOException {
        loadGameFile(NO_PLAYER_PATH);
    }

    private void loadGameFile (String path) throws IOException {
        String file = getClass().getResource(path).getFile();
        StringBuilder sb = new StringBuilder();
        sb.append("2\n"); // Select LoadGame
        sb.append(file).append("\n"); // Enter file name
        sb.append("0\n"); // Select ExitGame

        TestConsole console = new TestConsole(sb.toString());
        new Application(console, new StartMenu()).run();

        assertThat(console.getReader().read(), is(-1));
    }
}
